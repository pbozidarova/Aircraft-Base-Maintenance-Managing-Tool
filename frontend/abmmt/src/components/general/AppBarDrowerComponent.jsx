import React, {Component} from 'react'
import AuthenticationService from '../AuthenticationService.js'
import { withRouter } from 'react-router';

import logo from '../../favicon-32x32.png'
import clsx from 'clsx';

import IconButton from '@material-ui/core/IconButton';
import NotificationsIcon from '@material-ui/icons/Notifications';
import Badge from '@material-ui/core/Badge';
import PropTypes from 'prop-types';

import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Drawer from '@material-ui/core/Drawer';
import Button from '@material-ui/core/Button';
import List from '@material-ui/core/List';
import Divider from '@material-ui/core/Divider';
import ListItems from './ListItems.jsx';

import { styles } from '../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';


  class AppBarDrawerComponent extends Component {

    constructor(props){
        super(props)
        this.state = {
            open : false
        }
        
        this.handleDrawerOpen = this.handleDrawerOpen.bind(this)
        this.handleDrawerClose = this.handleDrawerClose.bind(this)
        
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


    // [open, setOpen] = React.useState(true);
      
    render(){
        const { classes } = this.props;
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
                    className={clsx(classes.menuButton, this.state.open && classes.menuButtonHidden)}
                    >
                    {/* <MenuIcon /> */}
                    <img src={logo} className="App-logo" alt="logo" />
                    </IconButton>
                    
                    <Typography component="h1" variant="h6" color="inherit" noWrap className={classes.title} > 
                        Aicraft Base Maintenance Management Tool
                    </Typography>

                    {!isUserLoggedIn && <Button 
                        variant="contained"
                        className={classes.menuButton}
                        onClick={() => this.redirectTo("/login") }
                    >
                        Login
                    </Button>}
                    {isUserLoggedIn && <Button 
                        variant="contained" 
                        className={classes.menuButton}
                        onClick={  () => {this.redirectTo("/logout"); AuthenticationService.logout()}}>
                        Logout
                    </Button>}

                    <IconButton color="inherit">
                    <Badge badgeContent={4} color="secondary">
                        <NotificationsIcon />
                    </Badge>
                    </IconButton>
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
                    {/* <ChevronLeftIcon /> */}
                    <img src={logo} className="App-logo" alt="logo" />
        
                    </IconButton>
                </div>
                <Divider />
                {/* <List>{items}</List> */}
                <List><ListItems/></List>
                <Divider />
                {/* <List>{secondaryListItems}</List>  */}
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