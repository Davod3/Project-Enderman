package com.project.enderman.handlers;

import com.project.enderman.entities.ServerData;
import com.project.enderman.repositories.ServerDataRepository;
import com.project.enderman.utils.Downloader;

import java.io.File;
import java.io.FileWriter;
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

            String destinationFolder = maybeSv.get().getFolder();

            return Downloader.download(url, destinationFolder);

        }

        return false;
    }

    public boolean selectStartScript(String filePath, long serverID) throws IOException {

        Optional<ServerData> maybeSv = serverRepo.findById(serverID);
        File script = new File(filePath);

        if(maybeSv.isPresent() && script.exists()){

            ServerData sv = maybeSv.get();
            sv.setStartScript(filePath);
            sv.setMainFolder(script.getParentFile().getPath());

            serverRepo.save(sv);

            acceptEula(sv);
            setPort(sv, sv.getPort());

            return true;

        }

        System.out.println("File or server does not exist!");
        return false;
    }

    private void setPort(ServerData sv, String port) throws IOException {

        //Set server.properties at parent folder (must be root folder)
        String propertiesPath = sv.getMainFolder() + "/server.properties";
        File serverProperties = new File(propertiesPath);
        serverProperties.createNewFile();

        FileWriter writer = new FileWriter(propertiesPath);
        writer.write("server-port=" + port);
        writer.close();

    }

    private void acceptEula(ServerData sv) throws IOException {

        //Set eula at script's parent folder (must be root folder)
        String eulaPath = sv.getMainFolder() + "/eula.txt";
        File eula = new File(eulaPath);
        eula.createNewFile();

        FileWriter writer = new FileWriter(eulaPath);
        writer.write("eula=true");
        writer.close();

    }

}
