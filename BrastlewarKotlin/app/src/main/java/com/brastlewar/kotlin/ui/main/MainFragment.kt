package com.brastlewar.kotlin.ui.main

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.brastlewar.kotlin.R
import com.brastlewar.kotlin.api.response.PopulationResponse
import com.brastlewar.kotlin.domain.Citizen
import com.brastlewar.kotlin.ui.AbstractFragment
import com.brastlewar.kotlin.ui.view.CitizenItemView
import com.brastlewar.kotlin.ui.view.LoadingView
import com.brastlewar.kotlin.utils.SpacesItemDecoration

/**
 * Created by nicolas on 11/10/17.
 */
class MainFragment : AbstractFragment<MainFragment.Callback>(), MainView {

    private lateinit var loadingView: LoadingView

    private var presenter: MainPresenter = MainPresenter()

    private var recyclerView: RecyclerView? = null

    private lateinit var adapter: CitizenAdapter


    companion object {
        fun newInstance() = MainFragment()
    }


    interface Callback {
        fun onItemSelect(citizen: Citizen, view: View)
    }

    override fun getMainLayoutResId() = R.layout.fragment_common_list


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachMvpView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        recyclerView = view!!.findViewById(R.id.fragment_common_list_recycler)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingView = LoadingView(context)
        loadingView.attach(view as ViewGroup, show = true, onClickAction = {
            fetchData()
        })
        loadingView.setThemeBackgroundColor()

        setupRecyclerView()

        if (presenter.isIdle()) {
            fetchData()
        } else if (presenter.isFinished()) {
            if (presenter.filteredList != null) {
                onSearchResult(presenter.filteredList)
            } else {
                onGetData(presenter.response)
            }

        } else if (presenter.isLoading()) {
            loadingView.show()
        } else if (presenter.hasFailed()) {
            loadingView.showErrorView()
        }
    }

    private fun setupRecyclerView() {

        recyclerView?.let {
            it.setHasFixedSize(true)
            it.isNestedScrollingEnabled = false
            it.layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.span_count))
            it.itemAnimator = DefaultItemAnimator()
            it.addItemDecoration(SpacesItemDecoration(resources.getInteger(R.integer.span_count), 40))

            adapter = CitizenAdapter {
                val view = recyclerView?.findViewHolderForLayoutPosition(it)?.itemView as CitizenItemView
                this@MainFragment.callback?.onItemSelect(adapter.getItemAt(it)!!, view.imageView)
            }
            it.adapter = adapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_show_all) {
            loadingView.show()
            presenter.showAll()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    fun performSearch(textToSearch: String) {
        loadingView.show()
        presenter.searchCitizen(textToSearch)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachMvpView()
    }

    private fun fetchData() {
        loadingView.show()
        presenter.getPopulationList()
    }

    override fun onError(throwable: Throwable) {
        loadingView.showErrorView()
    }

    override fun onHostUnreachable() {
        loadingView.showErrorView()
    }

    override fun onHttpErrorCode(errorCode: Int, message: String?) {
        loadingView.showErrorView(getString(R.string.problem_while_connecting, errorCode, message))
    }

    override fun onGetData(response: PopulationResponse?) {
        if (response != null) {
            loadingView.dismiss(true)
            adapter.addList(response.citizenList)
        } else {
            loadingView.showNoContentView()
        }
    }

    override fun onSearchResult(filteredList: List<Citizen>?) {
        if (filteredList != null && !filteredList.isEmpty()) {
            loadingView.dismiss(true)
            adapter.addList(filteredList)
        } else {
            loadingView.showLabel(R.string.no_matches)
        }
    }
}