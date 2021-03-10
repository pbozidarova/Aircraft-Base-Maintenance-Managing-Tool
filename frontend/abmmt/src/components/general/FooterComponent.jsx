import React, {Component} from 'react'
import Link from '@material-ui/core/Link';
import Typography from '@material-ui/core/Typography';

class FooterComponent extends Component {

    render(){
        return (
            <Typography variant="body2" color="textSecondary" align="center">
              {'Copyright © '}
              
                <Link color="inherit" to="/home">Aicraft Base Maintenance Management Tool</Link>
              {' '}
              {new Date().getFullYear()}
              {'.'}
            </Typography>
          );
    }
}


export default FooterComponent;