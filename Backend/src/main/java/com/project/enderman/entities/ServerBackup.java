package com.project.enderman.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class ServerBackup {

    private static final String BACKUPS_FOLDER = "/backups";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @OneToOne
    private ServerData server;

    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date backupDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ServerData getServer() {
        return server;
    }

    public void setServer(ServerData server) {
        this.server = server;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBackupDate() {
        return backupDate;
    }

    public void setBackupDate(Date backupDate) {
        this.backupDate = backupDate;
    }

    public String getPath() {
        return BACKUPS_FOLDER + "/" + this.name;
    }

}
