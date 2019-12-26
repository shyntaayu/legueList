package com.example.sub2.detail_league

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.sub2.next_event.NextFragment
import com.example.sub2.past_event.PastFragment

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment {
        return when (p0) {
            0 -> {
                PastFragment()
            }
            else -> {
                NextFragment()
            }
        }
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> {
                "Past Event"
            }
            else -> {
                "Next Event"
            }
        }
    }
}