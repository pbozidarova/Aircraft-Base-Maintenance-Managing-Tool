import BackendService from '../api/CommonAPI.js'
import Utils from './Utils.js'
import {MESSAGES} from '../Constanst.js'

class ComponentsStateService {

    refreshData(keyState, keyResponse, fetchAll, partialFetch, shouldFetchPartialData, setState, handleInfo, renderedInfo){         
        Utils.infoMessage(handleInfo, MESSAGES.pleaseWait);

        //Check if the user wants to render all the tasks or a HATEOAS link requires partial fetch
        shouldFetchPartialData ? partialFetch(keyState, keyResponse, setState, shouldFetchPartialData.href, shouldFetchPartialData.title, handleInfo) 
                               : fetchAll(keyState, keyResponse, setState, handleInfo, renderedInfo)
    }

    fetchAll(urlParam, keyResponse, setState, handleInfo, renderedInfo){
        BackendService.getAll(urlParam)
        .then(
            response => {
                setState({
                    loading : false, 
                    [urlParam] : response.data._embedded[keyResponse]
                }, () => {Utils.successMessage(handleInfo, MESSAGES[renderedInfo])});
            }
        ).catch(e => {Utils.errorMessage(e, handleInfo, MESSAGES.allData )});
    }

    partialFetch(keyState, keyResponse, setState, hateoasUrl, title, handleInfo){
        BackendService.fetchDataFrom(hateoasUrl)
        .then(
            response => {
                setState({
                    loading : false, 
                    [keyState] : response.data._embedded[keyResponse]
                }, () => Utils.successMessage(handleInfo, title)
                );
            }
        )
        .catch(e => setState({loading : false,}, Utils.errorMessage(e, handleInfo, title)))
    }

    submitUpdate(errors, entity, selectedNum, selected, refreshData, handleInfo){
        if(Utils.formIsValid(errors)) {
            BackendService.updateOne(entity, selectedNum, selected)
                .then((r) => {                        
                    refreshData()
                    Utils.successMessage(handleInfo, MESSAGES.successUpdated)
                    
                }).catch(e => {
                    Utils.errorMessage(e, handleInfo )
                })
        
        }
      }
  
      submitCreate(errors, entity, selectedNum, selected, refreshData, handleInfo){  
        if(Utils.formIsValid(errors)) {
            BackendService.createOne(entity, selectedNum, selected)
                .then((response) => {        
                    console.log(response)                
                    refreshData()
                    Utils.successMessage(handleInfo, MESSAGES.successCreated)
                }
                ).catch(e => {
                    console.log(e)
                    Utils.errorMessage(e, handleInfo )
                })
        }
      }

}
    export default new ComponentsStateService();