package com.mati.launcher.utils;

import com.mati.launcher.data.model.News;
import com.mati.launcher.data.model.Update;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface Interface {

    @GET("version")
    @Headers("Token: P7p]j+AeL479")
    Call<Update> getUpdate();


    @GET("stories")
    @Headers("Token: P7p]j+AeL479")
    Call<News> getStories();

    /*@GET("http://wh3606.web3.maze-host.ru/zakazi/35/servers.json")
    Call<List<Servers>> getServers();*/

    /*@GET("http://wh3606.web3.maze-host.ru/zakazi/35/stories.json")
    Call<List<News>> getNews();*/

    /*@GET("http://wh3606.web3.maze-host.ru/donate.json")
    Call<List<Actions>> getActions();*/
}
