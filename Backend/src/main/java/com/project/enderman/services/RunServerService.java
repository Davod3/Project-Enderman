package com.project.enderman.services;

import com.project.enderman.dtos.ResponseDTO;
import com.project.enderman.exceptions.ServerStatusException;
import com.project.enderman.handlers.RunServerHandler;
import com.project.enderman.repositories.ServerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RunServerService {

    @Autowired private ServerDataRepository serverRepo;

    public ResponseDTO<Boolean> startServer(long serverID) {

        ResponseDTO<Boolean> response = new ResponseDTO<>();

        try {
            boolean result = new RunServerHandler(serverRepo).start(serverID);
            response.setResult(result);
        } catch (IOException | ServerStatusException | InterruptedException e) {
            response.setErrorMsg(e.getMessage());
        }

        return response;

    }

    public ResponseDTO<Boolean> stopServer(long serverID) {

        ResponseDTO<Boolean> response = new ResponseDTO<>();

        try {
            boolean result = new RunServerHandler(serverRepo).stop(serverID);
            response.setResult(result);
        } catch (IOException | ServerStatusException | InterruptedException e) {
            response.setErrorMsg(e.getMessage());
        }

        return response;

    }

    public ResponseDTO<Boolean> isRunning(long serverID) {

        ResponseDTO<Boolean> response = new ResponseDTO<>();

        try {
            boolean result = new RunServerHandler(serverRepo).isRunning(serverID);
            response.setResult(result);
        } catch (IOException | InterruptedException e) {
            response.setErrorMsg(e.getMessage());
        }

        return response;

    }

}
