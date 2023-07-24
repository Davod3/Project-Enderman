package com.project.enderman.dtos;

import com.project.enderman.entities.ServerData;

public class ServerDTO {

    private long id;

    private String name;

    private String port;

    private boolean isRunning;

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

    public static ServerDTO dtofy(ServerData sv) {

        ServerDTO dto = new ServerDTO();

        dto.setId(sv.getID());
        dto.setName(sv.getName());
        dto.setPort(sv.getPort());
        dto.setRunning(sv.isRunning());

        return dto;

    }
}