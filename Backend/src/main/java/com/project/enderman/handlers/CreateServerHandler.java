package com.project.enderman.handlers;

import com.project.enderman.entities.ServerData;
import com.project.enderman.repositories.ServerDataRepository;
import com.project.enderman.utils.Downloader;

import java.io.IOException;
import java.util.Optional;

public class CreateServerHandler {

    private final ServerDataRepository serverRepo;

    public CreateServerHandler(ServerDataRepository serverRepo) {
        this.serverRepo = serverRepo;
    }

    public long createServer(String name, String port){

        ServerData sv = new ServerData(name, port);

        ServerData saved = serverRepo.save(sv);

        return saved.getID();
    }
    public boolean downloadServer(String url, long serverID) throws IOException {

        //https://www.curseforge.com/api/v1/mods/715572/files/4642332/download

        Optional<ServerData> maybeSv = serverRepo.findById(serverID);

        if(maybeSv.isPresent()){

            String destinationFolder = maybeSv.get().getRootFolder();

            return Downloader.download(url, destinationFolder);

        }

        return false;
    }

    public boolean selectStartScript(String filePath, long serverID) {

        Optional<ServerData> maybeSv = serverRepo.findById(serverID);

        if(maybeSv.isPresent()){

            ServerData sv = maybeSv.get();

            sv.setStartScript(filePath);

            serverRepo.save(sv);

            return true;

        }

        return false;
    }

}
