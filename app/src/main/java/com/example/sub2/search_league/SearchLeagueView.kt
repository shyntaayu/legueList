package com.example.sub2.search_league

import com.example.sub2.model.Event

interface SearchLeagueView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<Event>)
    fun showNullData()
}