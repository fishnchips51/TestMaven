package com.example;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class test {
    public static void main(String[] args) {
        try {
            InetAddress a = InetAddress.getLocalHost();
            System.out.println(a.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
