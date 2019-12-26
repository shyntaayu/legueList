package com.example.sub2.league

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sub2.R
import com.squareup.picasso.Picasso
import com.example.sub2.model.Liga
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_league_item.*

class LeagueAdapter (private val context: Context, private val leagues:List<Liga>, private val listener:(Liga)->Unit) :RecyclerView.Adapter<LeagueViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): LeagueViewHolder {
        return LeagueViewHolder(LayoutInflater.from(context).inflate(R.layout.list_league_item,p0,false))
    }

    override fun getItemCount(): Int = leagues.size

    override fun onBindViewHolder(p0: LeagueViewHolder, p1: Int) {
        p0.bindItem(leagues[p1], listener)
    }
}

class LeagueViewHolder(override val containerView: View):RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bindItem(leagues: Liga, listener: (Liga) -> Unit){
        leagues.image?.let{Picasso.get().load(it).into(ivImage)}
        tvName.text = leagues.name
        containerView.setOnClickListener { listener(leagues) }
    }

}