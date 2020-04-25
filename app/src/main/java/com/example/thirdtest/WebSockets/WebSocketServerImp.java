package com.example.thirdtest.WebSockets;

import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class WebSocketServerImp extends WebSocketServer {

    public  WebSocketServerImp(InetSocketAddress address){
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        Log.i("SERVER OPEN","Arive open");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        conn.close();
        Log.i("SERVER CLOSE","Arrive close");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        broadcast(message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        Log.i("SERVER ERROR","Arrive Error");
    }

    @Override
    public void onStart() {

    }
}