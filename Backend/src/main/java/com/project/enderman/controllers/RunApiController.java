package com.project.enderman.controllers;

import com.project.enderman.dtos.ResponseDTO;
import com.project.enderman.services.RunServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("api")
public class RunApiController {

    @Autowired private RunServerService runService;

    @GetMapping("server/{id}/start")
    public ResponseDTO<Boolean> startServer(@PathVariable("id") long serverID) {
        return runService.startServer(serverID);
    }

    @GetMapping("server/{id}/stop")
    public ResponseDTO<Boolean> stopServer(@PathVariable("id") long serverID) {
        return runService.stopServer(serverID);
    }

    @GetMapping("server/{id}/status")
    public ResponseDTO<Boolean> isRunning(@PathVariable("id") long serverID) {
        return runService.isRunning(serverID);
    }

}
