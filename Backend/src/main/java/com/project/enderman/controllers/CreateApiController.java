package com.project.enderman.controllers;

import com.project.enderman.dtos.ResponseDTO;
import com.project.enderman.dtos.ServerDTO;
import com.project.enderman.services.CreateServerService;
import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api")
public class CreateApiController {

    @Autowired private CreateServerService createService;

    @PostMapping("/server/create")
    public ResponseDTO<Long> createServer(
            @RequestParam(name="name") String name,
            @RequestParam(name="port") String port,
            @RequestParam(name="rcon") String rconPort) {

        return createService.createServer(name, port, rconPort);
    }

    @PatchMapping("/server/download/{id}")
    public ResponseDTO<Boolean> downloadServer(
            @PathVariable("id") long serverID,
            @RequestParam(name="url") String url) {

        return createService.downloadServer(url, serverID);
    }

    @PatchMapping("/server/{id}/script")
    public ResponseDTO<Boolean> setScript(@RequestParam(name="path") String path,
                                          @PathVariable("id") long serverID) {
        return createService.selectStartScript(path, serverID);
    }

    @GetMapping("/servers")
    public ResponseDTO<List<ServerDTO>> getServers() {
        return createService.getServers();
    }

    @GetMapping("/server/{id}")
    public ResponseDTO<ServerDTO> getServer(@PathVariable("id") long serverID) {
        return this.createService.getServer(serverID);
    }

    @DeleteMapping("/server/{id}")
    public ResponseDTO<Boolean> deleteServer(@PathVariable("id") long serverID) {
        return this.createService.deleteServer(serverID);
    }
}
