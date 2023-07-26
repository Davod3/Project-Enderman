import React, { Component } from 'react';
import { Form, Button, Col, Container } from 'react-bootstrap';


class DownloadForm extends Component {

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
                <Form.Group as={Col} controlId="formURL">
                    <Form.Label className="label">Download Url: </Form.Label>
                    <Form.Control
                        type="text"
                        defaultValue={this.props.inputValues.url}
                        name="url"
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

export default DownloadForm;