import React, { Component } from 'react'
import TasksService from '../../api/TasksAPI.js'

import {Button, Input } from '@material-ui/core'
import SaveIcon from '@material-ui/icons/Save'
import DeleteIcon from '@material-ui/icons/Delete'

import EditIcon from '@material-ui/icons/Edit';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import TablePagination from '@material-ui/core/TablePagination';
import TableContainer from '@material-ui/core/TableRow';
import Table from '@material-ui/core/TableRow';
import TableBody from '@material-ui/core/TableRow';
import TableFooter from '@material-ui/core/TableFooter';


class TaskComponent extends Component{
    constructor(props){
        super(props)
        // this.getAllUsers = this.getAllUsers.bind(this);
        // this.handleSuccessfulResponse = this.handleSuccessfulResponse.bind(this);
        this.state = {
            tasks : [],
            selectedTask: {},
            page: 0,
            rowsPerPage: 10
            // selected : [],
            // setSelected : []
        }

        this.refreshTasks = this.refreshTasks.bind(this)
        this.handleClick = this.handleClick.bind(this)
        this.isSelected = this.isSelected.bind(this)
        this.handleChangePage = this.handleChangePage.bind(this)
        this.handleChangeRowsPerPage = this.handleChangeRowsPerPage.bind(this)
        
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

    TablePaginationActions(props) {
        // const classes = useStyles1();
        // const theme = useTheme();
        const { count, page, rowsPerPage, onChangePage } = props;
      
        const handleFirstPageButtonClick = (event) => {
          onChangePage(event, 0);
        };
      
        const handleBackButtonClick = (event) => {
          onChangePage(event, page - 1);
        };
      
        const handleNextButtonClick = (event) => {
          onChangePage(event, page + 1);
        };
    }
    handleClick(event, task) {      
        this.setState({selectedTask: task})
    }

    isSelected(taskNum) {
        return this.state.selectedTask.taskNum == taskNum;
    }
    handleChangePage = (event, newPage) => {
        this.setState({page: newPage});
    }
    handleChangeRowsPerPage = (event) => {
        // setRowsPerPage(parseInt(event.target.value, 10));
        this.setState({rowsPerPage: parseInt(event.target.value, 10)});
        this.setState({page: 0});
    }
    render(){
        return(
            <div>
            <div>TaskComponent</div>
            <TableContainer>
                <Table>
                    <TableBody>
                        {   this.state.tasks
                            .slice(this.state.page * this.state.rowsPerPage, this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                            .map(task => {
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
                    </TableBody>

           
                </Table>
            </TableContainer>
            <TablePagination
            rowsPerPageOptions={[5, 10, 25]}
            component="div"
            count={this.state.tasks.length}
            rowsPerPage={this.state.rowsPerPage}
            page={this.state.page}
            onChangePage={this.handleChangePage}
            onChangeRowsPerPage={this.handleChangeRowsPerPage}
            />
            
         </div>
        )
    }

}

export default TaskComponent;