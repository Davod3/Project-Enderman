package com.project.enderman.handlers;

import com.project.enderman.entities.ServerData;
import com.project.enderman.exceptions.MissingFileException;
import com.project.enderman.exceptions.ServerStatusException;
import com.project.enderman.repositories.ServerDataRepository;
import com.project.enderman.utils.Downloader;
import org.apache.catalina.Server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class CreateServerHandler {

    private final ServerDataRepository serverRepo;

    public CreateServerHandler(ServerDataRepository serverRepo) {
        this.serverRepo = serverRepo;
    }

    public long createServer(String name, String port) {

        ServerData sv = new ServerData(name, port);

        ServerData saved = serverRepo.save(sv);

        return saved.getID();
    }
    public boolean downloadServer(String url, long serverID) throws IOException, ServerStatusException {

        //https://www.curseforge.com/api/v1/mods/715572/files/4642332/download

        Optional<ServerData> maybeSv = serverRepo.findById(serverID);

        if(maybeSv.isPresent()){

            ServerData sv = maybeSv.get();

            if(!sv.isInstalled()) {

                String destinationFolder = sv.getFolder();

                boolean result = Downloader.download(url, destinationFolder);

                sv.setInstalled(result);

                serverRepo.save(sv);

                return result;
            } else {
                throw new ServerStatusException("Server is already installed!");
            }

        }

        throw new ServerStatusException("Server does not exist!");
    }

    public boolean selectStartScript(String filePath, long serverID) throws IOException, MissingFileException, ServerStatusException {

        Optional<ServerData> maybeSv = serverRepo.findById(serverID);
        File script = new File(filePath);

        if(maybeSv.isPresent()){

            if(script.exists()) {

                ServerData sv = maybeSv.get();
                sv.setStartScript(filePath);
                sv.setMainFolder(script.getParentFile().getPath());
                sv.setInstalled(true);

                serverRepo.save(sv);

                acceptEula(sv);
                setPort(sv, sv.getPort());

                return true;

            }

            throw new MissingFileException("Script file does not exist!");
        }

        throw new ServerStatusException("Server does not exist!");
    }

    private void setPort(ServerData sv, String port) throws IOException {

        //Set server.properties at parent folder (must be root folder)
        String propertiesPath = sv.getMainFolder() + "/server.properties";
        File serverProperties = new File(propertiesPath);

        if(!serverProperties.exists()) {

            serverProperties.createNewFile();

            FileWriter writer = new FileWriter(propertiesPath);
            writer.write("server-port=" + port);
            writer.close();

        }

    }

    private void acceptEula(ServerData sv) throws IOException {

        //Set eula at script's parent folder (must be root folder)
        String eulaPath = sv.getMainFolder() + "/eula.txt";
        File eula = new File(eulaPath);

        if(!eula.exists()){

            eula.createNewFile();

            FileWriter writer = new FileWriter(eulaPath);
            writer.write("eula=true");
            writer.close();

        }

    }

}
