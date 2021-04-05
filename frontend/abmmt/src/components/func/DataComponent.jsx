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


import IconButton from '@material-ui/core/IconButton';
import SendIcon from '@material-ui/icons/Send';

import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp';


import ExpandMoreIcon from '@material-ui/icons/ExpandMore';


import Fab from '@material-ui/core/Fab';
import Tooltip from '@material-ui/core/Tooltip';


import { styles } from '../UseStyles.js'
import { MuiThemeProvider, withStyles } from '@material-ui/core/styles';

import PropTypes from 'prop-types';
import RepliesComponent from './RepliesComponent.jsx';


class DataComponentAccordion extends Component{
    constructor(props){
        super(props)

        this.state = {
            page: 0,
            rowsPerPage: 5,
            selected: '',
            open: false,
            fetchedReplies: [],
            currentReply: '',
            

        }

        this.isSelected = this.isSelected.bind(this)
        this.selectRow = this.selectRow.bind(this)
        this.handleChangePage = this.handleChangePage.bind(this)
        this.handleChangeRowsPerPage = this.handleChangeRowsPerPage.bind(this)
        this.handleOpenState = this.handleOpenState.bind(this)
        this.saveReply = this.saveReply.bind(this)
        this.handleReplyChange = this.handleReplyChange.bind(this)
    }
 

    isSelected(selectedRowId) {
        return this.props.selectedId === selectedRowId;
    }

    selectRow(selectedRowId) {      
        this.setState({selected: selectedRowId})
    }

    handleReplyChange(event){
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
    handleOpenState(index){
        this.setState({
            ...this.state,
            open: {[index]:!this.state.open.[index]},
        })
    }

    fetchAndExpand(index, notificationNum){

        BackendService.getOne('replies', notificationNum)
            .then(response => {
                this.setState( { 
                    ...this.state, 
                    // open: {[index]:!this.state.open.[index]},
                    fetchedReplies: response.data
                }, () => {this.handleOpenState(index); Utils.successMessage(this.props.handleInfo, MESSAGES.allData)});
            }
        ).catch(e => Utils.errorMessage(e, this.props.handleInfo, MESSAGES.allData ));

    }

    saveReply(index, notificationNum){
        
        BackendService.createOne('replies', notificationNum, {description: this.state.currentReply})
        .then(() => {
            this.handleOpenState(index)
                     
            this.fetchAndExpand(index, notificationNum)
        }
        ).catch(e => {
            Utils.errorMessage(e, this.props.handleInfo)

        })
    }

    render(){
        const { classes, selectRow } = this.props;

        return(
            <div>
            <TableContainer>
            <Table  className={classes.table} >
            <TableBody>
                <TableHead> 
                    <TableRow size="small"  className={classes.tableRow}>
                        
                        {//load an empty cell in the table header only if the parent component is the Notifications component
                        this.props.tableHeader.notificationNum && <TableCell  />}
                    
                        { this.props.tableRows[0] && 
                            Object.keys(this.props.tableRows[0])
                                .map(key => 
                                    <TableCell size="small" className={classes.tableCell} key={key} > 
                                        {this.props.tableHeader[key]} 
                                    </TableCell>)
                        }
                    </TableRow>
                </TableHead>
                
                {this.props.tableRows.length > 0 && this.props.tableRows
                .slice(this.state.page * this.state.rowsPerPage, this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                .map((tableRow, index) => {
                    let rowID = Object.entries(tableRow)[0][1]
                    console.log(rowID)
                    let notificationNum = tableRow.notificationNum;
                    return (   
                        <>
                        <TableRow  size="small"
                            hover
                            onClick={() => selectRow(tableRow)}
                            tabIndex={-1}
                            key={index}
                            selected={this.isSelected(rowID)}
                        >   
                            {this.props.tableHeader.notificationNum &&
                            //load the arrow button only if the parent component is the Notifications component
                            <TableCell >
                                <IconButton aria-label="expand row" size="small" 
                                    onClick={() => this.fetchAndExpand(index, notificationNum)}>
                                {this.state.open[index] ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                                </IconButton>
                            </TableCell>
                            }
                            
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
                                                onClick={  () => Utils.redirectTo(this.props, `/${link}`, tableRow._links[link])}>
                                                {ICONS_MAPPING[link]}
                                            </Fab>
                                        </Tooltip>)
                                    }
                                </div>
                            </TableCell>}
                        </TableRow>     

                        {this.props.tableHeader.notificationNum &&
                        //load the replies block only if the parent component is the Notifications component                        
                            <TableRow>
                                <RepliesComponent
                                    index={index}
                                    notificationNum={notificationNum}
                                    open={this.state.open}
                                    saveReply={this.saveReply}
                                    handleReplyChange={this.handleReplyChange}
                                    fetchedReplies={this.state.fetchedReplies}
                                    classes={this.props.classes}
                                    handleInfo={this.handleInfo}
                                />
                            </TableRow>
                        }
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