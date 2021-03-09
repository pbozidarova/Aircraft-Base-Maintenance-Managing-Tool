import logo from './android-chrome-192x192.png';
import './App.css';
import LoginComponent from './components/LoginComponent'
import Button from '@material-ui/core/Button'

function App() {
  return (
    <div className="App">
      <header className="App-header">

        <LoginComponent></LoginComponent>

        <Button onClick={()=>alert('hello')} href="#" variant="contained">Hello</Button>

        <img src={logo} className="App-logo" alt="logo" />
        
        Hello ABMM Tool!

      </header>
    </div>
  );
}

export default App;
