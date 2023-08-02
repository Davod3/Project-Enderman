import React, { Component } from 'react';
import { Form, Button, Col, Container } from 'react-bootstrap';


class DownloadForm extends Component {

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

            <p className='step-instruction'>Please insert the download url for your server files: <br/><i>(or leave blank if already downloaded)</i></p>

            <Form className='form-group'>
                <Form.Group as={Col} controlId="formURL">
                    <Form.Label className="label">URL: </Form.Label>
                    <Form.Control
                        type="text"
                        defaultValue={this.props.inputValues.url}
                        name="url"
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

export default DownloadForm;