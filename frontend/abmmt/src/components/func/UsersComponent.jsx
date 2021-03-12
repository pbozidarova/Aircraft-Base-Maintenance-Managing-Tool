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

import Container from '@material-ui/core/Container';
import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';

import AircraftComponent from './AircraftComponent'


class UsersComponent extends Component {
    constructor(props){
        super(props)
        // this.getAllUsers = this.getAllUsers.bind(this);
        // this.handleSuccessfulResponse = this.handleSuccessfulResponse.bind(this);
        this.state = {
            users : [],
            selected : [],
            setSelected : []
        }

        this.refreshUsers = this.refreshUsers.bind(this)
        this.handleClick = this.handleClick.bind(this)
        this.isSelected = this.isSelected.bind(this)
    }

    
    componentDidMount(){
        // let username = AuthenticationService.isUserLoggedIn();
        this.refreshUsers();
    }

    refreshUsers(){
        UserService.allUsers()
        .then(
            response => {
                console.log(response);
                this.setState({users : response.data});
            }
        );
    }

    handleClick(event, name) {
        console.log(name);
        const selectedIndex = this.state.selected.indexOf(name);
        let newSelected = [];
    
        if (selectedIndex === -1) {
          newSelected = newSelected.concat(this.state.selected, name);
        } else if (selectedIndex === 0) {
          newSelected = newSelected.concat(this.state.selected.slice(1));
        } else if (selectedIndex === this.state.selected.length - 1) {
          newSelected = newSelected.concat(this.state.selected.slice(0, -1));
        } else if (selectedIndex > 0) {
          newSelected = newSelected.concat(
            this.state.selected.slice(0, selectedIndex),
            this.state.selected.slice(selectedIndex + 1),
          );
        }
    }

    isSelected(name) {
        return this.state.selected.indexOf(name) !== -1
    }

    render(){
        return(
            <div>
                {/* <h1>User Component</h1> */}

        <Container maxWidth="lg" className='{classes.container}'>
            <Grid container spacing={3}>
              {/* Chart */}
              <Grid item xs={12} md={8} lg={7}>
                <Paper className='{fixedHeightPaper}'>
                  
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
                                return <TableRow
                                        hover
                                        onClick={(event) => this.handleClick(event, user.companyNum)}
                                        aria-checked={this.isSelected(user.companyNum)}
                                        tabIndex={-1}
                                        key={user.companyNum}
                                        selected={this.isSelected(user.companyNum)}
                                        >
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
                </Paper>
              </Grid>
              {/* Recent Deposits */}
              <Grid item xs={12} md={4} lg={5}>
                <Paper className='{fixedHeightPaper}'>
                  {/* <AircraftComponent/> */}
                </Paper>
              </Grid>
              {/* Recent Orders */}
              <Grid item xs={12}>
                <Paper className='{classes.paper}'>
                  <AircraftComponent />
                </Paper>
              </Grid>
            </Grid>
            <Box pt={4}>
              {/* <Copyright /> */}
            </Box>
          </Container>
     
            </div>
        )
    }

    
}


export default UsersComponent;