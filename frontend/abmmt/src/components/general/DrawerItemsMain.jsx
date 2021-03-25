import React, {Component} from 'react'
import AuthenticationService from '../AuthenticationService.js'
import Utils from '../Utils.js'
import {ICONS_MAPPING} from '../../Constanst.js'

import { withRouter } from 'react-router';

import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Badge from '@material-ui/core/Badge';



class DrawerItemsMain extends Component {
       
    render(){

        const isUserLoggedIn = AuthenticationService.isUserLoggedIn();


        return (
            <>
                {  isUserLoggedIn &&
                <div>
                    
                    <ListItem button onClick={() => Utils.redirectTo(this.props, "/home") }>
                        <ListItemIcon>
                            {ICONS_MAPPING.dashboard}
                        </ListItemIcon>
                        <ListItemText primary="Dashboard" />              
                    </ListItem>
                    
                    
                    <ListItem button onClick={() => Utils.redirectTo(this.props, "/tasks") }>
                        <ListItemIcon>
                             {ICONS_MAPPING.tasks}
                        </ListItemIcon>
                        <ListItemText primary="Maintenance Data" />
                    </ListItem>
                    
                    
                    <ListItem button onClick={() => Utils.redirectTo(this.props, "/maintenance") }>
                        <ListItemIcon>
                            {ICONS_MAPPING.maintenance}
                        </ListItemIcon>
                        <ListItemText primary="Maintenance Events" />
                    </ListItem>
                    
                    <ListItem button onClick={() => Utils.redirectTo(this.props, "/notifications") }>
                        <ListItemIcon>
                            <Badge badgeContent={4} color="secondary">
                                {ICONS_MAPPING.notifications}
                            </Badge>
                        </ListItemIcon>
                        <ListItemText primary="Notifications" />
                    </ListItem>
                    

                    {/* <ListItem button onClick={() => this.redirectTo("/aircraft") }>
                        <ListItemIcon>
                            <LocalAirportIcon />
                        </ListItemIcon>
                        <ListItemText primary="Aircraft" />
                    </ListItem>

                    <ListItem button onClick={() => this.redirectTo("/facility") }>
                        <ListItemIcon>
                            <LocationCityIcon />
                        </ListItemIcon>
                        <ListItemText primary="Faclity" />
                    </ListItem>
                        
                    <ListItem button onClick={() => this.redirectTo("/users") }>
                        <ListItemIcon>
                            <PeopleIcon />
                        </ListItemIcon>
                        <ListItemText primary="Users" />
                    </ListItem> */}
                    

                    {/* <ListItem button onClick={  () => {this.redirectTo("/logout"); AuthenticationService.logout(); }}>
                    <ListItemIcon>
                        <BarChartIcon />
                    </ListItemIcon>
                    <ListItemText primary="Logout" />

                    {/* {isUserLoggedIn && <Link to="/logout" onClick={AuthenticationService.logout}><ListItemText primary="Login" /></Link>} 
                    </ListItem> */}
                    
                </div>
             }

              {/* !isUserLoggedIn &&
                 <div>
                    <ListItem button onClick={() => this.redirectTo("/login") }>
                    <ListItemIcon>
                        <BarChartIcon />
                    </ListItemIcon>
                    <ListItemText primary="Login" />
                    //{ {!isUserLoggedIn && <Link to="/login"><ListItemText primary="Login" /></Link>} }
                    </ListItem>
                
                </div>
              */}
        </>
        )
    }
}

export default withRouter(DrawerItemsMain)

