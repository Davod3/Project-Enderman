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