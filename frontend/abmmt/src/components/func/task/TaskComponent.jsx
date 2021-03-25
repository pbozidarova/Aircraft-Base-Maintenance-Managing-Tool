import React, { Component } from 'react'
import BackendService from '../../../api/CommonAPI.js'
import {TASKS_HEADER_DATA, TASKS_BOOLEAN_FIELDS, MESSAGES} from '../../../Constanst.js'
import Utils from '../../Utils.js'
import { withRouter } from 'react-router';

import DataComponent from '../DataComponent'
import EditTask from './EditTask'
import CircularProgress from '@material-ui/core/CircularProgress';

import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import clsx from 'clsx';

import { styles } from '../../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';

import PropTypes from 'prop-types';


import {Button, Input } from '@material-ui/core'


class TaskComponent extends Component{
    constructor(props){
        super(props)

        this.state = {
            tasks : [],
            selected: {},
            loading: true,            
        }

        this.refreshTasks = this.refreshTasks.bind(this)
        this.selectTask = this.selectTask.bind(this)
        this.handleChange = this.handleChange.bind(this)
        this.handleInfo = this.handleInfo.bind(this);
    }
    
    componentDidMount(){
        this.refreshTasks();
        this.selectTask(Utils.emptyObj(TASKS_HEADER_DATA))
    }

    refreshTasks(){
        
        this.props.location.fetchDataFromURL != null
                ?   this.partialFetch(this.props.location.fetchDataFromURL.href) 
                :   this.fetchAll("tasks");
        
    }
    partialFetch(hateoasUrl){
        BackendService.fetchDataFrom(hateoasUrl)
        .then(
            response => {
                console.log(response)
                this.setState({
                    loading : false, 
                    tasks : response.data._embedded.taskViewDtoList
                }, () => this.props.handleInfo({success : MESSAGES.successLoaded})
                );
                
            }
        ).catch(e => {
            this.props.handleInfo({error : e.response.data.message});
        })
    }

    fetchAll(urlParam){
        BackendService.getAll(urlParam)
        .then(
            response => {
                this.setState({
                    loading : false, 
                    tasks : response.data
                }, () => this.props.handleInfo({success : MESSAGES.successLoaded})
                );
            }
        ).catch(e => {
            this.props.handleInfo({error : e.response.data.message});
        });
    }
   
    selectTask(task) {      
        this.setState({selected: task})
    }
    handleChange(event){
        this.setState(
            {   ...this.state,
                selected: {...this.state.selected, [event.target.name] : event.target.value}
            }
        , console.log(this.state))
        
    }

    handleInfo(msg){
        this.setState({...this.state, infoPanel : msg})
    }

    render(){
        const { classes } = this.props;
        const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

        return(
                           
            <Grid container spacing={3}>
              
              <Grid item xs={12} md={6} lg={8}>
                <Paper className={fixedHeightPaper}>
                    
                    { this.state.loading && <CircularProgress color="secondary"/> }
                    <DataComponent 
                        tableRows={this.state.tasks} 
                        tableHeader={TASKS_HEADER_DATA}
                        selectedId={this.state.selected.taskNum}
                        selectRow={this.selectTask} 
                    />
                </Paper>
               </Grid>

              <Grid item xs={12} md={6} lg={4}>
                <Paper className={fixedHeightPaper}>
                  {this.state.selected.taskNum && 
                    <EditTask
                        selectedTask={this.state.selected} 
                        handleChange={this.handleChange} 
                        handleInfo={this.handleInfo}
                        refreshTasks={this.refreshTasks} 
                        labels = {TASKS_HEADER_DATA} 
                        booleanFields = {TASKS_BOOLEAN_FIELDS}
                    />
                  }
                </Paper>
              </Grid>
            </Grid>
     )
    }

}

TaskComponent.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(withRouter(TaskComponent));