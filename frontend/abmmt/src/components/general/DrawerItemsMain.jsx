import React, {Component} from 'react'
import AuthenticationService from '../AuthenticationService.js'
import Utils from '../Utils.js'
import {ICONS_MAPPING} from '../../Constanst.js'

import { withRouter } from 'react-router';

import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Badge from '@material-ui/core/Badge';
import Tooltip from '@material-ui/core/Tooltip';



class DrawerItemsMain extends Component {
       
    render(){
        const {select, isSelected} = this.props;
        const isUserLoggedIn = AuthenticationService.isUserLoggedIn();
        return (
            <>
                {  isUserLoggedIn &&
                <div>
                    <Tooltip title="Dashboard" placement="right">
                    <ListItem 
                    key="Dashboard" 
                    selected={isSelected("Dashboard")} 
                    button onClick={() => {select("Dashboard"); Utils.redirectTo(this.props, "/home") }}>
                        <ListItemIcon>
                            {ICONS_MAPPING.dashboard}
                        </ListItemIcon>
                        <ListItemText primary="Dashboard" />              
                    </ListItem>
                    </Tooltip>
                    
                    <Tooltip title="Maintenance Data" placement="right">
                    <ListItem 
                    key="mData" 
                    selected={isSelected("mData")} 
                    button onClick={() =>  {select("mData");  Utils.redirectTo(this.props, "/tasks") }}>
                        <ListItemIcon>
                             {ICONS_MAPPING.tasks}
                        </ListItemIcon>
                        <ListItemText primary="Maintenance Data" />
                    </ListItem>
                    </Tooltip>
                    
                    <Tooltip title="Maintenance Events" placement="right">
                    <ListItem
                    key="Events" 
                    selected={isSelected("Events")} 
                    button onClick={() =>  {select("Events"); Utils.redirectTo(this.props, "/maintenance")} }>
                        <ListItemIcon>
                            {ICONS_MAPPING.maintenance}
                        </ListItemIcon>
                        <ListItemText primary="Maintenance Events" />
                    </ListItem>
                    </Tooltip>
                    
                    <Tooltip title="Notifications" placement="right">
                    <ListItem 
                    key="Notifications" 
                    selected={isSelected("Notifications")} 
                    button onClick={() =>  {select("Notifications"); Utils.redirectTo(this.props, "/notifications")} }>
                        <ListItemIcon>
                            <Badge badgeContent={this.props.openNotifications} color="secondary">
                                {ICONS_MAPPING.notifications}
                            </Badge>
                        </ListItemIcon>
                        <ListItemText primary="Notifications" />
                    </ListItem>
                    </Tooltip>
                  
                </div>
             }
 
        </>
        )
    }
}

export default withRouter(DrawerItemsMain)

