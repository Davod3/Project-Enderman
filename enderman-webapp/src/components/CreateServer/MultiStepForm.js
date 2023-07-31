import { Component } from 'react';
import DetailsForm from './DetailsForm'
import DownloadForm from './DownloadForm';
import ScriptForm from './ScriptForm';
import Completed from './Completed';
import { createServer, downloadServer, setScript } from '../../services/CreateService';
import Downloading from './Downloading';

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

    setLoading = (runLoad) => {

        if(runLoad) {
            this.setState({step : 5})
        } else {
            this.setState({step : 2})
        }

    }

    nextStep = async (data) => {
        
        const { id, step, serverName, serverPort, url, hasUrl, script } = this.state

        let result = true;

        switch(step){

            case 1:
                //Handle case 1 requests

                result = await this.createServer(serverName, serverPort);

                break;
            case 2: 
                //Handle case 2 requests
                result = await this.downloadServer(id, url);
                break;
            case 3:
                //Handle case 3 requests
                
                if(data !== undefined && data !== null) {
                    result = this.setScript(id, data);
                } else {
                    alert('Please select a start script :)')
                    result = false;
                }

                break;
        }

        if(result) {

            this.setState({
                step : step + 1
            })

        }

    }

    createServer = async (serverName, serverPort) => {


        if(serverName===''){
            alert('Server Name must not be empty!');
            return false;
        }

        if(serverPort===''){
            alert('Server Port must not be empty!');
            return false;
        }

        //Call service
        const user = localStorage.getItem('user');
        const token = localStorage.getItem('token');

        const response = await createServer(serverName, serverPort, user, token);

        if(response === null) {

            return false;

        }

        this.setState({id : response})
        console.log('After create: ' + this.state.id);
        return true;

    }

    downloadServer = async (serverID, packURL) => {

        if(packURL === '') {

            const proceed = window.confirm('No pack download url selected. \n No files will be downloaded. Are you sure you want to continue?');
          
            return proceed;
        }


        const user = localStorage.getItem('user');
        const token = localStorage.getItem('token');

        const response = await downloadServer(serverID, packURL, this.setLoading, user, token);

        if(response === null) {

            return false;
            
        }

        console.log('After download: ' + this.state.id);

        return response;

    }

    setScript = async (serverID, path) => {

        console.log('While setting script: ' + serverID);

        const user = localStorage.getItem('user');
        const token = localStorage.getItem('token');

        const response = await setScript(serverID, path, user, token);

        return response;

    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value})
    }

    render(){
        const { id, step, serverName, serverPort, url, hasUrl, script } = this.state;
        const inputValues = { id, serverName, serverPort, url, hasUrl, script };
        
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

        case 5:
            return <Downloading/>
        }
    }
}

export default MultiStepForm;