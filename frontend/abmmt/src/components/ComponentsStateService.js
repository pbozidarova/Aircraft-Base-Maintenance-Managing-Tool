import BackendService from '../api/CommonAPI.js'
import Utils from './Utils.js'
import {MESSAGES} from '../Constanst.js'

class ComponentsStateService {

    refreshData(keyState, keyResponse, fetchAll, partialFetch, shouldFetchPartialData, setState, handleInfo){ 
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
                }, () => {Utils.successMessage(handleInfo, MESSAGES.allData)});
            }
        ).catch(e => {Utils.errorMessage(e, handleInfo, MESSAGES.allData )});
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

    submitUpdate(errors, entity, selectedNum, selected, refreshTasks, handleInfo){
        if(Utils.formIsValid(errors)) {
            BackendService.updateOne(entity, selectedNum, selected)
                .then((r) => {                        
                    refreshTasks()
                    Utils.successMessage(handleInfo, MESSAGES.successUpdated)
                    
                }).catch(e => {
                    Utils.errorMessage(e, handleInfo )
                })
        
        }
      }
  
      submitCreate(errors, entity, selectedNum, selected, refreshTasks, handleInfo){  
        if(Utils.formIsValid(errors)) {
            BackendService.createOne(entity, selectedNum, selected)
                .then(() => {                        
                    refreshTasks()
                    Utils.successMessage(handleInfo, MESSAGES.successCreated)
                }
                ).catch(e => {
                    Utils.errorMessage(e, handleInfo )
                })
        }
      }

}
    export default new ComponentsStateService();