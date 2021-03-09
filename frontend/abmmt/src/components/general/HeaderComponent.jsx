import React, {Component} from 'react'

import{Link} from 'react-router-dom'


class HeaderComponent extends Component {

    render(){
        return(
            <header>
                <nav >
                    <div><Link to="/home">Aicraft Base Maintenance Management Tool</Link></div>
                    <ul className="navbar-nav">
                        <li><Link to="/home">Home</Link></li>
                        <li><Link to="/mpd">Maintenance Data</Link></li>
                        <li><Link to="/maintenance">Maintenance Events</Link></li>
                        <li><Link to="/aircraft">Aircraft</Link></li>
                    </ul>
                    <ul>
                        <li><Link to="/users">Users</Link></li>
                        <li><Link to="/login">Login</Link></li>
                        <li><Link to="/logout">Logout</Link></li>
                    </ul>
                </nav>
            </header>            
        )
    }
}

export default HeaderComponent;