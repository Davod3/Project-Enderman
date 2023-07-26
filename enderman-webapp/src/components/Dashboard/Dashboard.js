import React from "react";
import * as Icon from 'react-bootstrap-icons';
import './Dashboard.css'

function goToForm() {
    window.location.href='/createserver'
}

export default function Dashboard() {
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