import { Component } from 'react';
import {Button,Container } from 'react-bootstrap';
import * as Icon from 'react-bootstrap-icons';

class ServerDisplay extends Component {

    handleClick = (e) => {
        e.preventDefault();

        this.props.clickEvent(this.props.server);
    }

    render() {
        
        return (
            <div onClick={this.handleClick} className='server'>

                <p>{this.props.server.name}</p>

            </div>
        );
    }
}

export default ServerDisplay;