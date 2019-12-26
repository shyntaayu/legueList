package com.example.sub2.detail_league

import com.example.sub2.model.League

interface DetailLeagueView {
    fun showLoading()
    fun hideLoading()
    fun showDetaiLeague(data: List<League>)
}