package com.example.sub2.league

import com.example.sub2.model.League

interface LeagueView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: List<League>)
}