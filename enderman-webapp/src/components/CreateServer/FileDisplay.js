import { Component } from 'react';

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