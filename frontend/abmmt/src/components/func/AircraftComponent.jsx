import React, {Component} from 'react'
import BackendService from '../../api/CommonAPI.js'
import {AIRCRAFT_HEADER_DATA, AIRCRAFT_BOOLEAN_FIELDS, AIRCRAFT_DISABLED_FIELDS, MESSAGES} from '../../Constanst.js'
import Utils from '../Utils.js'
 

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
            loading: true,       
            errors: {},     
        }

        this.refreshAircraft = this.refreshAircraft.bind(this)
        this.selectAircraft = this.selectAircraft.bind(this)
        this.handleChange = this.handleChange.bind(this)
        this.handleInfo = this.handleInfo.bind(this);

        this.validateAndSubmit = this.validateAndSubmit.bind(this);
        this.submitUpdate = this.submitUpdate.bind(this)
        this.submitCreate = this.submitCreate.bind(this)
    
    }
   
    componentDidMount(){
           this.refreshAircraft();
           this.selectAircraft(Utils.emptyObj(AIRCRAFT_HEADER_DATA))
    }

    refreshAircraft(){
        
        BackendService.getAll('aircraft')
            .then(response => {
                    this.setState({
                        aircraft : response.data._embedded.aircraftViewDtoList
                    }, () => Utils.successMessage(this.props.handleInfo, MESSAGES.allData));
                }
            ).catch(e => Utils.errorMessage(e, this.props.handleInfo, MESSAGES.allData ));
    }
  
    selectAircraft(aircraft) {      
        this.setState({selected: aircraft})
    }

    handleChange(event){
        this.setState(
            {   ...this.state,
                selected: {...this.state.selected, [event.target.name] : event.target.value}
            })
        
    }

    validateAndSubmit(submit){
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
        }, () => submit(selected.aircraftRegistration, selected) );
    
      }

      submitUpdate(aircraftRegistration, selected){
        if(Utils.formIsValid(this.state.errors)) {
            BackendService.updateOne("aircraft", aircraftRegistration, selected)
                .then((r) => {                        
                    this.refreshAircraft()
                    Utils.successMessage(this.props.handleInfo, MESSAGES.successUpdated)
                }).catch(e => {
                    Utils.errorMessage(e, this.props.handleInfo )
                    // this.props.handleInfo({error : e});
                })
            console.log('submit Update')
        }
      }

      submitCreate(aircraftRegistration, selected){  
        if(Utils.formIsValid(this.state.errors)) {
            BackendService.createOne("aircraft", aircraftRegistration, selected)
                .then(() => {                        
                    this.refreshAircraft()
                    Utils.successMessage(this.props.handleInfo, MESSAGES.successCreated)
                }
                ).catch(e => {
                    Utils.errorMessage(e, this.props.handleInfo )
                })
        }
      }

      handleInfo(msg){
        this.setState({...this.state, infoPanel : msg})
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
                        selectedId={this.state.selected.aircraftRegistration}
                        selectRow={this.selectAircraft} 
                    />

                </Paper>
              </Grid>
              <Grid item xs={12} md={6} lg={4}>
                <Paper className={fixedHeightPaper}>
                  {this.state.selected.aircraftRegistration && 
                    <EditGlobalComponent 
                        selected={this.state.selected} 
                        selectedId={this.state.selected.aircraftRegistration}
                        handleChange={this.handleChange} 
                        handleInfo={this.handleInfo}
                        labels = {AIRCRAFT_HEADER_DATA} 
                        booleanFields = {AIRCRAFT_BOOLEAN_FIELDS}
                        disabledFields={AIRCRAFT_DISABLED_FIELDS}
                        errors={this.state.errors}
                        validateAndSubmit={this.validateAndSubmit}
                        submitUpdate={this.submitUpdate}
                        submitCreate={this.submitCreate}
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