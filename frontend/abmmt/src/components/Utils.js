

class Utils {
    redirectTo(props, urlParam){
        props.history.push(urlParam)
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
}

export default new Utils();