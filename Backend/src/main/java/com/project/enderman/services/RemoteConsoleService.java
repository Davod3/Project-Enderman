package com.project.enderman.services;


import com.project.enderman.dtos.ResponseDTO;
import com.project.enderman.exceptions.RemoteConsoleException;
import com.project.enderman.exceptions.ServerStatusException;
import com.project.enderman.handlers.RemoteConsoleHandler;
import com.project.enderman.repositories.ServerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RemoteConsoleService {

    @Autowired
    private ServerDataRepository serverRepo;

    public ResponseDTO<String> executeCommand(String command, long serverID) {

        ResponseDTO<String> response = new ResponseDTO<>();

        try {

            String result = new RemoteConsoleHandler(this.serverRepo).execute(command, serverID);
            response.setResult(result);

        } catch (ServerStatusException | RemoteConsoleException e) {

            response.setErrorMsg(e.getMessage());

        }

        return response;
    }

}
