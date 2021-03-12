import React, {Component} from 'react'
import AuthenticationService from '../AuthenticationService.js'
import{Link} from 'react-router-dom'
import { withRouter } from 'react-router';

import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import ListSubheader from '@material-ui/core/ListSubheader';
import DashboardIcon from '@material-ui/icons/Dashboard';
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart';
import PeopleIcon from '@material-ui/icons/People';
import BarChartIcon from '@material-ui/icons/BarChart';
import LocalAirportIcon from '@material-ui/icons/LocalAirport';
import AssignmentIcon from '@material-ui/icons/Assignment';
import ListIcon from '@material-ui/icons/List';

class ListItems extends Component {
    
    redirectTo(urlParam){
        this.props.history.push(urlParam)
    }

    
    render(){

        const isUserLoggedIn = AuthenticationService.isUserLoggedIn();


        return (
            <>
                {  isUserLoggedIn &&
                <div>
                    
                    <ListItem button onClick={() => this.redirectTo("/home") }>
                        <ListItemIcon>
                            <DashboardIcon />
                        </ListItemIcon>
                        <ListItemText primary="Dashboard" />              
                    </ListItem>
                    
                    
                    <ListItem button onClick={() => this.redirectTo("/mpd") }>
                        <ListItemIcon>
                            <AssignmentIcon />
                        </ListItemIcon>
                        <ListItemText primary="Maintenance Data" />
                        {/* {isUserLoggedIn && <Link to="/mpd"><ListItemText primary=" Maintenance Data" /></Link>} */}
                    </ListItem>
                    
                    
                    <ListItem button onClick={() => this.redirectTo("/maintenance") }>
                        <ListItemIcon>
                            <ListIcon />
                        </ListItemIcon>
                        <ListItemText primary="Maintenance Events" />
                    </ListItem>
                    

                    
                    <ListItem button onClick={() => this.redirectTo("/aircraft") }>
                        <ListItemIcon>
                            <LocalAirportIcon />
                        </ListItemIcon>
                        <ListItemText primary="Aircraft" />
                    </ListItem>
                        
                    <ListItem button onClick={() => this.redirectTo("/users") }>
                        <ListItemIcon>
                            <PeopleIcon />
                        </ListItemIcon>
                        <ListItemText primary="Users" />
                    </ListItem>
                    

                    <ListItem button onClick={  () => {this.redirectTo("/logout"); AuthenticationService.logout(); }}>
                    <ListItemIcon>
                        <BarChartIcon />
                    </ListItemIcon>
                    <ListItemText primary="Logout" />

                    {/* {isUserLoggedIn && <Link to="/logout" onClick={AuthenticationService.logout}><ListItemText primary="Login" /></Link>} */}
                    </ListItem>
                    
                </div>
             }

             { !isUserLoggedIn &&
                 <div>
                    <ListItem button onClick={() => this.redirectTo("/login") }>
                    <ListItemIcon>
                        <BarChartIcon />
                    </ListItemIcon>
                    <ListItemText primary="Login" />
                    {/* {!isUserLoggedIn && <Link to="/login"><ListItemText primary="Login" /></Link>} */}
                    </ListItem>
                
                </div>
             }
        </>
        )
    }
}

export default withRouter(ListItems)

