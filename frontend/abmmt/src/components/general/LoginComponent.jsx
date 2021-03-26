import React, { Component } from 'react'
import {MESSAGES} from '../../Constanst.js'

import {Button, Input } from '@material-ui/core'
import SaveIcon from '@material-ui/icons/Save'
import AuthenticationService from '../AuthenticationService.js'
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import Utils from '../Utils.js'
import { withRouter } from 'react-router';

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
                AuthenticationService.registerSuccessfullLogin(this.state.username, response.data.token)
                Utils.redirectTo(this.props, '/home')

                this.props.handleInfo({success : MESSAGES.successLogingIn});

            }).catch((e) => {
                // console.log(e)
                this.props.handleInfo({error : e.response.data.message});
            })
    }

    render() {
        return(
            <Grid container spacing={3}>
                <Grid item xs={12} md={5} lg={5}>
                    <Paper className="{fixedHeightPaper}">

                        <div>
                            <Input 
                                type="text" 
                                name="username" 
                                placeholder="Username" 
                                variant="outlined"  
                                value={this.state.username} onChange={this.handleChange}/> 
                        </div>
                        <div>
                            <Input 
                                type="password" 
                                name="password" 
                                placeholder="Password"  
                                variant="outlined"  
                                value={this.state.password} onChange={this.handleChange}/> 
                        </div>
                        <Button 
                            startIcon={<SaveIcon />}
                            size="large"
                            variant="contained"
                            onClick={this.loginClicked}>
                                Login
                        </Button>
                    </Paper>
                </Grid>
                
            </Grid>
        )
    }

}

export default withRouter(LoginComponent);