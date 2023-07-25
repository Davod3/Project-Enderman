import './App.css';
import Dashboard from '../Dashboard/Dashboard';
import ServerDetails from '../ServerDetails/ServerDetails';
import {BrowserRouter, Route, Routes, Navigate} from 'react-router-dom';
import Login from '../Login/Login';

function setToken(userToken) {

  localStorage.setItem('token', userToken);

}

function getToken() {

  const token = localStorage.getItem('token');

  console.log("Getting token: " + token)

  return token;

}

function App() {
  
  const token = getToken();

  console.log("Token: " + token)

  if(!token) {
    return <Login setToken={setToken}/>
  }
  
  return (
  
  <div className='wrapper'>

    <h1>Project Enderman</h1>

    <BrowserRouter>
      <Routes>

        <Route path='/' element={<Navigate to="/dashboard" />} />
        
        <Route path='/dashboard' element={<Dashboard />}/>

        <Route path='/server' element={<ServerDetails />}/>

      </Routes>
    </BrowserRouter>

  </div>);
}

export default App;
