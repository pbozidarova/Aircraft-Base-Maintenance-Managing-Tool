import logo from './android-chrome-192x192.png';
import './App.css';
import Button from '@material-ui/core/Button'
import AbmmtComponent from './components/general/AbmmtComponent';

function App() {
  return (
    <div className="App">
      
        <p>
          <img src={logo} className="App-logo" alt="logo" />
        </p>
        <AbmmtComponent/>
        
        <Button onClick={()=>alert('hello')} href="#" variant="contained">Hello</Button>

    </div>
  );
}

export default App;
