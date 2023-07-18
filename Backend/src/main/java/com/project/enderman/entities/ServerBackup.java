package com.project.enderman.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class ServerBackup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @PrimaryKeyJoinColumn(referencedColumnName = "id")
    private ServerData server;

    private String path;

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


    public Date getBackupDate() {
        return backupDate;
    }

    public void setBackupDate(Date backupDate) {
        this.backupDate = backupDate;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
