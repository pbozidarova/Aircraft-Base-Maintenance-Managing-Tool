import React, {Component} from 'react'
import UserService from '../../api/UsersAPI.js'
import EnhancedTable from '../material-ui/Table.js'

import PropTypes from 'prop-types';
import clsx from 'clsx';
import { lighten, makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TablePagination from '@material-ui/core/TablePagination';
import TableRow from '@material-ui/core/TableRow';
import TableSortLabel from '@material-ui/core/TableSortLabel';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import Checkbox from '@material-ui/core/Checkbox';
import IconButton from '@material-ui/core/IconButton';
import Tooltip from '@material-ui/core/Tooltip';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Switch from '@material-ui/core/Switch';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import FilterListIcon from '@material-ui/icons/FilterList';



class UserComponent extends Component {

    constructor(props){
        super(props)
        // this.getAllUsers = this.getAllUsers.bind(this);
        // this.handleSuccessfulResponse = this.handleSuccessfulResponse.bind(this);
        this.state = {
            users : []
        }

    }

    
    componentDidMount(){
        // let username = AuthenticationService.isUserLoggedIn();
        UserService.allUsers()
            .then(
                response => {
                    console.log(response);
                    this.setState({users : response.data});
                }
            );
    }

    render(){
        return(
            <div>
                User Component<hr/>

                {/* <EnhancedTable/> */}
                <TableContainer>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>First Name</TableCell>
                                <TableCell>Last Name</TableCell>
                                <TableCell>Company Number</TableCell>
                                <TableCell>Email</TableCell>
                                <TableCell>Roles</TableCell>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                                {/* {Object.keys(this.state.users[0]).map(label => <TableCell/>)} */}
                            </TableRow>
                        </TableHead>
                    
                        <TableBody>
                            {   this.state.users.map(user => {
                                return <TableRow>
                                    {/* <TableCell component="th" id={user.companyNum} scope="row" >
                                        {user.companyNum}
                                    </TableCell> */}
                                                                   
                                    {Object.keys(user).map(key => <TableCell id={user.companyNum} align="right">{user[key]}</TableCell>)}
                                    
                                    <TableCell align="right"><EditIcon/></TableCell>
                                    <TableCell align="right"><DeleteIcon/></TableCell>
                                </TableRow>
                                })
                            }
                    </TableBody>
                    </Table>
                 </TableContainer>
            </div>
        )
    }

    
}


export default UserComponent;