import { Component } from 'react';
import * as Icon from 'react-bootstrap-icons';

class ServerDisplay extends Component {

    handleClick = (e) => {
        e.preventDefault();

        this.props.clickEvent(this.props.server);
    }

    render() {
        
        return (
            <div onClick={this.handleClick} className='server'>

                <div className='server-field-container'><Icon.CircleFill size={40} color={this.props.server.running?'green':'red'}/></div>
                <div className='server-field-container'><p>{this.props.server.name}</p></div>
            
            </div>
        );
    }
}

export default ServerDisplay;