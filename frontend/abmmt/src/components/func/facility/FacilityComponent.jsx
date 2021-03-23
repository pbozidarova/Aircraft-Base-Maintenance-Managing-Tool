import React, {Component} from 'react'
import BackendService from '../../../api/CommonAPI.js'
import {FACILITIES_HEADER_DATA, MESSAGES} from '../../../Constanst.js'

import { styles } from '../../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';

import DataComponent from '../DataComponent'
import EditFacility from './EditFacility';

import PropTypes from 'prop-types';
import clsx from 'clsx';

import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';


class FacilityComponent extends Component {
    constructor(props){
        super(props)
        
        this.state = {
            infoPanel : {
                info: MESSAGES.initialLoad,
                success: MESSAGES.initialLoad,
                error: MESSAGES.initialLoad,
            },
            facilities : [],
            selected: {},
            
        }

        this.refreshUsers = this.refreshUsers.bind(this)
        this.selectFacility = this.selectFacility.bind(this)
        

    }
   
    componentDidMount(){
        // let username = AuthenticationService.isUserLoggedIn();
        this.refreshUsers();
        this.createEmptySelect();

    }

    refreshUsers(){
        BackendService.getAll('facilities')
            .then(
                response => {
                    // console.log(response.data);
                    this.setState({facilities : response.data});
                }
            ); 
    }

    createEmptySelect(){
        let emptyUser = Object.keys(FACILITIES_HEADER_DATA)
                            .reduce((acc, curr) =>  acc = {...acc, [curr] : ' '}, {} );
    
        this.selectFacility(emptyUser);
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
                        tableRows={this.state.facilities}
                        tableHeader = {FACILITIES_HEADER_DATA}
                        selected={this.state.selected}
                        selectRow={this.selectFacility} 
                    />

                </Paper>
              </Grid>
              <Grid item xs={12} md={6} lg={4}>
                <Paper className={fixedHeightPaper}>
                  {this.state.selected.companyNum && 
                    <EditFacility
                        selectedUser={this.state.selected} 
                        handleChange={this.handleChange} 
                        handleAuthorityRoleChange={this.handleAuthorityRoleChange} 
                        handleInfo={this.handleInfo}
                        refreshUsers={this.refreshUsers} 
                        labels = {FACILITIES_HEADER_DATA} 

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
