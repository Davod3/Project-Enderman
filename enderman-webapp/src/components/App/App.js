import './App.css';
import Dashboard from '../Dashboard/Dashboard';
import ServerDetails from '../ServerDetails/ServerDetails';
import {BrowserRouter, Route, Routes, Navigate} from 'react-router-dom';
import Login from '../Login/Login';
import logo from '../../../src/logo.png';
import CreateServer from '../CreateServer/CreateServer';

function setToken(userToken) {

  localStorage.setItem('token', userToken);

}

function setUser(username) {

  localStorage.setItem('user', username)

}

function getToken() {

  const token = localStorage.getItem('token');

  return token;

}

function logout() {

  if(getToken() !== null) {
    localStorage.removeItem('token');
    window.location.href = "/login";
  }

}

function App() {
  
  const token = getToken();

  if(!token) {
    return <Login setToken={setToken} setUser={setUser}/>
  }
  
  return (
  
  <div className='wrapper'>

    <header>

      <img src={logo} alt='Enderman holding a minecraft dirt block'></img>

      <h1>Project Enderman</h1>

      <button onClick={logout}>Logout</button>

    </header>

    <BrowserRouter>
      <Routes>

        <Route path='/' element={<Navigate to="/dashboard" />} />
        
        <Route path='/dashboard' element={<Dashboard />}/>

        <Route path='/server/:id' element={<ServerDetails/>}/>

        <Route path='/createserver' element={<CreateServer />}/>

      </Routes>
    </BrowserRouter>

  </div>);
}

export default App;
