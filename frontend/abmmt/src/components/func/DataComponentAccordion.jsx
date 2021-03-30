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

import Box from '@material-ui/core/Box';
import Collapse from '@material-ui/core/Collapse';
import IconButton from '@material-ui/core/IconButton';
import Paper from '@material-ui/core/Paper';
import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp';

import Typography from '@material-ui/core/Typography';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';


import Fab from '@material-ui/core/Fab';
import Tooltip from '@material-ui/core/Tooltip';


import { styles } from '../UseStyles.js'
import { MuiThemeProvider, withStyles } from '@material-ui/core/styles';

import PropTypes from 'prop-types';


class DataComponentAccordion extends Component{
    constructor(props){
        super(props)

        this.state = {
            page: 0,
            rowsPerPage: 5,
            open: false,
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
            <Table  className={classes.table}>
            <TableBody>
                <TableHead>
                    <TableRow size="small"  className={classes.tableRow}>
                        <TableCell />

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
                                <>
                                <TableRow size="small"
                                    hover
                                    onClick={() => this.props.selectRow(tableRow)}
                                    tabIndex={-1}
                                    key={index}
                                    selected={this.isSelected(Object.entries(tableRow)[0][1])}
                                >   

                                    <TableCell>
                                        <IconButton aria-label="expand row" size="small" 
                                            onClick={() => this.setState( { ...this.state, open: {[index]:!this.state.open.[index]}}, console.log(this.state))}>
                                        {this.state.open[index] ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                                        </IconButton>
                                    </TableCell>
                                    
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
                                <TableRow>
                                    <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
                                    <Collapse in={this.state.open[index]} timeout="auto" unmountOnExit>
                                        <Box margin={1}>
                                        <Typography variant="h6" gutterBottom component="div">
                                            History
                                        </Typography>
                                        <Table size="small" aria-label="purchases">
                                            <TableHead>
                                            <TableRow>
                                                <TableCell>Date</TableCell>
                                                <TableCell>Customer</TableCell>
                                                <TableCell align="right">Amount</TableCell>
                                                <TableCell align="right">Total price ($)</TableCell>
                                            </TableRow>
                                            </TableHead>
                                            <TableBody>
                                            
                                                <TableRow >
                                                <TableCell>
                                                    Lorem ipsum dolor sit amet consectetur adipisicing elit. Adipisci perferendis ipsa voluptatem laudantium, odio cumque quam accusantium natus quis tempore temporibus aperiam voluptatibus fugit voluptate ipsum hic quasi inventore at.    
                                                </TableCell>
                                                <TableCell>
                                                    Author
                                                </TableCell>
                                                
                                                </TableRow>
                                            
                                            </TableBody>
                                        </Table>
                                        </Box>
                                    </Collapse>
                                    </TableCell>
                                </TableRow>

                                </>

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

DataComponentAccordion.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(withRouter(DataComponentAccordion));