import { useEffect, useState } from 'react';
import './ServerDetails.css'
import * as Icon from 'react-bootstrap-icons';
import { useParams } from 'react-router-dom';
import { getServer } from '../../services/CreateService';

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

    
    console.log(server);

    return(
        <div className='server-details-container'>

            <h1 className='server-name'>{server===undefined?'':server.name}</h1>

            {server===undefined?'':(server.running?<div className='status running'>RUNNING</div>:<div className='status offline'>STOPPED</div>)}

            <div className='server-data'>

                <p>Port: {server===undefined?'':server.port}</p>
                <p>Downloaded: {server===undefined?'':server.installed.toString()}</p>
                <p>Start Script: {server===undefined?'':server.startPath} <Icon.Pencil/> </p>

            </div>

            <div className='controls'>

                <button className='control-btn start'>Start</button>
                <button className='control-btn stop'>Stop</button>
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