import React, { Component } from 'react'
import {Button, Input } from '@material-ui/core'
import SaveIcon from '@material-ui/icons/Save'
import{BrowserRouter as Router, Route} from 'react-router-dom'

class LoginComponent extends Component{


    constructor(props){
        super(props)
        this.state = {
            username: 'N90909',
            password: '',
            hasLoginFailed: false,
            showSuccessMsg: false
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
        //N90909, 1234
        if(this.state.username === 'N90909' && this.state.password === '1234'){
            this.setState({
                    showSuccessMsg:true, 
                    hasLoginFailed:false
                })
        }else{
            this.setState({
                showSuccessMsg:false, 
                hasLoginFailed:true
            })
        } 
    }

    render() {
        return(
            <div>
                {this.state.hasLoginFailed && <div>Invalid Credentials</div>}
                {this.state.showSuccessMsg && <div>Login Successful</div>}
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
            </div>
        )
    }

}

export default LoginComponent;