package com.example.sub2.next_event

import android.util.Log
import com.example.sub2.api.ApiRepository
import com.example.sub2.api.TheSportDBApi
import com.example.sub2.model.EventResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class NextEventPresenter(
    private val view: NextEventView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {

    fun getEventList(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getEventsNextLeague(league)),
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