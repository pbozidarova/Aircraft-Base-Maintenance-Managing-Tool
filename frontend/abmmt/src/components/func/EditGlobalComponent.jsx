import React, { Component } from 'react'
import { styles } from '../UseStyles.js'
import Utils from '../Utils.js'
import {MESSAGES} from '../../Constanst.js'

import CreateUpdateBtnGroup from './CreateUpdateBtnGroup'

import TextField from '@material-ui/core/TextField';
import Checkbox from '@material-ui/core/Checkbox';

import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';
import Select from '@material-ui/core/Select';
import BackendService from '../../api/CommonAPI.js'

import PropTypes from 'prop-types';

import { MuiThemeProvider, withStyles } from '@material-ui/core/styles';
import { FETCH_DATA_KEY, GLOBAL_SELECT_FIELDS } from '../../Constanst.js';



class EditGlobalComponent extends Component {
    
      constructor(props){
          super(props)
          this.state = {
              facility: {},
              aircraftRegistration: {},
              manager: {},
              responsibleEngineer: {},
              //TODO!!!!
              authority: {},
              role: {}
          }

        this.refreshSelectMenusOptions = this.refreshSelectMenusOptions.bind(this);
        
      }
      componentDidMount(){
        //  this.refreshSelectMenusOptions();
      }

      refreshSelectMenusOptions(){
          Object.keys(GLOBAL_SELECT_FIELDS).forEach(select =>{
            let keyResponse = FETCH_DATA_KEY[select]
            let keyState = GLOBAL_SELECT_FIELDS[select][0]
            let keyValues = GLOBAL_SELECT_FIELDS[select].slice(1)
            
            select != 'responsibleEngineer' &&
                      
            BackendService.getAll(keyState)
              .then(response => {              
                let responseObj = Object.values(response.data._embedded[keyResponse]);
                let stateArray = responseObj.map(row => {
                      return select != 'manager' 
                            ? row[keyValues[0]]
                            : `${row[keyValues[0]]} - ${row[keyValues[1]]}, ${row[keyValues[2]]}` 
                })
                
                let stateObj = (select != 'manager' 
                                      ? {[select] : stateArray} 
                                      : {[select] : stateArray, 'responsibleEngineer' : stateArray})

                this.setState({
                    ...this.state,
                    ...stateObj
                })
              }).catch(e => Utils.errorMessage(e, this.props.handleInfo, MESSAGES.errorSomethingIsWrong ));

            })
      }
  
  
    render(){
      const { classes } = this.props;
      const { selected, booleanFields, disabledFields, labels, handleChange, errors, selectedId, validateAndSubmit, submitUpdate, submitCreate} = this.props;        

      return (
          <MuiThemeProvider key={selectedId} > 
             
          {console.log(selected) ||
          Object.keys(GLOBAL_SELECT_FIELDS).map(key => {                                              
            // console.log(key)
            // console.log(key + ' ' + selected[key])
            return (
              this.props.labels[key] &&
              <FormControl 
                className={classes.formControl} 
                error={errors[key] && errors.length > 0}
                helperText={errors[key]}
                >
                <InputLabel htmlFor={key}>Select {this.props.labels[key]}</InputLabel>
                <Select
                    native
                    value={selected[key]}
                    onChange={handleChange}
                    id={key}
                    name={key}
                    >
                    <option aria-label="None" value="" />
                    {Object.values(this.state[key]).map(f => <option value={f}>{f}</option>)}
                    
                </Select>
                {/* <FormHelperText>{this.state.errors[key]}</FormHelperText> */}
              </FormControl> 
            )    
        })}

          {Object.keys(this.props.labels).map(key => {
              return (
                     booleanFields[key] == null && 
                     GLOBAL_SELECT_FIELDS[key] == null &&
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
