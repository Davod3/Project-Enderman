import React from "react";
import * as Icon from 'react-bootstrap-icons';
import './Dashboard.css'
import { listServers } from "../../services/CreateService";

function goToForm() {
    window.location.href='/createserver'
}

async function loadServerList() {

    const username = localStorage.getItem('user');
    const token = localStorage.getItem('token');

    const servers = await listServers(username,token);

    console.log(servers);

}

export default function Dashboard() {
    
    loadServerList();
    
    return(
        
        <div className="dashboard-wrapper">

            <h1 id='dashboard-title'>Dashboard</h1>

            <div id='list-wrapper'>
               
                <Icon.PlusCircle size={30} onClick={goToForm}></Icon.PlusCircle>
                <hr/>

                

            </div>

        </div>

    );
}