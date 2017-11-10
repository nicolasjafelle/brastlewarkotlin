package com.brastlewar.kotlin.ui.main

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brastlewar.kotlin.R
import com.brastlewar.kotlin.api.response.PopulationResponse
import com.brastlewar.kotlin.domain.Citizen
import com.brastlewar.kotlin.ui.AbstractFragment
import com.brastlewar.kotlin.ui.view.LoadingView

/**
 * Created by nicolas on 11/10/17.
 */
class MainFragment : AbstractFragment<MainFragment.Callback>(), MainView, LoadingView.Callback {

    private lateinit var loadingView: LoadingView

    private var presenter: MainPresenter = MainPresenter()

    private var recyclerView: RecyclerView? = null
//
//
//    private LoadingView loadingView;
//
//    @Inject
//    private MainPresenter presenter;
//
//    private RecyclerView recyclerView;
//    private CitizenAdapter adapter;


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
        loadingView.attach(view as ViewGroup, show = true, callback = this)
        loadingView.setThemeBackgroundColor()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachMvpView()
    }

    override fun onRetryClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(throwable: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onHostUnreachable() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onHttpErrorCode(errorCode: Int, message: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetData(response: PopulationResponse) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSearchResult(filteredList: List<Citizen>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}