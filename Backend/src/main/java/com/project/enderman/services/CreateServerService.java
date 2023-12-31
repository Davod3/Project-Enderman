package com.project.enderman.services;

import com.project.enderman.dtos.ResponseDTO;
import com.project.enderman.dtos.ServerDTO;
import com.project.enderman.entities.ServerData;
import com.project.enderman.exceptions.MissingFileException;
import com.project.enderman.exceptions.ServerStatusException;
import com.project.enderman.handlers.CreateServerHandler;
import com.project.enderman.repositories.ServerBackupRepository;
import com.project.enderman.repositories.ServerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class CreateServerService {

    @Autowired private ServerDataRepository serverRepo;

    @Autowired private ServerBackupRepository backupRepo;

    public ResponseDTO<Long> createServer(String name, String port, String rconPort) {

        ResponseDTO<Long> response = new ResponseDTO<>();

        try {
            long result = new CreateServerHandler(this.serverRepo, this.backupRepo).createServer(name,port, rconPort);
            response.setResult(result);
        } catch (DataIntegrityViolationException e) {
            response.setErrorMsg("Server name already exists. Please use a different one!");
        } catch (ServerStatusException e) {
            response.setErrorMsg(e.getMessage());
        }

        return response;

    }

    public ResponseDTO<Boolean> downloadServer(String url, long serverID){

        ResponseDTO<Boolean> response = new ResponseDTO<>();

        try {

            boolean result = new CreateServerHandler(this.serverRepo, this.backupRepo).downloadServer(url,serverID);
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

            boolean result = new CreateServerHandler(this.serverRepo, this.backupRepo).selectStartScript(filePath, serverID);
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

    public ResponseDTO<List<ServerDTO>> getServers() {

        List<ServerData> servers = this.serverRepo.findAll();
        List<ServerDTO> result = new LinkedList<>();

        for(ServerData sv : servers) {
            result.add(ServerDTO.dtofy(sv));
        }

        ResponseDTO<List<ServerDTO>> response = new ResponseDTO<>();

        response.setResult(result);

        return response;
    }

    public ResponseDTO<ServerDTO> getServer(long id) {

        Optional<ServerData> maybeSv = this.serverRepo.findById(id);
        ResponseDTO<ServerDTO> response = new ResponseDTO<>();

        if(maybeSv.isPresent()){

            response.setResult(ServerDTO.dtofy(maybeSv.get()));

        } else {

            response.setErrorMsg("No server with the given id.");

        }

        return response;

    }

    public ResponseDTO<Boolean> deleteServer(long id) {

        ResponseDTO<Boolean> response = new ResponseDTO<>();

        try {

            boolean result = new CreateServerHandler(this.serverRepo, this.backupRepo).deleteServer(id);
            response.setResult(result);

        } catch (ServerStatusException e) {

            response.setErrorMsg(e.getMessage());

        }

        return response;

    }

}
