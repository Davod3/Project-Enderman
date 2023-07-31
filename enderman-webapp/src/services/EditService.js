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