/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.bboard.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Utilities for getting the heartbeat and experiment results from the
 * file system BBS
 */
public class GenericBBSUtil {
    public static void main(String[] args) {
        if (args.length != 3) usage();
        String cmd = args[0];
        String host = args[1];
        String port = args[2];

        if ("alive".equalsIgnoreCase(cmd)) {
            boolean result = heartbeat(host, Integer.parseInt(port));
            if (result) {
                System.out.println("BB " + host + " " + port + " is alive");
            }
            else {
                System.out.println("BB " + host + " " + port + " is not responding");                
            }
            System.exit(result?1:0);
        }
        else if ("results".equalsIgnoreCase(cmd)) {
            experimentResults(host, Integer.parseInt(port));
        }
        else {
            usage();
        }
    }

    private static void usage() {
        System.err.println("Usage: GenericBBSUtil cmd host port");
        System.err.println("  where cmd is one of 'alive' or 'results' and ");
        System.err.println("     host and port identify the bulletin board.");
        System.err.println("  'GenericBBSUtil alive host port' will exit ");
        System.err.println("     with a-non zero result if the bulletin board is ");
        System.err.println("     responding.");
        System.err.println("  'GenericBBSUtil results host port' will output ");
        System.err.println("     some time results for every election on the");
        System.err.println("     bulletin board.");
        System.exit(0);
    }

    private static boolean heartbeat(String host, int port) {
        try {
            Socket s = new Socket(host, port);
            BufferedReader input = new BufferedReader(new InputStreamReader(s
                                                                            .getInputStream()));
            PrintStream output = new PrintStream(s.getOutputStream());
            output.println("HEARTBEAT");
            output.flush();
            input.readLine();
            s.close();
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }
    private static boolean experimentResults(String host, int port) {
        try {
            Socket s = new Socket(host, port);
            BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintStream output = new PrintStream(s.getOutputStream());
            output.println("EXPERIMENT_RESULTS");
            output.flush();
            String r = input.readLine();
            while ( !"<END>".equals(r)) {
                System.out.println(r);
                r = input.readLine();
            }
            s.close();
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
