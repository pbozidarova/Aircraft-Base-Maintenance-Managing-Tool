
class Utils {
    redirectTo(props, urlParam, fetchDataFromURL){
        console.log('param ' + urlParam)
        props.history.push({pathname: urlParam, fetchDataFromURL})
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

    

      
}

export default new Utils();