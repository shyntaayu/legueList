package com.example.sub2.detail_league

import com.example.sub2.api.ApiRepository
import com.example.sub2.api.TheSportDBApi
import com.example.sub2.model.LeagueResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailLeaguePresenter(
    private val view: DetailLeagueView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getDetailLeague(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getDetailLeague(league)),
                LeagueResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showDetaiLeague(data.leagues)
            }
        }
    }
}