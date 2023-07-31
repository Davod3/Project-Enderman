import { useEffect, useState } from 'react';
import './ServerDetails.css'
import * as Icon from 'react-bootstrap-icons';
import { useParams } from 'react-router-dom';
import { getServer } from '../../services/CreateService';
import { start, stop } from '../../services/RunService';

async function loadServer(id) {

    const username = localStorage.getItem('user');
    const token = localStorage.getItem('token');

    const server = await getServer(id, username, token);
    
    return server;
    
}

export default function ServerDetails() {

    const { id } = useParams();
    const [server, setServer] = useState();

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


    return(
        <div className='server-details-container'>

            <h1 className='server-name'>{server===undefined?'':server.name}</h1>

            {server===undefined?'':(server.running?<div className='status running'>RUNNING</div>:<div className='status offline'>STOPPED</div>)}

            <div className='server-data'>

                <p>Port: {server===undefined?'':server.port}</p>
                <p>Downloaded: {server===undefined?'':server.installed.toString()}</p>
                <p>Start Script: {server===undefined?'':server.startPath}<Icon.Pencil/> </p>

            </div>

            <div className='controls'>

                <button className='control-btn start' disabled={server===undefined?'':server.running} onClick={startServer}>Start</button>
                <button className='control-btn stop' disabled={server===undefined?'':!server.running} onClick={stopServer}>Stop</button>
                <button className='control-btn edit'>Edit Properties</button>
                <button className='control-btn delete'>Delete Server</button>
                <button className='control-btn start'>Create Backup</button>
                <button className='control-btn restore'>Restore Backup</button>
                <button className='control-btn delete'>Delete Backup</button>

            </div>

            <div className='console-container'>

            </div>

        </div>
    );

}