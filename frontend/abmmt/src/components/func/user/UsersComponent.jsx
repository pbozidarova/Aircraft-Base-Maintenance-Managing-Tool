import React, {Component} from 'react'
import BackendService from '../../../api/CommonAPI.js'
import {USERS_BOOLEAN_FIELDS, USERS_HEADER_DATA, MESSAGES} from '../../../Constanst.js'
import Utils from '../../Utils.js'

import { styles } from '../../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';

import EditUserComponent from './UserEditComponent'
import DataComponent from '../DataComponent'
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';

import PropTypes from 'prop-types';
import clsx from 'clsx';

import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';


class UsersComponent extends Component {
    constructor(props){
        super(props)
        
        this.state = {
            infoPanel : {
                info: MESSAGES.initialLoad,
                success: MESSAGES.empty,
                error: MESSAGES.empty,
            },
            users : [],
            selected: {},
            authority: {},
            role: {},
        }

        this.refreshUsers = this.refreshUsers.bind(this)
        this.selectUser = this.selectUser.bind(this)
        this.handleAuthorityRoleChange = this.handleAuthorityRoleChange.bind(this)
        this.handleChange = this.handleChange.bind(this)
        this.handleInfo = this.handleInfo.bind(this);
        this.Alert = this.Alert.bind(this);

    }
   
    componentDidMount(){
        // let username = AuthenticationService.isUserLoggedIn();
        this.refreshUsers();
        this.selectUser(Utils.emptyObj(USERS_HEADER_DATA));

        // this.createEmptySelect();
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
        let authorityObj = user.roles.includes('ADMIN') ? 'ADMIN' : 'USER'
        let roleObj = user.roles.includes('ENGINEER') ? 'ENGINEER' : 'MECHANIC'
        
        this.setState({
            selected: user, 
            authority: authorityObj,
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
        
        let eName = event.target.name
        let eValue = event.target.value
        let updateAuthortyAndRoles = '';

        console.log(eName + ' ' + eValue)
        if(eName.includes('authority')) {
            updateAuthortyAndRoles += eValue.includes('ADMIN') ? 'ADMIN' : 'USER'
        } else {
            updateAuthortyAndRoles += this.state.authority
        }
        
        if(eName.includes('role')) {
            updateAuthortyAndRoles += eValue.includes('ENGINEER') ? ', ENGINEER' : ', MECHANIC'
        } else {
            updateAuthortyAndRoles += `, ${this.state.role}`
        }
        
        console.log(updateAuthortyAndRoles)
        
        this.setState({
            ...this.state,
            selected: {...this.state.selected, roles: updateAuthortyAndRoles},
            [eName] : eValue
        })
        
        
    }

    handleInfo(msg){
        this.setState({...this.state, infoPanel : msg})
    }

    Alert(props) {
        return <MuiAlert elevation={6} variant="filled" {...props} />;
    }

    render(){
        const { classes } = this.props;
        const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

        return(
                   
            <Grid container spacing={3}>

            <Grid item xs={12}>
                    <div>
                        {this.state.infoPanel.info && <this.Alert severity="info" >{this.state.infoPanel.info} </this.Alert>}
                        {this.state.infoPanel.success && <this.Alert severity="success" >{this.state.infoPanel.success} </this.Alert>}
                        {this.state.infoPanel.error && <this.Alert severity="error" >{this.state.infoPanel.error} </this.Alert>}
                    </div>
              </Grid>

              {/* Chart */}
              <Grid item xs={12} md={8} lg={8}>
                <Paper className={fixedHeightPaper}>
                            
                    <DataComponent 
                        tableRows={this.state.users}
                        tableHeader = {USERS_HEADER_DATA}
                        selectedId={this.state.selected.companyNum}
                        selectRow={this.selectUser}
                        
                    />
                     
                </Paper>
              </Grid>
              <Grid item xs={12} md={4} lg={4}>
                <Paper className={fixedHeightPaper}>
                  {this.state.selected.companyNum && 
                    <EditUserComponent 
                        selectedUser={this.state.selected} 
                        handleChange={this.handleChange} 
                        handleAuthorityRoleChange={this.handleAuthorityRoleChange} 
                        handleInfo={this.handleInfo}
                        refreshUsers={this.refreshUsers} 
                        labels = {USERS_HEADER_DATA} 
                        booleanFields = {USERS_BOOLEAN_FIELDS}
                        authority={this.state.authority} 
                        role={this.state.role}
                    />
                  }
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