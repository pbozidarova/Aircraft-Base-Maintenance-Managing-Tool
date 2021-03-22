import React, {Component} from 'react'
import BackendService from '../../api/CommonAPI.js'
import {USERS_BOOLEAN_FIELDS, USERS_HEADER_DATA} from '../../Constanst.js'

import { styles, theme } from '../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';

import EnhancedTable from '../material-ui/Table.js'

import EditUserComponent from './UserEditComponent'
import DataComponent from './DataComponent'

import PropTypes from 'prop-types';
import clsx from 'clsx';

import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';


class UsersComponent extends Component {
    constructor(props){
        super(props)
        
        this.state = {
            users : [],
            selected: {},
            authoriry: {},
            role: {}
        }

        this.refreshUsers = this.refreshUsers.bind(this)
        this.selectUser = this.selectUser.bind(this)
        this.handleChange = this.handleChange.bind(this)
        this.handleAuthorityRoleChange = this.handleAuthorityRoleChange.bind(this)
        

    }
   
    componentDidMount(){
        // let username = AuthenticationService.isUserLoggedIn();
        this.refreshUsers();
    }

    refreshUsers(){
        BackendService.getAll('users')
            .then(
                response => {
                    this.setState({users : response.data._embedded.userViewDtoList});
                }
            ); 
    }

    selectUser( user) {
        let authoriryObj = user.roles.includes('ADMIN') ? 'ADMIN' : 'USER'
        let roleObj = user.roles.includes('ENGINEER') ? 'ENGINEER' : 'MECHANIC'
        
        this.setState({
            selected: user, 
            authoriry: authoriryObj,
            role: roleObj
        },console.log(this.state))
        
    }
    
    handleChange(event){
        this.setState(
            {   ...this.state,
                selected: {...this.state.selected, [event.target.name] : event.target.value}
            }
        , console.log(this.state))
        
    }

    handleAuthorityRoleChange(event){
        console.log(event.target.name)
        let updateRolesString = event.target.value.includes('ADMIN') ? 'ADMIN' : 'USER'
        updateRolesString += event.target.value.includes('ENGINEER') ? ', ENGINEER' : ', MECHANIC'
        
        this.setState({
            ...this.state,
            selected: {...this.state.selected, roles: updateRolesString},
            [event.target.name] : event.target.value
        })
        console.log(this.state)
        
    }

    render(){
        const { classes } = this.props;
        const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

        return(
                   
            <Grid container spacing={3}>

            <Grid item xs={12}>
                <Paper className={classes.paper}>
                    <form className={classes.root} noValidate autoComplete="off">
                        <div>
                            Select user in order to edit it.
                            <Button 
                                variant="contained" 
                                className={classes.menuButton}
                                color="secondary"

                                onClick={() => {
                                    // let emptyObj = Object.keys(USERS_HEADER_DATA).map(key => USERS_HEADER_DATA[key] = '')    
                                    this.selectUser(USERS_HEADER_DATA)
                                }}
                                >
                                Create User
                            </Button>
                            
                        </div>
                    </form>
                </Paper>
              </Grid>

              {/* Chart */}
              <Grid item xs={12} md={6} lg={8}>
                <Paper className={fixedHeightPaper}>
                            
                    <DataComponent 
                        tableRows={this.state.users}
                        tableHeader = {USERS_HEADER_DATA}
                        selectedId={this.state.selected.companyNum}
                        selectRow={this.selectUser}
                        
                    />
                     

                </Paper>
              </Grid>
              <Grid item xs={12} md={6} lg={4}>
                <Paper className={fixedHeightPaper}>
                  {this.state.selected.companyNum && 
                    <EditUserComponent 
                        selectedUser={this.state.selected} 
                        handleChange={this.handleChange} 
                        handleAuthorityRoleChange={this.handleAuthorityRoleChange} 
                        refreshUsers={this.refreshUsers} 
                        labels = {USERS_HEADER_DATA} 
                        booleanFields = {USERS_BOOLEAN_FIELDS}
                        authoriry={this.state.authoriry} 
                        role={this.state.role}
                    />
                  }
                </Paper>
              </Grid>
              {/* Recent Orders */}
             
            </Grid>
    
     
        )
    }
}


UsersComponent.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(UsersComponent);