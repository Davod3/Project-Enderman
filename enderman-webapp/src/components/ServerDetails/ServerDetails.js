import { useEffect, useState } from 'react';
import './ServerDetails.css'
import * as Icon from 'react-bootstrap-icons';
import { useParams } from 'react-router-dom';
import { getServer, deleteServer } from '../../services/CreateService';
import { start, stop } from '../../services/RunService';
import { createBackup, restoreBackup, removeBackup } from '../../services/BackupService';
import animation from "../../spinner.gif"

async function loadServer(id) {

    const username = localStorage.getItem('user');
    const token = localStorage.getItem('token');

    let server = await getServer(id, username, token);

    if(server === null || server === undefined) {
        server={
            id: -1,
            installed: false,
            name: '',
            port: '',
            running: false,
            startPath: ''
            }
    }
    
    return server;
    
}

export default function ServerDetails() {

    const { id } = useParams();
    const [server, setServer] = useState();
    const [isLoading, setLoading] = useState();

    useEffect( () => {

        let mounted = true;

        loadServer(id).then(server => {

            if(mounted){
                setServer(server);
            }

        })
        
        return () => mounted = false;

    }, []);

    async function startServer() {

        let username = localStorage.getItem('user');
        let token = localStorage.getItem('token');

        let result = await start(server.id, username, token);

        if(!result) {
            alert('Failed to start server!');
        }
        
        window.location.reload();

    }

    async function stopServer() {

        let username = localStorage.getItem('user');
        let token = localStorage.getItem('token');

        let result = await stop(server.id, username, token);

        if(!result) {
            alert('Failed to stop server!');
        }

        window.location.reload();

    }

    async function backupServer() {

        const proceed = window.confirm('You are about to start a server backup. \n The previous backup will be replaced. Proceed?');

        if(proceed) {

            setLoading(true);

            let username = localStorage.getItem('user');
            let token = localStorage.getItem('token');

            let result = await createBackup(server.id, username, token);

            if(result){
                alert('Successfully backed up server!');
            }

            setLoading(false);

            window.location.reload();

        }

    }

    async function deleteBackup() {

        const proceed = window.confirm('You are about to delete a server backup. All data in that backup will be lost. Proceed?');

        if(proceed) {

            setLoading(true);

            let username = localStorage.getItem('user');
            let token = localStorage.getItem('token');

            let result = await removeBackup(server.id, username, token);

            if(result){
                alert('Successfully removed server backup!');
            }

            setLoading(false);

            window.location.reload();

        }

    }

    async function restoreServer() {

        const proceed = window.confirm('You are about to restore a server backup. All data in the server folder will be replaced by this backup. Proceed?');

        if(proceed) {

            
            setLoading(true);

            let username = localStorage.getItem('user');
            let token = localStorage.getItem('token');

            let result = await restoreBackup(server.id, username, token);

            if(result){
                alert('Successfully restored server backup!');
            }

            setLoading(false);

            window.location.reload();

        }
        

    }

    async function removeServer() {

        const proceed = window.confirm('You are about to delete the server instance. ALL SERVER DATA AND SERVER BACKUPS WILL BE LOST. Proceed?');

        if(proceed) {

            setLoading(true);

            let username = localStorage.getItem('user');
            let token = localStorage.getItem('token');

            let result = await deleteServer(server.id, username, token);

            if(result){
                alert('Successfully removed server backup!');
            }

            setLoading(false);

            window.location.href = '/dashboard';

        }

    }

    function editProperties() {
        window.location.href = `/server/${id}/properties`;
    }

    function finishDownloading() {

        if(!server.installed){
            //Redirect to download
            window.location.href = `/createserver?id=${server.id}&step=2`;

        } else {

            alert('Server is already downloaded!');
        }

    }

    function editScript() {

        if(!server.installed){
            //Redirect to download
            alert('The server is not yet installed. To select a start script first download the server.')
            window.location.href = `/createserver?id=${server.id}&step=2`;

        } else {

            window.location.href = `/createserver?id=${server.id}&step=3`;
        }

    }

    function goBack() {
        window.location.href = '/dashboard'
    }


    return(
        <div className='server-details-container'>

            <h1 className='server-name'>{server===undefined?'':server.name}</h1>

            {server===undefined?'':(server.running?<div className='status running'>RUNNING</div>:<div className='status offline'>STOPPED</div>)}

            <div className='server-data'>

                <p>Port: {server===undefined?'':server.port}</p>
                <p>Downloaded: {server===undefined?'':server.installed.toString()} <Icon.Download className='action-btn' onClick={finishDownloading}/></p>
                <p>Start Script: {server===undefined?'':server.startPath}<Icon.Pencil className='action-btn' onClick={editScript}/> </p>

            </div>

            <div className='controls'>

                <button className='control-btn start' disabled={server===undefined?'':server.running || isLoading} onClick={startServer}>Start</button>
                <button className='control-btn stop' disabled={server===undefined?'':!server.running || isLoading} onClick={stopServer}>Stop</button>
                <button className='control-btn edit' disabled={isLoading} onClick={editProperties}>Edit Properties</button>
                <button className='control-btn delete' disabled={isLoading} onClick={removeServer}>Delete Server</button>
                <button className='control-btn start' disabled={isLoading} onClick={backupServer}>Create Backup</button>
                <button className='control-btn restore' disabled={isLoading} onClick={restoreServer}>Restore Backup</button>
                <button className='control-btn delete' disabled={isLoading} onClick={deleteBackup}>Delete Backup</button>

            </div>

            
            {isLoading?<img id='loading-animation' src={animation} alt='Loading animation'></img>:<></>}

            <div className='console-container'>

            </div>

            <button id='back-btn' onClick={goBack}>Back</button>

        </div>
    );

}