import React, { Component } from 'react';
import { Form, Button, Col, Container } from 'react-bootstrap';


class DetailsForm extends Component {

    back = (e) => {
        e.preventDefault();
        window.location.href = '/dashboard';
    }

    saveAndContinue = (e) => {
        e.preventDefault();
        this.props.nextStep();
    };


    render() {
        return (
        
        <Container>

            <p className='step-instruction'>Please fill in your server name and port: </p>

            <Form className='form-group'>
                <Form.Group as={Col} controlId="formName">
                    <Form.Label className="label">Name:</Form.Label>
                    <Form.Control
                        type="text"
                        defaultValue={this.props.inputValues.name}
                        name="serverName"
                        required
                        onChange={this.props.handleChange}
                    />
                </Form.Group>

                <Form.Group controlId="formPort">
                    <Form.Label className="label">Port:</Form.Label>
                    <Form.Control
                        type="number"
                        defaultValue={this.props.inputValues.port}
                        name="serverPort"
                        required
                        onChange={this.props.handleChange}
                    />
                </Form.Group>

                <div className='btn-container'>

                    <Button className='next-btn' onClick={this.back}>Cancel</Button>
                    <Button className='next-btn' onClick={this.saveAndContinue}>Next</Button>

                </div>
                
            </Form>
        </Container>
        );
    }
}

export default DetailsForm;