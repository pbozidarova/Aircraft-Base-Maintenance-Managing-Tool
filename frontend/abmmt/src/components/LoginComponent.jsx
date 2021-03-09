import React, { Component } from 'react'
import {Button, Input } from '@material-ui/core'
import SaveIcon from '@material-ui/icons/Save'


class LoginComponent extends Component{

    render() {
        return(
            <div>
                Username: <Input type="text" name="username" variant="outlined"  /> 
                <Button 
                    startIcon={<SaveIcon />}
                    size="large"
                    variant="contained">
                        Login
                </Button>
            </div>
        )
    }

}

export default LoginComponent;