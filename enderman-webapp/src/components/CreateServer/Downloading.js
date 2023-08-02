import React, { Component } from 'react';
import { Container } from 'react-bootstrap';
import animation from "../../spinner.gif"


class Downloading extends Component {

    render() {
        return (
        
        <Container>

            <img src={animation} alt='Downloading animation'></img>
            <p>Downloading server...</p>

        </Container>

        );
    }
}

export default Downloading;