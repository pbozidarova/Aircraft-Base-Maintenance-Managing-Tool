import React, {Component} from 'react'

import clsx from 'clsx';
import DataComponent from '../func/DataComponent'

import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import PropTypes from 'prop-types';
import { styles } from '../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';
import { MAINTENANCE_HEADER_DATA, NOTIFICATIONS_HEADER_DATA, TASKS_HEADER_DATA, MESSAGES, FETCH_DATA_KEY } from '../../Constanst.js';
import BackendService from '../../api/CommonAPI.js'
import Utils from '../Utils.js'
import CircularProgress from '@material-ui/core/CircularProgress';

class DashboardComponent extends Component {
    constructor(props){
      super(props)
      
      this.state = {
          // tasks : [],
          maintenance: [],
          notifications: [],     
          loading: true,       
  
          // errors: {},     
      }

      this.refreshNotifications = this.refreshNotifications.bind(this)
      this.selectNotification = this.selectNotification.bind(this)
      this.selectMaintenance = this.selectMaintenance.bind(this)
    }

    componentDidMount(){
      this.refreshNotifications();
      this.refreshMaintenance();
      // this.selectFacility(Utils.emptyObj(FACILITIES_HEADER_DATA));

    }

    refreshMaintenance(){
      let key = 'maintenanceViewDtoList'

      BackendService.getAll("maintenance")
        .then(
            response => {
                this.setState({
                    loading : false, 
                    maintenance : response.data._embedded[key]
                }, () => Utils.allocateCorrectSuccessMessage(this.props.handleInfo, MESSAGES.allData));
            }
        ).catch(e => Utils.allocateCorrectErrorMessage(e, this.props.handleInfo, MESSAGES.allData ));
    }

    refreshNotifications(){
      BackendService.getAll('notifications')
      .then(
          response => {
              this.setState({
                  loading : false, 
                  notifications : response.data
              }, () => {console.log(this.state); Utils.allocateCorrectSuccessMessage(this.props.handleInfo, MESSAGES.allData)});
          }
      ).catch(e => {console.log(e); Utils.allocateCorrectErrorMessage(e, this.props.handleInfo, MESSAGES.allData )});
      // Object.keys(this.state).forEach((key, index) => {
      //   let responseKey = FETCH_DATA_KEY[key]
      //   BackendService.getAll(key)
      //   .then(
      //       response => {
      //         let responseKeyObj = response.data._embedded[responseKey] != null 
      //                             ? response.data._embedded[responseKey]
      //                             : []

      //         // setTimeout(() => {
      //         //   console.log(response.data);
      //         //   console.log(index);
      //           this.setState({
      //               loading : index == 0 ? false : true, 
      //               [key] : response.data._embedded[responseKey]
      //           },  () => {console.log(this.state); Utils.allocateCorrectSuccessMessage(this.props.handleInfo, MESSAGES.allData)});
      //         // }, 500);
      //       }
      //   ).catch(e => Utils.allocateCorrectErrorMessage(e, this.props.handleInfo, MESSAGES.allData ));
      // })
    }
    selectNotification(notification) {      
      this.setState({selected: notification})
  } 
  
  selectMaintenance(maintenance) {      
    this.setState({selected: maintenance})
  }

    render(){
        const { classes } = this.props;
        const fixedHeightPaper = clsx(classes.paper, classes.fixedHeightDash);

        return (
            <Grid container spacing={3}>
            
            { this.state.loading && <CircularProgress color="secondary"/> }

            <Grid item xs={12} md={5} lg={6}>
              <Paper className={fixedHeightPaper}>
                <DataComponent 
                        tableRows={this.state.maintenance}
                        tableHeader = {MAINTENANCE_HEADER_DATA}
                        selectedId={this.state.maintenance[0]}
                        selectRow={this.selectMaintenance} 
                        handleInfo={this.props.handleInfo}
                    />       
                 
              </Paper>
            </Grid>
            

            <Grid item xs={12} md={7} lg={6}>
              <Paper className={fixedHeightPaper}>
                <DataComponent 
                        tableRows={this.state.notifications}
                        tableHeader = {NOTIFICATIONS_HEADER_DATA}
                        selectedId={this.state.notifications[0]}
                        selectRow={this.selectNotification} 
                        handleInfo={this.props.handleInfo}
                    />

              </Paper>
            </Grid>

            {/* <Grid item xs={12} md={5} lg={6}>
              <Paper className={classes.paper, classes.fixedHeightDash}>
              <DataComponent 
                        tableRows={this.state.tasks}
                        tableHeader = {TASKS_HEADER_DATA}
                        selectedId={this.state.tasks[0]}
                        selectRow={this.selectFacility} 
                    />
                  
              </Paper>
            </Grid> */}
            
           
            
           
          </Grid>
  
        )
    }
}


DashboardComponent.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(DashboardComponent);