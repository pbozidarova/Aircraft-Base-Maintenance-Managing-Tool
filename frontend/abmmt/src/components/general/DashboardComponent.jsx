import React, {Component} from 'react'

import clsx from 'clsx';

import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import PropTypes from 'prop-types';
import { styles } from '../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';

class DashboardComponent extends Component {

    render(){
        const { classes } = this.props;
        const fixedHeightPaper = clsx(classes.paper, classes.fixedHeightDash);

        return (
            <Grid container spacing={3}>

            <Grid item xs={12}>
              <Paper className={classes.paper, classes.fixedHeightDash}>
                  
                  
              </Paper>
            </Grid>
            
            <Grid item xs={12} md={5} lg={5}>
              <Paper className={fixedHeightPaper}>
                          
                 
              </Paper>
            </Grid>
            <Grid item xs={12} md={7} lg={7}>
              <Paper className={fixedHeightPaper}>
                

              </Paper>
            </Grid>
            
           
          </Grid>
  
        )
    }
}


DashboardComponent.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(DashboardComponent);