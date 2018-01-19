package com.supertel.pgmanager.process;

import com.supertel.pgmanager.Settings;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * File: ProcessExecutor.java (UTF-8)
 * Date: Jan 18, 2018
 * Time: 11:39:31 AM
 */
public class ProcessExecutor {

    private Thread process;
    private ProcessBuilder pb;
    private Process p;
    private String waitingString = null;
    private Long timeout = null;
    private final ProcessReturnValues prv = new ProcessReturnValues();
    private final List<Map.Entry<String, String>> params = new ArrayList<>();
    private String utilName;
    private String command;

    //
    private static final Logger LOG = Logger.getLogger(ProcessExecutor.class.getName());

    public ProcessExecutor(String utilName, String command) {
        this.utilName = utilName;
        if (command != null && !command.isEmpty()) {
            this.command = command;
        }
    }

    private void setEnv() {
        // Установка переменных окружения
        Map<String, String> env = pb.environment();
        env.put("LC_MESSAGES", "C");
        env.put("PGDATA", Settings.getInstance().getPathData());
    }

    public ProcessExecutor addParam(String key, String value) {
        params.add(new AbstractMap.SimpleEntry<>(key, value));
        return this;
    }

    public ProcessExecutor setWaitingString(String waitingString) {
        this.waitingString = waitingString;
        return this;
    }

    public ProcessExecutor setTimeout(Long timeout) {
        this.timeout = timeout;
        return this;
    }

    public ProcessReturnValues start() throws InterruptedException {
        // Формирование команды
        List<String> cmd = new ArrayList<>();
        cmd.add(utilName);
        if (command != null && !command.isEmpty()) {
            cmd.add(command);
        }

        if (!params.isEmpty()) {
            for (Map.Entry<String, String> param : params) {
                cmd.add(param.getKey());
                if (param.getValue() != null && !param.getValue().isEmpty()) {
                    cmd.add(param.getValue());
                }
            }
        }

        // Создание ссылки на процесс
        pb = new ProcessBuilder(cmd);
        setEnv();
        pb.directory(new File(Settings.getInstance().getPathBin()));

        process = new Thread(new CommandThread(), "CommandThread: " + pb.command());
        process.start();
        process.join();

        return prv;
    }

    public boolean isAlive() {
        return process.isAlive();
    }

    public void stop() {
        process.interrupt();
    }

    class CommandThread implements Runnable {

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();

            try {
                p = pb.start();
                p.waitFor();

                InputStream inputStream = p.getInputStream();
                InputStream errStream = p.getErrorStream();

                while (true) {
                    String line = read(inputStream);
                    prv.appendLineToInput(line);
                    prv.appendLineToError(read(errStream));

                    if (!p.isAlive()) {
//                        LOG.warning("Process not alive");
                        break;
                    }

                    if (timeout != null && System.currentTimeMillis() > (startTime + timeout)) {
                        prv.appendLineToError("Timeout");
                        break;
                    }

                    if (waitingString != null && line.trim().equals(waitingString)) {
//                        LOG.warning("break after waitingString");
                        break;
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        prv.appendLineToError("Interrupted");
                        break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private String read(InputStream is) throws IOException {
            if (is.available() > 0) {
                byte[] buff = new byte[2048];
                int length = is.read(buff);
                return new String(buff, 0, length).trim();
            }
            return "";
        }
    }
}
