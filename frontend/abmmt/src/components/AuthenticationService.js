import {BACKEND_URL, SESSION_ATTRIBUTE_NAME} from '../Constanst.js'
import axios from 'axios'

class AuthenticationService {


    constructor(){
        this.myInterceptor = null;
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
    }

    createToken(token){
        return 'Bearer ' + token;
    }

    logout(){
        axios.interceptors.request.eject(this.myInterceptor);
        this.myInterceptor = null;
        sessionStorage.removeItem(SESSION_ATTRIBUTE_NAME)
    }

    isUserLoggedIn(){
        return this.myInterceptor != null;
        // return sessionStorage.getItem(SESSION_ATTRIBUTE_NAME);
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