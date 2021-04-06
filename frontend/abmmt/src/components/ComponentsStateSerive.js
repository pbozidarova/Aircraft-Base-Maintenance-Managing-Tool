import BackendService from '../api/CommonAPI.js'
import Utils from './Utils.js'
class ComponentsStateService {

    refreshTasks(keyState, keyResponse, fetchAll, partialFetch, shouldFetchPartialData, setState, handleInfo){ 
        console.log(this)  
        // let shouldFetchPartialData = this.props.location.fetchDataFromURL
        
        // this.infoMessage(this.props.handleInfo, MESSAGES.pleaseWait);

        //Check if the user wants to render all the tasks or a HATEOAS link requires partial fetch
        shouldFetchPartialData ? partialFetch(keyState, keyResponse, setState, shouldFetchPartialData.href, shouldFetchPartialData.title, handleInfo) 
                               : fetchAll(keyState, keyResponse, setState, handleInfo)
    }

    fetchAll(urlParam, keyResponse, setState, handleInfo){
        BackendService.getAll(urlParam)
        .then(
            response => {
                console.log(response)
                setState({
                    loading : false, 
                    [urlParam] : response.data._embedded[keyResponse]
                }
                //  , this.infoMessage(this.props.handleInfo, MESSAGES.allTasks + MESSAGES.initialAdvice)
                );
            }
        )
        .catch(e => this.props.handleInfo({error: e}));
    }

    partialFetch(keyState, keyResponse, setState, hateoasUrl, title, handleInfo){
        BackendService.fetchDataFrom(hateoasUrl)
        .then(
            response => {
                console.log(response)
                setState({
                    loading : false, 
                    [keyState] : response.data._embedded[keyResponse]
                }, () => Utils.successMessage(handleInfo, title)
                );
            }
        )
        .catch(e => setState({loading : false,}, Utils.errorMessage(e, handleInfo, title)))
    }

}
    export default new ComponentsStateService();