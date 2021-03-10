import React, {Component} from 'react'
import LoginComponent from './LoginComponent'
import LogoutComponent from './LogoutComponent'

import ErrorComponent from './ErrorComponent'
import AircraftComponent from '../func/AircraftComponent'
import TaskComponent from '../func/TaskComponent'
import MaintenanceComponent from '../func/MaintenanceComponent'
import UserComponent from '../func/UserComponent'
import{BrowserRouter as Route, Switch, Link, withRouter} from 'react-router-dom'
import ListItems from './ListItems.jsx';

class SwitchComponent extends Component {

    render(){
        return (
            
            <Switch>
                <Route path="/" exact component={LoginComponent}></Route>
                <Route path="/home"></Route>
                <Route path="/mpd" component={TaskComponent}></Route>
                <Route path="/maintenance" component={MaintenanceComponent}></Route>
                <Route path="/aircraft" component={AircraftComponent}></Route>
                
                <Route path="/users" component={UserComponent}></Route>
                <Route path="/login" component={LoginComponent}></Route>
                <Route path="/logout" component={LogoutComponent}></Route>
                <Route component={ErrorComponent}/>
            </Switch>                
            
        )
    }
}

export default withRouter(SwitchComponent);