import React, { Component } from 'react'
import {ICONS_MAPPING} from '../../Constanst.js'


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

    render(){
        const {notificationNum, index, classes, open} = this.props
        return (
            <TableRow>
                <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
                <Collapse in={open[index]} timeout="auto" unmountOnExit>
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
                                    onClick={() => this.saveReply(index, notificationNum, this.state.currentReply)}
                                    >Send</Button>
                            </TableCell>
                        </TableRow>
                        </TableBody>
                    </Table>
                    </Box>
                </Collapse>
                </TableCell>
            </TableRow>
        )
    }

}

export default RepliesComponent;