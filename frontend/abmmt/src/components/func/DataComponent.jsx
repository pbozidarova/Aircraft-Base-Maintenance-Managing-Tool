import React, { Component } from 'react'
import {ICONS_MAPPING, MESSAGES} from '../Constants.js'
import Utils from '../Utils'
import { withRouter } from 'react-router';
import BackendService from '../CommonAPI.js'

import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import TablePagination from '@material-ui/core/TablePagination';
import TableContainer from '@material-ui/core/TableContainer';
import TableSortLabel from '@material-ui/core/TableSortLabel';
import Table from '@material-ui/core/TableRow';
import TableBody from '@material-ui/core/TableRow';
import TableHead from '@material-ui/core/TableHead';


import IconButton from '@material-ui/core/IconButton';

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
            attachment: '',
            orderBy: '',
            order: '',
         }  


        this.isSelected = this.isSelected.bind(this)
        this.handleChangePage = this.handleChangePage.bind(this)
        this.handleChangeRowsPerPage = this.handleChangeRowsPerPage.bind(this)
        this.handleOpenState = this.handleOpenState.bind(this)
        this.saveReply = this.saveReply.bind(this)
        this.handleAttachment = this.handleAttachment.bind(this)
        this.handleReplyChange = this.handleReplyChange.bind(this)
        this.createSortHandler =  this.createSortHandler.bind(this)
    }
 

    isSelected(selectedRowId) {
        return this.props.selectedId === selectedRowId;
    }


    handleReplyChange(event){
        this.setState(
            {   ...this.state,
                currentReply : event.target.value
            }) 
    }

    handleAttachment(event){
        
        let file = event ? event.target.files[0] : null;

        this.setState({
            ...this.state,
            attachment: file
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
                }, () => {this.handleOpenState(index); Utils.successMessage(this.props.handleInfo, MESSAGES.allCommunication)});
            }
        ).catch(e => Utils.errorMessage(e, this.props.handleInfo, MESSAGES.allData ));
    }

    createSortHandler(property){
        const isAsc = this.state.orderBy === property && this.state.order === 'asc';
        const orderDirection = isAsc ? 'desc' : 'asc'
        this.setOrder(property, orderDirection);
        
    }
    setOrder(property, orderDirection){
        this.setState({
            ...this.state,
            order: orderDirection,
            orderBy: property,
        }, () => console.log(this.state))
    }
    getComparator(order, orderBy) {
        return order === 'desc'
          ? (a, b) => this.descendingComparator(a, b, orderBy)
          : (a, b) => -this.descendingComparator(a, b, orderBy);
      }
    descendingComparator(a, b, orderBy) {
        if (b[orderBy] < a[orderBy]) {
          return -1;
        }
        if (b[orderBy] > a[orderBy]) {
          return 1;
        }
        return 0;
      }

    stableSort(array, comparator) {
    const stabilizedThis = array.map((el, index) => [el, index]);
    stabilizedThis.sort((a, b) => {
        const order = comparator(a[0], b[0]);
        if (order !== 0) return order;
        return a[1] - b[1];
    });
    return stabilizedThis.map((el) => el[0]);
    }

    saveReply(index, notificationNum){
        // let replyObj = {description: this.state.currentReply, attachment: this.state.attachment}
        let formData = new FormData();
        formData.append('description', this.state.currentReply);
        formData.append('attachment', this.state.attachment);

        BackendService.createReply('replies', notificationNum, formData )
        .then(() => {
            this.handleOpenState(index)
            this.handleAttachment()  
            this.setState({...this.state, currentReply: ''})
            this.fetchAndExpand(index, notificationNum)
        }
        ).catch(e => {
            Utils.errorMessage(e, this.props.handleInfo)

        })
    }

    render(){
        const { classes, selectRow, tableHeader, tableRows } = this.props;
        const { page, rowsPerPage, orderBy, order } = this.state
        return(
            <div>
            <TableContainer >
            <Table className={classes.table} >
            <TableBody>
                <TableHead> 
                    <TableRow size="small"  className={classes.head}>
                        {//load an empty cell in the table header only if the parent component is the Notifications component
                        tableHeader.notificationNum && <TableCell  />}
                    
                        {tableRows[0] && 
                            Object.keys(tableHeader)
                                .map(key => 
                                    <TableCell size="small" key={key} > 
                                        <TableSortLabel
                                            active={orderBy === key}
                                            direction={orderBy === key ? order : 'asc'}
                                            onClick={() => this.createSortHandler(key)}
                                            classes={{
                                                root:  classes.icon 
                                            }}
                                        >
                                            <div className={classes.head} >
                                                {tableHeader[key]} 
                                            </div>
                                            {orderBy === key ? (
                                                <span className={classes.visuallyHidden}>
                                                {order === 'desc' ? 'sorted descending' : 'sorted ascending'}
                                                </span>
                                            ) : null} 
                                        </TableSortLabel>
                                        
                                        
                                    </TableCell>)
                        }
                         {//load an empty cell in the table header only if the parent component is NOT the Notifications component
                        !tableHeader.notificationNum && <TableCell  />}
                    </TableRow>
                </TableHead>
                
                {tableRows.length > 0 && 
                this.stableSort(tableRows, this.getComparator(order, orderBy))
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((tableRow, index) => {
                    // let rowID = Object.entries(tableRow)[0][1]
                    let notificationNum = tableRow.notificationNum;
                    return (   
                        <>
                        <TableRow  size="small"
                            hover
                            onClick={() => selectRow(tableRow, index)}
                            tabIndex={-1}
                            key={index}
                            selected={this.isSelected(index)}
                        >   
                            {tableHeader.notificationNum &&
                            //load the arrow button only if the parent component is the Notifications component
                            <TableCell >
                                <IconButton aria-label="expand row" size="small" 
                                    onClick={() => this.fetchAndExpand(index, notificationNum)}>
                                {this.state.open[index] ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                                </IconButton>
                            </TableCell>
                            }
                            
                            {Object.keys(tableHeader)
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

                        {tableHeader.notificationNum &&
                        //load the replies block only if the parent component is the Notifications component                        
                            <TableRow>
                                <RepliesComponent
                                    index={index}
                                    notificationNum={notificationNum}
                                    open={this.state.open}
                                    attachment={this.state.attachment && this.state.attachment.name}
                                    saveReply={this.saveReply}
                                    handleReplyChange={this.handleReplyChange}
                                    handleAttachment={this.handleAttachment}
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
                count={tableRows.length}
                rowsPerPage={rowsPerPage}
                page={page}
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