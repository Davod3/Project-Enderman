package com.project.enderman.handlers;

import com.project.enderman.entities.ServerData;
import com.project.enderman.repositories.ServerDataRepository;
import com.project.enderman.utils.Downloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
        //https://www.baeldung.com/java-http-request

        Optional<ServerData> maybeSv = serverRepo.findById(serverID);

        if(maybeSv.isPresent()){

            String destinationFolder = maybeSv.get().getRootFolder();

            return Downloader.download(url, destinationFolder);

        }

        return false;
    }

    public boolean selectStartScript(String filePath, String serverID) {

        return false;
    }

}
