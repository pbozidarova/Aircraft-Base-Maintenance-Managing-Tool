import React, {Component} from 'react'
import {ButtonGroup, Button} from '@material-ui/core';
import {ICONS_MAPPING} from '../../Constanst.js'
import { styles } from '../UseStyles.js'
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import ComponentsStateService from '../ComponentsStateService.js'


class CreateUpdateBtnGroup extends Component {

    render(){
        const { classes } = this.props;
        const {validateAndSubmit, refreshData, reset} = this.props;        
        return(
            <ButtonGroup className={classes.buttonGroup} >  
              <Button 
                  variant="contained" 
                  className={classes.menuButton}
                  color="primary"
                  startIcon={ICONS_MAPPING.update}

                  onClick={() => {validateAndSubmit(ComponentsStateService.submitUpdate, refreshData);}}
                  >
                  Update
              </Button>

              <Button 
                  variant="contained" 
                  className={classes.menuButton}
                  color="default"
                  endIcon={ICONS_MAPPING.create}
                  onClick={() => {validateAndSubmit(ComponentsStateService.submitCreate, refreshData); }}
                  >
                  Create
              </Button>

              <Button 
                  variant="contained" 
                  className={classes.menuButton}
                  color="secondary"
                  endIcon={ICONS_MAPPING.reset}
                  onClick={reset}
                  >
                  Reset
              </Button>
          </ButtonGroup>
        )
    }
}


CreateUpdateBtnGroup.propTypes = {
    classes: PropTypes.object.isRequired,
  };
  
  export default withStyles(styles)(CreateUpdateBtnGroup);