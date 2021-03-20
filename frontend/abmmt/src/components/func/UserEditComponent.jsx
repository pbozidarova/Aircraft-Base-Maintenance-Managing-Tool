import React, { Component } from 'react'
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import PropTypes from 'prop-types';
import { withRouter} from 'react-router-dom';

import Utils from '../Utils.js'

import { styles } from '../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';
import { ButtonGroup } from '@material-ui/core';

class EditUserComponent extends Component {
    

    render(){
        const { classes } = this.props;

        return (
            <div key={this.props.selectedUser.companyNum}>
                {Object.keys(this.props.labels).map(key => {
                    return (

                            <TextField
                                error
                                id="standard-error-helper-text"
                                label={key}
                                defaultValue={this.props.selectedUser[key]}
                                helperText="Incorrect entry."
                            />
                        
                    )    
                })}
                <br/>
                <ButtonGroup>
                    <Button 
                        variant="contained" 
                        className={classes.menuButton}
                        // onClick={  () => {this.redirectTo("/logout"); AuthenticationService.logout()}}
                        >
                        Check Projects
                    </Button>
                    <Button 
                        variant="contained" 
                        className={classes.menuButton}
                        onClick={  () => { console.log(this.props.selectedUser._links.tasks.href); Utils.redirectTo(this.props, "/mpd");}}
                        >
                        Check Tasks
                    </Button>
                    <Button 
                        variant="contained" 
                        className={classes.menuButton}
                        // onClick={  () => {this.redirectTo("/logout"); AuthenticationService.logout()}}
                        >
                        Check Issues
                    </Button>
                </ButtonGroup>
            </div>
            
        )
    }
}

EditUserComponent.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(withRouter(EditUserComponent));