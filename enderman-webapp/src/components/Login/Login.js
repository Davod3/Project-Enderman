import React, { useState } from "react";
import "./Login.css";
import PropTypes from 'prop-types';
import {verifyToken} from '../../services/UserService';

export default function Login({ setToken }) {
    
    const [username, setUserName] = useState();
    const [usertoken, setUserToken] = useState();

    const handleSubmit = async e => {
        e.preventDefault();
        const validLogin = await verifyToken(username, usertoken);

        console.log(validLogin);

        if(validLogin){
            setToken(usertoken);
            window.location.href = "/dashboard";
        } else {

            alert("Wrong username or token!");

        }
    }

    return(

        <div className="login-wrapper">

            <h1>Please Log In</h1>

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
                    <button type="submit">Login</button>
                </div>

            </form>

        </div>
        
    );
}

Login.propTypes = {
    setToken: PropTypes.func.isRequired
}