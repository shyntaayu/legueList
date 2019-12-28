package com.example.sub2.favorite.next

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sub2.R
import com.example.sub2.model.Favorite
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_event.*

class NextEventFavoriteAdapter (private val favorites:List<Favorite>, private val listener:(Favorite)->Unit) :
    RecyclerView.Adapter<NextEventFavoriteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextEventFavoriteViewHolder {
        return NextEventFavoriteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_event,parent,false))
    }

    override fun getItemCount(): Int = favorites.size

    override fun onBindViewHolder(holder: NextEventFavoriteViewHolder, position: Int) {
        holder.bindItem(favorites[position], listener)
    }
}

class NextEventFavoriteViewHolder (override val containerView: View):RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    fun bindItem(favorites: Favorite, listener: (Favorite) -> Unit){
        tvEventDate.text = favorites.eventDate
        Log.d("eventsd",favorites.eventId)
        tvEventHomeTeam.text = favorites.teamHome
        tvEventAwayTeam.text = favorites.teamAway
        tvEventScoreHome.text = favorites.scoreHome
        tvEventScoreAway.text = favorites.scoreAway
        containerView.setOnClickListener { listener(favorites) }
    }

}
