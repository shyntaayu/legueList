package com.example.sub2.past_event

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sub2.R
import com.example.sub2.model.Event
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_event.*
import java.text.SimpleDateFormat
import java.util.*

class PastEventAdapter(private val context: Context, private val events:List<Event>, private val listener:(Event)->Unit):
    RecyclerView.Adapter<PastEventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastEventViewHolder {
        return PastEventViewHolder(LayoutInflater.from(context).inflate(R.layout.list_event,parent,false))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: PastEventViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }
}

class PastEventViewHolder (override val containerView: View):RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    fun bindItem(events: Event, listener: (Event) -> Unit){
        Log.d("eventsd",events.strEvent)
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
