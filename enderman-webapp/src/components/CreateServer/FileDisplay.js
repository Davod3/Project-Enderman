import { Component } from 'react';
import {Button,Container } from 'react-bootstrap';
import * as Icon from 'react-bootstrap-icons';

class FileDisplay extends Component {

    handleClick = (e) => {
        e.preventDefault();

        this.props.clickEvent(this.props.file);
    }

    render() {
        
        return (
            <div onClick={this.handleClick} className='file'>

                <p>{this.props.file.name}</p>

            </div>
        );
    }
}

export default FileDisplay;