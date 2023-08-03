package com.project.enderman.rcon;

import com.project.enderman.exceptions.RemoteConsoleException;
import net.kronos.rkon.core.Rcon;
import net.kronos.rkon.core.ex.AuthenticationException;

import java.io.IOException;

public class RemoteConsoleImp implements RemoteConsole {

    private static final String ADDRESS = "127.0.0.1";

    @Override
    public String sendCommand(String command, String port, String password) throws RemoteConsoleException {

        String result = "";

        try {

            Rcon rcon = new Rcon(ADDRESS, Integer.parseInt(port), password.getBytes());

            result = rcon.command(command);

        } catch (IOException | AuthenticationException e) {

            throw new RemoteConsoleException(e.getMessage());

        }

        return result;

    }
}
