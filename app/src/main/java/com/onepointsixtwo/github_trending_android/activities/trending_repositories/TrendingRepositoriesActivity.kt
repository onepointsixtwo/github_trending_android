package com.onepointsixtwo.github_trending_android.activities.trending_repositories

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.onepointsixtwo.github_trending_android.R
import com.onepointsixtwo.github_trending_android.databinding.ActivityTrendingRepositoriesBinding
import com.onepointsixtwo.github_trending_android.entities.GitHubRepository
import javax.inject.Inject
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.SearchView
import android.view.inputmethod.InputMethodManager
import com.onepointsixtwo.github_trending_android.activities.repository.RepositoryActivity


class TrendingRepositoriesActivity : AppCompatActivity(), TrendingRepositoriesPresenterView,
        SearchView.OnQueryTextListener,
        SearchView.OnCloseListener,
        OnRepositoryClickListener {

    @Inject
    lateinit var presenter: TrendingRepositoriesPresenter
    private var adapter: TrendingRepositoriesAdapter? = null
    private var binding: ActivityTrendingRepositoriesBinding? = null


    // Activity Overrides

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.view = this

        val binding = DataBindingUtil.setContentView<ActivityTrendingRepositoriesBinding>(this, R.layout.activity_trending_repositories)
        binding.presenter = presenter
        this.binding = binding

        setupSearchView(binding.searchView)
        setupRecyclerView(binding.recyclerView)
    }

    override fun onResume() {
        super.onResume()
        presenter.loadRepositories()
    }

    override fun onPause() {
        super.onPause()
        presenter.cancelLoadingRepositories()
    }


    // SETUP

    private fun setupSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(this)
        searchView.setOnCloseListener(this)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val linearLayoutManager = LinearLayoutManager(this)
        adapter = TrendingRepositoriesAdapter(this)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL))
    }


    // PRESENTER VIEW IMPLEMENTATION

    override fun openRepository(repository: GitHubRepository) {
        startActivity(RepositoryActivity.createIntent(this, repository))
    }

    override fun updateRepositoriesList(repositories: List<TrendingRepository>) {
        adapter?.updateRepositories(repositories)
    }


    // ADAPTER CLICK LISTENER

    override fun onRepositoryClick(index: Int) {
        presenter.repositorySelected(index)
    }


    // SEARCH VIEW LISTENERS

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            presenter.searchStringUpdated(newText)
        } else {
            presenter.searchStringUpdated("")
        }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        closeKeyboardAndRemoveFocus()
        return true
    }

    override fun onClose(): Boolean {
        closeKeyboardAndRemoveFocus()
        return true
    }


    // HELPERS

    private fun closeKeyboardAndRemoveFocus() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        binding?.searchView?.clearFocus()
    }
}