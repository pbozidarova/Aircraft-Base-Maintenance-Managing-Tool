import React, { Component } from 'react'
import BackendService from '../../api/CommonAPI.js'
import {NOTIFICATIONS_HEADER_DATA, NOTIFICATIONS_BOOLEAN_FIELDS, NOTIFICATION_EDIT_FIELDS, MESSAGES} from '../../Constanst.js'
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


class NotificationComponent extends Component {

    constructor(props){
        super(props)

        this.state = {
            notifications : [],
            selected: {},
            selectedId: '',
            loading: true,       
            errors: {},     
        }

        ComponentsStateService.refreshData = ComponentsStateService.refreshData.bind(this)
        this.setState = this.setState.bind(this)

        this.refreshNotifications = this.refreshNotifications.bind(this)
        this.selectNotification = this.selectNotification.bind(this)
        this.handleChange = this.handleChange.bind(this)
        this.handleAutocompleteChange = this.handleAutocompleteChange.bind(this);

        this.validateAndSubmit = this.validateAndSubmit.bind(this);
        // this.submitUpdate = this.submitUpdate.bind(this)
        // this.submitCreate = this.submitCreate.bind(this)
    }
    
    componentDidMount(){
       this.refreshNotifications();

        this.selectNotification(Utils.emptyObj(NOTIFICATIONS_HEADER_DATA))
    }
   
    refreshNotifications(){
 
        let keyState = 'notifications'
        let keyResponse = 'notificationViewDtoList'
        let shouldFetchPartialData = this.props.location.fetchDataFromURL

        ComponentsStateService.refreshData( keyState, 
                                            keyResponse, 
                                            ComponentsStateService.fetchAll, 
                                            ComponentsStateService.partialFetch, 
                                            shouldFetchPartialData, 
                                            this.setState,
                                            this.props.handleInfo);
    }

    selectNotification(notification, selectedId) {      
        this.setState({...this.state, selected: notification, selectedId})
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
        const { selected, classification } = this.state;
        // let notificationNum = selected.notificationNum && "newNotification"
        selected.notificationNum = selected.notificationNum.length > 2 ? selected.notificationNum : "newNotification"
        selected.author = selected.author.length > 2 ? selected.author : "newNotification"
        
        this.setState({ errors: 
             {
                 
                maintenanceNum:  /^\d{7}$/.test(selected.maintenanceNum) ? '' : "The maintenance number length must equal 7 numbers." ,
                taskNum: selected.taskNum.length > 5 ? '' : "Please select a task number!" ,
                status: selected.status.length > 5 ? '' : "Please select status!" ,
                classification: (selected.status == 'CLOSED' && selected.classification) || 
                                (selected.status != 'CLOSED' && selected.classification == null) 
                                ? '' : "You are not allowed to close a notification without classifying it! In order to classify the notification, you need to first close it!" ,
                dueDate: selected.dueDate.length > 2  ? '' : "The due date is mandatory!",
  
             }
        }, () => submit(this.state.errors, "notifications", selected.notificationNum, selected, refreshData, this.props.handleInfo) );
    
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
                        tableRows={this.state.notifications} 
                        tableHeader={NOTIFICATIONS_HEADER_DATA}
                        selectedId={this.state.selectedId}
                        selectRow={this.selectNotification} 
                        handleInfo={this.props.handleInfo}
                    />
                </Paper>
               </Grid>

              <Grid item xs={12} md={6} lg={4}>
                <Paper className={fixedHeightPaper}>
                  {this.state.selected.notificationNum && 
                    <EditGlobalComponent
                        selected={this.state.selected} 
                        selectedId={this.state.selectedId}
                        handleChange={this.handleChange} 
                        handleAutocompleteChange={this.handleAutocompleteChange}
                        handleInfo={this.props.handleInfo}
                        labels = {NOTIFICATIONS_HEADER_DATA} 
                        booleanFields = {NOTIFICATIONS_BOOLEAN_FIELDS}
                        editFields={NOTIFICATION_EDIT_FIELDS}
                        errors={this.state.errors}
                        feedback={MESSAGES.notificationsEditInfo}
                        validateAndSubmit={this.validateAndSubmit}
                        refreshData={this.refreshNotifications}
                    />
                  }
                </Paper>
              </Grid>
            </Grid>
     )
    }
}


NotificationComponent.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(withRouter(NotificationComponent));