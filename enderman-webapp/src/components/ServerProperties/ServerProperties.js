import { useParams } from "react-router-dom";
import { getProperties, sendProperties } from "../../services/EditService";
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
    const [changedProps, setChangedProps] = useState();

    useEffect(() => {

        let mounted = true;

        loadProperties(id).then(properties => {

            if (mounted) {
                setProperties(properties);
                setChangedProps({});
            }

        })

        return () => mounted = false;

    }, []);

    function goBack(){
        window.location.href = `/server/${id}`
    }

    async function save() {

        const username = localStorage.getItem('user');
        const token = localStorage.getItem('token');

        //Send properties map back to server

        let result = await sendProperties(id, changedProps, username, token);

        if(result){
            alert('Successfully changed server properties!');
            goBack();
        }

    }

    function handleChange(event) {

        changedProps[event.target.name] = event.target.value;

    }

    return (

        <div className="page-container">

            <h1 className="title">Server Properties</h1>

            <div className="server-properties-container">

                <div className="list-container">

                    {properties === undefined ? '' : Array.from(properties.entries()).map(entry => <PropertyDisplay name={entry[0]} value={entry[1]} handleChange={handleChange}/>)}

                </div>

            </div>

            <div className="btn-container">

                <button onClick={goBack}>Back</button>
                <button onClick={save}>Save</button>

            </div>

        </div>

    );
}