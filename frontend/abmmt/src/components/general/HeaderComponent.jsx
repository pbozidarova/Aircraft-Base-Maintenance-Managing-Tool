import React, {Component} from 'react'
import AuthenticationService from '../AuthenticationService.js'
import{Link} from 'react-router-dom'
import { withRouter } from 'react-router';


class HeaderComponent extends Component {

    render(){

        const isUserLoggedIn = AuthenticationService.isUserLoggedIn();

        return(
            <header>
                <nav >
                    <div><Link to="/home">Aicraft Base Maintenance Management Tool</Link></div>
                    <ul className="navbar-nav">
                        {isUserLoggedIn && <li><Link to="/home">Home</Link></li>}
                        {isUserLoggedIn && <li><Link to="/mpd">Maintenance Data</Link></li>}
                        {isUserLoggedIn && <li><Link to="/maintenance">Maintenance Events</Link></li>}
                        {isUserLoggedIn && <li><Link to="/aircraft">Aircraft</Link></li>}
                    </ul>
                    <ul>
                        {isUserLoggedIn && <li><Link to="/users">Users</Link></li>}
                        {!isUserLoggedIn && <li><Link to="/login">Login</Link></li>}
                        {isUserLoggedIn && <li><Link to="/logout" onClick={AuthenticationService.logout}>Logout</Link></li>}
                    </ul>
                </nav>
            </header>            
        )
    }
}

export default withRouter(HeaderComponent);