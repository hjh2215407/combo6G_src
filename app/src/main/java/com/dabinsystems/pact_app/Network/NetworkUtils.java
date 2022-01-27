package com.dabinsystems.pact_app.Network;

import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class NetworkUtils {

    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();

                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%');
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) { }
        return "";
    }

    public static boolean pingTest(String ip) {

        String cmd = "ping -c 1 -W 10 "+ ip;

        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            proc.waitFor();
            int result = proc.exitValue();

            if(result == 0) return true;
            else return false;
        } catch (Exception e) {
            Log.e("pingTest", e.getMessage());
            return false;
        }

    }

}
