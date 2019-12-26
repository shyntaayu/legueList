package com.example.sub2.api

import com.example.sub2.model.EventResponse
import com.example.sub2.model.LeagueResponse
import com.example.sub2.model.TeamResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/v1/json/1/lookupleague.php")
    fun getDetailLeague(@Query("id") id: String): Call<LeagueResponse>

    @GET("api/v1/json/1/search_all_teams.php")
    fun getTeams(@Query("l") l: String): Call<TeamResponse>

    @GET("api/v1/json/1/all_leagues.php")
    fun getAllLeagues(): Call<LeagueResponse>

    @GET("api/v1/json/1/eventsnextleague.php")
    fun getEventsNextLeague(@Query("id") id: String): Call<EventResponse>

    @GET("api/v1/json/1/eventspastleague.php")
    fun getEventsPastLeague(@Query("id") id: String): Call<EventResponse>

    @GET("api/v1/json/1/lookupevent.php")
    fun getDetailEvent(@Query("id") id: String): Call<EventResponse>

    @GET("api/v1/json/1/lookupteam.php")
    fun getDetailTeam(@Query("id") id: String): Call<TeamResponse>

    @GET("api/v1/json/1/searchevents.php")
    fun searchEvent(@Query("e") e: String): Call<EventResponse>
}