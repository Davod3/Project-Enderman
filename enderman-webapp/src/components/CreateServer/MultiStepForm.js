import React, { Component } from 'react';
import DetailsForm from './DetailsForm'

class MultiStepForm extends Component {
    state = {
        step: 1,
        name: '',
        port: '',
        url: '',
        hasUrl: false,
        script: '',
    }

    nextStep = () => {
        const { step } = this.state
        this.setState({
            step : step + 1
        })
    }

    prevStep = () => {
        const { step } = this.state
        this.setState({
            step : step - 1
        })
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value})
        console.log(this.state);
    }

    render(){
        const { step, name, port, url, hasUrl, script } = this.state;
        const inputValues = { name, port, url, hasUrl, script };
        
        switch(step) {
        case 1:
            return <DetailsForm
                    nextStep={this.nextStep}
                    handleChange = {this.handleChange}
                    inputValues={inputValues}
                    />
        case 2:
            return <></>
        }
    }
}

export default MultiStepForm;