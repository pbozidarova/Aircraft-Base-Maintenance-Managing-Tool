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

    updateOne(entity, num, putObj){
        return axios.put(`${BACKEND_URL}/${entity}/${num}/update`, putObj);
    }

    createOne(entity, num, putObj){
        return axios.put(`${BACKEND_URL}/${entity}/${num}/create`, putObj);
    }

    fetchDataFrom(url){
        return axios.get(url);
    }
   
}

export default new BackendService();