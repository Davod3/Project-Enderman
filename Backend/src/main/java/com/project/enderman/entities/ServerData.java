package com.project.enderman.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.lang.NonNull;



@Entity
public class ServerData {

    private static final String SERVER_FOLDER = "servers";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NonNull
    private String name;

    @NonNull
    private String port;

    private String startScript;

    public ServerData(String name, String port) {
        this.name = name;
        this.port = port;
    }

    public ServerData(){
        //Empty on purpose
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getRootFolder() {
        return SERVER_FOLDER + "/" + this.name + this.id;
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
        return SERVER_FOLDER + "/" + this.startScript;
    }
}
