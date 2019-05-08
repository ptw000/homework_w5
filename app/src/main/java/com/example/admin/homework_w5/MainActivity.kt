package com.example.admin.homework_w5

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_action_bar.*
import com.google.gson.GsonBuilder


class MainActivity : AppCompatActivity(), NowPlayingFragment.OnFragmentInteractionListener, TopRateFragment.OnFragmentInteractionListener, IClickOnFragment {

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var adapterFragment: AdapterFragment
    private var arrFragments : ArrayList<Fragment> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()?.setHomeButtonEnabled(true);
        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setCustomView(R.layout.custom_action_bar)
        //Setup adapter fragment
        arrFragments.add(NowPlayingFragment())
        arrFragments.add(TopRateFragment())
        adapterFragment = AdapterFragment(supportFragmentManager, arrFragments)
        viewPager.adapter = adapterFragment
        tabLayout.setupWithViewPager(viewPager)

        imgSearch.setOnClickListener {
            val keyword  : String = edSearch.text.toString()
            if (keyword.trim().length > 0) {
                val intent : Intent = Intent(this, SearchActivity::class.java)
                intent.putExtra("keyword", keyword)
                startActivity(intent)
            }
        }
    }

    override fun itemClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
