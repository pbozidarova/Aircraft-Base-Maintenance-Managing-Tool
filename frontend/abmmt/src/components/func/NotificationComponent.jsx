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
        // this.handleInfo = this.handleInfo.bind(this);

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
        }, () => submit(this.state.errors, "notifications", selected.notificationNum, selected, this.refreshNotifications, this.props.handleInfo) );
    
      }
  
    //   submitUpdate(notificationNum, selected){
    //     if(Utils.formIsValid(this.state.errors)) {
    //         BackendService.updateOne("notifications", notificationNum, selected)
    //             .then((r) => {                        
    //                 this.refreshNotifications()
    //                 Utils.successMessage(this.props.handleInfo, MESSAGES.successUpdated)
    //             }).catch(e => {
    //                 Utils.errorMessage(e, this.props.handleInfo )
    //                 // this.props.handleInfo({error : e});
    //             })
    //         console.log('submit Update')
    //     }
    //   }
  
    //   submitCreate(notificationNum, selected){  
    //     if(Utils.formIsValid(this.state.errors)) {
    //         BackendService.createOne("notifications", notificationNum, selected)
    //             .then(() => {                        
    //                 this.refreshNotifications()
    //                 Utils.successMessage(this.props.handleInfo, MESSAGES.successCreated)
    //             }
    //             ).catch(e => {
    //                 Utils.errorMessage(e, this.props.handleInfo )
    //             })
    //     }
    //   }


    // handleInfo(msg){
    //     this.setState({...this.state, infoPanel : msg})
    // }

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
                        handleInfo={this.props.handleInfo}
                        labels = {NOTIFICATIONS_HEADER_DATA} 
                        booleanFields = {NOTIFICATIONS_BOOLEAN_FIELDS}
                        editFields={NOTIFICATION_EDIT_FIELDS}
                        errors={this.state.errors}
                        validateAndSubmit={this.validateAndSubmit}
                        // submitUpdate={this.submitUpdate}
                        // submitCreate={this.submitCreate}
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