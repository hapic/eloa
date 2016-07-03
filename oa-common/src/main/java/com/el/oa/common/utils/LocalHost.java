package com.el.oa.common.utils;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Created by Administrator on 2015/5/9.
 */
public class LocalHost {
    private static NetworkInterface localNetworkInterface;
    private static String hostName;

    static {
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while(e.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface)e.nextElement();
                if(!networkInterface.getName().equals("lo")) {
                    localNetworkInterface = networkInterface;
                    break;
                }
            }
        } catch (SocketException var3) {
            var3.getStackTrace();
        }

        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException var2) {
            var2.getStackTrace();
        }

    }

    public LocalHost() {
    }

    public static NetworkInterface getLocalNetworkInterface() {
        return localNetworkInterface;
    }

    public static String getMachineName() {
        return hostName;
    }
}

