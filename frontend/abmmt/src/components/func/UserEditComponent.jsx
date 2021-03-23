import React, { Component } from 'react'
import BackendService from '../../api/CommonAPI.js'

import TextField from '@material-ui/core/TextField';
import Radio from '@material-ui/core/Radio';


import FormControlLabel from '@material-ui/core/FormControlLabel';

import Button from '@material-ui/core/Button';

import PropTypes from 'prop-types';
import { withRouter} from 'react-router-dom';


import { styles } from '../UseStyles.js'
import { MuiThemeProvider, withStyles } from '@material-ui/core/styles';
import { RadioGroup, FormControl, FormHelperText, FormLabel, FormGroup, ButtonGroup } from '@material-ui/core';
import { USERS_HEADER_DATA, MESSAGES } from '../../Constanst.js';
import Utils from '../Utils.js';


class EditUserComponent extends Component {

    constructor(props){
        super(props)
        this.state = {
            errors: {},
            
        }
        this.handleChange = this.handleChange.bind(this);
        this.validateAndSubmit = this.validateAndSubmit.bind(this);
        this.submitUpdate = this.submitUpdate.bind(this);
        this.submitCreate = this.submitCreate.bind(this);
        
    }

    handleChange(event){
        
        this.setState(
            {  ...this.state, 
                 roles : {
                     ...this.state.roles, 
                     [event.target.name]
                     :event.target.checked
                 }
            }
        )            
    }


    validateAndSubmit(submit){
        const { selectedUser } = this.props;
        
        this.setState({ errors: 
             { 
                companyNum: /^[N]\d{5}$/.test(selectedUser.companyNum) ? '' : "Follow the pattern N plus 5 digits!" ,
                firstName:  selectedUser.firstName != 'First Name' && selectedUser.firstName.length > 2 ? '' : "The first name must contain more than 2 digits!" ,
                lastName:  selectedUser.lastName != 'Last Name' && selectedUser.lastName.length > 2 ? '' : "The last name must contain more than 2 digits!",
                email: /^\S+@\S+$/.test(selectedUser.email)  ? '' : "Please provide a valid email!",

                //TODO!!!
                facility: this.props.selectedUser.facility.length > 2 ? '' : "Please select a facility!",
                
                authority: this.props.selectedUser.roles.length > 0 ? '' : "At least one authority must be checked!",
                role: this.props.selectedUser.roles.length > 0 ? '' : "At least one role must be checked!",

             }
        }, () => submit(selectedUser.companyNum, selectedUser) );

    
    }


    submitUpdate(companyNum, selectedUser){
        if(Utils.formIsValid(this.state.errors)) {
            BackendService.updateOne("users", companyNum, selectedUser)
                .then(() => {                        
                    this.props.refreshUsers()
                    this.props.handleInfo({success : MESSAGES.successUpdated});
                }
                ).catch(e => {
                    this.props.handleInfo({error : MESSAGES.errorNonExistant});
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
                    this.props.handleInfo({error : MESSAGES.errorExist});
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
                        key !== "roles" && 
                    //  key !== "companyNum" &&
                    
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
                    <RadioGroup   value={role} onChange={handleAuthorityRoleChange}>
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
            <ButtonGroup row >  
                <Button 
                    variant="contained" 
                    className={classes.menuButton}
                    color="primary"

                    onClick={() => {this.validateAndSubmit(this.submitUpdate);}}
                    >
                    Update
                </Button>

                <Button 
                    variant="contained" 
                    className={classes.menuButton}
                    color="secodary"

                    onClick={() => {this.validateAndSubmit(this.submitCreate); }}
                    >
                    Create
                </Button>
            </ButtonGroup>
                
            </MuiThemeProvider>
            
        )
    }
}

EditUserComponent.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(withRouter(EditUserComponent));