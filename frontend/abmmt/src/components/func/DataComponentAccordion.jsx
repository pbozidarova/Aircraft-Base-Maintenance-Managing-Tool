import React, { Component } from 'react'
import {ICONS_MAPPING} from '../../Constanst.js'
import Utils from '../Utils'
import { withRouter } from 'react-router';
import BackendService from '../../api/CommonAPI.js'
import {MESSAGES} from '../../Constanst.js'

import EditIcon from '@material-ui/icons/Edit';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import TablePagination from '@material-ui/core/TablePagination';
import TableContainer from '@material-ui/core/TableRow';
import Table from '@material-ui/core/TableRow';
import TableBody from '@material-ui/core/TableRow';
import TableHead from '@material-ui/core/TableHead';
import Button from '@material-ui/core/Button';


import Box from '@material-ui/core/Box';
import Collapse from '@material-ui/core/Collapse';
import IconButton from '@material-ui/core/IconButton';
import SendIcon from '@material-ui/icons/Send';
import TextField from '@material-ui/core/TextField';
import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp';

import Paper from '@material-ui/core/Paper';
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
            fetchedReplies: [],
            currentReply: '',
        }

        this.isSelected = this.isSelected.bind(this)
        this.handleChange = this.handleChange.bind(this)
        this.handleChangePage = this.handleChangePage.bind(this)
        this.handleChangeRowsPerPage = this.handleChangeRowsPerPage.bind(this)
        this.saveReply = this.saveReply.bind(this)
    }
 

    isSelected(currentRow) {
        
        return this.props.selectedId === currentRow;
    }

    handleChange(event){
        this.setState(
            {   ...this.state,
                currentReply : event.target.value
            }) 
    }

    handleChangePage = (event, newPage) => {
        this.setState({page: newPage});
    }
    handleChangeRowsPerPage = (event) => {

        this.setState({rowsPerPage: parseInt(event.target.value, 10)});
        this.setState({page: 0});
    }

    fetchAndExpand(index, notificationNum){
        BackendService.getOne('replies', notificationNum)
            .then(response => {
                
                this.setState( { 
                    ...this.state, 
                    open: {[index]:!this.state.open.[index]},
                    fetchedReplies: response.data
                }, () => {console.log(this.state); Utils.allocateCorrectSuccessMessage(this.props.handleInfo, MESSAGES.allData)});
            }
        ).catch(e => Utils.allocateCorrectErrorMessage(e, this.props.handleInfo, MESSAGES.allData ));

    }

    saveReply(notificationNum, reply){
        console.log(reply)
        BackendService.createOne('replies', notificationNum, {description: reply})
        .then(() => {                        
            // this.refreshFacilities()
            // this.props.handleInfo({success : MESSAGES.successCreated});
        }
        ).catch(e => {
            // Utils.allocateCorrectErrorMessage(e, reply, this.props.handleInfo)

        })

    console.log('submit Create')
    }

    render(){
        const { classes } = this.props;

        return(
            <>
            <TableContainer>
            <Table  className={classes.table} >
            <TableBody>
                <TableHead> 
                    <TableRow size="small"  className={classes.tableRow}>
                        <TableCell  />

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
                    let notificationNum = tableRow.notificationNum;
                    
                    return (   
                        <>
                        <TableRow  size="small"
                            hover
                            onClick={() => this.props.selectRow(tableRow)}
                            tabIndex={-1}
                            key={index}
                            selected={this.isSelected(notificationNum)}
                        >   

                            <TableCell >
                                <IconButton aria-label="expand row" size="small" 
                                    onClick={() => this.fetchAndExpand(index, notificationNum)}>
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
                                <Table size="small" aria-label="replies" component={Paper}>
                                    <TableBody>
                                    <TableHead>
                                    
                                    <TableRow fullWidth>
                                        <TableCell colSpan={4} align="left">Description</TableCell>
                                        <TableCell align="left">Author</TableCell>
                                        <TableCell align="left">Date</TableCell>
                                        <TableCell align="left">Attachments</TableCell>
                                    </TableRow>
                                    </TableHead>
                                    
                                    {this.state.fetchedReplies.map(reply => {
                                        
                                       return <>
                                            <TableRow >
                                                {Object.keys(reply).map(key => <TableCell colSpan={key=='description' ? 4 : 1}>{reply[key]}</TableCell>)}
                                            </TableRow>
                                        </>
                                        })
                                    }
                                    <TableRow >
                                        <TableCell colSpan={4} >
                                            <TextField
                                                fullWidth
                                                // id={key}
                                                // name={key}
                                                label={"Enter your reply here"}
                                                // defaultValue={selected[key]}
                                                // disabled={disabledFields[key]}
                                                rows={3}
                                                multiline
                                                onChange={this.handleChange}
                                                // error={errors[key] && errors.length > 0}
                                                helperText={''}
                                            /> 
                                        </TableCell>
                                        <TableCell  align="right">
                                            <Button 
                                                variant="contained" 
                                                className={classes.menuButton}
                                                color="default"
                                                endIcon={ICONS_MAPPING.attach}>Attach</Button>
                                            
                                        </TableCell>
                                        <TableCell>
                                            <Button
                                                variant="contained" 
                                                className={classes.menuButton}
                                                color="default"
                                                endIcon={ICONS_MAPPING.create}
                                                onClick={() => this.saveReply(notificationNum, this.state.currentReply)}
                                                >Send</Button>
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
         </>
        )
    }

}

DataComponentAccordion.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(withRouter(DataComponentAccordion));