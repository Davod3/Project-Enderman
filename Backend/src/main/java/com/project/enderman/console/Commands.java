package com.project.enderman.console;

import com.project.enderman.entities.ServerData;
import com.project.enderman.repositories.ServerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class Commands {

    @Autowired
    ServerDataRepository serverRepo;

    @ShellMethod(key="create-server")
    public boolean createServer() {

        ServerData sv = new ServerData("ATM-9", "25566");

        serverRepo.save(sv);

        ServerData fetched = serverRepo.findById(sv.getID()).get();

        System.out.println(sv.getName() + fetched.getName());

        System.out.println(sv.getPort() + fetched.getPort());

        boolean result = sv.equals(fetched);
        serverRepo.deleteAll();

        return result;
    }

}
