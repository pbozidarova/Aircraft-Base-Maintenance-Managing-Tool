import React, {Component} from 'react';
import PropTypes from 'prop-types';
import BackendService from '../CommonAPI.js'

import CssBaseline from '@material-ui/core/CssBaseline';
import Container from '@material-ui/core/Container';

import Box from '@material-ui/core/Box';

import{BrowserRouter as Router} from 'react-router-dom'

import AppBarDrawerComponent from './AppBarDrawerComponent'
import SwitchComponent from './SwitchComponent'

import FooterComponent from './FooterComponent'

import { styles } from '../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';
import {MESSAGES} from '../Constants.js'
import { Grid } from '@material-ui/core';
import MuiAlert from '@material-ui/lab/Alert';

class AbmmtComponent extends Component {
  
  constructor(props){
    super(props)
    this.state = {
      infoPanel : {
          info: MESSAGES.welcomeMsg + MESSAGES.usersInfo,
          success: MESSAGES.empty,
          error: MESSAGES.empty,
      },
      openNotifications: '',
    }

    this.Alert = this.Alert.bind(this);
    this.handleInfo = this.handleInfo.bind(this);
    this.fetchOpenNotifCount = this.fetchOpenNotifCount.bind(this);
  }

  Alert(props) {
    return <MuiAlert elevation={3} variant="filled" {...props} />;
  }
  
  handleInfo(msg){
    this.setState({...this.state, infoPanel : msg})
  }

  fetchOpenNotifCount(){
    BackendService.fetchOpenNotificationsCount()
        .then(response => {
            this.setState({openNotifications: response.data})
        });
  }

 
  render(){
    const { classes } = this.props;

    return (

      <div className={classes.abmmt}>
          <CssBaseline />
          <Router>
            <AppBarDrawerComponent handleInfo={this.handleInfo} openNotifications={this.state.openNotifications} />        
    
            <main className={classes.content}>
              <div className={classes.appBarSpacer} />
                <Container maxWidth={false} className={classes.container}>
                  <Grid container spacing={2}>
                  {/* Here is the info panel */}
                    <Grid item xs={12}>
                      <div>
                          {this.state.infoPanel.info && <this.Alert severity="info" className={classes.tableCell}  variant="outlined" >{this.state.infoPanel.info} </this.Alert>}
                          {this.state.infoPanel.success && <this.Alert severity="success" className={classes.tableCell}  variant="outlined">{this.state.infoPanel.success} </this.Alert>}
                          {this.state.infoPanel.error && <this.Alert severity="error" className={classes.tableCell}  >{this.state.infoPanel.error} </this.Alert>}
                      </div>
                    </Grid>

                    {/* Here are the components between which we are switching */}
                    <Grid item xs={12}>
                      <SwitchComponent handleInfo={this.handleInfo} fetchOpenNotifCount={this.fetchOpenNotifCount}/>
                    </Grid>
                  </Grid>        
                </Container>
                  
                <Box  >
                  <FooterComponent />
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

