package com.example.sub2.detail_league

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.sub2.R
import com.example.sub2.api.ApiRepository
import com.example.sub2.model.League
import com.example.sub2.model.Liga
import com.example.sub2.search_league.SearchLeagueActivity
import com.example.sub2.util.SharedPreference
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_league.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton


class DetailLeagueActivity : AppCompatActivity(), DetailLeagueView {
    private lateinit var presenter: DetailLeaguePresenter
    private lateinit var leagueId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_league)

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailLeaguePresenter(this, request, gson)

        val liga: Liga = intent.getParcelableExtra("ligaBundle")
        leagueId = liga.idLiga.toString()
        val sharedPreference: SharedPreference = SharedPreference(this)
        sharedPreference.clearSharedPreference()
        sharedPreference.save("leagueId", leagueId)

        presenter.getDetailLeague(leagueId)

        val fragmentAdapter =
            PagerAdapter(supportFragmentManager)
        viewPagerEvent.adapter = fragmentAdapter
        tabEvent.setupWithViewPager(viewPagerEvent)
        swipeRefresh.setOnRefreshListener() {
            swipeRefresh.isRefreshing = false
            presenter.getDetailLeague(leagueId)
        }

        btnBack.onClick {
            alert("Are you sure want to back?", "${liga.name}!") {
                yesButton { super.onBackPressed() }
                noButton {}
            }.show()
        }
        btnSearch.onClick { startActivity<SearchLeagueActivity>() }
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun showDetaiLeague(data: List<League>) {
        val data1 = data[0]
        Log.d("shy", data1.strWebsite)
        data1.strTrophy?.let { Picasso.get().load(it).into(ivDetailImage) }
        tvDetailDesc?.text = data1.strDescriptionEN
        tvDetailTitle?.text = data1.strLeague
        tvDetailType?.text = data1.strSport
        tvDetailDate?.text = data1.dateFirstEvent
        tvDetailCountry?.text = data1.strCountry

        btnWeb.onClick {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("http://" + data1.strWebsite))
            startActivity(i)
        }
    }
}