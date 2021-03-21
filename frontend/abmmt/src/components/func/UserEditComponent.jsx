import React, { Component } from 'react'
import TextField from '@material-ui/core/TextField';
import Radio from '@material-ui/core/Radio';


import FormControlLabel from '@material-ui/core/FormControlLabel';

import Button from '@material-ui/core/Button';
import Utils from '../Utils.js'

import PropTypes from 'prop-types';
import { withRouter} from 'react-router-dom';


import { styles } from '../UseStyles.js'
import { MuiThemeProvider, withStyles } from '@material-ui/core/styles';
import { RadioGroup, FormControl, FormHelperText, FormLabel, FormGroup } from '@material-ui/core';


class EditUserComponent extends Component {

    constructor(props){
        super(props)
        this.state = {
            errors: {},
            
        }
        this.handleChange = this.handleChange.bind(this);
        this.rolesBuilder = this.rolesBuilder.bind(this);
        this.validate = this.validate.bind(this);
        this.formIsValid = this.formIsValid.bind(this);
        this.submit = this.submit.bind(this);
        
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
        // console.log(this.state)
        
        this.props.handleChange(event, this.rolesBuilder())
    
    }

    rolesBuilder(){
        
        let roles = Object.keys(this.state.roles)
                .reduce( (acc, val) => {
                    acc += this.state.roles[val]  ? `${val}, ` : ''
                    return acc
                }, ''
                    ).replace(/,\s*$/, "")
                // ;

            console.log(roles)
        return roles
    
    }

    async validate(){
        
        this.setState({ errors: 
             { 
                companyNum: /^[N]\d{5}$/.test(this.props.selectedUser.companyNum) ? null : "Follow the pattern N plus 5 digits!" ,
                firstName: this.props.selectedUser.firstName.length > 2 ? null : "The first name must contain more than 2 digits!" ,
                lastName: this.props.selectedUser.lastName.length > 2 ? null : "The last name must contain more than 2 digits!",
                email: /^\S+@\S+$/.test(this.props.selectedUser.email)  ? null : "Please provide a valid email!",

                //TODO!!!
                facility: this.props.selectedUser.facility.length > 2 ? null : "Please select a facility!",
                
                authoriry: this.props.selectedUser.roles.length > 0 ? null : "At least one authoriry must be checked!",
                role: this.props.selectedUser.roles.length > 0 ? null : "At least one role must be checked!",

             }
        },  this.formIsValid);

           
    }

    formIsValid(){
        //CHECK IF THE FORM IS VALID
        console.log('istheretrue' +Object.values(this.state.roles).includes(true))
        let formIsValid = true;
        Object.keys(this.state.errors).forEach( errorField => {
            console.log('error ' + this.state.errors[errorField])
        
            if(this.state.errors[errorField] ) {
                formIsValid = false;                
                return;
            }
        })
        
        return formIsValid;
    }

    submit(){
        
        if(this.formIsValid()) console.log('submit')
        
    }


    render(){
        const { classes } = this.props;
        const { selectedUser, booleanFields, handleChange, authoriry, role, handleAuthorityRoleChange } = this.props;
        const links = this.props.selectedUser._links;
        

        return (
            <MuiThemeProvider row key={selectedUser.companyNum} > 
                
                {Object.keys(this.props.labels).map(key => {
                      
                        return (
                            ( key !== "roles" && key !== "companyNum") &&
                            <TextField
                                id={key}
                                name={key}
                                label={this.props.labels[key]}
                                defaultValue={selectedUser[key]}
                                onChange={handleChange}
                                error={this.state.errors[key]}
                                helperText={this.state.errors[key]}
                            /> 
                        
                    )    
                })}
                <FormGroup row>
                <FormControl required error={this.state.errors.authoriry} 
                    component="fieldset" className={classes.formControl}>
                    <FormLabel component="legend">Pick Authority</FormLabel>
                    <RadioGroup  value={authoriry} onChange={handleAuthorityRoleChange}>
                    {  Object.keys(booleanFields).slice(0, 2).map(a => {                                              
                        return (
                            <FormControlLabel control={
                                            <Radio box name='authoriry' value={a} key={a} />} 
                                        label={a}  />
                            
                        )    
                    })}
                    </RadioGroup>
                    <FormHelperText>{this.state.errors.authoriry}</FormHelperText>
               </FormControl>

               <FormControl required error={this.state.errors.role} component="fieldset">
                    <FormLabel component="legend">Pick Role</FormLabel>
                    <RadioGroup   value={role} onChange={handleAuthorityRoleChange}>
                        {  Object.keys(booleanFields).slice(2, 4).map(r => {                                              
                            return (
                                <FormControlLabel control={
                                                    <Radio box name='role' value={r} key={r} />} 

                                                label={r}  />
                            )    
                        })}
                    </RadioGroup>
                    <FormHelperText>{this.state.errors.role}</FormHelperText>
               </FormControl>
               </FormGroup>
                   
                
                <Button 
                        variant="contained" 
                        className={classes.menuButton}
                        color="primary"

                        onClick={() => {this.validate(); this.submit()}}
                        >
                        Update
                    </Button>
                
                
            </MuiThemeProvider>
            
        )
    }
}

EditUserComponent.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(withRouter(EditUserComponent));