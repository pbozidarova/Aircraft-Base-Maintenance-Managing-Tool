import React, {Component} from 'react'
import BackendService from '../../api/CommonAPI.js'
import {FACILITIES_HEADER_DATA} from '../../Constanst.js'

import { styles } from '../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';

import EnhancedTable from '../material-ui/Table.js'


import DataComponent from './DataComponent'

import PropTypes from 'prop-types';
import clsx from 'clsx';

import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';


class FacilityComponent extends Component {
    constructor(props){
        super(props)
        
        this.state = {
            facilities : [],
            selected: {},
            
        }

        this.refreshUsers = this.refreshUsers.bind(this)
        this.selectFacility = this.selectFacility.bind(this)
        

    }
   
    componentDidMount(){
        // let username = AuthenticationService.isUserLoggedIn();
        this.refreshUsers();
    }

    refreshUsers(){
        BackendService.all('facilities')
            .then(
                response => {
                    // console.log(response.data);
                    this.setState({facilities : response.data});
                }
            ); 
    }

    selectFacility(event, facility) {      
        this.setState({selected: facility})
    }


    render(){
        const { classes } = this.props;
        const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

        return(
                   
            <Grid container spacing={3}>
              {/* Chart */}
              <Grid item xs={12} md={5} lg={5}>
                <Paper className={fixedHeightPaper}>
                            
                    <DataComponent 
                        tableRows={this.state.facilities}
                        tableHeader = {FACILITIES_HEADER_DATA}
                        selected={this.state.selected}
                        selectRow={this.selectFacility} 
                    />

                </Paper>
              </Grid>
              <Grid item xs={12} md={7} lg={7}>
                <Paper className={fixedHeightPaper}>
                  
                    

                </Paper>
              </Grid>
              {/* Recent Orders */}
              <Grid item xs={12}>
                <Paper className={classes.paper}>
                    
                    <form className={classes.root} noValidate autoComplete="off">
                        <div>

                            {/* <EditUserComponent selectedUser={this.state.selectedUser}/> */}
                            
                        </div>
                    </form>
                </Paper>
              </Grid>
            </Grid>
    
     
        )
    }
}


FacilityComponent.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(FacilityComponent);
