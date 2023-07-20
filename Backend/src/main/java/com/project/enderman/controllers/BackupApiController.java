package com.project.enderman.controllers;

import com.project.enderman.dtos.ResponseDTO;
import com.project.enderman.services.BackupServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api")
public class BackupApiController {

    @Autowired private BackupServerService backupService;

    @PostMapping("/server/{id}/backup")
    public ResponseDTO<Boolean> createBackup(@PathVariable("id") long serverID) {
        return this.backupService.createBackup(serverID);
    }

    @DeleteMapping("/server/{id}/backup")
    public ResponseDTO<Boolean> removeBackup(@PathVariable("id") long serverID) {
        return this.backupService.removeBackup(serverID);
    }

    @PutMapping("/server/{id}/backup")
    public ResponseDTO<Boolean> restoreBackup(@PathVariable("id") long serverID) {
        return this.backupService.restoreBackup(serverID);
    }

}
