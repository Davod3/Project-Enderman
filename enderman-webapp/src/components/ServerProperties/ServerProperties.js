import { useParams } from "react-router-dom";
import { getProperties } from "../../services/EditService";
import { useState, useEffect } from "react";
import './ServerProperties.css'
import PropertyDisplay from "./PropertyDisplay";

async function loadProperties(id) {

    const username = localStorage.getItem('user');
    const token = localStorage.getItem('token');

    let properties = await getProperties(id, username, token);

    const map = new Map();

    properties.map(entry => map.set(Object.keys(entry)[0], Object.values(entry)[0]));

    return map;

}

export default function ServerDetails() {

    const { id } = useParams();
    const [properties, setProperties] = useState();

    useEffect(() => {

        let mounted = true;

        loadProperties(id).then(properties => {

            if (mounted) {
                setProperties(properties);
            }

        })

        return () => mounted = false;

    }, []);

    function goBack(){
        window.location.href = `/server/${id}`
    }

    return (

        <div className="page-container">

            <h1 className="title">Server Properties</h1>

            <div className="server-properties-container">

                <div className="list-container">

                    {properties === undefined ? '' : Array.from(properties.entries()).map(entry => <PropertyDisplay name={entry[0]} value={entry[1]} />)}

                </div>

            </div>

            <div className="btn-container">

                <button onClick={goBack}>Back</button>
                <button>Save</button>

            </div>

        </div>

    );
}