package com.project.enderman.rcon;

import com.project.enderman.exceptions.RemoteConsoleException;

public interface RemoteConsole {


    public String sendCommand(String command, String port, String password) throws RemoteConsoleException;

}
