import React, { Component } from 'react'
import {ICONS_MAPPING, REPLIES_HEADER_DATA} from '../Constants.js'


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

import Link from '@material-ui/core/Link';

class RepliesComponent extends Component {

    
    render(){
        const {notificationNum, index, classes, open, attachment, saveReply, handleReplyChange, handleAttachment} = this.props
        
        return (
            
            <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={8}>
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
                                                                        
                                                                        { key != 'attachment'  ?  reply[key] : 
                                                                            <Button 
                                                                            startIcon={ICONS_MAPPING.attach}
                                                                            target="_blank" 
                                                                            href={reply[key]}
                                                                            disabled={!reply[key]}
                                                                            color="secondary"
                                                                            >Attachment</Button>
                                                                        } 
                                                                       
                                                                    </TableCell>)}
                                           </TableRow>
                                    })
                                }
                                <TableRow >
                                    <TableCell colSpan={6} >
                                        <TextField
                                            fullWidth
                                            label={"Enter your reply here"}
                                            rows={3}
                                            multiline
                                            onChange={handleReplyChange}
                                            // error={errors[key] && errors.length > 0}
                                            helperText={attachment}
                                        /> 
                                    </TableCell>
                                    <TableCell  align="right">
                                        <Button 
                                            variant="contained" 
                                            className={classes.menuButton}
                                            color="default"
                                            component="label"
                                            endIcon={ICONS_MAPPING.attach}>
                                                
                                            Attach
                                            <input
                                                type="file"
                                                onChange={handleAttachment}
                                                style={{ display: 'none' }}

                                            />
                                        </Button>
                                    </TableCell>
                                    <TableCell>
                                        <Button
                                            variant="contained" 
                                            className={classes.menuButton}
                                            color="default"
                                            endIcon={ICONS_MAPPING.create}
                                            onClick={() => saveReply(index, notificationNum)}>
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