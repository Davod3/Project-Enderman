package com.project.enderman.console;

import com.project.enderman.entities.FileDTO;
import com.project.enderman.entities.ServerData;
import com.project.enderman.handlers.CreateServerHandler;
import com.project.enderman.handlers.NavigateHandler;
import com.project.enderman.handlers.RunServerHandler;
import com.project.enderman.repositories.ServerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.IOException;
import java.util.List;

@ShellComponent
public class Commands {

    @Autowired
    ServerDataRepository serverRepo;

    @ShellMethod(key="create-server")
    public long createServer(@ShellOption String name, @ShellOption String port) {

        CreateServerHandler createSvHandler = new CreateServerHandler(this.serverRepo);

        return createSvHandler.createServer(name, port);
    }

    @ShellMethod(key="download-server")
    public boolean downloadServer(@ShellOption String url) {

        CreateServerHandler createSvHandler = new CreateServerHandler(this.serverRepo);
        boolean result = false;

        try {
            result = createSvHandler.downloadServer(url, (long) 1);

        } catch (IOException e) {

            throw new RuntimeException(e);
        }

        return result;
    }

    @ShellMethod(key="view-server-files")
    public boolean viewServerFiles(@ShellOption Long serverid) {

        NavigateHandler nav = new NavigateHandler(serverRepo);

        List<FileDTO> files = nav.listFiles(serverid);

        if(files != null) {

            for(FileDTO f : files) {
                System.out.println(f.getName() + " -- " + f.getPath() + " -- " + f.isFolder());
            }

            return true;
        }

        return false;

    }

    @ShellMethod(key="file-forward")
    public boolean fileForward(@ShellOption String path) {

        NavigateHandler nav = new NavigateHandler(serverRepo);

        List<FileDTO> files = nav.goForward(path);

        if(files != null) {

            for(FileDTO f : files) {
                System.out.println(f.getName() + " -- " + f.getPath() + " -- " + f.isFolder());
            }

            return true;
        }

        return false;


    }

    @ShellMethod(key="file-back")
    public boolean fileBack(@ShellOption String path) {

        NavigateHandler nav = new NavigateHandler(serverRepo);

        List<FileDTO> files = nav.goBackward(path);

        if(files != null) {

            for(FileDTO f : files) {
                System.out.println(f.getName() + " -- " + f.getPath() + " -- " + f.isFolder());
            }

            return true;
        }

        return false;

    }

    @ShellMethod(key="start-server")
    public boolean startServer(@ShellOption long serverID) {

        RunServerHandler runServer = new RunServerHandler(serverRepo);

        try {

            return runServer.start(serverID);

        } catch (IOException e) {

            throw new RuntimeException(e);

        } catch (InterruptedException e) {

            throw new RuntimeException(e);

        }

    }

    @ShellMethod(key="select-script")
    public boolean selectScript(@ShellOption long id, @ShellOption String path) {

        CreateServerHandler createServer = new CreateServerHandler(serverRepo);

        try {
            return createServer.selectStartScript(path, id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @ShellMethod(key="stop-server")
    public boolean stopServer(@ShellOption long serverID) {

        RunServerHandler runServer = new RunServerHandler(serverRepo);

        try {

            return runServer.stop(serverID);

        } catch (IOException e) {

            throw new RuntimeException(e);

        } catch (InterruptedException e) {

            throw new RuntimeException(e);

        }

    }

}
