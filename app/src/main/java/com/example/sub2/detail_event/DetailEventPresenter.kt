package com.example.sub2.detail_event

import com.example.sub2.api.ApiRepository
import com.example.sub2.api.TheSportDBApi
import com.example.sub2.model.EventResponse
import com.example.sub2.model.TeamResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailEventPresenter(private val view: DetailEventView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson
) {
    fun getDetailEvent(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getDetailEvent(league)),
                EventResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showDetailEvent(data.events)
            }
        }
    }

    fun getDetailHomeTeam(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getDetailTeam(league)),
                TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showDetailHomeTeam(data.teams)
            }
        }
    }

    fun getDetailAwayTeam(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getDetailTeam(league)),
                TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showDetailAwayTeam(data.teams)
            }
        }
    }
}