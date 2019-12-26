package com.example.sub2.detail_event

import com.example.sub2.model.Event
import com.example.sub2.model.Team

interface DetailEventView {
    fun showLoading()
    fun hideLoading()
    fun showDetailEvent(data: List<Event>)
    fun showDetailHomeTeam(data: List<Team>)
    fun showDetailAwayTeam(data: List<Team>)
}