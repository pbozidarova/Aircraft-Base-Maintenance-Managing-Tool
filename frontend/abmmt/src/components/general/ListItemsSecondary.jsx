import React, {Component} from 'react'
import AuthenticationService from '../AuthenticationService.js'
import Utils from '../Utils.js'

import { withRouter } from 'react-router';

import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import LocationCityIcon from '@material-ui/icons/LocationCity';
import PeopleIcon from '@material-ui/icons/People';
import LocalAirportIcon from '@material-ui/icons/LocalAirport';

class ListItemsSecondary extends Component {
    
    redirectTo(urlParam){
        this.props.history.push(urlParam)
    }

    
    render(){

        const isUserLoggedIn = AuthenticationService.isUserLoggedIn();


        return (
            <>
                {  isUserLoggedIn &&
                <div>
                    
                    <ListItem button onClick={() => Utils.redirectTo(this.props, "/aircraft") }>
                        <ListItemIcon>
                            <LocalAirportIcon />
                        </ListItemIcon>
                        <ListItemText primary="Aircraft" />
                    </ListItem>

                    <ListItem button onClick={() => Utils.redirectTo(this.props, "/facility") }>
                        <ListItemIcon>
                            <LocationCityIcon />
                        </ListItemIcon>
                        <ListItemText primary="Faclity" />
                    </ListItem>
                        
                    <ListItem button onClick={() => Utils.redirectTo(this.props, "/users") }>
                        <ListItemIcon>
                            <PeopleIcon />
                        </ListItemIcon>
                        <ListItemText primary="Users" />
                    </ListItem>
                    

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

export default withRouter(ListItemsSecondary)

