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


class AbmmtComponent extends Component {
  
  render(){
    const { classes } = this.props;

    return (

      <div className={classes.abmmt}>
          <CssBaseline />
          <Router>
            <AppBarDrawerComponent/>        
    
            <main className={classes.content}>
              <div className={classes.appBarSpacer} />
                <Container maxWidth={false} className={classes.container}>
                    <Switch>
                        <Route path="/" exact component={LoginComponent}></Route>
                        <AuthenticatedRoute path="/home" component={DashboardComponent}></AuthenticatedRoute>
                        <AuthenticatedRoute path="/mpd" component={TaskComponent}></AuthenticatedRoute>
                        <AuthenticatedRoute path="/maintenance" component={MaintenanceComponent}></AuthenticatedRoute>
                        <AuthenticatedRoute path="/notifications" component={NotificationComponent}></AuthenticatedRoute>


                        <AuthenticatedRoute path="/aircraft" component={AircraftComponent}></AuthenticatedRoute>
                        <AuthenticatedRoute path="/facility" component={FacilityComponent}></AuthenticatedRoute>
                        
                        <AuthenticatedRoute path="/users" component={UsersComponent}></AuthenticatedRoute>
                        <AuthenticatedRoute path="/logout" component={LogoutComponent}></AuthenticatedRoute>
                        <Route path="/login" component={LoginComponent}></Route>
                        <Route component={ErrorComponent}/>
                    </Switch>              
                </Container>
                  
                <Box pt={4}>
                  <FooterComponent/>
                </Box> 
          
            </main>
          </Router>
        </div>
    )}
 
}

AbmmtComponent.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(AbmmtComponent);

