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

    render(){

        const isUserLoggedIn = AuthenticationService.isUserLoggedIn();

        return (
            <>
                {   isUserLoggedIn &&
        
                <div>
                    
                    <Link to="/home">
                        <ListItem button>
                        <ListItemIcon>
                            <DashboardIcon />
                        </ListItemIcon>
                        <ListItemText primary="Dashboard" />              
                        </ListItem>
                    </Link>

                    <Link to="/mpd">
                        <ListItem button>
                        <ListItemIcon>
                            <AssignmentIcon />
                        </ListItemIcon>
                        <ListItemText primary="Maintenance Data" />
                        {/* {isUserLoggedIn && <Link to="/mpd"><ListItemText primary=" Maintenance Data" /></Link>} */}
                        </ListItem>
                    </Link>

                    <Link to="/maintenance">
                        <ListItem button>
                        <ListItemIcon>
                            <ListIcon />
                        </ListItemIcon>
                        <ListItemText primary="Maintenance Events" />
                        {/* {isUserLoggedIn && <Link to="/maintenance"><ListItemText primary="Maintenance Events" /></Link>} */}      
                        </ListItem>
                    </Link>

                    <Link to="/aircraft">
                        <ListItem button>
                        <ListItemIcon>
                            <LocalAirportIcon />
                        </ListItemIcon>
                        <ListItemText primary="Aircraft" />

                        {/* {isUserLoggedIn && <Link to="/aircraft"><ListItemText primary="Aircraft" /></Link>} */}
                        </ListItem>
                    </Link>

                    <Link to="/users">
                        <ListItem button>
                        <ListItemIcon>
                            <PeopleIcon />
                        </ListItemIcon>
                        <ListItemText primary="Users" />
                        {/* {isUserLoggedIn && <Link to="/users"><ListItemText primary="Users" /></Link>} */}
                        </ListItem>
                    </Link>

                    <Link to="/Logout">
                    <ListItem button>
                    <ListItemIcon>
                        <BarChartIcon />
                    </ListItemIcon>
                    <ListItemText primary="Logout" onClick={AuthenticationService.logout}/>

                    {/* {isUserLoggedIn && <Link to="/logout" onClick={AuthenticationService.logout}><ListItemText primary="Login" /></Link>} */}
                    </ListItem>
                    </Link>
                </div>
             }

             { !isUserLoggedIn &&
             <div>
                <Link to="/login">
                        <ListItem button>
                        <ListItemIcon>
                            <BarChartIcon />
                        </ListItemIcon>
                        <ListItemText primary="Login" />
                        {/* {!isUserLoggedIn && <Link to="/login"><ListItemText primary="Login" /></Link>} */}
                        </ListItem>
                </Link>
                </div>
             }
        </>
        )
    }
}

export default withRouter(ListItems)

