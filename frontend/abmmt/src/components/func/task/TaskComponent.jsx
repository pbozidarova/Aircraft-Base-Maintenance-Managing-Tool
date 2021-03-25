import React, { Component } from 'react'
import BackendService from '../../../api/CommonAPI.js'
import {TASKS_HEADER_DATA, TASKS_BOOLEAN_FIELDS, TASKS_DISABLED_FIELDS, MESSAGES} from '../../../Constanst.js'
import Utils from '../../Utils.js'
import { withRouter } from 'react-router';

import DataComponent from '../DataComponent'
import EditGlobalComponent from '../EditGlobalComponent'
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
            errors: {},     
        }

        this.refreshTasks = this.refreshTasks.bind(this)
        this.selectTask = this.selectTask.bind(this)
        this.handleChange = this.handleChange.bind(this)
        this.handleInfo = this.handleInfo.bind(this);

        this.validateAndSubmit = this.validateAndSubmit.bind(this);
        this.submitUpdate = this.submitUpdate.bind(this)
        this.submitCreate = this.submitCreate.bind(this)
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
                console.log(response.data)
            }
        ).catch(e => {
            this.props.handleInfo({error : e.response.data.message});
        });
    }
   
    selectTask(task) {      
        this.setState({selected: task})
    }
    handleChange(event){
        let eName = event.target.name
        let eValue = event.target.value
        let eCheked = event.target.checked
        
        let updatePair = eCheked ? {[eName] : eCheked }: {[eName]: eValue}
        
        this.setState(
            {   ...this.state,
                selected: {...this.state.selected, ...updatePair}
            })
        
    }

    validateAndSubmit(submit){
        const { selected } = this.state;
        
        this.setState({ errors: 
             { 
                // companyNum: /^[N]\d{5}$/.test(selectedUser.companyNum) ? '' : "Follow the pattern N plus 5 digits!" ,
                // firstName:  selectedUser.firstName != 'First Name' && selectedUser.firstName.length > 2 ? '' : "The first name must contain more than 2 digits!" ,
                // lastName:  selectedUser.lastName != 'Last Name' && selectedUser.lastName.length > 2 ? '' : "The last name must contain more than 2 digits!",
                // email: /^\S+@\S+$/.test(selectedUser.email)  ? '' : "Please provide a valid email!",
                // facility: this.props.selectedUser.facility.length > 2 ? '' : "Please select a facility!",
                
                // authority: this.props.selectedUser.roles.length > 0 ? '' : "At least one authority must be checked!",
                // role: this.props.selectedUser.roles.length > 0 ? '' : "At least one role must be checked!",
  
             }
        }, () => submit(selected.taskNum, selected) );
    
      }
  
      submitUpdate(taskNum, selected){
        if(Utils.formIsValid(this.state.errors)) {
            BackendService.updateOne("tasks", taskNum, selected)
                .then((r) => {                        
                    this.refreshTasks()
                    this.props.handleInfo({success : MESSAGES.successUpdated});
                }).catch(e => {
                    this.props.handleInfo({error : e.response.data.message});
                    // this.props.handleInfo({error : e});
                })
            console.log('submit Update')
        }
      }
  
      submitCreate(taskNum, selected){  
        if(Utils.formIsValid(this.state.errors)) {
            BackendService.createOne("tasks", taskNum, selected)
                .then(() => {                        
                    this.refreshTasks()
                    this.props.handleInfo({success : MESSAGES.successCreated});
                }
                ).catch(e => {
                    this.props.handleInfo({error : e.response.data.message});
                })
  
            console.log('submit Create')
        }
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
                    <EditGlobalComponent
                        selected={this.state.selected} 
                        selectedId={this.state.selected.taskNum}
                        handleChange={this.handleChange} 
                        handleInfo={this.handleInfo}
                        labels = {TASKS_HEADER_DATA} 
                        booleanFields = {TASKS_BOOLEAN_FIELDS}
                        disabledFields={TASKS_DISABLED_FIELDS}
                        errors={this.state.errors}
                        validateAndSubmit={this.validateAndSubmit}
                        submitUpdate={this.submitUpdate}
                        submitCreate={this.submitCreate}
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