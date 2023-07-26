import { Component } from 'react';
import {Button,Container } from 'react-bootstrap';
import * as Icon from 'react-bootstrap-icons';

class Completed extends Component {

    

    finish = (e) => {
        e.preventDefault();
        window.location.href = '/dashboard'
        
    }

    render() {
        
        return (<Container id='finished-container'>

                <Icon.Check2Circle size={150} color='#88FF88'></Icon.Check2Circle>
           
                <Button className='next-btn' onClick={this.finish}>Next</Button>
            
        </Container>
        );
    }
}

export default Completed;