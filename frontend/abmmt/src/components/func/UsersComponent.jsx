import React, {Component} from 'react'
import BackendService from '../../api/CommonAPI.js'
import {USERS_BOOLEAN_FIELDS, USERS_HEADER_DATA} from '../../Constanst.js'

import { styles } from '../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';

import EnhancedTable from '../material-ui/Table.js'

import EditUserComponent from './UserEditComponent'
import DataComponent from './DataComponent'

import PropTypes from 'prop-types';
import clsx from 'clsx';

import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';


class UsersComponent extends Component {
    constructor(props){
        super(props)
        // this.getAllUsers = this.getAllUsers.bind(this);
        // this.handleSuccessfulResponse = this.handleSuccessfulResponse.bind(this);
        this.state = {
            users : [],
            selected: {},
        }

        this.refreshUsers = this.refreshUsers.bind(this)
        this.selectUser = this.selectUser.bind(this)
        

    }
   
    componentDidMount(){
        // let username = AuthenticationService.isUserLoggedIn();
        this.refreshUsers();
    }

    refreshUsers(){
        BackendService.all('users')
            .then(
                response => {
                    console.log(response.data._embedded.userViewDtoList)
                    this.setState({users : response.data._embedded.userViewDtoList});
                }
            ); 
    }

    selectUser(event, user) {      
        this.setState({selected: user})
    }


    render(){
        const { classes } = this.props;
        const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

        return(
                   
            <Grid container spacing={3}>
              {/* Chart */}
              <Grid item xs={12} md={5} lg={5}>
                <Paper className={fixedHeightPaper}>
                            
                    <DataComponent 
                        tableRows={this.state.users}
                        tableHeader = {USERS_HEADER_DATA}
                        selected={this.state.selected}
                        selectRow={this.selectUser} 
                    />

                </Paper>
              </Grid>
              <Grid item xs={12} md={7} lg={7}>
                <Paper className={fixedHeightPaper}>
                  {this.state.selected.companyNum && 
                    <EditUserComponent 
                        selectedUser={this.state.selected} 
                        labels = {USERS_HEADER_DATA} 
                        booleanFields = {USERS_BOOLEAN_FIELDS}
                    />
                  }
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


UsersComponent.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(UsersComponent);