import React, { Component } from 'react'
import {ICONS_MAPPING, REPLIES_HEADER_DATA} from '../../Constanst.js'
import BackendService from '../../api/CommonAPI.js'
import Utils from '../Utils'

import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import Table from '@material-ui/core/TableRow';
import TableBody from '@material-ui/core/TableRow';
import TableHead from '@material-ui/core/TableHead';
import Button from '@material-ui/core/Button';
import Paper from '@material-ui/core/Paper';
import Box from '@material-ui/core/Box';
import Collapse from '@material-ui/core/Collapse';
import TextField from '@material-ui/core/TextField';

class RepliesComponent extends Component {

    constructor(props){
        super(props)

        this.saveReply = this.saveReply.bind(this)
    }

    saveReply(index, notificationNum, reply){
        console.log(reply)
        BackendService.createOne('replies', notificationNum, {description: reply})
        .then(() => {
            this.handleOpenState(index)
                     
             this.fetchAndExpand(index, notificationNum)
            // this.props.handleInfo({success : MESSAGES.successCreated});
        }
        ).catch(e => {
            Utils.allocateCorrectErrorMessage(e, this.props.handleInfo, reply)

        })

    console.log('submit Create')
    }

    render(){
        const {notificationNum, index, classes, open, saveReply} = this.props
        
        return (
            
            <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
                <Collapse in={open[index]} timeout="auto" unmountOnExit>
                    <Box margin={1}>
                         <Table size="small" aria-label="replies" component={Paper}>
                            <TableBody>
                                <TableHead>
                                    <TableRow fullWidth>
                                        {Object.keys(REPLIES_HEADER_DATA)
                                               .map(key => <TableCell 
                                                            colSpan={key=='description' ? 4 : 1} 
                                                            align="left">
                                                                {REPLIES_HEADER_DATA[key]}
                                                            </TableCell>)}
                                    </TableRow>
                                </TableHead>
                                
                                
                                {this.props.fetchedReplies.map(reply => {
                                    //The replies content
                                    return <TableRow >
                                                {Object.keys(reply)
                                                       .map(key => <TableCell 
                                                                    colSpan={key=='description' ? 4 : 1}>
                                                                        {reply[key]}
                                                                    </TableCell>)}
                                           </TableRow>
                                    })
                                }
                                <TableRow >
                                    <TableCell colSpan={4} >
                                        <TextField
                                            fullWidth
                                            label={"Enter your reply here"}
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
                                            endIcon={ICONS_MAPPING.attach}>
                                            Attach
                                        </Button>
                                    </TableCell>
                                    <TableCell>
                                        <Button
                                            variant="contained" 
                                            className={classes.menuButton}
                                            color="default"
                                            endIcon={ICONS_MAPPING.create}
                                            onClick={() => saveReply(index, notificationNum, this.state.currentReply)}>
                                            Send
                                        </Button>
                                    </TableCell>
                                </TableRow>
                    </TableBody>
                        </Table>
                     </Box>
                </Collapse> 
            </TableCell>
          
        )
    }

}

export default RepliesComponent;