import React, { Component } from 'react'
import BackendService from '../../../api/CommonAPI.js'
import {  MESSAGES } from '../../../Constanst.js';
import { styles } from '../../UseStyles.js'
import Utils from '../../Utils.js';
import CreateUpdateBtnGroup from '../CreateUpdateBtnGroup'

import TextField from '@material-ui/core/TextField';
import Checkbox from '@material-ui/core/Radio';
import SendIcon from '@material-ui/icons/Send';

import CloudUploadIcon from '@material-ui/icons/CloudUpload';

import FormControlLabel from '@material-ui/core/FormControlLabel';
import InputLabel from '@material-ui/core/InputLabel';

import Select from '@material-ui/core/Select';

import Button from '@material-ui/core/Button';

import PropTypes from 'prop-types';
import { withRouter} from 'react-router-dom';


import { MuiThemeProvider, withStyles } from '@material-ui/core/styles';
import { RadioGroup, FormControl, FormHelperText, FormLabel, FormGroup, ButtonGroup } from '@material-ui/core';


class EditMaintenance extends Component {
    constructor(props){
      super(props)
      this.state = {
          facilities: {},
          errors: {},
          
      }

      
      this.validateAndSubmit = this.validateAndSubmit.bind(this);
      this.submitUpdate = this.submitUpdate.bind(this);
      this.submitCreate = this.submitCreate.bind(this);
    }

    validateAndSubmit(submit){
      const { selectedTask } = this.props;
      
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
      }, () => submit(selectedTask.taskNum, selectedTask) );
  
    }

    submitUpdate(companyNum, selectedTask){
      if(Utils.formIsValid(this.state.errors)) {
          BackendService.updateOne("users", companyNum, selectedTask)
              .then((r) => {                        
                  this.props.refreshTasks()
                  this.props.handleInfo({success : MESSAGES.successUpdated});
              }).catch(e => {
                  this.props.handleInfo({error : e.response.data.message});
              })
          console.log('submit Update')
      }
    }

    submitCreate(taskNum, selectedTask){  
      if(Utils.formIsValid(this.state.errors)) {
          BackendService.createOne("tasks", taskNum, selectedTask)
              .then(() => {                        
                  this.props.refreshTasks()
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
      const { selectedTask, booleanFields, labels, handleChange} = this.props;        

      return (
          <MuiThemeProvider key={selectedTask.taskNum} > 
              
          {Object.keys(this.props.labels).map(key => {
              console.log("I render")
              return (
                    // key !== "roles" && 
                    // key !== "facility" &&
                  
                  
                  <TextField
                      id={key}
                      name={key}
                      label={labels[key]}
                      defaultValue={selectedTask[key]}
                      onChange={handleChange}
                      error={this.state.errors[key] && this.state.errors[key].length > 0}
                      helperText={this.state.errors[key]}
                  /> 
              )    
          })}

          
            {  Object.keys(booleanFields).map(a => {                                              
                      return (
                          
                          <FormControlLabel control={<Checkbox name={a} value={selectedTask[a]} key={a}  />} label={a} />

                      )    
                  })}

          {/* <FormGroup row>       
              <FormControl required error={this.state.errors.authority} component="fieldset" className={classes.formControl}>
                  <FormLabel component="legend">Pick Authority</FormLabel>
                  <RadioGroup  value={authority} onChange={handleAuthorityRoleChange}>
                  {  Object.keys(booleanFields).map(a => {                                              
                      return (
                          <FormControlLabel control={
                                          <Checkbox name='authority' value={a} key={a} />} 
                                      label={a}  />
                      )    
                  })}
                  </RadioGroup> */}
                  {/* <FormHelperText>{this.state.errors.authority}</FormHelperText>
              </FormControl> */}

              
          {/* </FormGroup> */}
          
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

EditMaintenance.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(EditMaintenance);
