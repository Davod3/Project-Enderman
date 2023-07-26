import { Component } from 'react';
import DetailsForm from './DetailsForm'
import DownloadForm from './DownloadForm';
import ScriptForm from './ScriptForm';
import Completed from './Completed';
import { createServer } from '../../services/CreateService';

class MultiStepForm extends Component {
    state = {
        id: '',
        step: 1,
        serverName: '',
        serverPort: '',
        url: '',
        hasUrl: false,
        script: '',
    }

    nextStep = async () => {
        
        const { id, step, serverName, serverPort, url, hasUrl, script } = this.state

        let result;

        switch(step){

            case 1:
                //Handle case 1 requests
                console.log(serverName);
                console.log(serverPort);
                result = await this.createServer(serverName, serverPort);

                break;
            case 2: 
                //Handle case 2 requests
                console.log(url);
                console.log(hasUrl);
                break;
            case 3:
                //Handle case 3 requests
                console.log(script);
                break;
        }

        console.log(result);

        if(result) {

            console.log('this happens');

            this.setState({
                step : step + 1
            })

        }

        
    }

    createServer = async (serverName, serverPort) => {

        //Call service
        const user = localStorage.getItem('user');
        const token = localStorage.getItem('token');

        const response = await createServer(serverName, serverPort, user, token);

        if(response.errorMsg !== null) {

            alert(response.errorMsg)
            return false;

        }

        this.setState({id : response.result})
        return true;

    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value})
    }

    render(){
        const { id, step, serverName, serverPort, url, hasUrl, script } = this.state;
        const inputValues = { serverName, serverPort, url, hasUrl, script };
        
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