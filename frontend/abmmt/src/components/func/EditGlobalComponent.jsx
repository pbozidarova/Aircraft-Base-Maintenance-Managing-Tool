import React, { Component } from 'react'
import { styles } from '../UseStyles.js'
import Utils from '../Utils.js'
import MuiAlert from '@material-ui/lab/Alert';
import CreateUpdateBtnGroup from './CreateUpdateBtnGroup'
import TextField from '@material-ui/core/TextField';
import Checkbox from '@material-ui/core/Checkbox';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import BackendService from '../CommonAPI.js'

import PropTypes from 'prop-types';
import Autocomplete from '@material-ui/lab/Autocomplete';

import { MuiThemeProvider, withStyles } from '@material-ui/core/styles';
import {MESSAGES, FETCH_DATA_KEY, GLOBAL_SELECT_FIELDS } from '../Constants.js';



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
              status: ['OPENED', 'PROGRESSING', 'CLOSED'],
              classification: ['CLARIFICATION', 'IMPROVEMENT', 'MISTAKE', 'REFUSED'],
              authority: ['ADMIN', 'USER'],
              role: ['MECHANIC', 'ENGINEER'],

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
            
            !['responsibleEngineer', 'status', 'classification', 'role', 'authority'].includes( select )  &&
                      
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
      const { selected, booleanFields, feedback, 
        editFields, labels, handleChange, handleAutocompleteChange, 
        errors, selectedId, validateAndSubmit, refreshData, reset} = this.props;        
      let isError = (key) => errors[key] && errors[key].length > 0

      return (
          <MuiThemeProvider key={selectedId} > 
          <this.Alert severity="info" className={classes.tableCell}  variant="outlined" > {feedback} </this.Alert>
          
          {Object.keys(GLOBAL_SELECT_FIELDS).map(key => {                                              
            console.log(this.state[key])
            return (
              this.props.editFields[key] &&
              <Autocomplete
                id={key}
                size="small"
                value={selected[key]}
                InputProps={{
                  classes: {
                    input: classes.tableCell,
                  },
                }}
                onChange={(e, v) => handleAutocompleteChange(e, v, key)}
                options={Object.values(this.state[key])}
                renderInput={(params) => (
                  <TextField {...params} 
                        name={key}
                        label={editFields[key]}  
                        margin="normal" 
                        variant="outlined"
                        onChange={(e)=> handleChange(e, key)} 
                        error={isError(key)}
                        helperText={errors[key]}
                        />
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
                      className={classes.tableCell} 
                      margin="normal" variant="outlined"
                      name={key}
                      label={labels[key] }
                      type={key.includes('Date') && 'date'}
                      defaultValue={selected[key]}
                      // disabled={disabledFields[key]}
                      rows={key =='description' ? 4 : 1}
                      multiline={key =='description'}
                      onChange={handleChange}
                      error={isError(key)}
                      helperText={errors[key]}
                      InputLabelProps={{ shrink: true }}
                      InputProps={{
                        classes: {
                          input: classes.tableCell,
                        },
                      }}
                  /> 
              )    
          })}

          
          
            {  Object.keys(booleanFields).map(a => {                                              
                // console.log(selected)
                return (
                  <FormControlLabel className={classes.tableCell} 
                      control={
                        <Checkbox
                          checked={selected[a]}
                          onChange={(e) => handleChange(e)}
                          name={a}
                          color="primary"
                        />
                      }
                      
                      label={<span className={classes.tableCell}>{booleanFields[a]}</span>}
                    />
                )    
            })}
          
           <CreateUpdateBtnGroup
            validateAndSubmit={validateAndSubmit}
            refreshData={refreshData}
            reset={reset}
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
