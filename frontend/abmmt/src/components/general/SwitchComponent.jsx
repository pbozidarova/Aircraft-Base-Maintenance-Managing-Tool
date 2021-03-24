import React, {Component} from 'react';
import PropTypes from 'prop-types';


import{BrowserRouter as Router, Route, Switch} from 'react-router-dom'

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


import { styles } from '../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';


class SwitchComponent extends Component {
  

  shouldComponentUpdate(){
    return false
  }
 
  render(){
    const { classes } = this.props;

    return (   
        
          <Switch >
              <Route path="/" exact component={LoginComponent} />
              <AuthenticatedRoute path="/home" 
                                  component={() => (<DashboardComponent handleInfo={this.props.handleInfo} />)} />
              <AuthenticatedRoute path="/mpd" 
                                  component={() => (<TaskComponent handleInfo={this.props.handleInfo}/>)} />
              <AuthenticatedRoute path="/maintenance" 
                                  component={() => (<MaintenanceComponent handleInfo={this.props.handleInfo}/> )} />
              <AuthenticatedRoute path="/notifications" 
                                  component={() => (<NotificationComponent handleInfo={this.props.handleInfo}/> )} />
              <AuthenticatedRoute path="/aircraft" 
                                  component={() => (<AircraftComponent handleInfo={this.props.handleInfo}/>)} />
              <AuthenticatedRoute path="/facility" 
                                  component={() => (<FacilityComponent handleInfo={this.props.handleInfo}/>)} />
              <AuthenticatedRoute path="/users" 
                                  component={() => (<UsersComponent handleInfo={this.props.handleInfo} />)} />
            
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

