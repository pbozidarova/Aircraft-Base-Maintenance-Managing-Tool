import React, {Component} from 'react'

class AircraftComponent extends Component {

  
    constructor(props){
        super(props)
        this.state = {
            todos: [
                {id: 1, description: 'Learn to Dance', done: false, targerDate: new Date()},
                {id: 2, description: 'Learn React', done: false, targerDate: new Date()},
                {id: 3, description: 'Become Expert', done: false, targerDate: new Date()}
            ]
        }
    }

    render(){
        return ( 
            <div>
                <h1>Aircraft Component</h1>
                <div className="container">
                    <table className="table">
                        <thead>
                            <tr>
                                {/* <th>id</th> */}
                                <th>description</th>
                                <th>Target Date</th>
                                <th>Is Completed</th>
                                
                            </tr>
                        </thead>
                        <tbody>
                            {   
                                this.state.todos.map(
                                    todo =>
                                        <tr key={todo.id}>
                                            <td>{todo.description}</td>
                                            <td>{todo.targerDate.toString()}</td>
                                            <td>{todo.done.toString()}</td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}




export default AircraftComponent;