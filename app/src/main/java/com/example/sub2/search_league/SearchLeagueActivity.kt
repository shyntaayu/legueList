package com.example.sub2.search_league

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.View
import com.example.sub2.R
import com.example.sub2.api.ApiRepository
import com.example.sub2.detail_event.DetailEventActivity
import com.example.sub2.model.Event
import com.example.sub2.util.invisible
import com.example.sub2.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search_league.*
import kotlinx.coroutines.Deferred
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class SearchLeagueActivity : AppCompatActivity(), SearchLeagueView {
    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: SearchLeaguePresenter
    private lateinit var adapter: SearchLeagueAdapter
    private lateinit var leagueSearch: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_league)

        val request = ApiRepository()
        val gson = Gson()
        presenter = SearchLeaguePresenter(this, request, gson)
        rv_search_event.layoutManager = LinearLayoutManager(this)
        adapter = SearchLeagueAdapter(events) {
            toast(it.strEvent.toString())
            val intent = Intent(this, DetailEventActivity::class.java)
            intent.putExtra("eventBundle", it)
            startActivity(intent)
        }
        rv_search_event.adapter = adapter
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showEventList(data: List<Event>) {
        Log.d("carid", data.size.toString())
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showNullData() {
        events.clear()
        adapter.notifyDataSetChanged()
        alert("Your key word not found", "Not Found!") {}.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setQueryHint("Search Event...")

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                progressBar.visibility = View.VISIBLE
                if(newText.length>2){
                    presenter.getSearchEventList(newText)
                    progressBar.visibility = View.INVISIBLE
                    toast(newText)
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                progressBar.visibility = View.VISIBLE
                if(query.length>0){
                    progressBar.visibility = View.INVISIBLE
                    searchView.clearFocus()
                    searchView.setQuery("", false)
                    searchItem.collapseActionView()
                    leagueSearch = query
                    presenter.getSearchEventList(query)
                    toast(query)
                }
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}
