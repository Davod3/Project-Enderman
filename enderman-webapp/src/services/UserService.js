import { url} from "./ApiProperties"


export async function verifyToken(username, token){

        return fetch( `${url}/test_token`, {
            method: 'POST',
            headers: {
                'X-API-USER': username,
                'X-API-KEY': token
            }
        })
        .then(response => {

            if(response.status === 401) {
                return false;
            }

            return true;

        })
        .catch(err => {

            console.log("Erro: " + err);
            return false
            
        });

}

