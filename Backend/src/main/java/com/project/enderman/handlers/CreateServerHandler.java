package com.project.enderman.handlers;

import com.project.enderman.entities.ServerBackup;
import com.project.enderman.entities.ServerData;
import com.project.enderman.exceptions.FailedBackupException;
import com.project.enderman.exceptions.MissingFileException;
import com.project.enderman.exceptions.ServerStatusException;
import com.project.enderman.repositories.ServerBackupRepository;
import com.project.enderman.repositories.ServerDataRepository;
import com.project.enderman.utils.Downloader;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.*;
import java.util.Optional;
import java.util.Properties;

public class CreateServerHandler {

    private final ServerDataRepository serverRepo;

    private final ServerBackupRepository backupRepo;

    public CreateServerHandler(ServerDataRepository serverRepo, ServerBackupRepository backupRepo) {
        this.serverRepo = serverRepo;
        this.backupRepo = backupRepo;
    }

    public long createServer(String name, String port) throws DataIntegrityViolationException {

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
                setProperties(sv, sv.getPort());

                return true;

            }

            throw new MissingFileException("Script file does not exist!");
        }

        throw new ServerStatusException("Server does not exist!");
    }

    public boolean deleteServer(long serverID) throws ServerStatusException {

        Optional<ServerData> maybeSv = serverRepo.findById(serverID);

        if(maybeSv.isPresent()){

                ServerData sv = maybeSv.get();

                ServerBackup backup = sv.getBackup();

                if(backup != null) {
                    //Server has backup. Delete it

                    try {

                        boolean result = new BackupServerHandler(serverRepo, backupRepo).removeBackup(serverID);

                    } catch (FailedBackupException e) {

                        System.out.println(e.getMessage());

                    }
                }

                if(sv.isInstalled()){

                    //Delete Server Folder
                    deleteFolder(new File(sv.getFolder()));

                }

                //Delete sv from DB
                serverRepo.delete(sv);

                return true;
        }

        throw new ServerStatusException("Server does not exist!");

    }

    private void setProperties(ServerData sv, String port) throws IOException {

        //Check if server.properties already exists
        String propertiesPath = sv.getMainFolder() + "/server.properties";
        File serverProperties = new File(propertiesPath);

        if(!serverProperties.exists()) {

            //If it doesn't exist, create it

            serverProperties.createNewFile();

            FileWriter writer = new FileWriter(propertiesPath);
            writer.write("server-port=" + port);
            writer.close();

        } else {

            //If it exists, load it and change it

            //Load
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream(propertiesPath);
            props.load(fis);

            //Edit
            props.setProperty("server-port", port);

            //Save
            FileOutputStream fos = new FileOutputStream(propertiesPath);
            props.store(fos, "");

            fis.close();
            fos.close();

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

    private boolean deleteFolder(File folder){

        File[] contents = folder.listFiles();

        if(contents != null) {

            for(File f : contents) {
                deleteFolder(f);
            }
        }

        return folder.delete();
    }

}
