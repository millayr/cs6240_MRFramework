package com.neu.mrlite.master;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Pramod Khare Main class i.e. starting point of master node process
 *         gets the ioHandleUrl as input and initializes all its threadpools
 */
public class Main {
    private static URL ioHandleServerUrl;

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            setIoHandleServerUrl(new URL("http://localhost:9290"));
        } catch (MalformedURLException e) {
            System.err
                    .println("Invalid ioHandleServerUrl. Can't initialize the master node.");
            System.exit(0);
        }
        
        // Connect with ioHandleServer and get Socket to be passed to 
    }

    public static URL getIoHandleServerUrl() {
        return ioHandleServerUrl;
    }

    public static void setIoHandleServerUrl(URL ioHandleServerUrl) {
        Main.ioHandleServerUrl = ioHandleServerUrl;
    }

}
