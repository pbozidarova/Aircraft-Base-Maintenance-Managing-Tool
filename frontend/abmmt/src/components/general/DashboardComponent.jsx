import React, {Component} from 'react'

import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import {DASHBOARD_CARDS, ICONS_MAPPING} from '../../Constanst.js'
import { withRouter } from 'react-router';
import clsx from 'clsx';
import Grid from '@material-ui/core/Grid';
import PropTypes from 'prop-types';
import { styles } from '../UseStyles.js'
import { withStyles } from '@material-ui/core/styles';
import Utils from '../Utils.js'


class DashboardComponent extends Component {
    constructor(props){
      super(props)
    }

    render(){
        const { classes } = this.props;
        const fixedHeightPaper = clsx(classes.paper, classes.fixedHeightDash);

        return (
        <Grid container spacing={4}>
            
            {Object.keys(DASHBOARD_CARDS).map((card, index) => {
             { console.log(DASHBOARD_CARDS[card])}
               return <Grid key={`grid_${index}`} item xs={12} md={4} lg={4}> 
                
                  <Card className={classes.root} key={index}>
                    <CardActionArea onClick={() => Utils.redirectTo( this.props, `/${card}`)}>
                      <CardMedia
                        className={classes.media}
                        image={DASHBOARD_CARDS[card].image}
                        title={DASHBOARD_CARDS[card].title}
                      />
                      <CardContent>
                        <Typography gutterBottom variant="h5" component="h2">
                            {DASHBOARD_CARDS[card].title} {ICONS_MAPPING[card]} 
                        </Typography>
                        <Typography variant="body2" color="textSecondary" component="p">
                            {DASHBOARD_CARDS[card].description}
                        </Typography>
                        <Typography align="right">
                          </Typography>
                      </CardContent>
                    </CardActionArea>
                    <CardActions>
                      <Button size="small" color="primary" onClick={() => Utils.redirectTo( this.props, `/${card}`)}> 
                          Visit {DASHBOARD_CARDS[card].title} 
                      </Button>
                      
                  </CardActions>
                  </Card>
                </Grid>
            })}
            
        </Grid>
  
        )
    }
}


DashboardComponent.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(withRouter(DashboardComponent));