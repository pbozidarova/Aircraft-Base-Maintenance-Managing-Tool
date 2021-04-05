import React, { Component } from 'react'
import BackendService from '../../api/CommonAPI.js'
import {TASKS_HEADER_DATA, TASKS_BOOLEAN_FIELDS, TASK_EDIT_FIELDS, MESSAGES} from '../../Constanst.js'
import Utils from '../Utils.js'
import { withRouter } from 'react-router';

import DataComponent from './DataComponent'
import EditGlobalComponent from './EditGlobalComponent'
import CircularProgress from '@material-ui/core/CircularProgress';

import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import clsx from 'clsx';

import { styles } from '../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';

import PropTypes from 'prop-types';


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
        let key = 'taskViewDtoList'
        let shouldFetchPartialData = this.props.location.fetchDataFromURL
        
        Utils.infoMessage(this.props.handleInfo, MESSAGES.pleaseWait);

        //Check if the user wants to render all the tasks of it is coming from a HATEOAS link and requires partial fetch
        shouldFetchPartialData ? this.partialFetch(shouldFetchPartialData.href, shouldFetchPartialData.title, key) 
                               : this.fetchAll("tasks", key)
        
    }
    partialFetch(hateoasUrl, title, key){
        BackendService.fetchDataFrom(hateoasUrl)
        .then(
            response => {
                console.log(response)
                this.setState({
                    loading : false, 
                    tasks : response.data._embedded[key]
                }, () => Utils.successMessage(this.props.handleInfo, title));
            }
        ).catch(e => Utils.errorMessage(e, this.props.handleInfo, title))
    }

    fetchAll(urlParam, key){
        BackendService.getAll(urlParam)
        .then(
            response => {
                this.setState({
                    loading : false, 
                    tasks : response.data._embedded[key]
                }, () => Utils.infoMessage(this.props.handleInfo, MESSAGES.allTasks + MESSAGES.initialAdvice));
            }
        ).catch(e => Utils.errorMessage(e, this.props.handleInfo ));

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
                taskNum: selected.taskNum.length > 5 ? '' : "The task number length must be more than 5 symbols." ,
                code: selected.code.length == 3 ? '' : "The functional code length must equal 3 symbols." ,
                description: selected.description.length > 10 ? '' : "The description length must be more than 10 symbols." ,
             }
        }, () => submit(selected.taskNum, selected) );
    
      }
  
      submitUpdate(taskNum, selected){
        if(Utils.formIsValid(this.state.errors)) {
            BackendService.updateOne("tasks", taskNum, selected)
                .then((r) => {                        
                    this.refreshTasks()
                    Utils.successMessage(this.props.handleInfo, MESSAGES.successUpdated)
                    
                }).catch(e => {
                    Utils.errorMessage(e, this.props.handleInfo )
                })
        
        }
      }
  
      submitCreate(taskNum, selected){  
        if(Utils.formIsValid(this.state.errors)) {
            BackendService.createOne("tasks", taskNum, selected)
                .then(() => {                        
                    this.refreshTasks()
                    Utils.successMessage(this.props.handleInfo, MESSAGES.successCreated)
                }
                ).catch(e => {
                    Utils.errorMessage(e, this.props.handleInfo )
                })
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
                        editFields={TASK_EDIT_FIELDS}
                        errors={this.state.errors}
                        feedback={MESSAGES.taskEditInfo}
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