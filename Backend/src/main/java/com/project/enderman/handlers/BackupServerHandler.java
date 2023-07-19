package com.project.enderman.handlers;

import com.project.enderman.entities.ServerBackup;
import com.project.enderman.entities.ServerData;
import com.project.enderman.exceptions.FailedBackupException;
import com.project.enderman.exceptions.ServerStatusException;
import com.project.enderman.repositories.ServerBackupRepository;
import com.project.enderman.repositories.ServerDataRepository;
import com.project.enderman.utils.Compression;

import java.io.*;
import java.util.Date;
import java.util.Optional;
import java.util.zip.ZipInputStream;
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

    public boolean createBackup(long serverID) throws IOException, FailedBackupException, ServerStatusException {

        Optional<ServerData> maybeSV = this.serverRepo.findById(serverID);

        if(maybeSV.isPresent()){

            ServerData sv = maybeSV.get();

            ServerBackup backup = sv.getBackup();

            if(backup != null) {

                //Backup already exists

                //Delete old backup
                if(deleteFolder(new File(backup.getPath()))) {

                String pathToBackup = backup(sv);
                backup.setBackupDate(new Date());
                backup.setPath(pathToBackup);

                this.serverRepo.save(sv);

                return true;

                } else {
                    throw new FailedBackupException("Failed to delete old backup");
                }

            } else {

                //No backup is present, create it
                backup = new ServerBackup();
                sv.setBackup(backup);
                String pathToBackup = backup(sv);
                backup.setBackupDate(new Date());
                backup.setPath(pathToBackup);

                this.serverRepo.save(sv);

                return true;
            }

        }

        throw new ServerStatusException("Server does not exist!");
    }

    public boolean removeBackup(long serverID) throws ServerStatusException, FailedBackupException {

        Optional<ServerData> maybeServer = serverRepo.findById(serverID);

        if(maybeServer.isPresent()) {

            ServerData sv = maybeServer.get();
            ServerBackup backup = sv.getBackup();

            if(backup != null) {

                sv.setBackup(null);
                serverRepo.save(sv);

                deleteFolder(new File(backup.getPath()));
                backupRepo.delete(backup);

                return true;
            }

            throw new FailedBackupException("Backup does not exist!");

        }

        throw new ServerStatusException("Server does not exist!");
    }

    public boolean restoreBackup(long serverID) throws IOException, FailedBackupException, ServerStatusException {

        Optional<ServerData> maybeServer = serverRepo.findById(serverID);

        if(maybeServer.isPresent()) {

            ServerData sv = maybeServer.get();
            ServerBackup backup = sv.getBackup();

            if(backup != null) {

                //Delete original server
                deleteFolder(new File(sv.getFolder()));

                //Restore backup
                ZipInputStream zis = new ZipInputStream(new FileInputStream(backup.getPath()));
                Compression.unzip(new File("servers"), zis);
                zis.close();

                return true;

            } else {

                throw new FailedBackupException("Backup does not exist!");
            }

        }

        throw new ServerStatusException("Server does not exist!");
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
