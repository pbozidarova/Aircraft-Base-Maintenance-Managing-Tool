import React, {Component} from 'react'
import {USERS_BOOLEAN_FIELDS, USERS_HEADER_DATA, USERS_EDIT_FIELDS, MESSAGES} from '../../Constanst.js'
import Utils from '../Utils.js'
import ComponentsStateService from '../ComponentsStateService.js'

import { styles } from '../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';
import { withRouter } from 'react-router';


import DataComponent from './DataComponent'

import PropTypes from 'prop-types';
import clsx from 'clsx';

import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import EditGlobalComponent from './EditGlobalComponent.jsx'


class UsersComponent extends Component {
    constructor(props){
        super(props)
        
        this.state = {
            users : [],
            selected: {},
            selectedId: '',
            loading: true,       
            errors: {},     

            authority: {},
            role: {},
        }

        ComponentsStateService.refreshData = ComponentsStateService.refreshData.bind(this)
        this.setState = this.setState.bind(this)

        this.refreshUsers = this.refreshUsers.bind(this)
        this.selectUser = this.selectUser.bind(this)
        
        this.handleChange = this.handleChange.bind(this)    
        this.updateAuthortyAndRoles = this.updateAuthortyAndRoles.bind(this)
        // this.handleAuthorityRoleChange = this.handleAuthorityRoleChange.bind(this)
        this.handleAutocompleteChange = this.handleAutocompleteChange.bind(this)
        this.validateAndSubmit = this.validateAndSubmit.bind(this);
        this.reset = this.reset.bind(this)
    }
   
    componentDidMount(){
        this.refreshUsers();
        this.reset();
    }
    reset(){
        this.selectUser(Utils.emptyObj(USERS_HEADER_DATA));
    }

    refreshUsers(){
        let keyState = 'users'
        let keyResponse = 'userViewDtoList'
        let shouldFetchPartialData = this.props.location.fetchDataFromURL

        ComponentsStateService.refreshData( keyState, 
                                            keyResponse, 
                                            ComponentsStateService.fetchAll, 
                                            ComponentsStateService.partialFetch, 
                                            shouldFetchPartialData, 
                                            this.setState,
                                            this.props.handleInfo,
                                            "allUsersRenderedInfo");
    }

  
    selectUser( user, selectedId) {
        let authorityObj = user.roles.includes('ADMIN') ? 'ADMIN' : 'USER'
        let roleObj = user.roles.includes('ENGINEER') ? 'ENGINEER' : 'MECHANIC'
        
        this.setState({
            ...this.state,
            selected: { ...user, authority: authorityObj, role: roleObj },
            selectedId,
            
        }, () => console.log(this.state))
        
    }
    
    handleChange(event){
        let eName = event.target.name
        let eValue = event.target.value
        let updateAuthortyAndRoles = this.updateAuthortyAndRoles(eName, eValue);
                
        this.setState(
            {   ...this.state,
                selected: {...this.state.selected, roles: updateAuthortyAndRoles, [eName] : eValue},
                
            }, console.log(this.state))
    }

    handleAutocompleteChange(event, value, key){
        let updateAuthortyAndRoles = this.updateAuthortyAndRoles(key, value);

        this.setState(
            {   ...this.state,
                selected: {...this.state.selected, 
                    roles: updateAuthortyAndRoles, 
                    [key] : value}
            }, () => console.log(this.state)) 
    }

    updateAuthortyAndRoles(eName, eValue){
        let returningAuthortyAndRolesString = "";
        console.log(eName + ' ' + eValue)


        if('authority'.includes(eName)) {
            returningAuthortyAndRolesString += 'ADMIN'.includes(eValue) ? 'ADMIN' : 'USER'
        } else {
            returningAuthortyAndRolesString += this.state.selected.authority
        }
        
        if('role'.includes(eName)) {
            returningAuthortyAndRolesString += 'ENGINEER'.includes(eValue) ? ', ENGINEER' : ', MECHANIC'
        } else {
            returningAuthortyAndRolesString += `, ${this.state.selected.role}`
        }
        console.log(returningAuthortyAndRolesString)

        return returningAuthortyAndRolesString;
    }

    validateAndSubmit(submit, refreshUsers){
        const { selected } = this.state;
        console.log(selected)
        this.setState({ errors: 
             { 
                companyNum: /^[N]\d{5}$/.test(selected.companyNum) ? '' : "Follow the pattern N plus 5 digits!" ,
                firstName:  selected.firstName != 'First Name' && selected.firstName.length > 2 ? '' : "The first name must be longer than 2 symbols!" ,
                lastName:  selected.lastName != 'Last Name' && selected.lastName.length > 2 ? '' : "The last name must be longer than 2 symbols!",
                email: /^\S+@\S+$/.test(selected.email)  ? '' : "Please provide a valid email!",
                facility: selected.facility && selected.facility.length > 2 ? '' : "Please select a facility!",
                authority: selected.authority && selected.authority.length > 0 ? '' : "At least one authority must be selected!",
                role: selected.role && selected.role.length > 0 ? '' : "At least one role must be selected!",

             }
        }, () => submit(this.state.errors, 
                        "users", 
                        selected.companyNum, 
                        selected, 
                        refreshUsers, 
                        this.props.handleInfo)  );
    
    }

    render(){
        const { classes, handleInfo } = this.props;
        const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

        return(
                   
            <Grid container spacing={3}>        

              {/* Chart */}
              <Grid item xs={12} md={8} lg={8}>
                <Paper className={fixedHeightPaper}>
                    <DataComponent 
                        tableRows={this.state.users}
                        tableHeader = {USERS_HEADER_DATA}
                        selectedId={this.state.selectedId}
                        selectRow={this.selectUser} 
                    />
                     
                </Paper>
              </Grid>
              <Grid item xs={12} md={4} lg={4}>
                <Paper className={fixedHeightPaper}>
                  {this.state.selected.companyNum &&
                        <EditGlobalComponent
                        selected={this.state.selected} 
                        selectedId={this.state.selectedId}
                        handleChange={this.handleChange} 
                        handleAutocompleteChange={this.handleAutocompleteChange} 
                //     handleAuthorityRoleChange={this.handleAuthorityRoleChange} 
                        handleInfo={this.props.handleInfo}
                        labels = {USERS_HEADER_DATA} 
                        booleanFields = {USERS_BOOLEAN_FIELDS}
                        editFields={USERS_EDIT_FIELDS}
                        errors={this.state.errors}
                        feedback={MESSAGES.usersEditInfo}
                        validateAndSubmit={this.validateAndSubmit}
                        refreshData={this.refreshUsers}
                        reset={this.reset}

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
  
  export default withStyles(styles)(withRouter(UsersComponent));