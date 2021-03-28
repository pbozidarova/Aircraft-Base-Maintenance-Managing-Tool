import React, { Component } from 'react'
import BackendService from '../../api/CommonAPI.js'
import {NOTIFICATIONS_HEADER_DATA, NOTIFICATIONS_BOOLEAN_FIELDS, NOTIFICATIONS_DISABLED_FIELDS, MESSAGES} from '../../Constanst.js'
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


class NotificationComponent extends Component {

    constructor(props){
        super(props)

        this.state = {
            notifications : [],
            selected: {},
            loading: true,       
            errors: {},     
        }

        this.refreshNotifications = this.refreshNotifications.bind(this)
        this.selectNotification = this.selectNotification.bind(this)
        this.handleChange = this.handleChange.bind(this)
        this.handleInfo = this.handleInfo.bind(this);

        this.validateAndSubmit = this.validateAndSubmit.bind(this);
        this.submitUpdate = this.submitUpdate.bind(this)
        this.submitCreate = this.submitCreate.bind(this)
    }
    
    componentDidMount(){
        this.refreshNotifications();
        this.selectNotification(Utils.emptyObj(NOTIFICATIONS_HEADER_DATA))
    }

    refreshNotifications(){
        let key = 'notificationViewDtoList'
        let shouldFetchPartialData = this.props.location.fetchDataFromURL
        
        shouldFetchPartialData ? this.partialFetch(shouldFetchPartialData.href, shouldFetchPartialData.title, key) 
                               : this.fetchAll("notifications", key)
    }

    partialFetch(hateoasUrl, title, key){
        BackendService.fetchDataFrom(hateoasUrl)
        .then(
            response => {
                this.setState({
                    loading : false, 
                    notifications : response.data._embedded[key]
                }, () => Utils.allocateCorrectSuccessMessage(this.props.handleInfo, title));
            }
        ).catch(e => Utils.allocateCorrectErrorMessage(e, this.props.handleInfo, title))
    }

    fetchAll(urlParam, key){
        BackendService.getAll(urlParam)
        .then(
            response => {
                this.setState({
                    loading : false, 
                    notifications : response.data
                }, () => {console.log(this.state); Utils.allocateCorrectSuccessMessage(this.props.handleInfo, MESSAGES.allData)});
            }
        ).catch(e => {console.log(e); Utils.allocateCorrectErrorMessage(e, this.props.handleInfo, MESSAGES.allData )});

    }
   
    selectNotification(notification) {      
        this.setState({selected: notification})
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
        }, () => submit(selected.notificationNum, selected) );
    
      }
  
      submitUpdate(notificationNum, selected){
        if(Utils.formIsValid(this.state.errors)) {
            BackendService.updateOne("notifications", notificationNum, selected)
                .then((r) => {                        
                    this.refreshNotifications()
                    this.props.handleInfo({success : MESSAGES.successUpdated});
                }).catch(e => {
                    this.props.handleInfo({error : e.response.data.message});
                    // this.props.handleInfo({error : e});
                })
            console.log('submit Update')
        }
      }
  
      submitCreate(notificationNum, selected){  
        if(Utils.formIsValid(this.state.errors)) {
            BackendService.createOne("notifications", notificationNum, selected)
                .then(() => {                        
                    this.refreshNotifications()
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
                        tableRows={this.state.notifications} 
                        tableHeader={NOTIFICATIONS_HEADER_DATA}
                        selectedId={this.state.selected.notificationNum}
                        selectRow={this.selectNotification} 
                    />
                </Paper>
               </Grid>

              <Grid item xs={12} md={6} lg={4}>
                <Paper className={fixedHeightPaper}>
                  {this.state.selected.notificationNum && 
                    <EditGlobalComponent
                        selected={this.state.selected} 
                        selectedId={this.state.selected.notificationNum}
                        handleChange={this.handleChange} 
                        handleInfo={this.handleInfo}
                        labels = {NOTIFICATIONS_HEADER_DATA} 
                        booleanFields = {NOTIFICATIONS_BOOLEAN_FIELDS}
                        disabledFields={NOTIFICATIONS_DISABLED_FIELDS}
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


NotificationComponent.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(withRouter(NotificationComponent));