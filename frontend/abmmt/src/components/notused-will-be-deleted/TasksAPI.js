import axios from 'axios'
import {BACKEND_URL} from '../../Constanst.js'


class TasksService {
    allTasks(){
        // console.log('executed service');
        return axios.get(`${BACKEND_URL}/tasks/all`);
    }

}

export default new TasksService();