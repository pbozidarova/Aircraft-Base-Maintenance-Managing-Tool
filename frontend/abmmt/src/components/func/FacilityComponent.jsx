import React, {Component} from 'react'
import BackendService from '../../api/CommonAPI.js'
import {FACILITIES_HEADER_DATA, FACILITIES_BOOLEAN_FIELDS, FACILITIES_DISABLED_FIELDS, MESSAGES} from '../../Constanst.js'
import Utils from '../Utils.js'

import { styles } from '../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';

import DataComponent from './DataComponent'
import EditGlobalComponent from './EditGlobalComponent'

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
            loading: true,       
            errors: {},     
        }

        this.refreshFacilities = this.refreshFacilities.bind(this)
        this.selectFacility = this.selectFacility.bind(this)
        this.handleChange = this.handleChange.bind(this)
        this.handleInfo = this.handleInfo.bind(this);

        this.validateAndSubmit = this.validateAndSubmit.bind(this);
        this.submitUpdate = this.submitUpdate.bind(this)
        this.submitCreate = this.submitCreate.bind(this)
    
    }
   
    componentDidMount(){
        this.refreshFacilities();
        this.selectFacility(Utils.emptyObj(FACILITIES_HEADER_DATA));

    }

    refreshFacilities(){
        BackendService.getAll('facilities')
            .then(
                response => {
                    // console.log(response.data);
                    this.setState({facilities : response.data},
                        () => this.props.handleInfo({success : MESSAGES.successLoaded}));
                }
            ); 
    }

    selectFacility(facility) {      
        this.setState({selected: facility})
    }

    handleChange(event){
        this.setState(
            {   ...this.state,
                selected: {...this.state.selected, [event.target.name] : event.target.value}
            }
        , console.log(this.state))
        
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
        }, () => submit(selected.name, selected) );
    
      }

      submitUpdate(name, selected){
        if(Utils.formIsValid(this.state.errors)) {
            BackendService.updateOne("facilities", name, selected)
                .then((r) => {                        
                    this.refreshFacilities()
                    this.props.handleInfo({success : MESSAGES.successUpdated});
                }).catch(e => {
                    this.props.handleInfo({error : e.response.data.message});
                    // this.props.handleInfo({error : e});
                })
            console.log('submit Update')
        }
      }

      submitCreate(name, selected){  
        if(Utils.formIsValid(this.state.errors)) {
            BackendService.createOne("facilities", name, selected)
                .then(() => {                        
                    this.refreshFacilities()
                    this.props.handleInfo({success : MESSAGES.successCreated});
                }
                ).catch(e => {
                    this.props.handleInfo({error : e.response.data.message});
                })
  
            console.log('submit Create')
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
                        tableRows={this.state.facilities}
                        tableHeader = {FACILITIES_HEADER_DATA}
                        selectedId={this.state.selected.name}
                        selectRow={this.selectFacility} 
                    />

                </Paper>
              </Grid>
              <Grid item xs={12} md={6} lg={4}>
                <Paper className={fixedHeightPaper}>
                  {this.state.selected.name && 
                    <EditGlobalComponent
                    selected={this.state.selected} 
                    selectedId={this.state.selected.name}
                    handleChange={this.handleChange} 
                    handleInfo={this.handleInfo}
                    labels = {FACILITIES_HEADER_DATA} 
                    booleanFields = {FACILITIES_BOOLEAN_FIELDS}
                    disabledFields={FACILITIES_DISABLED_FIELDS}
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


FacilityComponent.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(FacilityComponent);
