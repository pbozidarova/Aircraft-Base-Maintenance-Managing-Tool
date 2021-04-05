import React, {Component} from 'react';
import PropTypes from 'prop-types';

import{BrowserRouter as Router, Route, Switch} from 'react-router-dom'

import LoginComponent from './LoginComponent'
import LogoutComponent from './LogoutComponent'
import AircraftComponent from '../func/AircraftComponent'
import DashboardComponent from './DashboardComponent';
import FacilityComponent from '../func/FacilityComponent'
import TaskComponent from '../func/TaskComponent'
import MaintenanceComponent from '../func/MaintenanceComponent'
import NotificationComponent from '../func/NotificationComponent'

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
    return (   
        
          <Switch >
              <Route path="/" exact component={() => (<LoginComponent handleInfo={this.props.handleInfo} fetchOpenNotifCount={this.props.fetchOpenNotifCount}/>)} />
              <AuthenticatedRoute path="/home" 
                                  component={() => (<DashboardComponent handleInfo={this.props.handleInfo} />)} />
              <AuthenticatedRoute path="/tasks" 
                                  component={() => (<TaskComponent handleInfo={this.props.handleInfo}/>)} />
              <AuthenticatedRoute path="/maintenance" 
                                  component={() => (<MaintenanceComponent handleInfo={this.props.handleInfo}/> )} />
              <AuthenticatedRoute path="/notifications" 
                                  component={() => (<NotificationComponent handleInfo={this.props.handleInfo}/> )} />
              <AuthenticatedRoute path="/aircraft" 
                                  component={() => (<AircraftComponent handleInfo={this.props.handleInfo}/>)} />
              <AuthenticatedRoute path="/facilities" 
                                  component={() => (<FacilityComponent handleInfo={this.props.handleInfo}/>)} />
              <AuthenticatedRoute path="/users" 
                                  component={() => (<UsersComponent handleInfo={this.props.handleInfo} />)} />
            
              <AuthenticatedRoute path="/logout" component={() => (<LogoutComponent handleInfo={this.props.handleInfo} />)}/>
              <Route path="/login" component={() => (<LoginComponent handleInfo={this.props.handleInfo} infoPanel={this.state.infoPanel}
                                                                     fetchOpenNotifCount={this.props.fetchOpenNotifCount}/>)}/>
              <Route component={ErrorComponent}/>
          </Switch>      
               
    )}
 
}

SwitchComponent.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SwitchComponent);

