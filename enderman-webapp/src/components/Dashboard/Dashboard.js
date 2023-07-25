import React from "react";
import * as Icon from 'react-bootstrap-icons';
import './Dashboard.css'

export default function Dashboard() {
    return(
        
        <div className="dashboard-wrapper">

            <h1 id='dashboard-title'>Dashboard</h1>

            <div id='list-wrapper'>
               
                <Icon.PlusCircle size={30}></Icon.PlusCircle>
                <hr/>

            </div>

        </div>

    );
}