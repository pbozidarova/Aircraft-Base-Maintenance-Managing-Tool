import {BACKEND_URL, SESSION_ATTRIBUTE_NAME} from '../Constanst.js'

class AuthenticationService {

    registerSuccessfullLogin(username, password){
        console.log('registerSuccessfullLogin');
        sessionStorage.setItem(SESSION_ATTRIBUTE_NAME, username);
    }

    createToken(token){
        return 'Bearer ' + token;
    }

    logout(){
        sessionStorage.removeItem(SESSION_ATTRIBUTE_NAME);
    }

    isUserLoggedIn(){
        return sessionStorage.getItem(SESSION_ATTRIBUTE_NAME);
    }

    isMechanic(){
        return true;
    }

    isEngineer(){
        return false;
    }
}

export default new AuthenticationService()