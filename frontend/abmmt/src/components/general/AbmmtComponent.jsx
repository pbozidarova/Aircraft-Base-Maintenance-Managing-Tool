import React, {Component} from 'react';
import PropTypes from 'prop-types';

import CssBaseline from '@material-ui/core/CssBaseline';
import Container from '@material-ui/core/Container';

import Box from '@material-ui/core/Box';

import{BrowserRouter as Router} from 'react-router-dom'

import AppBarDrawerComponent from './AppBarDrowerComponent'
import SwitchComponent from './SwitchComponent'

import FooterComponent from './FooterComponent'

import { styles } from '../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';
import {MESSAGES} from '../../Constanst.js'
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
    }

    this.Alert = this.Alert.bind(this);
    this.handleInfo = this.handleInfo.bind(this);
  }

  Alert(props) {
    return <MuiAlert elevation={3} variant="filled" {...props} />;
  }
  
  handleInfo(msg){
    this.setState({...this.state, infoPanel : msg})
  }

 
  render(){
    const { classes } = this.props;

    return (

      <div className={classes.abmmt}>
          <CssBaseline />
          <Router>
            <AppBarDrawerComponent handleInfo={this.handleInfo}/>        
    
            <main className={classes.content}>
              <div className={classes.appBarSpacer} />
                <Container maxWidth={false} className={classes.container}>
                  <Grid container spacing={3}>
                  {/* Here is the info panel */}
                    <Grid item xs={12}>
                      <div>
                          {this.state.infoPanel.info && <this.Alert severity="info" >{this.state.infoPanel.info} </this.Alert>}
                          {this.state.infoPanel.success && <this.Alert severity="success" >{this.state.infoPanel.success} </this.Alert>}
                          {this.state.infoPanel.error && <this.Alert severity="error" >{this.state.infoPanel.error} </this.Alert>}
                      </div>
                    </Grid>

                    {/* Here are the components between which we are switching */}
                    <Grid item xs={12}>
                      <SwitchComponent  handleInfo={this.handleInfo} />
                    </Grid>
                  </Grid>        
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

