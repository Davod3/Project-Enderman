package com.project.enderman.console;

import com.project.enderman.entities.FileDTO;
import com.project.enderman.entities.ServerData;
import com.project.enderman.handlers.*;
import com.project.enderman.repositories.ServerBackupRepository;
import com.project.enderman.repositories.ServerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ShellComponent
public class Commands {

    @Autowired
    ServerDataRepository serverRepo;

    @Autowired
    ServerBackupRepository backupRepo;

    @ShellMethod(key="create-server")
    public long createServer(@ShellOption String name, @ShellOption String port) {

        CreateServerHandler createSvHandler = new CreateServerHandler(this.serverRepo);

        return createSvHandler.createServer(name, port);
    }

    @ShellMethod(key="download-server")
    public boolean downloadServer(@ShellOption String url) {

        CreateServerHandler createSvHandler = new CreateServerHandler(this.serverRepo);
        boolean result = false;

        try {
            result = createSvHandler.downloadServer(url, (long) 1);

        } catch (IOException e) {

            throw new RuntimeException(e);
        }

        return result;
    }

    @ShellMethod(key="view-server-files")
    public boolean viewServerFiles(@ShellOption Long serverid) {

        NavigateHandler nav = new NavigateHandler(serverRepo);

        List<FileDTO> files = nav.listFiles(serverid);

        if(files != null) {

            for(FileDTO f : files) {
                System.out.println(f.getName() + " -- " + f.getPath() + " -- " + f.isFolder());
            }

            return true;
        }

        return false;

    }

    @ShellMethod(key="file-forward")
    public boolean fileForward(@ShellOption String path) {

        NavigateHandler nav = new NavigateHandler(serverRepo);

        List<FileDTO> files = nav.goForward(path);

        if(files != null) {

            for(FileDTO f : files) {
                System.out.println(f.getName() + " -- " + f.getPath() + " -- " + f.isFolder());
            }

            return true;
        }

        return false;


    }

    @ShellMethod(key="file-back")
    public boolean fileBack(@ShellOption String path) {

        NavigateHandler nav = new NavigateHandler(serverRepo);

        List<FileDTO> files = nav.goBackward(path);

        if(files != null) {

            for(FileDTO f : files) {
                System.out.println(f.getName() + " -- " + f.getPath() + " -- " + f.isFolder());
            }

            return true;
        }

        return false;

    }

    @ShellMethod(key="start-server")
    public boolean startServer(@ShellOption long serverID) {

        RunServerHandler runServer = new RunServerHandler(serverRepo);

        try {

            return runServer.start(serverID);

        } catch (IOException e) {

            throw new RuntimeException(e);

        } catch (InterruptedException e) {

            throw new RuntimeException(e);

        }

    }

    @ShellMethod(key="select-script")
    public boolean selectScript(@ShellOption long id, @ShellOption String path) {

        CreateServerHandler createServer = new CreateServerHandler(serverRepo);

        try {
            return createServer.selectStartScript(path, id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @ShellMethod(key="stop-server")
    public boolean stopServer(@ShellOption long serverID) {

        RunServerHandler runServer = new RunServerHandler(serverRepo);

        try {

            return runServer.stop(serverID);

        } catch (IOException e) {

            throw new RuntimeException(e);

        } catch (InterruptedException e) {

            throw new RuntimeException(e);

        }

    }

    @ShellMethod(key="backup-server")
    public boolean backupServer(@ShellOption long serverID){

        BackupServerHandler backupHandler = new BackupServerHandler(serverRepo,backupRepo);

        try {

            backupHandler.createBackup(serverID);
            return true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @ShellMethod(key="delete-backup")
    public boolean deleteBackup(@ShellOption long serverID){

        BackupServerHandler backupServerHandler = new BackupServerHandler(serverRepo, backupRepo);

        return backupServerHandler.removeBackup(serverID);

    }

    @ShellMethod(key="restore-backup")
    public boolean restoreBackup(@ShellOption long serverID){

        BackupServerHandler backupServerHandler = new BackupServerHandler(serverRepo, backupRepo);

        try {
            return backupServerHandler.restoreBackup(serverID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @ShellMethod(key="server-properties")
    public boolean serverProperties(@ShellOption long serverID){

        EditServerHandler editServerHandler = new EditServerHandler(serverRepo);

        try {
            Set<Map.Entry<Object, Object>> properties = editServerHandler.getProperties(serverID);

            if(properties != null) {

                for(Map.Entry entry : properties) {

                    System.out.println(entry.getKey() + "---" + entry.getValue());

                }
            }

            return true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @ShellMethod(key="edit-server")
    public boolean editServer(@ShellOption long serverID){

        EditServerHandler editServerHandler = new EditServerHandler(serverRepo);

        try {
            Map<String, String> properties = new HashMap<>();

            properties.put("server-port", "12345");

            return editServerHandler.setProperties(properties, serverID);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
