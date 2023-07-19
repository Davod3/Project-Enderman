package com.project.enderman.controllers;

import com.project.enderman.dtos.ResponseDTO;
import com.project.enderman.entities.FileDTO;
import com.project.enderman.services.NavigateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api")
public class NavigateApiController {

    @Autowired private NavigateService navService;

    @GetMapping("server/{id}/files")
    public ResponseDTO<List<FileDTO>> listFiles(@PathVariable("id") long serverID) {
        return this.navService.listFiles(serverID);
    }

    @GetMapping("folder/enter")
    public ResponseDTO<List<FileDTO>> enterFolder(@RequestParam(name="path") String path) {
        return this.navService.expandFolder(path);
    }

    @GetMapping("folder/exit")
    public ResponseDTO<List<FileDTO>> exitFolder(@RequestParam(name="path") String path) {
        return this.navService.exitFolder(path);
    }

}
