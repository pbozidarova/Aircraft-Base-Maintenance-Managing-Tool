import {MESSAGES} from '../Constanst.js'
import BackendService from '../api/CommonAPI.js'

class Utils {
    redirectTo(props, urlParam, fetchDataFromURL){

        props.history.push({pathname: urlParam, fetchDataFromURL: fetchDataFromURL})
    }

    formIsValid(errors){
        //CHECK IF THE FORM IS VALID
        let isValid = true;
        Object.keys(errors).forEach( errorField => {

            if(errors[errorField] && isValid) {       
                return isValid = false;
            }
        })
        
        return isValid;
    }

    emptyObj(fullObj){
       return Object.keys(fullObj)
                    .reduce((acc, curr) =>  acc = {...acc, [curr] : ' '}, {} );
    }

    
    allocateCorrectSuccessMessage(handleInfo, messageVar){
        handleInfo({success : MESSAGES.successLoaded + messageVar})
    }

    allocateCorrectErrorMessage(serverError, handleInfo, messageVar){
        let errorToShow = serverError.response == null 
        ? {error: MESSAGES.errorNonExistant + messageVar} 
        : {error: serverError.response.data.message}

        handleInfo(errorToShow);
    }
   
      
}

export default new Utils();