import './App.css';
import Dashboard from '../Dashboard/Dashboard';
import ServerDetails from '../ServerDetails/ServerDetails';
import {BrowserRouter, Route, Routes, Navigate} from 'react-router-dom';
import Login from '../Login/Login';
import logo from '../../../src/logo.png';
import CreateServer from '../CreateServer/CreateServer';
import ServerProperties from '../ServerProperties/ServerProperties';

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

function goHome() {
  window.location.href = '/';
}

function App() {
  
  const token = getToken();

  if(!token) {
    return <Login setToken={setToken} setUser={setUser}/>
  }
  
  return (
  
  <div className='wrapper'>

    <header>

      <img src={logo} alt='Enderman holding a minecraft dirt block' id="enderman-img" onClick={goHome}></img>

      <h1 id='title' onClick={goHome}>Project Enderman</h1>

      <button onClick={logout}>Logout</button>

    </header>

    <BrowserRouter>
      <Routes>

        <Route path='/' element={<Navigate to="/dashboard" />} />
        
        <Route path='/dashboard' element={<Dashboard />}/>

        <Route path='/server/:id' element={<ServerDetails/>}/>

        <Route path='/createserver' element={<CreateServer />}/>

        <Route path='/server/:id/properties' element={<ServerProperties />}/>

      </Routes>
    </BrowserRouter>

  </div>);
}

export default App;
