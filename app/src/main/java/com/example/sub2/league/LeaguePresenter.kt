package com.example.sub2.league

import com.example.sub2.api.ApiRepository
import com.example.sub2.api.TheSportDBApi
import com.example.sub2.model.LeagueResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LeaguePresenter(
    private val view: LeagueView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getLeagueList() {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getAllLeagues()),
                LeagueResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showLeagueList(data.leagues)
            }
        }
    }
}