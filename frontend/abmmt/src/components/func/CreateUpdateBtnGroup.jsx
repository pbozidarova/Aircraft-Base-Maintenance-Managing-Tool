import React, {Component} from 'react'
import {ButtonGroup, Button} from '@material-ui/core';
import {ICONS_MAPPING} from '../../Constanst.js'
import { styles } from '../UseStyles.js'
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';


class CreateUpdateBtnGroup extends Component {

    render(){
        const { classes } = this.props;
        const {validateAndSubmit, submitUpdate, submitCreate} = this.props;        
        return(
            <ButtonGroup row >  
              <Button 
                  variant="contained" 
                  className={classes.menuButton}
                  color="primary"
                  startIcon={ICONS_MAPPING.update}

                  onClick={() => {validateAndSubmit(submitUpdate);}}
                  >
                  Update
              </Button>

              <Button 
                  variant="contained" 
                  className={classes.menuButton}
                  color="default"
                  endIcon={ICONS_MAPPING.create}
                  onClick={() => {validateAndSubmit(submitCreate); }}
                  >
                  Create
              </Button>
          </ButtonGroup>
        )
    }
}


CreateUpdateBtnGroup.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(CreateUpdateBtnGroup);