import React, { Component } from 'react'
import BackendService from '../../api/CommonAPI.js'
import {TASKS_HEADER_DATA, TASKS_BOOLEAN_FIELDS, TASK_EDIT_FIELDS, MESSAGES} from '../../Constanst.js'
import Utils from '../Utils.js'
import ComponentsStateService from '../ComponentsStateService.js'
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
            selectedId: '',
            loading: true,       
            errors: {},     
        }

        ComponentsStateService.refreshData = ComponentsStateService.refreshData.bind(this)
        this.setState = this.setState.bind(this)
        
        this.refreshTasks = this.refreshTasks.bind(this)

        this.selectTask = this.selectTask.bind(this)
        this.handleChange = this.handleChange.bind(this)
        this.handleAutocompleteChange = this.handleAutocompleteChange.bind(this)

        this.validateAndSubmit = this.validateAndSubmit.bind(this);
        
    }
    
    componentDidMount(){
        this.refreshTasks();

        this.selectTask(Utils.emptyObj(TASKS_HEADER_DATA))
    }
   
    refreshTasks(){
        let keyState = 'tasks'
        let keyResponse = 'taskViewDtoList'
        let shouldFetchPartialData = this.props.location.fetchDataFromURL

        ComponentsStateService.refreshData( keyState, 
                                            keyResponse, 
                                            ComponentsStateService.fetchAll, 
                                            ComponentsStateService.partialFetch, 
                                            shouldFetchPartialData, 
                                            this.setState,
                                            this.props.handleInfo);
    }

    selectTask(task, selectedId) {      
        this.setState({...this.state, selected: task, selectedId})
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

    handleAutocompleteChange(event, value, key){
        this.setState(
            {   ...this.state,
                selected: {...this.state.selected, [key] : value}
            }) 
      }

    validateAndSubmit(submit, refreshData){
        const { selected } = this.state;
        
        this.setState({ errors: 
             { 
                taskNum: selected.taskNum.length > 5 ? '' : "The task number length must be more than 5 symbols." ,
                code: selected.code.length == 3 ? '' : "The functional code length must equal 3 symbols." ,
                description: selected.description.length > 10 ? '' : "The description length must be more than 10 symbols." ,
             }
        }, () => submit(this.state.errors, "tasks", selected.taskNum, selected, refreshData, this.props.handleInfo) );
    
      }
  
    render(){
        const { classes, handleInfo } = this.props;
        const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

        return(
                           
            <Grid container spacing={3}>
              
              <Grid item xs={12} md={6} lg={8}>
                <Paper className={fixedHeightPaper}>
                    
                    { this.state.loading && <CircularProgress color="secondary"/> }
                    <DataComponent 
                        tableRows={this.state.tasks} 
                        tableHeader={TASKS_HEADER_DATA}
                        selectedId={this.state.selectedId}
                        selectRow={this.selectTask} 
                    />
                </Paper>
               </Grid>

              <Grid item xs={12} md={6} lg={4}>
                <Paper className={fixedHeightPaper}>
                  {this.state.selected.taskNum && 
                    <EditGlobalComponent
                        selected={this.state.selected} 
                        selectedId={this.state.selectedId}
                        handleChange={this.handleChange} 
                        handleAutocompleteChange={this.handleAutocompleteChange}
                        handleInfo={handleInfo}
                        labels = {TASKS_HEADER_DATA} 
                        booleanFields = {TASKS_BOOLEAN_FIELDS}
                        editFields={TASK_EDIT_FIELDS}
                        errors={this.state.errors}
                        feedback={MESSAGES.taskEditInfo}
                        validateAndSubmit={this.validateAndSubmit}
                        refreshData={this.refreshTasks}
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