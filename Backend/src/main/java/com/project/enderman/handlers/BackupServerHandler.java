package com.project.enderman.handlers;

import com.project.enderman.entities.ServerBackup;
import com.project.enderman.entities.ServerData;
import com.project.enderman.repositories.ServerBackupRepository;
import com.project.enderman.repositories.ServerDataRepository;
import com.project.enderman.utils.Compression;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.zip.ZipOutputStream;

public class BackupServerHandler {

    private final ServerDataRepository serverRepo;
    private final ServerBackupRepository backupRepo;

    public BackupServerHandler(ServerDataRepository serverRepo, ServerBackupRepository backupRepo) {
        this.serverRepo = serverRepo;
        this.backupRepo = backupRepo;

        File backups = new File("backup");
        backups.mkdir();

    }

    public boolean createBackup(long serverID) throws IOException {

        Optional<ServerData> maybeSV = this.serverRepo.findById(serverID);

        if(maybeSV.isPresent()){

            ServerData sv = maybeSV.get();

            Optional<ServerBackup> maybeBackup = this.backupRepo.findById(sv.getID());

            if(maybeBackup.isPresent()) {

                //Backup already exists, fetch it
                ServerBackup backup = maybeBackup.get();

                //Delete old backup
                if(deleteFolder(new File(backup.getPath()))) {

                String pathToBackup = backup(sv);
                backup.setBackupDate(new Date());
                backup.setPath(pathToBackup);

                this.backupRepo.save(backup);

                return true;

                } else {

                    System.out.println("Failed to delete old backup");
                    return false;
                }

            } else {

                //No backup is present, create it
                ServerBackup backup = new ServerBackup();
                backup.setServer(sv);
                String pathToBackup = backup(sv);
                backup.setBackupDate(new Date());
                backup.setPath(pathToBackup);

                this.backupRepo.save(backup);

                return true;
            }

        }

        System.out.println("Server does not exist!");
        return false;
    }

    public boolean removeBackup(long serverID) {

        Optional<ServerBackup> maybeBackup = backupRepo.findById(serverID);

        if(maybeBackup.isPresent()) {

            ServerBackup backup = maybeBackup.get();

            deleteFolder(new File(backup.getPath()));

            backupRepo.delete(backup);

            return true;
        }

        System.out.println("No backup exists!");
        return false;
    }

    private String backup(ServerData sv) throws IOException {

        String sourceFile = sv.getFolder();
        String backupFile = "backup/" + sv.getName() + ".backup";
        FileOutputStream fos = new FileOutputStream(backupFile);
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        File original = new File(sourceFile);

        Compression.zip(original, original.getName(), zipOut);

        zipOut.close();
        fos.close();

        return backupFile;
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
