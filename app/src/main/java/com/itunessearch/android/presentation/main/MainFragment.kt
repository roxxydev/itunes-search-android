package com.itunessearch.android.presentation.main

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.itunessearch.android.R
import com.itunessearch.android.datasource.preference.AppPrefs
import com.itunessearch.android.domain.model.Media
import com.itunessearch.android.domain.state.DataState
import com.itunessearch.android.presentation.adapter.ContentRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragment: Fragment(R.layout.fragment_main) {

    @Inject
    lateinit var appPrefs: AppPrefs

    private val viewModel: MainViewModel by  viewModels()
    private lateinit var contentRecyclerAdapter: ContentRecyclerAdapter
    private lateinit var searchView: SearchView
    private var selectedFilter: Media = Media.ALL

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        subscribeObservers()

        if (!appPrefs.isFirstUse) {
            viewModel.setStateEvent(
                MainIntent.GetInitialContentsIntent(
                    null,
                    null
                )
            )
        }

        appPrefs.isFirstUse = false
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            when(dataState) {

                is DataState.SUCCESS<MainDataState> -> {
                    displayProgressBar(false)

                    dataState.data?.let { it ->

                        val isInitialLoad = it.isInitial ?: true

                        /*
                         * Display cache contents on initial start of app since
                         * api returns empty results for empty search term
                         */

                        when {
                            isInitialLoad -> {
                                contentRecyclerAdapter.submitList(it.cacheContents)
                                displayTextViewEmpty(it.cacheContents.isEmpty())
                            }
                            it.contents.isNotEmpty() -> {
                                contentRecyclerAdapter.submitList(it.contents)
                                displayTextViewEmpty(false)
                            }
                            it.contents.isEmpty() -> {
                                val toastMsg = getString(R.string.empty_results)
                                displayToast(toastMsg)
                                displayTextViewEmpty(it.cacheContents.isEmpty())
                            }
                        }
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

                    // If no network on application start then display cache data
                    dataState.data?.isInitial?.let {
                        val cacheContents = dataState.data?.cacheContents ?: emptyList()
                        contentRecyclerAdapter.submitList(cacheContents)
                        displayTextViewEmpty(cacheContents.isEmpty())
                    }
                }
            }
        })
    }

    // Only display label on first time use of app
    private fun displayTextViewEmpty(isDisplayed: Boolean) {
        tvEmptyMsg.visibility = when(isDisplayed) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progress_bar.visibility = when(isDisplayed) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    private fun displayToast(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    private fun initViews() {
        main_recyclerview.apply {
            layoutManager = LinearLayoutManager(this.context)

            val itemDecoration: ContentRecyclerAdapter.TopSpacingDecoration =
                ContentRecyclerAdapter.TopSpacingDecoration(30)
            addItemDecoration(itemDecoration)

            contentRecyclerAdapter = ContentRecyclerAdapter()
            adapter = contentRecyclerAdapter
        }

        initTopAppBar()
        initMenus()

        displayTextViewEmpty(true)
    }

    private fun initTopAppBar() {
        val filterOpts = Media.values().filter {
            it != Media.ALL
        }.map {
            it.value.toUpperCase()
        }

        var singleItems: Array<String> = arrayOf(Media.ALL.value.toUpperCase())
        singleItems = singleItems.plus(filterOpts)
        val checkedItem = fun(): Int {
            val selectedFilterTxt = selectedFilter.toString().toUpperCase()
            var pos = singleItems.indexOf(selectedFilterTxt)
            pos = if (pos < 0) 0 else pos
            return pos
        }

        val filterListDiagCallback = fun (dialog: DialogInterface, selection: Int) {
            val lw: ListView = (dialog as AlertDialog).listView
            val checkedFilter = lw.adapter.getItem(selection) as String
            selectedFilter = Media.fromString(checkedFilter)

            /*
             * If filter changed then only refresh list if search text is not empty. This
             * is to avoid returning empty list as ITunes search API returns empty list for
             * blank 'term' query parameter.
             */

            if (searchView.query.toString().isNotEmpty()) {
                viewModel.setStateEvent(
                    MainIntent.GetContentsIntent(
                        searchView.query.toString(),
                        selectedFilter
                    )
                )
            }

            dialog.dismiss()
        }

        val filterListDiag = MaterialAlertDialogBuilder(this.requireContext())
            .setTitle(resources.getString(R.string.menu_filter_list))
            .setSingleChoiceItems(singleItems, checkedItem(), filterListDiagCallback)

        topAppBar.inflateMenu(R.menu.menu)
        topAppBar.navigationIcon = null
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_search -> {
                    true
                }
                R.id.action_filter -> {
                    // Handle search icon press
                    filterListDiag.setSingleChoiceItems(singleItems, checkedItem(), filterListDiagCallback)
                    filterListDiag.show()
                    true
                }
                else -> false
            }
        }
    }

    private fun initMenus() {
        val searchItem = topAppBar.menu.findItem(R.id.action_search)
        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }

        searchView.setOnQueryTextListener(
            DebouncingQueryTextListener(
                this.lifecycle
            ) { newText ->
                newText?.let { txt ->
                    if (txt.isNotEmpty()) {
                        viewModel.setStateEvent(
                            MainIntent.GetContentsIntent(
                                txt,
                                selectedFilter
                            )
                        )
                    }
                }
            }
        )
    }
}

// Search as you type with a little throttling to avoid spam of network call to API
internal class DebouncingQueryTextListener(
    lifecycle: Lifecycle,
    private val onDebouncingQueryTextChange: (String?) -> Unit
) : SearchView.OnQueryTextListener {

    private var debouncePeriod: Long = 1000
    private val coroutineScope = lifecycle.coroutineScope
    private var searchJob: Job? = null

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            newText?.let {
                delay(debouncePeriod)
                onDebouncingQueryTextChange(newText)
            }
        }
        return false
    }
}