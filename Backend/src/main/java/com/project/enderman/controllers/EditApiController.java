package com.project.enderman.controllers;

import com.project.enderman.dtos.ResponseDTO;
import com.project.enderman.services.EditServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController()
@RequestMapping("api")
public class EditApiController {

    @Autowired private EditServerService editService;

    @GetMapping("server/{id}/properties")
    public ResponseDTO<Set<Map.Entry<Object, Object>>> getProperties(@PathVariable("id") long serverID) {
        return this.editService.getProperties(serverID);
    }

    @PatchMapping("server/{id}/properties")
    public ResponseDTO<Boolean> setProperties(@PathVariable("id") long serverID,
                                              @RequestBody Map<String, String> properties) {

        return this.editService.setProperties(properties, serverID);
    }

}
