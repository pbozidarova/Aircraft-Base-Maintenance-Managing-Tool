import React, { Component } from 'react'
import BackendService from '../../../api/CommonAPI.js'
import {TASKS_HEADER_DATA, MESSAGES} from '../../../Constanst.js'

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
            infoPanel : {
                info: MESSAGES.initialLoad,
                success: MESSAGES.initialLoad,
                error: MESSAGES.initialLoad,
            },
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
        // let username = AuthenticationService.isUserLoggedIn();
        this.refreshTasks();
        this.createEmptySelect();

    }

    refreshTasks(){
        BackendService.getAll('tasks')
            .then(
                response => {
                    this.setState({
                        loading : false, 
                        tasks : response.data
                    });
                }
            );
            
    }
    createEmptySelect(){
        let emptyUser = Object.keys(TASKS_HEADER_DATA)
                            .reduce((acc, curr) =>  acc = {...acc, [curr] : ' '}, {} );
    
        this.selectTask(emptyUser);
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
              <Grid item xs={12}>
                <Paper className={classes.paper}>
                    <form className={classes.root} noValidate autoComplete="off">
                        <div>
                            {this.state.infoPanel.info}
                            {this.state.infoPanel.success}
                            {this.state.infoPanel.error}                            
                        </div>
                    </form>
                </Paper>
              </Grid>

              <Grid item xs={12} md={6} lg={8}>
                <Paper className={fixedHeightPaper}>
                    
                    { this.state.loading && <CircularProgress color="secondary"/> }
                    <DataComponent 
                        tableRows={this.state.tasks} 
                        tableHeader={TASKS_HEADER_DATA}
                        selected={this.state.selected}
                        selectRow={this.selectTask} 
                    />

                </Paper>
              </Grid>

              <Grid item xs={12} md={6} lg={4}>
                <Paper className={fixedHeightPaper}>
                  {this.state.selected.companyNum && 
                    <EditTask
                        selectedUser={this.state.selected} 
                        handleChange={this.handleChange} 
                        handleAuthorityRoleChange={this.handleAuthorityRoleChange} 
                        handleInfo={this.handleInfo}
                        refreshUsers={this.refreshUsers} 
                        labels = {TASKS_HEADER_DATA} 

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
  
  export default withStyles(styles)(TaskComponent);