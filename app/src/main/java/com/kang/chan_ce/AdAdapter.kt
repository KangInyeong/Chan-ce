package com.kang.chan_ce
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class AdAdapter(fa: FragmentActivity, count : Int) : FragmentStateAdapter(fa) {
    var mCount = count

    override fun getItemCount(): Int {
        return 2000;
    }

    override fun createFragment(position: Int): Fragment {
        val index = getRealPosition(position)

        if (index == 0) return AdFragment1()
        else if (index == 1) return AdFragment2()
        else if (index == 2) return AdFragment3()
        else return AdFragment4()

    }

    private fun getRealPosition(position: Int): Int {
        return position % mCount
    }

}
