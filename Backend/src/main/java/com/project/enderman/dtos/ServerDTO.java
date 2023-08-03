package com.project.enderman.dtos;

import com.project.enderman.entities.ServerBackup;
import com.project.enderman.entities.ServerData;

import java.util.Date;

public class ServerDTO {

    private long id;

    private String name;

    private String port;

    private boolean isRunning;

    private boolean isInstalled;

    private String startPath;

    private String lastBackupDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public boolean isInstalled() {
        return isInstalled;
    }

    public void setInstalled(boolean installed) {
        isInstalled = installed;
    }

    public String getStartPath() {
        return startPath;
    }

    public void setStartPath(String startPath) {
        this.startPath = startPath;
    }

    public String getLastBackupDate() {
        return lastBackupDate;
    }

    public void setLastBackupDate(String lastBackupDate) {
        this.lastBackupDate = lastBackupDate;
    }

    public static ServerDTO dtofy(ServerData sv) {

        ServerDTO dto = new ServerDTO();
        ServerBackup backup;

        dto.setId(sv.getID());
        dto.setName(sv.getName());
        dto.setPort(sv.getPort());
        dto.setRunning(sv.isRunning());
        dto.setInstalled(sv.isInstalled());
        dto.setStartPath(sv.getStartScript());
        dto.setLastBackupDate((backup = sv.getBackup())!=null?backup.getBackupDate().toString():null);

        return dto;

    }
}
