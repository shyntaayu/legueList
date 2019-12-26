package com.example.sub2.search_league

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sub2.R
import com.example.sub2.model.Event
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_event.*
import java.text.SimpleDateFormat
import java.util.*

class SearchLeagueAdapter(
    private val events: List<Event>,
    private val listener: (Event) -> Unit
) : RecyclerView.Adapter<SearchEventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchEventViewHolder {
        return SearchEventViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_event,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: SearchEventViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }

}

class SearchEventViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bindItem(events: Event, listener: (Event) -> Unit) {
        val formatDate = SimpleDateFormat("yyy-MM-dd", Locale.getDefault())
        val date = formatDate.parse(events.dateEvent)
        val dateText = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
            .format(date).toString()
        tvEventDate.text = dateText
        Log.d("eventsd",events.strEvent)
        tvEventHomeTeam.text = events.strHomeTeam
        tvEventAwayTeam.text = events.strAwayTeam
        tvEventScoreHome.text = events.intHomeScore
        tvEventScoreAway.text = events.intAwayScore
        containerView.setOnClickListener { listener(events) }
    }

}
