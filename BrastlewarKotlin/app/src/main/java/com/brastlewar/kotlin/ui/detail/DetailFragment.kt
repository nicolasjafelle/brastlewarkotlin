package com.brastlewar.kotlin.ui.detail

import android.os.Build
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.brastlewar.kotlin.R
import com.brastlewar.kotlin.domain.Citizen
import com.brastlewar.kotlin.ui.AbstractFragment
import org.parceler.Parcels

/**
 * Created by nicolas on 11/14/17.
 */
class DetailFragment : AbstractFragment<Unit>() {

    private lateinit var rootLinear: LinearLayout
    private lateinit var secondLinear: LinearLayout

    private lateinit var ageView: TextView
    private lateinit var weightView: TextView
    private lateinit var heightView: TextView
    private lateinit var friendsLabel: TextView
    private lateinit var professionLabel: TextView

    private lateinit var friendRecyclerView: RecyclerView
    private lateinit var professionRecyclerView: RecyclerView


    companion object {
        val SELECTED_CITIZEN = "selected_citizen"

        fun newInstance(args: Bundle): Fragment {
            val fragment = DetailFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)!!

        rootLinear = view.findViewById(R.id.fragment_detail_linear);
        secondLinear = view.findViewById(R.id.fragment_detail_second_linear);
        friendRecyclerView = view.findViewById(R.id.fragment_detail_friend_recycler);
        professionRecyclerView = view.findViewById(R.id.fragment_detail_profession_recycler);
        ageView = view.findViewById(R.id.fragment_detail_age);
        weightView = view.findViewById(R.id.fragment_detail_weight);
        heightView = view.findViewById(R.id.fragment_detail_height);
        friendsLabel = view.findViewById(R.id.fragment_detail_friends_label);
        professionLabel = view.findViewById(R.id.fragment_detail_profession_label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rootLinear.isTransitionGroup = false
            secondLinear.isTransitionGroup = true
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val citizen = Parcels.unwrap<Citizen>(arguments?.getParcelable(SELECTED_CITIZEN))

        ageView.text = resources.getString(R.string.age_years_old, citizen.age)
        weightView.text = resources.getString(R.string.weight, citizen.weight)
        heightView.text = resources.getString(R.string.height, citizen.height)

        showOrNotRecycler(friendRecyclerView, friendsLabel, citizen.friendList, R.string.no_friends);
        showOrNotRecycler(professionRecyclerView, professionLabel, citizen.professionList, R.string.no_profession);
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, stringList: List<String>) {
        recyclerView.setHasFixedSize(true)
        recyclerView.isNestedScrollingEnabled = false

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recyclerView.adapter = ChipAdapter(stringList)
    }

    private fun showOrNotRecycler(recyclerView: RecyclerView, textView: TextView, stringList: List<String>?, @StringRes resId: Int) {
        if (stringList != null && !stringList.isEmpty()) {
            setupRecyclerView(recyclerView, stringList)
        } else {
            textView.setText(resId)
        }
    }


    override fun getMainLayoutResId() = R.layout.fragment_detail
}