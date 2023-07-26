import { Component } from 'react';
import {Button,Container } from 'react-bootstrap';


class ScriptForm extends Component {

    back = (e) => {
        e.preventDefault();
        this.props.prevStep();
    }

    saveAndContinue = (e) => {
        e.preventDefault();
        this.props.nextStep();
    };


    render() {
        return (<Container>

                <h2>Here sits a file navigator</h2>
           
                <Button className='next-btn' onClick={this.saveAndContinue}>Next</Button>
            
        </Container>
        );
    }
}

export default ScriptForm;