import { url } from "./ApiProperties"


export async function executeCommand(serverID, command, username, token){

        return fetch( `${url}/server/${serverID}/exec`, {
            method: 'POST',
            headers: {
                'X-API-USER': username,
                'X-API-KEY': token
            },
            body: command
        })
        .then(async response => {

            if(!response.ok) {
                return response.body;
            }

            //Process response
            const data = await response.json();

            if(data.errorMsg !== null) {

                return data.errorMsg;

            }

            console.log(data.result);
            return data.result; //String
        })
        .catch(err => {

            alert(err);
            return null;
            
        });

}