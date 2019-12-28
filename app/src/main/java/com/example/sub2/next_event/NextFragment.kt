package com.example.sub2.next_event


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
import com.example.sub2.api.ApiRepository
import com.example.sub2.detail_event.DetailEventActivity
import com.example.sub2.model.Event
import com.example.sub2.util.SharedPreference
import com.google.gson.Gson
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


/**
 * A simple [Fragment] subclass.
 */
class NextFragment : Fragment(), NextEventView {

    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: NextEventPresenter
    private lateinit var adapter: NextEventAdapter
    private lateinit var leagueId: String
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val request = ApiRepository()
        val gson = Gson()
        presenter = NextEventPresenter(this, request, gson)
        val view = inflater.inflate(R.layout.fragment_next, container, false)
        progressBar = view.findViewById(R.id.progressBarNext)
        swipeRefresh = view.findViewById(R.id.swipeRefreshNext)
        val rvEvent = view.findViewById(R.id.rv_event) as RecyclerView
        rvEvent.layoutManager = LinearLayoutManager(this.context)
        adapter = context?.let {
            NextEventAdapter(it, events){
                toast(it.strEvent.toString())
                startActivity<DetailEventActivity>("eventId" to it.idEvent,"eventName" to it.strEvent, "eventType" to "next")
            }
        }!!
        rvEvent.adapter = adapter
        val sharedPreference: SharedPreference = SharedPreference(activity!!.baseContext)
        leagueId = sharedPreference.getValueString("leagueId").toString()
        presenter.getEventList(leagueId)
        Log.d("eventsa2", leagueId)
        swipeRefresh.setOnRefreshListener() {
            swipeRefresh.isRefreshing = false
            presenter.getEventList(leagueId)
        }
        return view
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing=true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing=false
    }

    override fun showEventList(data: List<Event>) {
        swipeRefresh.isRefreshing=false
        Log.d("eventsi", data.size.toString())
        Log.d("eventsi1", data[0].strEvent)
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showNullData() {
        alert("Next Event not found", "Not Found!") {}.show()
    }
}
