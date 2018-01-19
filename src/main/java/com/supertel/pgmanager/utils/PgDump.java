package com.supertel.pgmanager.utils;

import com.supertel.pgmanager.Settings;
import com.supertel.pgmanager.process.ProcessExecutor;
import com.supertel.pgmanager.process.ProcessReturnValues;
import java.io.IOException;

/**
 * File: PgDump.java (UTF-8)
 * Date: Jan 18, 2018
 * Time: 2:14:55 PM
 */
public class PgDump {

    public ProcessReturnValues dump(String dumpPath) throws IOException, InterruptedException {
        return new ProcessExecutor("pg_dump", null)
                .addParam("-U", Settings.getInstance().getUserName())
                .addParam("-d", Settings.getInstance().getDbName())
                .addParam("-f", dumpPath)
                .addParam("-p", "" + Settings.getInstance().getPort())
                .addParam("-Z", "9")
                .start();
    }
    
    public ProcessReturnValues baseBackup(String backupPath) throws IOException, InterruptedException {
        return new ProcessExecutor("pg_basebackup", null)
                .addParam("-U", Settings.getInstance().getUserName())
                .addParam("-D", backupPath)
                .addParam("-z", null)
                .addParam("-F", "t")
                .addParam("-Z", "9")
                .start();
    }
}
