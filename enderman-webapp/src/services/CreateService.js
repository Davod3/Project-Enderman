import { url} from "./ApiProperties"


export async function createServer(serverName, serverPort, username, token){

        return fetch( `${url}/server/create?name=${serverName}&port=${serverPort}`, {
            method: 'POST',
            headers: {
                'X-API-USER': username,
                'X-API-KEY': token
            }
        })
        .then(async response => {

            if(!response.ok) {
                return response.body;
            }

            //Process response
            const data = await response.json();

            return data;

        })
        .catch(err => {

            console.log("Erro: " + err);
            
        });

}

export async function downloadServer(serverID, serverURL, setLoading, username, token){

    setLoading(true);

    return fetch( `${url}/server/download/${serverID}?url=${serverURL}`, {
        method: 'PATCH',
        headers: {
            'X-API-USER': username,
            'X-API-KEY': token
        }
    })
    .then(async response => {

        if(!response.ok) {
            return response.body;
        }

        //Process response
        const data = await response.json();

        setLoading(false);

        return data;

    })
    .catch(err => {

        console.log("Erro: " + err);
        setLoading(false);
        
    });

}

export async function setScript(serverID, scriptPath, username, token){

    return fetch( `${url}/server/${serverID}/script?path=${scriptPath}`, {
        method: 'PATCH',
        headers: {
            'X-API-USER': username,
            'X-API-KEY': token
        }
    })
    .then(async response => {

        if(!response.ok) {
            return response.body;
        }

        //Process response
        const data = await response.json();

        if(data.errorMsg !== null) {

            alert(data.errorMsg);
            return null;

        }

        return data.result; //Boolean

    })
    .catch(err => {

        console.log("Erro: " + err);
        
    });

}

export async function listServers(username, token){

    return fetch( `${url}/servers`, {
        method: 'GET',
        headers: {
            'X-API-USER': username,
            'X-API-KEY': token
        }
    })
    .then(async response => {

        if(!response.ok) {
            return response.body;
        }

        //Process response
        const data = await response.json();

        if(data.errorMsg !== null) {

            alert(data.errorMsg);
            return null;

        }

        return data.result; //Array
    })
    .catch(err => {

        console.log("Erro: " + err);
        
    });

    

}


