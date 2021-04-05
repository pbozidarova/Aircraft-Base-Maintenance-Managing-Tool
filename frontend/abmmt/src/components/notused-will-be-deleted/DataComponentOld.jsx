import React, { Component } from 'react'
import {ICONS_MAPPING} from '../../Constanst.js'
import Utils from '../Utils'
import { withRouter } from 'react-router';

import EditIcon from '@material-ui/icons/Edit';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import TablePagination from '@material-ui/core/TablePagination';
import TableContainer from '@material-ui/core/TableRow';
import Table from '@material-ui/core/TableRow';
import TableBody from '@material-ui/core/TableRow';
import TableHead from '@material-ui/core/TableHead';

import Fab from '@material-ui/core/Fab';
import Tooltip from '@material-ui/core/Tooltip';


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
            <Table className={classes.tableRow}>
            <TableBody>
                <TableHead>
                    <TableRow size="small"  >
                        { this.props.tableRows[0] && 
                            Object.keys(this.props.tableRows[0])
                                .map(key => 
                                    <TableCell size="small" className={classes.tableCell} key={key} > 
                                        {this.props.tableHeader[key]} 
                                    </TableCell>)
                        }
                    </TableRow>
                </TableHead>
                {this.props.tableRows
                        .slice(this.state.page * this.state.rowsPerPage, this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                        .map((tableRow, index) => {
                            
                            return (   
                                <TableRow size="small"
                                    hover
                                    onClick={() => this.props.selectRow(tableRow)}
                                    tabIndex={-1}
                                    key={index}
                                    selected={this.isSelected(Object.entries(tableRow)[0][1])}
                                >   
                                    {Object.keys(this.props.tableHeader)
                                        .map(key => <TableCell  size="small" 
                                                                className={classes.tableCell} 
                                                                key={key} align="right">{tableRow[key]}</TableCell>)}

                                    {tableRow._links && 
                                    
                                    <TableCell size="small" >
                                        <div className={classes.fab}>
                                            {Object.keys(tableRow._links)
                                            .map(link => 
                                                    <Tooltip title={`Go to ${link}`} aria-label="add">
                                                    <Fab 
                                                        size="small" 
                                                        color="primary" 
                                                        className={classes.fabChild}
                                                        onClick={  () => {console.log(tableRow); console.log(tableRow._links[link]);  Utils.redirectTo(this.props, `/${link}`, tableRow._links[link])}}>
                                                        {ICONS_MAPPING[link]}
                                                    </Fab>
                                                </Tooltip>)
                                            }
                                        </div>
                                    </TableCell>}
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
  
  export default withStyles(styles)(withRouter(DataComponent));