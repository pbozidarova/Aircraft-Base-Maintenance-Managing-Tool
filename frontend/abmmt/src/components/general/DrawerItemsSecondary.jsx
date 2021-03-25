import React, {Component} from 'react'
import AuthenticationService from '../AuthenticationService.js'
import Utils from '../Utils.js'

import { withRouter } from 'react-router';

import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';

import { ICONS_MAPPING } from '../../Constanst.js';

class DrawerItemsSecondary extends Component {
    
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
                            {ICONS_MAPPING.aircraft}
                        </ListItemIcon>
                        <ListItemText primary="Aircraft" />
                    </ListItem>

                    <ListItem button onClick={() => Utils.redirectTo(this.props, "/facilities") }>
                        <ListItemIcon>
                            {ICONS_MAPPING.facility}
                        </ListItemIcon>
                        <ListItemText primary="Faclities" />
                    </ListItem>
                        
                    <ListItem button onClick={() => Utils.redirectTo(this.props, "/users") }>
                        <ListItemIcon>
                            {ICONS_MAPPING.users}
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

export default withRouter(DrawerItemsSecondary)

