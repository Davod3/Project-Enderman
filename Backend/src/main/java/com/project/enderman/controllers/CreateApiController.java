package com.project.enderman.controllers;

import com.project.enderman.dtos.ResponseDTO;
import com.project.enderman.services.CreateServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api")
public class CreateApiController {

    @Autowired private CreateServerService createService;

    @PostMapping("/server/create")
    public ResponseDTO<Long> createServer(
            @RequestParam(name="name") String name,
            @RequestParam(name="port") String port) {

        return createService.createServer(name, port);
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
}
