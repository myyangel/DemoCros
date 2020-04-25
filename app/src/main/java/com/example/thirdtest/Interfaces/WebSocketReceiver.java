package com.example.thirdtest.Interfaces;

public interface WebSocketReceiver {
    void onWebSocketMessage(String message);
    void onWebSocketClose(int code, String reason, boolean remote);
}
