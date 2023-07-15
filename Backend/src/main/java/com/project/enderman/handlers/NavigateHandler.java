package com.project.enderman.handlers;

import com.project.enderman.entities.FileDTO;
import com.project.enderman.entities.ServerData;
import com.project.enderman.repositories.ServerDataRepository;
import com.project.enderman.utils.Navigator;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class NavigateHandler {

    private final ServerDataRepository serverRepo;

    public NavigateHandler(ServerDataRepository serverRepo) {
        this.serverRepo = serverRepo;
    }

    public List<FileDTO> listFiles(Long serverID){

        Optional<ServerData> maybeSv = this.serverRepo.findById(serverID);

        if(maybeSv.isPresent()) {

            File root = new File(maybeSv.get().getRootFolder());

            Optional<List<FileDTO>> serverFiles = Navigator.goInto(root);

            return serverFiles.isPresent()?serverFiles.get():null;

        }

        return null;
    }


    public List<FileDTO> goForward(String filePath){

        File next = new File(filePath);

        Optional<List<FileDTO>> contents = Navigator.goInto(next);

        return contents.isPresent()?contents.get():null;
    }

    public List<FileDTO> goBackward(String filePath){

        File current = new File(filePath);

        Optional<List<FileDTO>> contents = Navigator.goInto(current.getParentFile());

        return contents.isPresent()?contents.get():null;
    }

}
