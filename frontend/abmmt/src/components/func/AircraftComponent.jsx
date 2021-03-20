import React, {Component} from 'react'
import BackendService from '../../api/CommonAPI.js'
import {AIRCRAFT_HEADER_DATA} from '../../Constanst.js'

import { styles } from '../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';

import EditUserComponent from './UserEditComponent'
import DataComponent from './DataComponent'

import PropTypes from 'prop-types';
import clsx from 'clsx';

import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';


class AircraftComponent extends Component {
    constructor(props){
        super(props)

        this.state = {
            aircraft : [],
            selected: {},

        }

        this.refreshData = this.refreshData.bind(this)
        this.selectAircraft = this.selectAircraft.bind(this)

    }
   
    componentDidMount(){
           this.refreshData();
    }

    refreshData(){
        BackendService.all('aircraft')
            .then(
                response => {
                    this.setState({aircraft : response.data});
                }
            ); 
    }

    selectAircraft(event, aircraft) {      
        this.setState({selected: aircraft})
    }


    render(){
        const { classes } = this.props;
        const fixedHeightPaper = clsx(classes.paper, classes.fixedHeightMin);

        return(
                   
            <Grid container spacing={3}>
              {/* Chart */}
              <Grid item xs={12} md={12} lg={12}>
                <Paper className={fixedHeightPaper}>
                            
                    <DataComponent 
                        tableRows={this.state.aircraft}
                        tableHeader = {AIRCRAFT_HEADER_DATA}
                        selected={this.state.selected}
                        selectRow={this.selectAircraft} 
                    />

                </Paper>
              </Grid>
              <Grid item xs={12} md={12} lg={12}>
                <Paper className={fixedHeightPaper}>
                  
                    {/* <EditUserComponent selectedUser={this.state.selected} /> */}

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


AircraftComponent.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(AircraftComponent);