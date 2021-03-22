import axios from 'axios'
import {BACKEND_URL} from '../Constanst.js'


class BackendService {
    getAll(entity){
        // console.log('executed service');
        return axios.get(`${BACKEND_URL}/${entity}/all`);
    }

    getOne(entity, num){
        // console.log('executed service');
        return axios.get(`${BACKEND_URL}/${entity}/${num}`);
    }

    updateOne(entity, num, updatedObj){
        return axios.patch(`${BACKEND_URL}/${entity}/${num}`, updatedObj);
    }

   
}

export default new BackendService();