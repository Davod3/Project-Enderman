package com.project.enderman.services;

import com.project.enderman.dtos.ResponseDTO;
import com.project.enderman.entities.FileDTO;
import com.project.enderman.exceptions.ServerStatusException;
import com.project.enderman.handlers.NavigateHandler;
import com.project.enderman.repositories.ServerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NavigateService {

    @Autowired
    private ServerDataRepository serverRepo;

    public ResponseDTO<List<FileDTO>> listFiles(long serverID) {

        ResponseDTO<List<FileDTO>> response = new ResponseDTO<>();

        try {
            List<FileDTO> result = new NavigateHandler(serverRepo).listFiles(serverID);
            response.setResult(result);

        } catch (ServerStatusException e) {
            response.setErrorMsg(e.getMessage());
        }

        return response;
    }

    public ResponseDTO<List<FileDTO>> expandFolder(String filePath) {

        ResponseDTO<List<FileDTO>> response = new ResponseDTO<>();

        List<FileDTO> result = new NavigateHandler(serverRepo).goForward(filePath);
        response.setResult(result);

        return response;
    }

    public ResponseDTO<List<FileDTO>> exitFolder(String filePath) {

        ResponseDTO<List<FileDTO>> response = new ResponseDTO<>();

        List<FileDTO> result = new NavigateHandler(serverRepo).goBackward(filePath);
        response.setResult(result);

        return response;
    }

}
