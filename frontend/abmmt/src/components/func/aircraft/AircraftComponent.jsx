import React, {Component} from 'react'
import BackendService from '../../../api/CommonAPI.js'
import {AIRCRAFT_HEADER_DATA, MESSAGES} from '../../../Constanst.js'


import { styles } from '../../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';

import DataComponent from '../DataComponent'
import EditAircraft from './EditAircraft'

import PropTypes from 'prop-types';
import clsx from 'clsx';

import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';


class AircraftComponent extends Component {
    constructor(props){
        super(props)

        this.state = {
            infoPanel : {
                info: MESSAGES.initialLoad,
                success: MESSAGES.initialLoad,
                error: MESSAGES.initialLoad,
            },
            aircraft : [],
            selected: {},

        }

        this.refreshData = this.refreshData.bind(this)
        this.selectAircraft = this.selectAircraft.bind(this)
        this.handleChange = this.handleChange.bind(this)
        this.handleInfo = this.handleInfo.bind(this);
    }
   
    componentDidMount(){
           this.refreshData();
           this.createEmptySelect();

    }

    refreshData(){
        BackendService.getAll('aircraft')
            .then(
                response => {
                    this.setState({aircraft : response.data});
                }
            ); 
    }
    createEmptySelect(){
        let emptyUser = Object.keys(AIRCRAFT_HEADER_DATA)
                            .reduce((acc, curr) =>  acc = {...acc, [curr] : ' '}, {} );
    
        this.selectAircraft(emptyUser);
    }

    selectAircraft(aircraft) {      
        this.setState({selected: aircraft})
    }

    handleChange(event){
        this.setState(
            {   ...this.state,
                selected: {...this.state.selected, [event.target.name] : event.target.value}
            }
        , console.log(this.state))
        
    }

    handleInfo(msg){
        this.setState({...this.state, infoPanel : msg})
    }

    render(){
        const { classes } = this.props;
        const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

        return(
                   
            <Grid container spacing={3}>

                <Grid item xs={12}>
                <Paper className={classes.paper}>
                    <form className={classes.root} noValidate autoComplete="off">
                        <div>
                            {this.state.infoPanel.info}
                            {this.state.infoPanel.success}
                            {this.state.infoPanel.error}                            
                        </div>
                    </form>
                </Paper>
              </Grid>

              <Grid item xs={12} md={6} lg={8}>
                <Paper className={fixedHeightPaper}>
                            
                    <DataComponent 
                        tableRows={this.state.aircraft}
                        tableHeader = {AIRCRAFT_HEADER_DATA}
                        selected={this.state.selected}
                        selectRow={this.selectAircraft} 
                    />

                </Paper>
              </Grid>
              <Grid item xs={12} md={6} lg={4}>
                <Paper className={fixedHeightPaper}>
                  {this.state.selected.companyNum && 
                    <EditAircraft 
                        selectedUser={this.state.selected} 
                        handleChange={this.handleChange} 
                        handleAuthorityRoleChange={this.handleAuthorityRoleChange} 
                        handleInfo={this.handleInfo}
                        refreshUsers={this.refreshUsers} 
                        labels = {AIRCRAFT_HEADER_DATA} 

                    />
                  }
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