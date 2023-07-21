package com.project.enderman.entities;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;



@Entity
public class ServerData {

    private static final String SERVER_FOLDER = "servers";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NonNull
    @Column(unique = true)
    private String name;

    @NonNull
    private String port;

    @OneToOne(cascade = CascadeType.ALL)
    private ServerBackup backup;

    private String startScript;

    private String screenID;

    private String mainFolder;

    private boolean isInstalled;

    private boolean isRunning;

    public ServerData(String name, String port) {
        this.name = name;
        this.port = port;
        this.isInstalled = false;
        this.isRunning = false;
    }

    public ServerData(){
        //Empty on purpose
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isInstalled() {
        return isInstalled;
    }

    public void setInstalled(boolean installed) {
        isInstalled = installed;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public ServerBackup getBackup() {
        return backup;
    }

    public void setBackup(ServerBackup backup) {
        this.backup = backup;
    }

    public String getMainFolder() {
        return mainFolder;
    }

    public void setMainFolder(String mainFolder) {
        this.mainFolder = mainFolder;
    }

    public String getScreenID() {
        return screenID;
    }

    public void setScreenID(String screenID) {
        this.screenID = screenID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getFolder() {
        return SERVER_FOLDER + "/" + this.name;
    }

    public String getName() {
        return this.name;
    }

    public long getID() {
        return this.id;
    }

    public String getPort() {
        return this.port;
    }

    public void setStartScript(String filename) {
        this.startScript = filename;
    }

    public String getStartScript() {
        return this.startScript;
    }
}
