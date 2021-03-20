

class Utils {
    redirectTo(props, urlParam){
        props.history.push(urlParam)
    }
}

export default new Utils();