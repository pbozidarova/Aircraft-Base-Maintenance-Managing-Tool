import React, { Component } from 'react'
import TextField from '@material-ui/core/TextField';
import Checkbox from '@material-ui/core/Checkbox';

import FormControlLabel from '@material-ui/core/FormControlLabel';

import Button from '@material-ui/core/Button';

import PropTypes from 'prop-types';
import { withRouter} from 'react-router-dom';

import Utils from '../Utils.js'

import { styles } from '../UseStyles.js'
import { MuiThemeProvider, withStyles } from '@material-ui/core/styles';
import { ButtonGroup } from '@material-ui/core';

class EditUserComponent extends Component {

    constructor(props){
        super(props)
        this.state = {
            errors: {},
            ADMIN: false,
            USER: false,
            ENGINEER: false,
            MECHANIC: false
            
        }
        this.handleChange = this.handleChange.bind(this);
        this.rolesBuilder = this.rolesBuilder.bind(this);
        this.validate = this.validate.bind(this);
        
    }

    handleChange(event){
        console.log(this.state)

        this.setState(
            {  ...this.state, 
                [event.target.name]
                    :event.target.checked
            }
            )
            console.log(event.target.checked)
            // console.log(this.state)

            this.rolesBuilder()
    
    }

    rolesBuilder(){
        console.log(this.state)
    }

    validate(){
        console.log(this.props.selectedUser.companyNum) 
        console.log( /^[N]\d{5}$/.test(this.props.selectedUser.companyNum))
        console.log(this.props.selectedUser.firstName) 
        this.setState({ errors: 
             { 
                companyNum: /^[N]\d{5}$/.test(this.props.selectedUser.companyNum) ? "" : "Follow the pattern N plus 5 digits!" ,
                firstName: this.props.selectedUser.firstName.length > 2 ? '' : "The first name must contain more than 5 digits!" ,
                lastName: this.props.selectedUser.lastName.length > 2 ? '' : "The last name must contain more than 5 digits!",
                email: /^\S+@\S+$/.test(this.props.selectedUser.email)  ? '' : "Please provide a valid email!",
                //TODO!!!
                facility: this.props.selectedUser.facility.length > 2 ? '' : "Please select a facility!",
                roles: this.props.selectedUser.roles.length > 0 ? '' : "This Field is required",
             }
        });
        console.log(this.state.errors)
       
    }

    render(){
        const { classes } = this.props;
        const { selectedUser, booleanFields, handleChange } = this.props;
        const links = this.props.selectedUser._links;
        

        return (
            <MuiThemeProvider key={selectedUser.companyNum} > 
                
                {Object.keys(this.props.labels).map(key => {
                    return (
                            <TextField
                            id={key}
                            name={key}
                            label={this.props.labels[key]}
                            disabled={key === 'roles'}
                            defaultValue={selectedUser[key]}
                            onChange={handleChange}
                            // helperText="Incorrect entry."
                            error={this.state.errors[key] }
                            helperText={this.state.errors[key]}
                            /> 
                    )    
                })}
                
                {  Object.keys(booleanFields).map(f => {                      
            

                    return (
                        <FormControlLabel control={
                                        <Checkbox name={f} 
                                            checked={this.state[f]}
                                            key={f} 
                                            onChange={this.handleChange}

                                        />} 
                                        label={f}  />
                        
                    )    
                })}
                    
                <ButtonGroup>
                    <Button 
                        variant="contained" 
                        className={classes.menuButton}
                        onClick={  () => { console.log(links.maintenance.href); Utils.redirectTo(this.props, "/maintenance");}}
                        >
                        Check Projects
                    </Button>
                    <Button 
                        variant="contained" 
                        className={classes.menuButton}
                        onClick={  () => { console.log(links.tasks.href); Utils.redirectTo(this.props, "/mpd");}}
                        >
                        Check Tasks
                    </Button>
                    <Button 
                        variant="contained" 
                        className={classes.menuButton}
                        // onClick={  () => {this.redirectTo("/logout"); AuthenticationService.logout()}}
                        >
                        Check Notifications
                    </Button>
                </ButtonGroup>
                <ButtonGroup>
                <Button 
                        variant="contained" 
                        className={classes.menuButton}
                        color="primary"

                        onClick={this.validate}
                        >
                        Update
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