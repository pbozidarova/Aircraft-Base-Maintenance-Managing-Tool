import React, { Component } from 'react'
import TasksService from '../../api/TasksAPI.js'

import {Button, Input } from '@material-ui/core'
import SaveIcon from '@material-ui/icons/Save'
import DeleteIcon from '@material-ui/icons/Delete'

import EditIcon from '@material-ui/icons/Edit';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';


class TaskComponent extends Component{
    constructor(props){
        super(props)
        // this.getAllUsers = this.getAllUsers.bind(this);
        // this.handleSuccessfulResponse = this.handleSuccessfulResponse.bind(this);
        this.state = {
            tasks : [],
            selectedTask: {},
            // selected : [],
            // setSelected : []
        }

        this.refreshTasks = this.refreshTasks.bind(this)
        this.handleClick = this.handleClick.bind(this)
        this.isSelected = this.isSelected.bind(this)
    }

    
    componentDidMount(){
        // let username = AuthenticationService.isUserLoggedIn();
        this.refreshTasks();
    }

    refreshTasks(){
        TasksService.allTasks()
            .then(
                response => {
                    console.log(response.data);
                    this.setState({tasks : response.data});
                }
            );
            
    }
    handleClick(event, task) {      
        this.setState({selectedTask: task})
    }

    isSelected(taskNum) {
        return this.state.selectedTask.taskNum == taskNum;
    }

    render(){
        return(
            <div>
            <div>TaskComponent</div>
            {   this.state.tasks.map(task => {
                return (
                    <TableRow
                            hover
                            onClick={(event) => this.handleClick(event, task)}
                            tabIndex={-1}
                            key={task.taskNum}
                            selected={this.isSelected(task.taskNum)}
                            >
                        {/* <TableCell component="th" id={user.companyNum} scope="row" >
                            {user.companyNum}
                        </TableCell> */}
                        
                        {Object.keys(task).map(key => <TableCell align="right">{task[key]}</TableCell>)}
                        
                        <TableCell align="right"><EditIcon/></TableCell>
                        <TableCell align="right"><DeleteIcon/></TableCell>
                    </TableRow>
                    )   
                })
            }
        </div>
        )
    }

}

export default TaskComponent;