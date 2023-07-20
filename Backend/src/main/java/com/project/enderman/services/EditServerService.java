package com.project.enderman.services;

import com.project.enderman.dtos.ResponseDTO;
import com.project.enderman.exceptions.ServerStatusException;
import com.project.enderman.handlers.EditServerHandler;
import com.project.enderman.repositories.ServerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Component
public class EditServerService {

    @Autowired private ServerDataRepository serverRepo;

    public ResponseDTO<Set<Map.Entry<Object, Object>>> getProperties(long serverID){

        ResponseDTO<Set<Map.Entry<Object,Object>>> response = new ResponseDTO<>();

        try {
            Set<Map.Entry<Object, Object>> result = new EditServerHandler(this.serverRepo).getProperties(serverID);
            response.setResult(result);
        } catch (IOException e) {
            response.setErrorMsg("Operation failed. Contact an admin.");
        } catch (ServerStatusException e) {
            response.setErrorMsg(e.getMessage());
        }

        return response;

    }

    public ResponseDTO<Boolean> setProperties(Map<String, String> properties, long serverID) {

        ResponseDTO<Boolean> response = new ResponseDTO<>();

        try {

            boolean result = new EditServerHandler(this.serverRepo).setProperties(properties, serverID);
            response.setResult(result);

        } catch (IOException e) {
            response.setErrorMsg("Operation failed. Contact an admin.");
        } catch (InterruptedException e) {
            response.setErrorMsg("Failed to restart server. Contact an admin");
        } catch (ServerStatusException e) {
            response.setErrorMsg(e.getMessage());
        }

        return response;

    }

}
