package com.example.thirdtest.Networking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientNumber {
    @SerializedName("ClientNumber")
    @Expose
    private int clientNumber;

    public int getClientNumber() {
        return clientNumber;
    }
}
