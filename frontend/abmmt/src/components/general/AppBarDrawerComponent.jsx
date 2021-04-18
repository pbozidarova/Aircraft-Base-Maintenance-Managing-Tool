import React, {Component} from 'react'
import AuthenticationService from '../AuthenticationService.js'
import { withRouter } from 'react-router';

import logo from '../../favicon-32x32.png'
import clsx from 'clsx';

import IconButton from '@material-ui/core/IconButton';
import PropTypes from 'prop-types';

import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Drawer from '@material-ui/core/Drawer';
import Button from '@material-ui/core/Button';
import List from '@material-ui/core/List';
import Divider from '@material-ui/core/Divider';
import DrawerItemsMain from './DrawerItemsMain.jsx';
import DrawerItemsSecondary from './DrawerItemsSecondary.jsx';

import { styles } from '../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';
import Utils from '../Utils.js';
import { MESSAGES } from '../Constants.js';



  class AppBarDrawerComponent extends Component {

    constructor(props){
        super(props)
        this.state = {
            selected: '',
            open : false,
            
        }
        
        this.handleDrawerOpen = this.handleDrawerOpen.bind(this)
        this.handleDrawerClose = this.handleDrawerClose.bind(this)    
        this.select = this.select.bind(this);
        this.isSelected = this.isSelected.bind(this);    
    }

    redirectTo(urlParam){
        this.props.history.push(urlParam)
    }
   
    handleDrawerOpen = () => {        
        this.setState({ open : true })
    };

    handleDrawerClose = () => {
        this.setState({ open : false })
    };

    select(menuItem) {
        return this.state.selected = menuItem;
    }

    isSelected(menuItem) {
        return this.state.selected === menuItem;
    }

      
    render(){
        const { classes, handleInfo, openNotifications } = this.props;
        const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);
        const drawerWidth = 240;
        const isUserLoggedIn = AuthenticationService.isUserLoggedIn();
    
        return(
        <>
                <AppBar position="absolute" className={clsx(classes.appBar, this.state.open && classes.appBarShift)}>
                <Toolbar className={classes.toolbar}>
                    <IconButton
                        edge="start"
                        color="inherit"
                        aria-label="open drawer"
                        onClick={this.handleDrawerOpen}
                        className={clsx( this.state.open && classes.menuButtonHidden)}
                    >
                        <img src={logo} className="App-logo" alt="logo" />
                    </IconButton>
                    
                    <Typography component="h6" variant="h6" color="inherit" noWrap className={classes.title} > 
                        Aicraft Base Maintenance Management Tool
                    </Typography>

                    {isUserLoggedIn && <Button 
                        variant="contained" 
                        className={classes.logoutButton}
                        onClick={  () => {Utils.infoMessage(handleInfo, MESSAGES.successLogOut ); 
                                            this.redirectTo("/logout"); 
                                            AuthenticationService.logout()}}>
                        Logout
                    </Button>}

                </Toolbar>
                </AppBar>

                <Drawer
                    variant="permanent"
                    classes={{
                        paper: clsx(classes.drawerPaper, !this.state.open && classes.drawerPaperClose),
                    }}
                    open={this.state.open}
                >
                    <div className={classes.toolbarIcon}>
                        <IconButton onClick={this.handleDrawerClose}>
                        <img src={logo} className="App-logo" alt="logo" />
                        </IconButton>
                    </div>
                    <Divider />
                    <List>
                        <DrawerItemsMain 
                        openNotifications={openNotifications} 
                        select={this.select} 
                        isSelected={this.isSelected}/>
                    </List>

                    <Divider />
                    <List>
                        <DrawerItemsSecondary 
                        select={this.select}
                        isSelected={this.isSelected}/>
                    </List>
              
                </Drawer>
              </>                 
        )
    }
}

AppBarDrawerComponent.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(withRouter(AppBarDrawerComponent));
// export default AppBarComponent;