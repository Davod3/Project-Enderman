package com.project.enderman.services;

import com.project.enderman.dtos.ResponseDTO;
import com.project.enderman.exceptions.MissingFileException;
import com.project.enderman.exceptions.ServerStatusException;
import com.project.enderman.handlers.BackupServerHandler;
import com.project.enderman.handlers.CreateServerHandler;
import com.project.enderman.repositories.ServerBackupRepository;
import com.project.enderman.repositories.ServerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CreateServerService {

    @Autowired private ServerDataRepository serverRepo;

    public ResponseDTO<Long> createServer(String name, String port) {

        ResponseDTO<Long> response = new ResponseDTO<>();
        long result = new CreateServerHandler(this.serverRepo).createServer(name,port);

        response.setResult(result);

        return response;

    }

    public ResponseDTO<Boolean> downloadServer(String url, long serverID){

        ResponseDTO<Boolean> response = new ResponseDTO<>();

        try {

            boolean result = new CreateServerHandler(this.serverRepo).downloadServer(url,serverID);
            response.setResult(result);

        } catch (IOException e) {

            response.setErrorMsg("Download failed. Contact an admin.");

        } catch (ServerStatusException e) {

            response.setErrorMsg(e.getMessage());
        }

        return response;

    }

    public ResponseDTO<Boolean> selectStartScript(String filePath, long serverID) {

        ResponseDTO<Boolean> response = new ResponseDTO<>();

        try {

            boolean result = new CreateServerHandler(this.serverRepo).selectStartScript(filePath, serverID);
            response.setResult(result);

        } catch (IOException e) {

            response.setErrorMsg("Operation failed. Contact an admin.");

        } catch (MissingFileException e) {

            response.setErrorMsg(e.getMessage());

        } catch (ServerStatusException e) {

            response.setErrorMsg(e.getMessage());
        }

        return response;
    }

}
