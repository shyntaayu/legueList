package com.example.sub2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.example.sub2.R.array.*
import com.example.sub2.detail_league.DetailLeagueActivity
import com.example.sub2.league.LeagueAdapter
import com.example.sub2.model.Liga
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.portrait
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private var ligas: MutableList<Liga> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()

        rv_league.layoutManager =
            if (resources.configuration.portrait) GridLayoutManager(this, 2) else GridLayoutManager(
                this,
                4
            )
        rv_league.adapter = LeagueAdapter(this, ligas) {
            toast(it.name.toString())
            startActivity<DetailLeagueActivity>("ligaBundle" to it)
        }
    }

    private fun initData() {
        val ligaName = resources.getStringArray(liga_name)
        val ligaImage = resources.obtainTypedArray(liga_image)
        val ligaId = resources.getStringArray(liga_id)
        ligas.clear()
        for (i in ligaName.indices) {
            ligas.add(Liga(ligaName[i], ligaImage.getResourceId(i, 0), ligaId[i]))
        }
        ligaImage.recycle()
    }
}
