import { Component } from 'react';
import { Button, Container } from 'react-bootstrap';
import { enterFolder, listFiles } from '../../services/NavigateService';
import FileDisplay from './FileDisplay';


class ScriptForm extends Component {

    state = {
        loadedDirectory: [],
        currentFile: null
    }

    loadServerFiles = async () => {

        let username = localStorage.getItem('user');
        let token = localStorage.getItem('token');
        let id = 1//this.props.inputValues.id;

        //Call navigation service to list server files
        let directoryList = await listFiles(id, username, token);

        this.setState({loadedDirectory : directoryList});

    }

    handleClick = async (file) => {

        this.setState({currentFile : file});
        
        let username = localStorage.getItem('user');
        let token = localStorage.getItem('token');

        if(file.folder){
            //File is folder, expand

            let directoryList = await enterFolder(file.path, username, token);

            //this.setState({loadedDirectory : directoryList});

            console.log(directoryList);

        }

    } 

    saveAndContinue = (e) => {
        e.preventDefault();
        this.props.nextStep();
    };

    render() {

        if(this.state.loadedDirectory.length === 0) {
            this.loadServerFiles();
        }

        return (<Container>

            <div id='file-list-container'>

                {this.state.loadedDirectory.map(file => <FileDisplay file={file} clickEvent={this.handleClick}/>)}

            </div>

            <Button className='next-btn' onClick={this.saveAndContinue}>Next</Button>

        </Container>
        );
    }
}

export default ScriptForm;