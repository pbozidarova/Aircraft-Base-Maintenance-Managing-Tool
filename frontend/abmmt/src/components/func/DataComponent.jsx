import React, { Component } from 'react'

import EditIcon from '@material-ui/icons/Edit';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import TablePagination from '@material-ui/core/TablePagination';
import TableContainer from '@material-ui/core/TableRow';
import Table from '@material-ui/core/TableRow';
import TableBody from '@material-ui/core/TableRow';
import TableHead from '@material-ui/core/TableHead';

import NotificationsActiveOutlinedIcon from '@material-ui/icons/NotificationsActiveOutlined';
import AssignmentIcon from '@material-ui/icons/Assignment';
import ListIcon from '@material-ui/icons/List';

import Fab from '@material-ui/core/Fab';
import Tooltip from '@material-ui/core/Tooltip';


import {Button, ButtonGroup, Grid} from '@material-ui/core';
import Utils from '../Utils.js'

import { styles } from '../UseStyles.js'
import { MuiThemeProvider, withStyles } from '@material-ui/core/styles';

import PropTypes from 'prop-types';


class DataComponent extends Component{
    constructor(props){
        super(props)

        this.state = {
            page: 0,
            rowsPerPage: 5
        }

        this.isSelected = this.isSelected.bind(this)
        this.handleChangePage = this.handleChangePage.bind(this)
        this.handleChangeRowsPerPage = this.handleChangeRowsPerPage.bind(this)
        
    }

    isSelected(currentRow) {
        
        return this.props.selectedId === currentRow;
    }
    handleChangePage = (event, newPage) => {
        this.setState({page: newPage});
    }
    handleChangeRowsPerPage = (event) => {

        this.setState({rowsPerPage: parseInt(event.target.value, 10)});
        this.setState({page: 0});
    }
    render(){
        const { classes } = this.props;

        return(
            <div>

            <TableContainer>
                <Table   className={classes.tableRow}>
                    <TableBody>
                    <TableHead>
                        <TableRow   className={classes.tableRow} >
                            { this.props.tableRows[0] && Object.keys(this.props.tableRows[0])
                                .map(key => <TableCell size="small" className={classes.tableCell}   key={key} > {this.props.tableHeader[key]} </TableCell>)
                            }
                            
                        </TableRow>
                    </TableHead>
                        {this.props.tableRows
                            .slice(this.state.page * this.state.rowsPerPage, this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                            .map(tableRow => {
                                return (
                                
                                    <TableRow
                                        className={classes.tableRow}
                                        hover
                                        onClick={() => this.props.selectRow(tableRow)}
                                        tabIndex={-1}
                                        key={Object.entries(tableRow)[0][1]}
                                        selected={this.isSelected(Object.entries(tableRow)[0][1])}
                                    >   
                                    
                                        {Object.keys(this.props.tableHeader)
                                            .map(key => <TableCell  size="small" className={classes.tableCell} key={key} align="right">{tableRow[key]}</TableCell>)}
                                    
                                        <TableCell size="small" >
                                           <div className={classes.fab}>
                                            <Tooltip title="Go to notifications" aria-label="add">
                                                <Fab size="small" color="primary" className={classes.fabChild}>
                                                    <NotificationsActiveOutlinedIcon />
                                                </Fab>
                                            </Tooltip>
                                            <Tooltip title="Go to tasks" aria-label="add">
                                                <Fab size="small" color="primary" className={classes.fabChild}>
                                                    <AssignmentIcon />
                                                </Fab>
                                            </Tooltip>
                                            <Tooltip title="Go to maintenance" aria-label="add">
                                                <Fab size="small" color="primary" className={classes.fabChild}>
                                                    <ListIcon />
                                                </Fab>
                                            </Tooltip>
                                            </div>
                                            </TableCell>
                                        {/* <ButtonGroup color="primary" size="small" 
                                        className={classes.menuButton} 
                                        aria-label="outlined primary button group">

                                        <Button 
                                            
                                            // onClick={  () => { console.log(links.maintenance.href); Utils.redirectTo(this.props, "/maintenance");}}
                                            >
                                            Notifications
                                            
                                        </Button>
                                        <Button 
                                            // onClick={  () => { console.log(links.tasks.href); Utils.redirectTo(this.props, "/mpd");}}
                                            >Projects
                                            
                                        </Button>
                                        <Button 
                                            // onClick={  () => {this.redirectTo("/logout"); AuthenticationService.logout()}}
                                            >
                                            Tasks
                                        </Button>
                                        </ButtonGroup>
                                        */}
                                        
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
                count={this.props.tableRows.length}
                rowsPerPage={this.state.rowsPerPage}
                page={this.state.page}
                onChangePage={this.handleChangePage}
                onChangeRowsPerPage={this.handleChangeRowsPerPage}
            />
         </div>
        )
    }

}

DataComponent.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(DataComponent);