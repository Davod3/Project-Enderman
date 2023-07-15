package com.project.enderman.entities;

import java.io.File;

public class FileDTO {

    private String name;
    private String path;
    private String parentPath;
    private boolean isFolder;

    public String getParentPath() {
        return parentPath;
    }

    public FileDTO setParentPath(String parentPath) {
        this.parentPath = parentPath;
        return this;
    }

    public String getName() {
        return name;
    }

    public FileDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getPath() {
        return path;
    }

    public FileDTO setPath(String path) {
        this.path = path;
        return this;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public FileDTO setFolder(boolean folder) {
        isFolder = folder;
        return this;
    }
}
