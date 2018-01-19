package com.supertel.pgmanager.utils;

import com.supertel.pgmanager.process.ProcessExecutor;
import com.supertel.pgmanager.Settings;
import com.supertel.pgmanager.process.ProcessReturnValues;
import java.io.IOException;

/**
 * File: PgCtl.java (UTF-8)
 * Date: Jan 18, 2018
 * Time: 2:14:48 PM
 */
public class PgCtl {

    public ProcessReturnValues start() throws IOException, InterruptedException {
        String portParam = String.format("\"-p %d\"", Settings.getInstance().getPort());
        return new ProcessExecutor("pg_ctl", "start")
                .addParam("-o", portParam)
                .setWaitingString("server started")
                .setTimeout(10000L)
                .start();
    }

    public ProcessReturnValues stop() throws IOException, InterruptedException {
        return new ProcessExecutor("pg_ctl", "stop")
                .setWaitingString("server stopped")
                .setTimeout(10000L)
                .start();
    }

    public ProcessReturnValues restart() throws IOException, InterruptedException {
        return new ProcessExecutor("pg_ctl", "restart")
                .setWaitingString("server started")
                .setTimeout(10000L)
                .start();
    }

    public boolean status() throws IOException, InterruptedException {
        ProcessReturnValues prv = new ProcessExecutor("pg_ctl", "status")
                .start();
        return prv.getInput().contains("server is running");
    }
}
