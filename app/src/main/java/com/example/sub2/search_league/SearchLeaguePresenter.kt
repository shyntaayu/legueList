package com.example.sub2.search_league

import com.example.sub2.api.ApiRepository
import com.example.sub2.api.TheSportDBApi
import com.example.sub2.model.SearchEventResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SearchLeaguePresenter(private val view: SearchLeagueView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson
) {
    fun getSearchEventList(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.searchEvent(league)),
                SearchEventResponse::class.java
            )

            uiThread {
                view.hideLoading()
                if(!data.event.isNullOrEmpty()) {
                    val filtered = data.event.filter { it.strSport == "Soccer" }
                    view.showEventList(filtered)
                }else{
                    view.showNullData()
                }
            }
        }
    }
}