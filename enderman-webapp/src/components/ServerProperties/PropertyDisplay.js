import { Component } from 'react';
import * as Icon from 'react-bootstrap-icons';

class PropertyDisplay extends Component {

    render() {
        
        return (
            <div className='property'>

                <div className='element-container'><p>{this.props.name}</p></div>
                <div className='element-container'><input type='text' className='property-input' value={this.props.value}></input></div>
            
            </div>
        );
    }
}

export default PropertyDisplay;