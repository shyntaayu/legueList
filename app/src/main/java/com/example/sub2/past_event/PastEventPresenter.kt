package com.example.sub2.past_event

import com.example.sub2.api.ApiRepository
import com.example.sub2.api.TheSportDBApi
import com.example.sub2.model.EventResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PastEventPresenter(private val view: PastEventView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson
) {
    fun getPastEventList(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getEventsPastLeague(league)),
                EventResponse::class.java
            )

            uiThread {
                view.hideLoading()
                if(!data.events.isNullOrEmpty()) {
                    val filtered = data.events.filter { it.strSport == "Soccer" }
                    view.showEventList(filtered)
                }else{
                    view.showNullData()
                }
            }
        }
    }
}