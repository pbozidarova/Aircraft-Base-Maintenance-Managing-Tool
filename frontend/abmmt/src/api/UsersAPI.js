import axios from 'axios'

class UserService {
    allUsers(){
        // console.log('executed service');
        return axios.get('http://localhost:8000/users/all');
    }

}

export default new UserService();