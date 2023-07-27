import React, { Component } from 'react';
import { Container } from 'react-bootstrap';
import animation from "../../downloading-animation.gif"


class Downloading extends Component {

    render() {
        return (
        
        <Container>

            <img src={animation} alt='Downloading...'></img>

        </Container>

        );
    }
}

export default Downloading;