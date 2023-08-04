import { Component } from 'react';

class PropertyDisplay extends Component {

    render() {
        
        return (
            <div className='property'>

                <div className='element-container'><p>{this.props.name}</p></div>
                <div className='element-container'><input type='text' className='property-input' defaultValue={this.props.value} name={this.props.name} onChange={this.props.handleChange}></input></div>
            
            </div>
        );
    }
}

export default PropertyDisplay;