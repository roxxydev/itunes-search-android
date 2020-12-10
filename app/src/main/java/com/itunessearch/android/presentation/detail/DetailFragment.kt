package com.itunessearch.android.presentation.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.itunessearch.android.R
import com.itunessearch.android.domain.model.Content
import com.itunessearch.android.domain.state.DataState
import com.itunessearch.android.presentation.UiUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailFragment: Fragment(R.layout.fragment_detail) {

    private val viewModel: MainViewModel by  viewModels()

    companion object {
        val ARGS_CONTENT_ID = "content_id"
        val ARGS_CONTENT_TRACKID = "content_track_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews();
        subscribeObservers()

        val id = arguments?.getInt(ARGS_CONTENT_ID)
        val trackId = arguments?.getInt(ARGS_CONTENT_TRACKID)
        viewModel.setStateEvent(DetailIntent.ShowContentDetail(id, trackId))
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            when(dataState) {
                is DataState.SUCCESS<DetailDataState> -> {
                    dataState.data?.let {
                        displayContentDetails(it.content)
                    }
                }
                is DataState.ERROR -> {
                    dataState.stateMessage?.message?.let {
                        displayToast(it)
                    }
                }
            }
        })
    }

    private fun displayToast(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    private fun displayContentDetails(content: Content) {
        UiUtil.setContentTextValues(
            content,
            tvName,
            tvArtist,
            tvGenre,
            tvReleaseDate,
            tvPrice,
            tvDescription
        )
        UiUtil.displayImage(this.requireContext(), content.artworkUrl100, image)
    }

    private fun initViews() {
        topAppBar.navigationIcon = ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_arrow_back)
        topAppBar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }
}
