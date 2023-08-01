import { url} from "./ApiProperties"

export async function getProperties(serverID, username, token){

    return fetch( `${url}/server/${serverID}/properties`, {
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

        return data.result; //Map<String,String>
    })
    .catch(err => {

        console.log("Erro: " + err);
        
    });

}

export async function sendProperties(serverID, properties, username, token){

    return fetch( `${url}/server/${serverID}/properties`, {
        method: 'PATCH',
        headers: {
            'X-API-USER': username,
            'X-API-KEY': token,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(properties)
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