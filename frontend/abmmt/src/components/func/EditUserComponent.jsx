import React, { Component } from 'react'
import TextField from '@material-ui/core/TextField';

class EditUserComponent extends Component {

    constructor(props){
        super(props)   
    }

    render(){
        return (
            <div key={this.props.selectedUser.companyNum}>
                {Object.keys(this.props.selectedUser).map(key => {
                    return (
                        <div>
                            <TextField
                            error
                            id="standard-error-helper-text"
                            label={key}
                            defaultValue={this.props.selectedUser[key]}
                            helperText="Incorrect entry."
                            />
                        </div>
                    )    
                })}
            </div>
            
        )
    }
}

export default EditUserComponent