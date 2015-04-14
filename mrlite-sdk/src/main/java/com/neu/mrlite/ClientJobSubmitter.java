package com.neu.mrlite;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.Socket;

public class ClientJobSubmitter {
    public static void main(String args[]) throws InstantiationException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException,
            SecurityException, ClassNotFoundException, MalformedURLException {

        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            // Check if user has submitted the required minimum number of arguments
            if (args.length < 5) {
                usage();
            }

            socket = new Socket(args[0], 2121);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            // Take all remaining arguments and send them to JobListener
            StringBuffer jobCommand = new StringBuffer();
            for (int i = 1; i < args.length; i++) {
                jobCommand.append(args[i]).append(" ");
            }
            out.println(jobCommand.toString());
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                socket.close();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void usage() {
        // Command to be submitted to Job Listener => mrlite -jar <jar_name> <main_class> <input_file_path> <output_directory_path>
        System.out
                .println("usage: ClientJobSubmitter <JobListenerHost> mrlite -jar <jar_name> <main_class> <input_file_path> <output_directory_path>");
        System.exit(1);
    }
}