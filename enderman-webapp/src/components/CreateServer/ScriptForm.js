import { Component } from 'react';
import { Button, Container } from 'react-bootstrap';
import { enterFolder, exitFolder, listFiles } from '../../services/NavigateService';
import FileDisplay from './FileDisplay';
import * as Icon from 'react-bootstrap-icons';


class ScriptForm extends Component {

    state = {
        loadedDirectory: [],
        selectedFile: '',
        isLoaded: false
    }

    loadServerFiles = async () => {

        let username = localStorage.getItem('user');
        let token = localStorage.getItem('token');
        let id = 1//this.props.inputValues.id;

        //Call navigation service to list server files
        let directoryList = await listFiles(id, username, token);

        this.setState({loadedDirectory : directoryList, isLoaded : true });

    }

    handleClick = async (file) => {

        //this.setState({selectedFile : file.path})

        let username = localStorage.getItem('user');
        let token = localStorage.getItem('token');

        if(file.folder){
            //File is folder, expand

            let directoryList = await enterFolder(file.path, username, token);

            if(directoryList.length !== 0) {
                
                this.setState({loadedDirectory : directoryList, selectedFile : ''});

            } else {

                alert('Folder is empty!');
            } 

        } else {

            this.setState({selectedFile : file.path})

        }

    }
    
    goBack = async () => {

        let username = localStorage.getItem('user');
        let token = localStorage.getItem('token');

        let currentFolder = this.state.loadedDirectory[0];

        if(currentFolder.parentPath !== 'servers'){

            let directoryList = await exitFolder(currentFolder.parentPath, username, token);

            this.setState({loadedDirectory : directoryList, selectedFile : ''})

        }

    }

    saveAndContinue = (e) => {
        e.preventDefault();

        if(this.state.selectedFile === '') {
            alert('Please select a start script for your server!')
        } else {

            this.props.nextStep(this.state.selectedFile);
            
        }

    };

    render() {

        if(!this.state.isLoaded) {
            this.loadServerFiles();
        }

        return (<Container>
            
            <div id = 'file-list-header'>

                <Icon.Arrow90degUp id='return-btn' size={20} onClick={this.goBack}/>
                <div>{this.state.loadedDirectory[0] === undefined?'':this.state.loadedDirectory[0].parentPath }</div>

            </div>

            <div id='file-list-container'>

                {this.state.loadedDirectory.map(file => <FileDisplay file={file} clickEvent={this.handleClick}/>)}

            </div>

            <p>Start Script: {this.state.selectedFile}</p>

            <Button className='next-btn' onClick={this.saveAndContinue}>Next</Button>

        </Container>
        );
    }
}

export default ScriptForm;