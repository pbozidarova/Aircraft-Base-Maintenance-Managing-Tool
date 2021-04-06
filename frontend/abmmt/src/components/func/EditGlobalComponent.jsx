import React, { Component } from 'react'
import { styles } from '../UseStyles.js'
import Utils from '../Utils.js'
import {ICONS_MAPPING, MESSAGES} from '../../Constanst.js'

import MuiAlert from '@material-ui/lab/Alert';

import CreateUpdateBtnGroup from './CreateUpdateBtnGroup'

import TextField from '@material-ui/core/TextField';
import Checkbox from '@material-ui/core/Checkbox';

import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';
import FormHelperText from '@material-ui/core/FormHelperText';
import InputLabel from '@material-ui/core/InputLabel';
import Select from '@material-ui/core/Select';
import BackendService from '../../api/CommonAPI.js'

import PropTypes from 'prop-types';
import Autocomplete from '@material-ui/lab/Autocomplete';

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
              maintenanceNum: {},
              taskNum: {},
              //TODO!!!!
              authority: {},
              role: {}
          }

        this.refreshSelectMenusOptions = this.refreshSelectMenusOptions.bind(this);
        this.Alert = this.Alert.bind(this);

      }
      componentDidMount(){
         this.refreshSelectMenusOptions();
      }

      Alert(props) {
        return <MuiAlert elevation={3} variant="filled" {...props} />;
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
      const { selected, booleanFields, feedback, editFields, labels, handleChange, handleAutocompleteChange, errors, selectedId, validateAndSubmit, refreshData} = this.props;        
      let isError = (key) => errors[key] && errors[key].length > 0
      let isOptional = (Object.keys(labels).includes('aircraftRegistration') && Object.keys(labels).includes('serialNumber')) ||
                        (Object.keys(labels).includes('taskNum') && Object.keys(labels).includes('code')) 

      return (
          <MuiThemeProvider key={selectedId} > 
          <this.Alert severity="info" >{feedback} </this.Alert>
          
          {Object.keys(GLOBAL_SELECT_FIELDS).map(key => {                                              
            return (
              this.props.labels[key] &&
              <Autocomplete
                id="free-solo-demo"
                size="small"
                freeSolo={isOptional}
                value={selected[key]}
                onChange={(e, v) => handleAutocompleteChange(e, v, key)}
                options={Object.values(this.state[key])}
                renderInput={(params) => (
                  <TextField {...params} 
                        label={labels[key]}  
                        margin="normal" 
                        variant="outlined"
                        onChange={(e)=> handleChange(e, key)} 
                        error={isError(key)}
                        helperText={errors[key]}/>
                  )}
                
              />
            )    
        })}

          {Object.keys(labels).map(key => {
              return (
                     booleanFields[key] == null && 
                     GLOBAL_SELECT_FIELDS[key] == null &&
                     editFields[key] &&
                    // key !== "facility" &&
                  <TextField
                      id={key}
                      size="small"
                      margin="normal" variant="outlined"
                      name={key}
                      label={labels[key]}
                      type={key.includes('Date') && 'date'}
                      defaultValue={selected[key]}
                      // disabled={disabledFields[key]}
                      rows={key =='description' ? 4 : 1}
                      multiline={key =='description'}
                      onChange={handleChange}
                      error={isError(key)}
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
            refreshData={refreshData}
            // submitUpdate={submitUpdate}
            // submitCreate={submitCreate}
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
