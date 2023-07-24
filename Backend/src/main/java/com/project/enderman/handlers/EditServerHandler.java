package com.project.enderman.handlers;

import com.project.enderman.entities.ServerData;
import com.project.enderman.exceptions.ServerStatusException;
import com.project.enderman.repositories.ServerDataRepository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class EditServerHandler {

    private final ServerDataRepository serverRepo;

    public EditServerHandler(ServerDataRepository serverRepo) {
        this.serverRepo = serverRepo;
    }

    public Set<Map.Entry<Object, Object>> getProperties(long serverID) throws IOException, ServerStatusException {

        Optional<ServerData> maybeServer = serverRepo.findById(serverID);

        if(maybeServer.isPresent()){

            ServerData sv = maybeServer.get();
            String mainFolder = sv.getMainFolder();

            if(mainFolder != null){

                //Server is initialized, there should be a server.properties, load it
                Properties serverProperties = new Properties();
                serverProperties.load(new FileInputStream(mainFolder + "/server.properties"));


                return serverProperties.entrySet();

            } else {

                throw new ServerStatusException("Server is not yet initialized!");
            }

        }

        throw new ServerStatusException("Server does not exist!");
    }

    public boolean setProperties(Map<String, String> properties, long serverID) throws IOException, InterruptedException, ServerStatusException {

        Optional<ServerData> maybeServer = serverRepo.findById(serverID);

        if(maybeServer.isPresent()){

            ServerData sv = maybeServer.get();
            String mainFolder = sv.getMainFolder();

            if(mainFolder != null){

                //Server is initialized, there should be a server.properties, load it
                Properties serverProperties = new Properties();
                FileInputStream fis = new FileInputStream(mainFolder + "/server.properties");
                serverProperties.load(fis);

                Set<Map.Entry<String, String>> entries = properties.entrySet();

                for(Map.Entry<String, String> entry : entries) {

                    serverProperties.setProperty(entry.getKey(), entry.getValue());
                }

                FileOutputStream fos = new FileOutputStream(mainFolder + "/server.properties");
                serverProperties.store(fos, "");

                fis.close();
                fos.close();



                //This code only runs in linux (production) so it remains unused during development

                RunServerHandler runServerHandler = new RunServerHandler(serverRepo);

                if(runServerHandler.stop(serverID)){
                    //Server was running and must be restarted.
                    Thread.sleep(5000); //Wait 5 seconds to make sure server is fully stopped

                    if(!runServerHandler.start(serverID)){ //Might cause issues
                        throw new ServerStatusException("Server failed to restart but updates are stored!");
                    }
                }



                //Server was not running, just return
                return true;

            } else {
                throw new ServerStatusException("Server is not yet initialized!");
            }

        }

        throw new ServerStatusException("Server does not exist!");
    }
}
