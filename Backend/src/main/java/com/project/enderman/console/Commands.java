package com.project.enderman.console;

import com.project.enderman.entities.ServerData;
import com.project.enderman.handlers.CreateServerHandler;
import com.project.enderman.repositories.ServerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class Commands {

    @Autowired
    ServerDataRepository serverRepo;

    @ShellMethod(key="create-server")
    public long createServer(@ShellOption String name, @ShellOption String port) {

        CreateServerHandler createSvHandler = new CreateServerHandler(this.serverRepo);

        return createSvHandler.createServer(name, port);
    }

}
