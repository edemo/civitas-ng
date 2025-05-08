/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.common;

import java.io.*;
import jif.util.*;

/**
 * Abstract helper class for dealing with wire protocols. The methods of
 * this class are used to send and receive data over a communication channel.
 */
public abstract class Protocol {
    protected final void protocolOutputString{}(PrintStream[{}]{} output, String{} s) throws (IOException{})  {
        String m = s==null?"":s;
        if (output == null) return;
        int len = m.length();
        output.println(len);
        output.print(m);
    }
    protected final void protocolOutputString{}(PrintWriter[{}]{} output, String{} s) throws (IOException{})  {
        String m = s==null?"":s;
        if (output == null) return;
        int len = m.length();
        output.println(len);
        output.print(m);
    }
    protected final String protocolInputString{}(BufferedReader[{}]{} input) throws (IOException{})  {
        if (input == null) return null;

        int response_length = -1;
        try {
            response_length = Integer.parseInt(input.readLine());
        }
        catch (NumberFormatException e) {
            throw new IOException(e.getMessage());
        }
        char{}[] responseArr = new char[response_length<0?0:response_length];
        int ind = 0;
        while (ind < response_length) {
            int charsRead = input.read(responseArr, ind, response_length - ind);
            if (charsRead == -1) throw new IOException("Unexpected end of stream");
            ind += charsRead;
            if (ind < response_length) {
                // read a single char (which will block), to prevent a busy waiting loop
                int ch = input.read();
                if (ch == -1)  throw new IOException("Unexpected end of stream");
                try {
                    responseArr[ind++] = (char)ch;
                }
                catch (ArrayIndexOutOfBoundsException imposs) { }
            }
            
        }
        String response = null;
        try {
            response = new String(responseArr);
        }
        catch (NullPointerException impossible) { }
        return response;
    }
    private static final String SENTINAL = "**>>CIVITASSENTINAL<<**";
    protected final void protocolOutputSentinal{}(PrintStream[{}]{} output) throws (IOException{})  {
        if (output != null) output.print(SENTINAL);
    }
    protected final void protocolOutputSentinal{}(PrintWriter[{}]{} output) throws (IOException{})  {
        if (output != null) output.print(SENTINAL);
    }
    protected final void protocolInputToSentinal{}(BufferedReader[{}]{} input, PrintWriter[{}]{} sb) throws (IOException{})  {        
        try {
            int sentinalChar = SENTINAL.charAt(0);
            while (true) {
                int ch = input.read();
                if (ch == -1)  throw new IOException("Unexpected end of stream");
                if (((char)ch) == sentinalChar) {
                    boolean matchesSentinal = true;
                    for (int i = 1; i < SENTINAL.length(); i++) {
                        ch = input.read();
                        if (ch == -1)  throw new IOException("Unexpected end of stream");
                        if (((char)ch) != SENTINAL.charAt(i)) {
                            matchesSentinal = false;
                            sb.append(SENTINAL.substring(0, i));
                            sb.append((char)ch);
                            break;
                        }
                    }
                    if (matchesSentinal) {
                        // we've matched the sentinal
                        sb.flush();
                        return;
                    }                
                }
                else {
                    sb.append((char)ch);
                }
            }
        }
        catch (IndexOutOfBoundsException imposs) { }
        catch (NullPointerException imposs) { }
    }
    

}