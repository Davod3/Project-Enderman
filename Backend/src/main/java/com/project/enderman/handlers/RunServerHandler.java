package com.project.enderman.handlers;

import com.project.enderman.entities.ServerData;
import com.project.enderman.repositories.ServerDataRepository;
import org.apache.catalina.Server;

import java.io.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.Stack;

public class RunServerHandler {
    private final ServerDataRepository serverRepo;

    private static final String LIST_SESSIONS = "ps -x";

    public RunServerHandler(ServerDataRepository serverRepo) {
        this.serverRepo = serverRepo;
    }

    public boolean start(long serverID) throws IOException, InterruptedException {

        Optional<ServerData> maybeSv = this.serverRepo.findById(serverID);

        if(maybeSv.isPresent()) {

            //Retrieve server
            ServerData sv = maybeSv.get();

            // && sv.getStartScript() != null
            if(!isRunning(serverID)  && sv.getStartScript() != null) {

                String id = "Minecraft-" + sv.getPort() + "-" + sv.getName();

                String command = "screen -dmS " + id + " bash " + sv.getStartScript();

                Stack<String> lines = executeCommand(command);

                if(lines.size() == 0) {

                    //Server started with 0 errors
                    sv.setScreenID(id);
                    serverRepo.save(sv);

                    return true;
                } else {
                    //Something bad happened
                    for(String s : lines) {
                        System.out.println(s);
                    }
                    return false;
                }
            } else {

                System.out.println("Server is already running");
                return false;
            }

        }

        System.out.println("Server does not exist");
        return false;
    }

    public boolean stop(long serverID) throws IOException, InterruptedException {

        Optional<ServerData> maybeSv = this.serverRepo.findById(serverID);

        if(maybeSv.isPresent()) {

            //Retrieve server
            ServerData sv = maybeSv.get();

            if(isRunning(serverID)) {

                String command = "screen -X -S " + sv.getScreenID() + " quit";
                Stack<String> lines = executeCommand(command);

                if(lines.size() == 0) {

                    System.out.println("Server stopped successfully");
                    return true;
                } else {
                    //Something bad happened
                    for(String s : lines) {
                        System.out.println(s);
                    }
                    return false;
                }


            } else {

                System.out.println("Server is already stopped!");
                return false;
            }

        }

        System.out.println("Server does not exist");
        return false;
    }

    public boolean isRunning(long serverID) throws IOException, InterruptedException {

        Optional<ServerData> maybeSv = this.serverRepo.findById(serverID);

        if(maybeSv.isPresent()) {

            //THINGS WILL BREAK IF OUTPUT FORMAT OF 'ps x' COMMAND CHANGES!!!!!!!!
            Stack<String> lines = executeCommand(LIST_SESSIONS);
            ServerData sv = maybeSv.get();
            String id = sv.getScreenID();

            if(id != null) {

                for(String s : lines) {

                    if(s.contains(id)){
                        System.out.println("Server already has a session running");
                        return true;
                    }
                }
            }
            System.out.println("Server is not running");
            return false;

        }

        System.out.println("Server does not exist");

       return false;
    }

    private Stack<String> executeCommand(String command) throws IOException, InterruptedException {

        Process p = Runtime.getRuntime().exec(command);
        String s;
        Stack<String> lines = new Stack<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

        while ((s = br.readLine()) != null) {
            lines.push(s);
        }

        p.waitFor();
        p.destroy();

        return lines;
    }

}
