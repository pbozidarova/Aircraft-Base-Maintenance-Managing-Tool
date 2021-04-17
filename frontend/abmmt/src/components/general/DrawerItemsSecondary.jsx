import React, {Component} from 'react'
import AuthenticationService from '../AuthenticationService.js'
import Utils from '../Utils.js'

import { withRouter } from 'react-router';

import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';

import { ICONS_MAPPING } from '../../Constanst.js';
import Tooltip from '@material-ui/core/Tooltip';


class DrawerItemsSecondary extends Component {
    
    redirectTo(urlParam){
        this.props.history.push(urlParam)
    }

    render(){
        const {select, isSelected} = this.props;

        const isUserLoggedIn = AuthenticationService.isUserLoggedIn();


        return (
            <>
                {  isUserLoggedIn &&
                <div>
                    <Tooltip title="Aircraft" placement="right">
                    <ListItem 
                    key="Aircraft" 
                    selected={isSelected("Aircraft")} 
                    button onClick={() => {select("Aircraft"); Utils.redirectTo(this.props, "/aircraft")} }>
                        <ListItemIcon>
                            {ICONS_MAPPING.aircraft}
                        </ListItemIcon>
                        <ListItemText primary="Aircraft" />
                    </ListItem>
                    </Tooltip>

                    <Tooltip title="Faclities" placement="right">
                    <ListItem 
                    key="Faclities" 
                    selected={isSelected("Faclities")} 
                    button onClick={() =>{select("Faclities"); Utils.redirectTo(this.props, "/facilities") }}>
                        <ListItemIcon>
                            {ICONS_MAPPING.facilities}
                        </ListItemIcon>
                        <ListItemText primary="Faclities" />
                    </ListItem>
                    </Tooltip>
                        
                    <Tooltip title="Users" placement="right">
                    <ListItem
                    key="Users" 
                    selected={isSelected("Users")} 
                    button onClick={() => {select("Users"); Utils.redirectTo(this.props, "/users")}}>
                        <ListItemIcon>
                            {ICONS_MAPPING.users}
                        </ListItemIcon>
                        <ListItemText primary="Users" />
                    </ListItem>
                    </Tooltip>
                </div>
             }

        </>
        )
    }
}

export default withRouter(DrawerItemsSecondary)

