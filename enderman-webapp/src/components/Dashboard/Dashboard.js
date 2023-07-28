import React, { Component } from "react";
import * as Icon from 'react-bootstrap-icons';
import './Dashboard.css'
import { listServers } from "../../services/CreateService";
import ServerDisplay from "./ServerDisplay";


class Dashboard extends Component {

    state = {
        serverList: []
    }

    createServer = () => {
        window.location.href = '/createserver'
    }

    loadServerList = async () => {

        const username = localStorage.getItem('user');
        const token = localStorage.getItem('token');

        const servers = await listServers(username, token);

        this.setState({serverList : servers});

    }

    handleClick = (server) => {
        //TODO
    }

    render() {

        return (

            <div className="dashboard-wrapper">

                <h1 id='dashboard-title'>Dashboard</h1>

                <div id='list-wrapper'>

                    <Icon.PlusCircle size={30} onClick={this.createServer}></Icon.PlusCircle>
                    <hr />
                    <div id='server-list-container'>

                        {this.state.serverList.map(server => <ServerDisplay server={server} clickEvent={this.handleClick} />)}

                    </div>

                </div>

            </div>

        );
    }

}

export default Dashboard;