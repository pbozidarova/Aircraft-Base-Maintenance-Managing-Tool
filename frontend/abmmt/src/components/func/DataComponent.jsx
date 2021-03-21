import React, { Component } from 'react'

import EditIcon from '@material-ui/icons/Edit';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import TablePagination from '@material-ui/core/TablePagination';
import TableContainer from '@material-ui/core/TableRow';
import Table from '@material-ui/core/TableRow';
import TableBody from '@material-ui/core/TableRow';
import TableHead from '@material-ui/core/TableHead';


import { styles } from '../UseStyles.js'
import { MuiThemeProvider, withStyles } from '@material-ui/core/styles';

import PropTypes from 'prop-types';


class DataComponent extends Component{
    constructor(props){
        super(props)

        this.state = {
            page: 0,
            rowsPerPage: 10
        }

        this.isSelected = this.isSelected.bind(this)
        this.handleChangePage = this.handleChangePage.bind(this)
        this.handleChangeRowsPerPage = this.handleChangeRowsPerPage.bind(this)
        
    }

    isSelected(currentRow) {
        
        return this.props.selected === currentRow;
    }
    handleChangePage = (event, newPage) => {
        this.setState({page: newPage});
    }
    handleChangeRowsPerPage = (event) => {

        this.setState({rowsPerPage: parseInt(event.target.value, 10)});
        this.setState({page: 0});
    }
    render(){
     
        return(
            <MuiThemeProvider>

            <TableContainer>

                <Table >

                    <TableBody>
                    <TableHead>
                        <TableRow>
                            { this.props.tableRows[0] && Object.keys(this.props.tableRows[0])
                                .map(key => <TableCell align='left' key={key} > {this.props.tableHeader[key]} </TableCell>)
                            }
                        </TableRow>
                    </TableHead>
                        {this.props.tableRows
                            .slice(this.state.page * this.state.rowsPerPage, this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                            .map(tableRow => {
                                return (
                                    
                                    <TableRow
                                        hover
                                        onClick={() => this.props.selectRow(tableRow)}
                                        tabIndex={-1}
                                        key={Object.entries(tableRow)[0][1]}
                                        selected={this.isSelected(tableRow)}
                                    >
                                        {Object.keys(this.props.tableHeader)
                                               .map(key => <TableCell key={key} align="right">{tableRow[key]}</TableCell>)}
                                        
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
         </MuiThemeProvider>
        )
    }

}

DataComponent.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(DataComponent);