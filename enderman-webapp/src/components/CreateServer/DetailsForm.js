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

                <p className='step-instruction'>Please fill in your server name, server port and rcon port: </p>

                <Form className='form-group'>

                    <div className='form-container'>

                        <div className='form-group-container'>

                            <Form.Group as={Col} controlId="formName">
                                <div className='label-container'><Form.Label className="label">Name:</Form.Label></div>
                                <div className='control-container'>
                                    <Form.Control
                                        type="text"
                                        defaultValue={this.props.inputValues.name}
                                        name="serverName"
                                        required
                                        onChange={this.props.handleChange}
                                    />
                                </div>
                            </Form.Group>

                        </div>

                        <div className='form-group-container'>

                            <Form.Group controlId="formPort">

                                <div className='label-container'><Form.Label className="label">Port:</Form.Label></div>
                                <div className='control-container'>
                                    <Form.Control
                                        type="number"
                                        defaultValue={this.props.inputValues.port}
                                        name="serverPort"
                                        required
                                        onChange={this.props.handleChange}
                                    />
                                </div>

                            </Form.Group>

                        </div>

                        <div className='form-group-container'>

                            <Form.Group controlId="formRcon">

                                <div className='label-container'><Form.Label className="label">RCON: </Form.Label></div>

                                <div className='control-container'>

                                    <Form.Control
                                        type="number"
                                        defaultValue={this.props.inputValues.rconPort}
                                        name="rconPort"
                                        required
                                        onChange={this.props.handleChange}
                                    />

                                </div>

                            </Form.Group>

                        </div>

                    </div>

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