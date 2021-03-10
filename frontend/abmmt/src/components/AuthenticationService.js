class AuthenticationService {

    registerSuccessfullLogin(username, password){
        console.log('registerSuccessfullLogin');
        sessionStorage.setItem('authenticatedUser', username);

    }

    logout(){
        sessionStorage.removeItem('authenticatedUser');
    }

    isUserLoggedIn(){
        return sessionStorage.getItem('authenticatedUser');
    }

    isMechanic(){
        return true;
    }

    isEngineer(){
        return false;
    }
}

export default new AuthenticationService()