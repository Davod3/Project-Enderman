import { url } from "./ApiProperties"


export async function start(serverID, username, token){

        return fetch( `${url}/server/${serverID}/start`, {
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

            return data.result; //Boolean
        })
        .catch(err => {

            console.log("Erro: " + err);
            
        });

}

export async function stop(serverID, username, token){

    return fetch( `${url}/server/${serverID}/stop`, {
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

        return data.result; //Boolean
    })
    .catch(err => {

        console.log("Erro: " + err);
        
    });

}