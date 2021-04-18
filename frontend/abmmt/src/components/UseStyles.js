import { createMuiTheme, ThemeProvider } from '@material-ui/core/styles';


export const theme = createMuiTheme({
  palette: {
    primary: {
      light: '#ffffee',
      main: '#ffccbc',
      dark: '#cb9b8c',
      contrastText: '#fff',
    },
    secondary: {
      light: '#ffffff',
      main: '#e0e0e0',
      dark: '#aeaeae',
      contrastText: '#000',
    },
  },

});

export const drawerWidth = 240;
// const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);
export const styles = ( theme ) => ({
  abmmt: {
    display: 'flex',
  },

  root: {
    minHeight: 300,
    
    // maxWidth: 400,
    // maxHeight: 300,
  },
  media: {
    height: 140,
  },
    toolbar: {
      paddingRight: 24, // keep right padding when drawer closed
    },
    toolbarIcon: {
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'flex-end',
      padding: '0 8px',
      ...theme.mixins.toolbar,
    },
    appBar: {
      zIndex: theme.zIndex.drawer + 1,
      transition: theme.transitions.create(['width', 'margin'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
      }),
    },
    appBarShift: {
      marginLeft: drawerWidth,
      width: `calc(100% - ${drawerWidth}px)`,
      transition: theme.transitions.create(['width', 'margin'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.enteringScreen,
      }),
    },
   buttonGroup: {
     display: 'flex',
     justifyContent:"space-between"
   },
    menuButton: { 
      margin: 15,
      flexGrow: 1,
      // padding: theme.spacing(1),
    },
    menuButtonHidden: {
      display: 'none',
    },
    title: {
      flexGrow: 1,
    },
    drawerPaper: {  
      position: 'relative',
      whiteSpace: 'nowrap',
      width: drawerWidth,
      transition: theme.transitions.create('width', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.enteringScreen,
      }),
    },
    drawerPaperClose: {
      overflowX: 'hidden',
      transition: theme.transitions.create('width', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
      }),
      width: theme.spacing(7),
      [theme.breakpoints.up('sm')]: {
        width: theme.spacing(9),
      },
    },
    appBarSpacer: theme.mixins.toolbar,
    content: {
      flexGrow: 1,
      height: '100vh',
      overflow: 'auto',
    },
    container: {
      paddingTop: theme.spacing(4),
      paddingBottom: theme.spacing(4),
     
    },
    paper: {
      padding: theme.spacing(1),
      display: 'flex',
      overflow: 'auto',
      flexDirection: 'column',
    },
    fixedHeight: {
      height: 550,
    },
    fixedHeightMin: {
      height: 300,
    },
    fab: {
      display: 'flex',
      flexDirection: 'row',
      alignItems: 'center',
      justifyContent: 'center',
      height: 100
    },
    fabChild: {
      flexGrow: 1
    },
   table: {
    width: '100%',
    fontSize: 100,

   },
   tableBody: {
    width: '100%',

    flexGrow: 1,
   },
   head: {
    // backgroundColor: "#fff",
    backgroundColor: theme.palette.primary.main,
    color: theme.palette.secondary.contrastText,
    padding: 5 ,
    textAlign: 'left',
    position: "sticky",
    top: 0,
    zIndex: 10,
   },
  activeSortIcon: {
    color: "white"
  },
  inactiveSortIcon: {
    color: "white"
  },
  icon: {
    root: {
      color: 'white',
      "&:hover": {
        color: 'white',
      },
      '&$active': {
        color: 'white',
      },
    },
    active: {},
    icon: {
      color: 'inherit !important'
    },
  },
    tableCell: {
      padding: 5 ,
      // margin: 0,
      textAlign: 'left'
    },
    visuallyHidden: {
      border: 0,
      clip: 'rect(0 0 0 0)',
      height: 1,
      margin: -1,
      overflow: 'hidden',
      padding: 0,
      position: 'absolute',
      top: 20,
      width: 1,
    },
    icon: {
      backgroundColor: "grey[500]",
      '& path': {
          fill: '#eee'
      },
  }
  //   tableRow: {
  //     flexGrow: 1,
  //     width:100,
  //     borderBottom: 'unset',

  //     // margin: 0,
  //     // textAlign: 'left'
  //   },
  });