import { createMuiTheme } from '@material-ui/core/styles';


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
    minHeight: 300
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
      // justify-content: 'center',
      // height: 50,
      // width: 100
      // margin: theme.spacing(2),
    },
    fabChild: {
      flexGrow: 1
    },
   table: {
    width: '100%',
   },
   tableBody: {
    flexGrow: 1,
   },
    tableCell: {
      padding: 5 ,
      // margin: 0,
      textAlign: 'left'
    },
  //   tableRow: {
  //     flexGrow: 1,
  //     width:100,
  //     borderBottom: 'unset',

  //     // margin: 0,
  //     // textAlign: 'left'
  //   },
  });