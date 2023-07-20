package com.project.enderman.services;

import com.project.enderman.dtos.ResponseDTO;
import com.project.enderman.exceptions.FailedBackupException;
import com.project.enderman.exceptions.ServerStatusException;
import com.project.enderman.handlers.BackupServerHandler;
import com.project.enderman.repositories.ServerBackupRepository;
import com.project.enderman.repositories.ServerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BackupServerService {

    @Autowired private ServerDataRepository serverRepo;
    @Autowired private ServerBackupRepository backupRepo;

    public ResponseDTO<Boolean> createBackup(long serverID) {

        ResponseDTO<Boolean> response = new ResponseDTO<>();

        try {
            Boolean result = new BackupServerHandler(serverRepo,backupRepo).createBackup(serverID);
            response.setResult(result);
        } catch (IOException | ServerStatusException | FailedBackupException e) {
            response.setErrorMsg(e.getMessage());
        }

        return response;

    }

    public ResponseDTO<Boolean> removeBackup(long serverID) {

        ResponseDTO<Boolean> response = new ResponseDTO<>();

        try {
            Boolean result = new BackupServerHandler(serverRepo,backupRepo).removeBackup(serverID);
            response.setResult(result);
        } catch (ServerStatusException | FailedBackupException e) {
            response.setErrorMsg(e.getMessage());
        }

        return response;

    }

    public ResponseDTO<Boolean> restoreBackup(long serverID) {

        ResponseDTO<Boolean> response = new ResponseDTO<>();

        try {
            Boolean result = new BackupServerHandler(serverRepo,backupRepo).restoreBackup(serverID);
            response.setResult(result);
        } catch (ServerStatusException | FailedBackupException | IOException e) {
            response.setErrorMsg(e.getMessage());
        }

        return response;

    }

}
