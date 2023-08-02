package com.project.enderman.handlers;

import com.project.enderman.entities.ServerData;
import com.project.enderman.exceptions.RemoteConsoleException;
import com.project.enderman.exceptions.ServerStatusException;
import com.project.enderman.rcon.RemoteConsole;
import com.project.enderman.rcon.RemoteConsoleImp;
import com.project.enderman.repositories.ServerDataRepository;
import org.apache.catalina.Server;

import java.util.Optional;

public class RemoteConsoleHandler {

    ServerDataRepository serverRepo;

    public RemoteConsoleHandler(ServerDataRepository serverRepo) {
        this.serverRepo = serverRepo;
    }

    public String execute(String command, long serverID) throws ServerStatusException, RemoteConsoleException {

        Optional<ServerData> maybeSv = this.serverRepo.findById(serverID);

        if(maybeSv.isPresent()) {

            ServerData sv = maybeSv.get();

            RemoteConsole console = new RemoteConsoleImp();

            return console.sendCommand(command, "25575", "rootjarda27");

        }

        throw new ServerStatusException("Server does not exist!");
    }


}
