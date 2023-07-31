import { url } from "./ApiProperties"


export async function createBackup(serverID, username, token){

        return fetch( `${url}/server/${serverID}/backup`, {
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

export async function removeBackup(serverID, username, token){

    return fetch( `${url}/server/${serverID}/backup`, {
        method: 'DELETE',
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

export async function restoreBackup(serverID, username, token){

    return fetch( `${url}/server/${serverID}/backup`, {
        method: 'PUT',
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