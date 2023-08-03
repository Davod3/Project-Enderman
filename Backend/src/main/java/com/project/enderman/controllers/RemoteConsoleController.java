package com.project.enderman.controllers;

import com.project.enderman.dtos.ResponseDTO;
import com.project.enderman.services.RemoteConsoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class RemoteConsoleController {

    @Autowired
    private RemoteConsoleService rconService;

    @PostMapping("/server/{id}/exec")
    public ResponseDTO<String> executeCommand(@PathVariable("id") long id, @RequestBody String command){
        return this.rconService.executeCommand(command, id);
    }

}
