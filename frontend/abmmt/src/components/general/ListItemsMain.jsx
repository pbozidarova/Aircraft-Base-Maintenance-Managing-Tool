import React, {Component} from 'react'
import AuthenticationService from '../AuthenticationService.js'
import Utils from '../Utils.js'

import { withRouter } from 'react-router';

import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import DashboardIcon from '@material-ui/icons/Dashboard';
import AssignmentIcon from '@material-ui/icons/Assignment';
import ListIcon from '@material-ui/icons/List';

class ListItemsMain extends Component {
       
    render(){

        const isUserLoggedIn = AuthenticationService.isUserLoggedIn();


        return (
            <>
                {  isUserLoggedIn &&
                <div>
                    
                    <ListItem button onClick={() => Utils.redirectTo(this.props, "/home") }>
                        <ListItemIcon>
                            <DashboardIcon />
                        </ListItemIcon>
                        <ListItemText primary="Dashboard" />              
                    </ListItem>
                    
                    
                    <ListItem button onClick={() => Utils.redirectTo(this.props, "/mpd") }>
                        <ListItemIcon>
                            <AssignmentIcon />
                        </ListItemIcon>
                        <ListItemText primary="Maintenance Data" />
                        {/* {isUserLoggedIn && <Link to="/mpd"><ListItemText primary=" Maintenance Data" /></Link>} */}
                    </ListItem>
                    
                    
                    <ListItem button onClick={() => Utils.redirectTo(this.props, "/maintenance") }>
                        <ListItemIcon>
                            <ListIcon />
                        </ListItemIcon>
                        <ListItemText primary="Maintenance Events" />
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

export default withRouter(ListItemsMain)

