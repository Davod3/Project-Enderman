import { url} from "./ApiProperties"


export function verifyToken(username, token){
    
    try {

        const response = fetch( `${url}/test_token`, {
            method: 'POST',
            headers: {
                'X-API-USER': username,
                'X-API-KEY': token
            }
        });

        console.log(response.status);

        if(response.status === 401) {
            return false;
        }

        return true;

    } catch (err) {

        console.log(err)
        return false;

    }

}