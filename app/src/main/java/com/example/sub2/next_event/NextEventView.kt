package com.example.sub2.next_event

import com.example.sub2.model.Event

interface NextEventView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<Event>)
    fun showNullData()
}