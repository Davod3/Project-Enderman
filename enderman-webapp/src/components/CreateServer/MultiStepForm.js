import { Component } from 'react';
import DetailsForm from './DetailsForm'
import DownloadForm from './DownloadForm';
import ScriptForm from './ScriptForm';
import Completed from './Completed';

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

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value})
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
            return <DownloadForm
                    nextStep={this.nextStep}
                    handleChange = {this.handleChange}
                    inputValues = {inputValues}/>

        case 3:
            return <ScriptForm
                    nextStep={this.nextStep}
                    handleChange={this.handleChange}
                    inputValues={inputValues}/>

        case 4:
            return <Completed/>
        }
    }
}

export default MultiStepForm;