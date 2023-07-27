import React, { useState } from "react";
import "./Login.css";
import PropTypes from 'prop-types';
import {verifyToken} from '../../services/UserService';
import logo from '../../../src/logo.png';

export default function Login({ setToken, setUser }) {
    
    const [username, setUserName] = useState();
    const [usertoken, setUserToken] = useState();

    const handleSubmit = async e => {
        e.preventDefault();
        const validLogin = await verifyToken(username, usertoken);

        console.log(validLogin);

        if(validLogin){
            setToken(usertoken);
            setUser(username)
            window.location.href = "/dashboard";
        } else {

            alert("Wrong username or token!");

        }
    }

    return(

        <div className="login-wrapper">

            <h1>Please Log In</h1>

            <img src={logo} alt='Enderman holding a minecraft dirt block'></img>

            <form onSubmit={handleSubmit}>
            
                <label>
                    <p>Username</p>
                    <input type='text' onChange={e => setUserName(e.target.value)}/>
                </label>

                <label>
                    <p>Token</p>
                    <input type='password' onChange={e => setUserToken(e.target.value)}/>
                </label>

                <div>
                    <button id="login-btn" type="submit">Login</button>
                </div>

            </form>

        </div>
        
    );
}

Login.propTypes = {
    setToken: PropTypes.func.isRequired,
    setUser: PropTypes.func.isRequired
}