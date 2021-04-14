import axios from 'axios'
import {BACKEND_URL} from '../Constanst.js'


class UserService {
    allUsers(){
        // console.log('executed service');
        return axios.get(`${BACKEND_URL}/users/all`);
    }

}

export default new UserService();