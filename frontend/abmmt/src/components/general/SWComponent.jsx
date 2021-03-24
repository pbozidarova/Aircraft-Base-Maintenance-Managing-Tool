import React, {Component} from 'react';
import PropTypes from 'prop-types';

import CssBaseline from '@material-ui/core/CssBaseline';
import Container from '@material-ui/core/Container';

import Box from '@material-ui/core/Box';

import{BrowserRouter as Router, Route, Switch} from 'react-router-dom'

import AppBarDrawerComponent from './AppBarDrowerComponent'

import LoginComponent from './LoginComponent'
import LogoutComponent from './LogoutComponent'
import AircraftComponent from '../func/aircraft/AircraftComponent'
import DashboardComponent from './DashboardComponent';
import FacilityComponent from '../func/facility/FacilityComponent'
import TaskComponent from '../func/task/TaskComponent'
import MaintenanceComponent from '../func/maintenance/MaintenanceComponent'
import NotificationComponent from '../func/notification/NotificationComponent'

import UsersComponent from '../func/user/UsersComponent'
import ErrorComponent from './ErrorComponent'
import AuthenticatedRoute from './AuthenticatedRoute'
import FooterComponent from './FooterComponent'

import { styles } from '../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';
import {MESSAGES} from '../../Constanst.js'
import { Grid } from '@material-ui/core';
import MuiAlert from '@material-ui/lab/Alert';

class SwitchComponent extends Component {
  
  // constructor(props){
  //   super(props)
  //   this.state = {
  //     infoPanel : {
  //         info: MESSAGES.initialLoad,
  //         success: MESSAGES.empty,
  //         error: MESSAGES.empty,
  //     },
  //   }

  //   this.Alert = this.Alert.bind(this);
  //   this.handleInfo = this.handleInfo.bind(this);
  // }

  // Alert(props) {
  //   return <MuiAlert elevation={3} variant="filled" {...props} />;
  // }
  
  // handleInfo(msg){
  //   // return msg
  //   this.setState({...this.state, infoPanel : msg})
  // }

  shouldComponentUpdate(){
    return false
  }
 
  render(){
    const { classes } = this.props;

    return (   
        
          <Switch >
              <Route path="/" exact component={LoginComponent}></Route>
              <AuthenticatedRoute path="/home" 
                                  component={() => (<DashboardComponent handleInfo={this.props.handleInfo} infoPanel={this.props.handleInfo}/>)} />
              <AuthenticatedRoute path="/mpd" 
                                  component={() => (<TaskComponent handleInfo={this.props.handleInfo}/>)} />
              <AuthenticatedRoute path="/maintenance" 
                                  component={() => (<MaintenanceComponent handleInfo={this.props.handleInfo}/> )}></AuthenticatedRoute>
              <AuthenticatedRoute path="/notifications" 
                                  component={() => (<NotificationComponent handleInfo={this.props.handleInfo}/> )}></AuthenticatedRoute>
              <AuthenticatedRoute path="/aircraft" 
                                  component={() => (<AircraftComponent handleInfo={this.props.handleInfo}/>)} ></AuthenticatedRoute>
              <AuthenticatedRoute path="/facility" 
                                  component={() => (<FacilityComponent handleInfo={this.props.handleInfo}/>)} ></AuthenticatedRoute>
            
              <AuthenticatedRoute path="/users" 
                                  component={() => (<UsersComponent handleInfo={this.props.handleInfo} infoPanel={this.props.infoPanel}/>)} ></AuthenticatedRoute>
            
              <AuthenticatedRoute path="/logout" component={LogoutComponent}/>
              <Route path="/login" component={LoginComponent}/>
              <Route component={ErrorComponent}/>
          </Switch>      
               
    )}
 
}

SwitchComponent.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SwitchComponent);

