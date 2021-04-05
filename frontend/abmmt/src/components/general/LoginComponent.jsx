import React, { Component } from 'react'
import {MESSAGES} from '../../Constanst.js'

import {Button, TextField } from '@material-ui/core'
import SaveIcon from '@material-ui/icons/Save'
import AuthenticationService from '../AuthenticationService.js'
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import Divider from '@material-ui/core/Divider';
import Utils from '../Utils.js'
import { withRouter } from 'react-router';
import { MuiThemeProvider } from '@material-ui/core/styles';

class LoginComponent extends Component{

    constructor(props){
        super(props)
        this.state = {
            username: 'N90909',
            password: '',
            
        }
        this.handleChange = this.handleChange.bind(this);
        this.loginClicked = this.loginClicked.bind(this);

    }

    handleChange(event){
        this.setState(
            {
                [event.target.name]
                    :event.target.value
            }
        )
    }

    loginClicked(){
        AuthenticationService
            .executeAuthnetication(this.state.username, this.state.password)
            .then(response => {
                console.log(response.data.token);
                console.log(this.props)
                AuthenticationService.registerSuccessfullLogin(this.state.username, response.data.token)
                
                Utils.redirectTo(this.props, '/home')
                this.props.fetchOpenNotifCount();

                Utils.successMessage(this.props.handleInfo, MESSAGES.successLogingIn)
            }).catch((e) => {
                // console.log(e)
                console.log(this.props)
                // this.props.handleInfo({error : e.response.data.message});
                Utils.errorMessage(e, this.props.handleInfo)
            })
    }

    render() {
        console.log('omg')
        return(
            <Grid container>

                <Grid item xs={12} md={5} lg={5}>

                    <Paper className="{fixedHeightPaper}">
                        
                            <TextField 
                                type="text" 
                                name="username" 
                                id="username" 
                                label="Username" 
                                variant="outlined"  
                                value={this.state.username} 
                                onChange={this.handleChange}
                                size="small"
                                helperText="Change to N70707 to experience a different role and authority!"
                            /> 
                            <br/>
                            <Divider/>
                            <br/>
                            <TextField 
                                type="password" 
                                name="password" 
                                id="password" 
                                label="Password"  
                                variant="outlined"  
                                value={this.state.password} 
                                onChange={this.handleChange}
                                size="small"
                                helperText="The password is 1234"
                                /> 
                        <div align="right">
                        <Button
                            startIcon={<SaveIcon />}
                            size="large"
                            variant="contained"
                            onClick={this.loginClicked}>
                                Login
                        </Button>
                        </div>
                    </Paper>
                </Grid>
            </Grid> 
            
        )
    }

}

export default withRouter(LoginComponent);