import axios from 'axios'
import {BACKEND_URL} from '../Constanst.js'


class BackendService {
    all(entity){
        // console.log('executed service');
        return axios.get(`${BACKEND_URL}/${entity}/all`);
    }

    single(entity, num){
        // console.log('executed service');
        return axios.get(`${BACKEND_URL}/${entity}/${num}`);
    }

}

export default new BackendService();