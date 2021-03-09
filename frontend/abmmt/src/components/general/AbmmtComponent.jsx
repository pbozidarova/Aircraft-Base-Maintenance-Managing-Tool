import React, {Component} from 'react'
import LoginComponent from './LoginComponent'
import HeaderComponent from './HeaderComponent'
import FooterComponent from './FooterComponent'
import ErrorComponent from './ErrorComponent'
import AircraftComponent from '../func/AircraftComponent'
import TaskComponent from '../func/TaskComponent'
import MaintenanceComponent from '../func/MaintenanceComponent'
import UserComponent from '../func/UserComponent'
import{BrowserRouter as Router, Route, Switch, Link} from 'react-router-dom'


class AbmmtComponent extends Component {

    render(){
        return (
            <div className="abmmt">
                
                <Router>
                    <>  
                        <HeaderComponent/>
                        <Switch>
                            <Route path="/" exact component={LoginComponent}></Route>
                            <Route path="/home"></Route>
                            <Route path="/mpd" component={TaskComponent}></Route>
                            <Route path="/maintenance" component={MaintenanceComponent}></Route>
                            <Route path="/aircraft" component={AircraftComponent}></Route>
                            
                            <Route path="/users" component={UserComponent}></Route>
                            <Route path="/login" component={LoginComponent}></Route>
                            <Route path="/logout"></Route>
                            <Route component={ErrorComponent}/>
                        </Switch>
                        <FooterComponent/>
                    </>
                </Router>
                
            </div>
        )
    }
}

export default AbmmtComponent;