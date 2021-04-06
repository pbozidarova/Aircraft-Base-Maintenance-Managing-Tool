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
        this.handleAutocompleteChange = this.handleAutocompleteChange.bind(this)

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

    handleChange(event, key){

        let eName =  key || event.target.name
        let eValue = event.target.value
        
        this.setState(
            {   ...this.state,
                selected: {...this.state.selected, [eName] : eValue}
            },console.log(this.state))
        
    }

    handleAutocompleteChange(event, value, key){
        console.log(value)
        this.setState(
            {   ...this.state,
                selected: {...this.state.selected, [key] : value}
            }, console.log(this.state)) 
    }


    validateAndSubmit(submit, refreshData){
        const { selected } = this.state;
        
        this.setState({ errors: 
             { 
                aircraftRegistration : selected.aircraftRegistration.length > 2 ? '' : "The aircraft registration must be longer than 2 symbols!",
                serialNumber : selected.serialNumber.length > 2 ? '' : "The serial Number must be longer than 2 symbols!",
                aircraftType : selected.aircraftType.length > 2 ? '' : "The aircraft type must be longer than 2 symbols!",
                aircraftModel : selected.aircraftModel.length > 2 ? '' : "The aircraft model must be longer than 2 symbols!",
                operatorName: selected.operatorName.length > 2 ? '' : "The operatoor name must be longer than 2 symbols!",
                operatorICAOCode : selected.operatorICAOCode.length == 3 ? '' : "The operator ICAO code must equal 3 symbols!",
                engineManufacturer : selected.engineManufacturer.length > 2 ? '' : "The engine manufacturer must be longer than 2 symbols!",
                engineModelSeries : selected.engineModelSeries.length > 2 ? '' : "The engine model series must be longer than 2 symbols!",
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
                        handleAutocompleteChange={this.handleAutocompleteChange}
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