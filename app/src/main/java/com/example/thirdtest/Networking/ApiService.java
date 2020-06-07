package com.example.thirdtest.Networking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("/getClientNumber")
    Call<ClientNumber> affiliateClientAndGetNumber();

    @POST("/resetClientNumber")
    Call<Void> resetClientNumber();

}
