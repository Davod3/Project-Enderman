import React, { Component } from 'react';
import { Form, Button, Col, Container } from 'react-bootstrap';


class DetailsForm extends Component {

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
            <Form className='form-group'>
                <Form.Group as={Col} controlId="formName">
                    <Form.Label className="label">Server Name:</Form.Label>
                    <Form.Control
                        type="text"
                        defaultValue={this.props.inputValues.name}
                        name="serverName"
                        required
                        onChange={this.props.handleChange}
                    />
                </Form.Group>

                <Form.Group controlId="formPort">
                    <Form.Label className="label">Server Port:</Form.Label>
                    <Form.Control
                        type="number"
                        defaultValue={this.props.inputValues.port}
                        name="serverPort"
                        required
                        onChange={this.props.handleChange}
                    />
                </Form.Group>

                <Button className='next-btn' onClick={this.saveAndContinue}>Next</Button>
            </Form>
        </Container>
        );
    }
}

export default DetailsForm;