package com.skaliy.mobilecom.client.connection;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Client implements Runnable {

    private final String host;
    private final int port;
    private Channel channel;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientInitializer(port));

            channel = bootstrap.connect(host, port).sync().channel();

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                query(in.readLine() + "\r\n");
            }

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public ArrayList<String[]> query(String command) {
        channel.write(command + "\r\n");

        return getResult();
    }

    public boolean query(boolean result, String command) {
        channel.write(String.valueOf(result) + ":" + command + "\r\n");

        return Boolean.valueOf(getResult().get(0)[0]);
    }

    private ArrayList<String[]> getResult() {

        if (ClientHandler.isFullResult) {

            ArrayList<String[]> result = new ArrayList<String[]>(ClientHandler.resultSize);

            for (int i = 0; i < ClientHandler.resultSize; i++) {
                result.add(ClientHandler.queryResult.get(i));
            }

            ClientHandler.queryResult.removeAll(ClientHandler.queryResult);
            ClientHandler.resultSize = 0;
            ClientHandler.isFullResult = false;

            return result;

        } else {
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return getResult();
    }

    public boolean isOpen() {
        try {
            return channel.isOpen();
        } catch (NullPointerException e) {
            return false;
        }
    }

}