package com.itunessearch.android.presentation.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.itunessearch.android.R
import com.itunessearch.android.domain.model.Content
import com.itunessearch.android.domain.state.DataState
import com.itunessearch.android.presentation.UiUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*

@AndroidEntryPoint
class DetailFragment: Fragment(R.layout.fragment_detail) {

    private val viewModel: MainViewModel by  viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()
        viewModel.setStateEvent(DetailIntent.ShowContentDetail(""))
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            when(dataState) {

                is DataState.SUCCESS<DetailDataState> -> {
                    displayProgressBar(false)
                    dataState.data?.let {
                        displayContentDetails(it.content)
                    }
                }

                is DataState.LOADING -> {
                    displayProgressBar(dataState.loading)
                }

                is DataState.ERROR -> {
                    displayProgressBar(dataState.loading)
                    dataState.stateMessage?.message?.let {
                        displayToast(it)
                    }
                }
            }
        })
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
//        progress_bar.visibility = when(isDisplayed) {
//            true -> View.VISIBLE
//            else -> View.GONE
//        }
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
}
