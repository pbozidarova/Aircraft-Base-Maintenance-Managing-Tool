import React, { Component } from 'react'
import BackendService from '../../api/CommonAPI.js'
import {MAINTENANCE_HEADER_DATA, MAINTENANCE_BOOLEAN_FIELDS, MAINTENANCE_DISABLED_FIELDS, MESSAGES} from '../../Constanst.js'
import Utils from '../Utils.js'
import { withRouter } from 'react-router';

import DataComponent from './DataComponent'
import EditGlobalComponent from './EditGlobalComponent'
import CircularProgress from '@material-ui/core/CircularProgress';

import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import clsx from 'clsx';

import { styles } from '../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';

import PropTypes from 'prop-types';


class MaintenanceComponent extends Component {

    constructor(props){
        super(props)

        this.state = {
            maintenance : [],
            selected: {},
            loading: true,       
            errors: {},     
        }

        this.refreshMaintenance = this.refreshMaintenance.bind(this)
        this.selectMaintenance = this.selectMaintenance.bind(this)
        this.handleChange = this.handleChange.bind(this)
        this.handleInfo = this.handleInfo.bind(this);

        this.validateAndSubmit = this.validateAndSubmit.bind(this);
        this.submitUpdate = this.submitUpdate.bind(this)
        this.submitCreate = this.submitCreate.bind(this)
    }
    
    componentDidMount(){
        this.refreshMaintenance();
        this.selectMaintenance(Utils.emptyObj(MAINTENANCE_HEADER_DATA))
    }

    refreshMaintenance(){
        this.props.location.fetchDataFromURL != null
                ?   this.partialFetch(this.props.location.fetchDataFromURL.href) 
                :   this.fetchAll("maintenance");
    }

    partialFetch(hateoasUrl){
        BackendService.fetchDataFrom(hateoasUrl)
        .then(
            response => {
                console.log(response)
                this.setState({
                    loading : false, 
                    maintenance : response.data._embedded.maintenanceViewDtoList
                }, () => this.props.handleInfo({success : MESSAGES.successLoaded})
                );
                
            }
        ).catch(e => {
            this.props.handleInfo({error : e.response.data.message});
        })
    }

    fetchAll(urlParam){
        BackendService.getAll(urlParam)
        .then(
            response => {
                this.setState({
                    loading : false, 
                    maintenance : response.data
                }, () => this.props.handleInfo({success : MESSAGES.successLoaded})
                );
                console.log(response.data)
            }
        ).catch(e => {
            this.props.handleInfo({error : e.response.data.message});
        });
    }
   
    selectMaintenance(maintenance) {      
        this.setState({selected: maintenance})
    }
    handleChange(event){
        let eName = event.target.name
        let eValue = event.target.value
        let eCheked = event.target.checked
        
        let updatePair = eCheked ? {[eName] : eCheked }: {[eName]: eValue}
        
        this.setState(
            {   ...this.state,
                selected: {...this.state.selected, ...updatePair}
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
        }, () => submit(selected.maintenanceNum, selected) );
    
      }
  
      submitUpdate(maintenanceNum, selected){
        if(Utils.formIsValid(this.state.errors)) {
            BackendService.updateOne("maintenance", maintenanceNum, selected)
                .then((r) => {                        
                    this.refreshMaintenance()
                    this.props.handleInfo({success : MESSAGES.successUpdated});
                }).catch(e => {
                    this.props.handleInfo({error : e.response.data.message});
                    // this.props.handleInfo({error : e});
                })
            console.log('submit Update')
        }
      }
  
      submitCreate(maintenanceNum, selected){  
        if(Utils.formIsValid(this.state.errors)) {
            BackendService.createOne("maintenance", maintenanceNum, selected)
                .then(() => {                        
                    this.refreshMaintenance()
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
                    
                    { this.state.loading && <CircularProgress color="secondary"/> }
                    <DataComponent 
                        tableRows={this.state.maintenance} 
                        tableHeader={MAINTENANCE_HEADER_DATA}
                        selectedId={this.state.selected.maintenanceNum}
                        selectRow={this.selectMaintenance} 
                    />
                </Paper>
               </Grid>

              <Grid item xs={12} md={6} lg={4}>
                <Paper className={fixedHeightPaper}>
                  {this.state.selected.maintenanceNum && 
                    <EditGlobalComponent
                        selected={this.state.selected} 
                        selectedId={this.state.selected.maintenanceNum}
                        handleChange={this.handleChange} 
                        handleInfo={this.handleInfo}
                        labels = {MAINTENANCE_HEADER_DATA} 
                        booleanFields = {MAINTENANCE_BOOLEAN_FIELDS}
                        disabledFields={MAINTENANCE_DISABLED_FIELDS}
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


MaintenanceComponent.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(withRouter(MaintenanceComponent));