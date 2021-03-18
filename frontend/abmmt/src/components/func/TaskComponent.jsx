import React, { Component } from 'react'
import BackendService from '../../api/CommonAPI.js'
import {TASKS_HEADER_DATA} from '../../Constanst.js'

import DataComponent from './DataComponent'
import CircularProgress from '@material-ui/core/CircularProgress';

import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import clsx from 'clsx';

import { styles } from '../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';

import PropTypes from 'prop-types';


import {Button, Input } from '@material-ui/core'


class TaskComponent extends Component{
    constructor(props){
        super(props)

        this.state = {
            tasks : [],
            selected: {},
            loading: true,
            page: 0,
            rowsPerPage: 10
            
        }

        this.refreshTasks = this.refreshTasks.bind(this)
        this.selectTask = this.selectTask.bind(this)
        
    }
    

    
    componentDidMount(){
        // let username = AuthenticationService.isUserLoggedIn();
        this.refreshTasks();
    }

    refreshTasks(){
        BackendService.all('tasks')
            .then(
                response => {
                    this.setState({
                        loading : false, 
                        tasks : response.data
                    });
                }
            );
            
    }

    selectTask(event, task) {      
        this.setState({selected: task})
    }

    render(){
        const { classes } = this.props;
        const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

        return(
                           
            <Grid container spacing={3}>
              {/* Chart */}
              <Grid item xs={12} md={5} lg={5}>
                <Paper className={fixedHeightPaper}>
                    
                    { this.state.loading && <CircularProgress color="secondary"/> }
                    <DataComponent 
                        tableRows={this.state.tasks} 
                        tableHeader={TASKS_HEADER_DATA}
                        selected={this.state.selected}
                        selectRow={this.selectTask} 
                    />

                </Paper>
              </Grid>

              <Grid item xs={12} md={7} lg={7}>
                <Paper className={fixedHeightPaper}>


                </Paper>
              </Grid>
              {/* Recent Orders */}
              <Grid item xs={12}>
                <Paper className={classes.paper}>
                    
                    <form className={classes.root} noValidate autoComplete="off">
                        <div>

                            {/* <EditUserComponent selectedUser={this.state.selectedUser}/> */}
                            
                        </div>
                    </form>
                </Paper>
              </Grid>
            </Grid>
    
     )
    }

}

TaskComponent.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(TaskComponent);