import React, {Component} from 'react'
import BackendService from '../../api/CommonAPI.js'
import {AIRCRAFT_HEADER_DATA, AIRCRAFT_BOOLEAN_FIELDS, AIRCRAFT_EDIT_FIELDS, MESSAGES} from '../../Constanst.js'
import Utils from '../Utils.js'
import ComponentsStateService from '../ComponentsStateService.js'

import { styles } from '../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';

import DataComponent from './DataComponent'
import EditGlobalComponent from './EditGlobalComponent'

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
            selectedId: '',
            loading: true,       
            errors: {},     
        }

        ComponentsStateService.refreshData = ComponentsStateService.refreshData.bind(this)
        this.setState = this.setState.bind(this)
        
        this.refreshAircraft = this.refreshAircraft.bind(this)
        this.selectAircraft = this.selectAircraft.bind(this)
        this.handleChange = this.handleChange.bind(this)

        this.validateAndSubmit = this.validateAndSubmit.bind(this);
    
    }
   
    componentDidMount(){
        this.refreshAircraft();

        this.selectAircraft(Utils.emptyObj(AIRCRAFT_HEADER_DATA))
    }

    refreshAircraft(){
        let keyState = 'aircraft'
        let keyResponse = 'aircraftViewDtoList'
        let shouldFetchPartialData = false

        ComponentsStateService.refreshData( keyState, 
                                            keyResponse, 
                                            ComponentsStateService.fetchAll, 
                                            ComponentsStateService.partialFetch, 
                                            shouldFetchPartialData, 
                                            this.setState,
                                            this.props.handleInfo);
    }
  
    selectAircraft(aircraft, selectedId) {      
        this.setState({...this.state, selected: aircraft, selectedId})
    }

    handleChange(event){
        this.setState(
            {   ...this.state,
                selected: {...this.state.selected, [event.target.name] : event.target.value}
            })
        
    }

    validateAndSubmit(submit, refreshData){
        const { selected } = this.state;
        
        this.setState({ errors: 
             { 
                // companyNum: /^[N]\d{5}$/.test(selectedUser.companyNum) ? '' : "Follow the pattern N plus 5 digits!" ,
                // firstName:  selectedUser.firstName != 'First Name' && selectedUser.firstName.length > 2 ? '' : "The first name must contain more than 2 digits!" ,
                // lastName:  selectedUser.lastName != 'Last Name' && selectedUser.lastName.length > 2 ? '' : "The last name must contain more than 2 digits!",
                // email: /^\S+@\S+$/.test(selectedUser.email)  ? '' : "Please provide a valid email!",
                // facility: this.props.selectedUser.facility.length > 2 ? '' : "Please select a facility!",
                
                // authority: this.props.selectedUser.roles.length > 0 ? '' : "At least one authority must be checked!",
                // role: this.props.selectedUser.roles.length > 0 ? '' : "At least one role must be checked!",
  
             }
        }, () => submit(this.state.errors, 
                        "aircraft", 
                        selected.aircraftRegistration, 
                        selected, 
                        refreshData, 
                        this.props.handleInfo) );
    
      }

    render(){
        const { classes } = this.props;
        const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

        return(
                   
            <Grid container spacing={3}>

              <Grid item xs={12} md={6} lg={8}>
                <Paper className={fixedHeightPaper}>
                            
                    <DataComponent 
                        tableRows={this.state.aircraft}
                        tableHeader = {AIRCRAFT_HEADER_DATA}
                        selectedId={this.state.selectedId}
                        selectRow={this.selectAircraft} 
                    />

                </Paper>
              </Grid>
              <Grid item xs={12} md={6} lg={4}>
                <Paper className={fixedHeightPaper}>
                  {this.state.selected.aircraftRegistration && 
                    <EditGlobalComponent 
                        selected={this.state.selected} 
                        selectedId={this.state.selectedId}
                        handleChange={this.handleChange} 
                        handleInfo={this.props.handleInfo}
                        labels = {AIRCRAFT_HEADER_DATA} 
                        booleanFields = {AIRCRAFT_BOOLEAN_FIELDS}
                        editFields={AIRCRAFT_EDIT_FIELDS}
                        errors={this.state.errors}
                        feedback={MESSAGES.facilitiesEditInfo}
                        validateAndSubmit={this.validateAndSubmit}
                        refreshData={this.refreshAircraft}
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