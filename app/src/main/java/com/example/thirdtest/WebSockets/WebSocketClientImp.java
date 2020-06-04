package com.example.thirdtest.WebSockets;

import android.util.Log;

import com.example.thirdtest.Interfaces.WebSocketReceiver;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebSocketClientImp extends WebSocketClient {

    private WebSocketReceiver receiverMethod;

    public int numCliente = 1;

    public WebSocketClientImp(URI serverUri) {
        super(serverUri);
    }

    public WebSocketClientImp(URI serverUri, WebSocketReceiver receiver) {
        super(serverUri);
        this.receiverMethod = receiver;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.i("CLIENT OPEN","Arrive Open");
        numCliente ++;
    }

    @Override
    public void onMessage(String message) {
        receiverMethod.onWebSocketMessage(message);
        Log.i("CLIENT MESSAGE:","Arrive Message");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        receiverMethod.onWebSocketClose(code,reason,remote);
        Log.i("CLIENT CLOSE:","Arrive Close");
    }

    @Override
    public void onError(Exception ex) {
        Log.i("CLIENT ERROR","Arrive error: "+ex.getMessage());
    }

}