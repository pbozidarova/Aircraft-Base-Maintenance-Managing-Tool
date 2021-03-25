import React, { Component } from 'react'
import BackendService from '../../api/CommonAPI.js'
import {  MESSAGES } from '../../Constanst.js';
import { styles } from '../UseStyles.js'
import Utils from '../Utils.js';
import CreateUpdateBtnGroup from './CreateUpdateBtnGroup'

import TextField from '@material-ui/core/TextField';
import Checkbox from '@material-ui/core/Checkbox';
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


class EditGlobalComponent extends Component {
      
    render(){
      const { classes } = this.props;
      const { selected, booleanFields, disabledFields, labels, handleChange, errors, selectedId, validateAndSubmit, submitUpdate, submitCreate} = this.props;        

      return (
          <MuiThemeProvider key={selectedId} > 
              
          {Object.keys(this.props.labels).map(key => {
              console.log(key == 'description')
              return (
                     booleanFields[key] == null && 
                    // key !== "facility" &&
                  <TextField
                      id={key}
                      name={key}
                      label={labels[key]}
                      defaultValue={selected[key]}
                      disabled={disabledFields[key]}
                      rows={key =='description' ? 5 : 1}
                      multiline={key =='description'}
                      onChange={handleChange}
                      error={errors[key] && errors.length > 0}
                      helperText={errors[key]}
                  /> 
              )    
          })}

          
            {  Object.keys(booleanFields).map(a => {                                              
                      // console.log(selected)
                      return (
                        <FormControlLabel
                            control={
                              <Checkbox
                                checked={selected[a]}
                                onChange={handleChange}
                                name={a}
                                color="primary"
                              />
                            }
                            label={booleanFields[a]}
                          />
                          
                      )    
                  })}
          
           <CreateUpdateBtnGroup
            validateAndSubmit={validateAndSubmit}
            submitUpdate={submitUpdate}
            submitCreate={submitCreate}
            classes={classes}
            />
              
          </MuiThemeProvider>
          
      )
  }
}

EditGlobalComponent.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(EditGlobalComponent);
