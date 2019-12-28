package com.example.sub2.favorite.next


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.sub2.R
import com.example.sub2.db.database
import com.example.sub2.detail_event.DetailEventActivity
import com.example.sub2.model.Favorite
import kotlinx.android.synthetic.main.fragment_next_event_favorite.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 */
class NextEventFavoriteFragment : Fragment() {

    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var adapter: NextEventFavoriteAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            swipeRefreshNextFavorite.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITE).whereArgs("(EVENT={eventType})", "eventType" to "next")
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }

        Log.d("favooo",favorites.size.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_next_event_favorite, container, false)
        swipeRefresh = view.findViewById(R.id.swipeRefreshNextFavorite)
        val rvNextFavorite = view.findViewById(R.id.rv_next_favorite) as RecyclerView
        rvNextFavorite.layoutManager = LinearLayoutManager(this.context)
        adapter = NextEventFavoriteAdapter(favorites) {
            toast(it.teamHome+" vs "+it.teamAway)
            startActivity<DetailEventActivity>("eventId" to it.eventId,"eventName" to it.teamHome+" vs "+it.teamAway, "eventType" to "next")
        }
        rvNextFavorite.adapter = adapter
        swipeRefresh.setOnRefreshListener() {
            swipeRefresh.isRefreshing=false
            showFavorite()
        }
        return view
    }


}
