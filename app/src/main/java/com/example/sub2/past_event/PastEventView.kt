package com.example.sub2.past_event

import com.example.sub2.model.Event

interface PastEventView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<Event>)
    fun showNullData()
}