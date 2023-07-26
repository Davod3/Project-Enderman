import { url } from "./ApiProperties"


export async function listFiles(serverID, username, token){

        return fetch( `${url}/server/${serverID}/files`, {
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

export async function enterFolder(path, username, token){

    return fetch( `${url}/folder/enter?path=${path}`, {
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


