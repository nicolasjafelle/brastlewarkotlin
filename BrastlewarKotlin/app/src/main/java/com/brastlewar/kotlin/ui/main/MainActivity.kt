package com.brastlewar.kotlin.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.View
import android.view.ViewTreeObserver
import com.brastlewar.kotlin.R
import com.brastlewar.kotlin.domain.Citizen
import com.brastlewar.kotlin.ui.AbstractAppCompatActivity
import com.brastlewar.kotlin.ui.detail.DetailActivity
import com.brastlewar.kotlin.ui.view.MaterialSearchView

/**
 * Created by nicolas on 11/7/17.
 */
class MainActivity : AbstractAppCompatActivity(), MainFragment.Callback {

    private lateinit var searchView: MaterialSearchView

    companion object {
        fun launchActivity(activity: AppCompatActivity) {

            val intent = Intent(activity, MainActivity::class.java)
            ActivityCompat.startActivity(activity, intent, null)
        }
    }

    override fun setInitialFragment() {
        setInitialFragment(MainFragment.newInstance())
    }

    override fun getBaseLayoutResId() = R.layout.activity_single_search_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSearchView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val item = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)
        return true
    }

    override fun onBackPressed() {
        if (searchView.isSearchOpen) {
            searchView.closeSearch()
        } else {
            super.onBackPressed()
        }
    }

    private fun initSearchView() {
        searchView = findViewById(R.id.activity_single_fragment_search_view)

        toolbar?.viewTreeObserver?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                toolbar?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                toolbar?.let { searchView.toolbarPosition(it.width, it.height) }
            }
        })

        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?) = resolveSearch(newText!!)
        })
    }

    private fun resolveSearch(query: String): Boolean {
        if (getCurrentFragment() is MainFragment) {
            (getCurrentFragment() as MainFragment).performSearch(query)
            return true
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == MaterialSearchView.REQUEST_VOICE
                && resultCode == Activity.RESULT_OK) {
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (results != null && results.size > 0) {
                val searchWord = results[0]
                searchView.searchSrcTextView.setText(searchWord)
                resolveSearch(searchWord)
            }
        }
    }


    override fun onItemSelect(citizen: Citizen, view: View) {
//        Toast.makeText(this, "${citizen.name} | ${citizen.age}", Toast.LENGTH_SHORT).show()
        DetailActivity.launchActivity(this, citizen, view)

    }
}