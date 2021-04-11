import {BACKEND_URL, SESSION_ATTRIBUTE_NAME} from '../Constanst.js'
import axios from 'axios'

class AuthenticationService {


    constructor(){
        this.myInterceptor = '';
    }

    executeAuthnetication(username, password){
        return axios.post(`${BACKEND_URL}/authenticate`, 
        {
            username,
            password
        })
    }

    registerSuccessfullLogin(username, token){
        console.log('registerSuccessfullLogin');
        sessionStorage.setItem(SESSION_ATTRIBUTE_NAME, username)
            
        this.myInterceptor = this.setupAxiosInterceptors( this.createToken(token) )
        console.log(this.myInterceptor)
    }

    createToken(token){
        console.log("create token " + token)
        return 'Bearer ' + token;
    }

    logout(){
        axios.interceptors.request.eject(this.myInterceptor);
        sessionStorage.removeItem(SESSION_ATTRIBUTE_NAME)
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

    setupAxiosInterceptors = (token) => {
       return axios.interceptors.request.use(
            (config) => {
                if(this.isUserLoggedIn){
                    config.headers.authorization = token;
                }
                return config;
            }
        )               
    }

   
}

export default new AuthenticationService()