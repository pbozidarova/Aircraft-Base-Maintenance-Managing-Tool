import React, { Component } from 'react'
import BackendService from '../../../api/CommonAPI.js'
import {  MESSAGES } from '../../../Constanst.js';
import { styles } from '../../UseStyles.js'
import Utils from '../../Utils.js';
import CreateUpdateBtnGroup from '../CreateUpdateBtnGroup'

import TextField from '@material-ui/core/TextField';
import Radio from '@material-ui/core/Radio';


import FormControlLabel from '@material-ui/core/FormControlLabel';
import InputLabel from '@material-ui/core/InputLabel';

import Select from '@material-ui/core/Select';



import PropTypes from 'prop-types';
import { withRouter} from 'react-router-dom';


import { MuiThemeProvider, withStyles } from '@material-ui/core/styles';
import { RadioGroup, FormControl, FormHelperText, FormLabel, FormGroup, ButtonGroup, Button } from '@material-ui/core';


class EditUserComponent extends Component {

    constructor(props){
        super(props)
        this.state = {
            facilities: {},
            errors: {},
            
        }

        this.refreshFacilities = this.refreshFacilities.bind(this);
        this.validateAndSubmit = this.validateAndSubmit.bind(this);
        this.submitUpdate = this.submitUpdate.bind(this);
        this.submitCreate = this.submitCreate.bind(this);
        
    }

    componentDidMount(){
        this.refreshFacilities();
    }

   refreshFacilities(){
       BackendService.getAll('facilities')
        .then(response => {
            this.setState({
                ...this.state,
                facilities: Object.values(response.data._embedded.facilityViewDtoList)
            })
        })
   }

    validateAndSubmit(submit){
        const { selectedUser } = this.props;
        
        this.setState({ errors: 
             { 
                companyNum: /^[N]\d{5}$/.test(selectedUser.companyNum) ? '' : "Follow the pattern N plus 5 digits!" ,
                firstName:  selectedUser.firstName != 'First Name' && selectedUser.firstName.length > 2 ? '' : "The first name must be longer than 2 symbols!" ,
                lastName:  selectedUser.lastName != 'Last Name' && selectedUser.lastName.length > 2 ? '' : "The last name must be longer than 2 symbols!",
                email: /^\S+@\S+$/.test(selectedUser.email)  ? '' : "Please provide a valid email!",
                facility: selectedUser.facility.length > 2 ? '' : "Please select a facility!",
                
                authority: selectedUser.roles.length > 0 ? '' : "At least one authority must be checked!",
                role: selectedUser.roles.length > 0 ? '' : "At least one role must be checked!",

             }
        }, () => submit(selectedUser.companyNum, selectedUser) );
    
    }

    submitUpdate(companyNum, selectedUser){
        if(Utils.formIsValid(this.state.errors)) {
            BackendService.updateOne("users", companyNum, selectedUser)
                .then((r) => {                        
                    this.props.refreshUsers()
                    this.props.handleInfo({success : MESSAGES.successUpdated});
                }).catch(e => {
                    this.props.handleInfo({error : e.response.data.message});
                })
            console.log('submit Update')
        }
    }

    submitCreate(companyNum, selectedUser){  
        if(Utils.formIsValid(this.state.errors)) {
            BackendService.createOne("users", companyNum, selectedUser)
                .then(() => {                        
                    this.props.refreshUsers()
                    this.props.handleInfo({success : MESSAGES.successCreated});
                }
                ).catch(e => {
                    this.props.handleInfo({error : e.response.data.message});
                })

            console.log('submit Create')
        }
    }


    render(){
        const { classes } = this.props;
        const { selectedUser, booleanFields, labels, handleChange, authority, role, handleAuthorityRoleChange } = this.props;        

        return (
            <MuiThemeProvider key={selectedUser.companyNum} > 
                
            {Object.keys(this.props.labels).map(key => {
                return (
                    booleanFields[key] ==null && key !== "facility" &&
                                        
                    <TextField
                        id={key}
                        name={key}
                        label={labels[key]}
                        defaultValue={selectedUser[key]}
                        onChange={handleChange}
                        error={this.state.errors[key] && this.state.errors[key].length > 0}
                        helperText={this.state.errors[key]}
                    /> 
                )    
            })}

            <FormControl 
                className={classes.formControl} 
                error={this.state.errors.facility && this.state.errors.facility.length > 0}>
                <InputLabel htmlFor="facility">Select Facility</InputLabel>
                <Select
                    native
                    value={this.props.selectedUser.facility}
                    onChange={handleChange}
                    inputProps={{
                        name: 'facility',
                        id: 'facility',
                }}
                >
                    <option aria-label="None" value="" />
                    {Object.values(this.state.facilities).map(f => <option value={f.name}>{f.name}</option>)}
                    
                </Select>
                <FormHelperText>{this.state.errors.facility}</FormHelperText>
            </FormControl>   
             
            <FormGroup row>       
                <FormControl required error={this.state.errors.authority} component="fieldset" className={classes.formControl}>
                    <FormLabel component="legend">Pick Authority</FormLabel>
                    <RadioGroup  value={authority} onChange={handleAuthorityRoleChange}>
                    {  Object.keys(booleanFields).slice(0, 2).map(a => {                                              
                        return (
                            <FormControlLabel control={
                                            <Radio name='authority' value={a} key={a} />} 
                                        label={a}  />
                        )    
                    })}
                    </RadioGroup>
                    <FormHelperText>{this.state.errors.authority}</FormHelperText>
                </FormControl>

                <FormControl required error={this.state.errors.role} component="fieldset">
                    <FormLabel component="legend">Pick Role</FormLabel>
                    <RadioGroup value={role} onChange={handleAuthorityRoleChange}>
                        {  Object.keys(booleanFields).slice(2, 4).map(r => {                                              
                            return (
                                <FormControlLabel control={
                                                    <Radio name='role' value={r} key={r} />} 
                                                label={r}  />
                            )    
                        })}
                    </RadioGroup>
                    <FormHelperText>{this.state.errors.role}</FormHelperText>
                </FormControl>
            </FormGroup>

            <CreateUpdateBtnGroup
                validateAndSubmit={this.validateAndSubmit}
                submitUpdate={this.submitUpdate}
                submitCreate={this.submitCreate}
                classes={this.classes}
                />
            
            </MuiThemeProvider>
            
        )
    }
}

EditUserComponent.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(withRouter(EditUserComponent));