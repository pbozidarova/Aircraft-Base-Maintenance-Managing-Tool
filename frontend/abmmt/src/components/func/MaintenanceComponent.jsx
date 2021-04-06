import React, { Component } from 'react'
import BackendService from '../../api/CommonAPI.js'
import {MAINTENANCE_HEADER_DATA, MAINTENANCE_BOOLEAN_FIELDS, MAINTENANCE_EDIT_FIELDS, MESSAGES} from '../../Constanst.js'
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
            selectedId: '',
            loading: true,       
            errors: {},     
        }

        this.refreshMaintenance = this.refreshMaintenance.bind(this)
        this.selectMaintenance = this.selectMaintenance.bind(this)
        this.handleChange = this.handleChange.bind(this)

        this.validateAndSubmit = this.validateAndSubmit.bind(this);
        this.submitUpdate = this.submitUpdate.bind(this)
        this.submitCreate = this.submitCreate.bind(this)
    }
    
    componentDidMount(){
        this.refreshMaintenance();
        this.selectMaintenance(Utils.emptyObj(MAINTENANCE_HEADER_DATA))
    }

    refreshMaintenance(){
        let key = 'maintenanceViewDtoList'

        let shouldFetchPartialData = this.props.location.fetchDataFromURL
        
        shouldFetchPartialData ? this.partialFetch(shouldFetchPartialData.href, shouldFetchPartialData.title, key) 
                               : this.fetchAll("maintenance", key)
    }

    partialFetch(hateoasUrl, title, key){
        BackendService.fetchDataFrom(hateoasUrl)
        .then(
            response => {
                this.setState({
                    loading : false, 
                    maintenance : response.data._embedded[key]
                }, () => Utils.infoMessage(this.props.handleInfo, title));
            }
        ).catch(e => Utils.errorMessage(e, this.props.handleInfo, title))
    }

    fetchAll(urlParam, key){
        BackendService.getAll(urlParam)
        .then(
            response => {
                this.setState({
                    loading : false, 
                    maintenance : response.data._embedded[key]
                }, () => Utils.infoMessage(this.props.handleInfo, MESSAGES.allData));
            }
        ).catch(e => Utils.errorMessage(e, this.props.handleInfo, MESSAGES.allData ));
    }
   
    selectMaintenance(maintenance, selectedId) {      
        this.setState({...this.state, selected: maintenance, selectedId})
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

                maintenanceNum: selected.maintenanceNum.length > 5 ? '' : "The maintenance number length must be more than 5 symbols." ,
                facility: selected.facility.length > 2 ? '' : "Please select a facility!",
                aircraftRegistration: selected.aircraftRegistration.length > 2 ? '' : "Please select an aircraft!",
                responsibleEngineer: selected.responsibleEngineer.length > 2 ? '' : "Please select an responsibleEngineer!",
                startDate: selected.startDate.length > 2 && Date.parse(selected.startDate) < Date.parse(selected.endDate) ? '' : "The date is mandatory and it must be lower than the end data!",
                endDate: selected.endDate.length > 2 && Date.parse(selected.startDate) < Date.parse(selected.endDate) ? '' : "The date is mandatory and it must be bigger than the start data!",  
             }
        }, () => submit(selected.maintenanceNum, selected) );
    
      }
  
      submitUpdate(maintenanceNum, selected){
        if(Utils.formIsValid(this.state.errors)) {
            BackendService.updateOne("maintenance", maintenanceNum, selected)
                .then((r) => {                        
                    this.refreshMaintenance()
                    Utils.successMessage(this.props.handleInfo, MESSAGES.successUpdated)
                }).catch(e => {
                    Utils.errorMessage(e, this.props.handleInfo )
                })
            console.log('submit Update')
        }
      }
  
      submitCreate(maintenanceNum, selected){  
        if(Utils.formIsValid(this.state.errors)) {
            BackendService.createOne("maintenance", maintenanceNum, selected)
                .then(() => {                        
                    this.refreshMaintenance()
                    Utils.successMessage(this.props.handleInfo, MESSAGES.successCreated)
                }
                ).catch(e => {
                    Utils.errorMessage(e, this.props.handleInfo )
                })
  
            console.log('submit Create')
        }
      }


    render(){
        const { classes, handleInfo } = this.props;
        const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

        return(
                           
            <Grid container spacing={3}>
              
              <Grid item xs={12} md={6} lg={8}>
                <Paper className={fixedHeightPaper}>
                    
                    { this.state.loading && <CircularProgress color="secondary"/> }
                    <DataComponent 
                        tableRows={this.state.maintenance} 
                        tableHeader={MAINTENANCE_HEADER_DATA}
                        selectedId={this.state.selectedId}
                        selectRow={this.selectMaintenance} 
                    />
                </Paper>
               </Grid>

              <Grid item xs={12} md={6} lg={4}>
                <Paper className={fixedHeightPaper}>
                  {this.state.selected.maintenanceNum && 
                    <EditGlobalComponent
                        selected={this.state.selected} 
                        selectedId={this.state.selectedId}
                        handleChange={this.handleChange} 
                        handleInfo={this.handleInfo}
                        labels = {MAINTENANCE_HEADER_DATA} 
                        booleanFields = {MAINTENANCE_BOOLEAN_FIELDS}
                        editFields={MAINTENANCE_EDIT_FIELDS}
                        errors={this.state.errors}
                        feedback={MESSAGES.maintenanceEditInfo}
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