package com.onepointsixtwo.github_trending_android.activities.trending_repositories

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.onepointsixtwo.github_trending_android.entities.GitHubRepositoriesList
import com.onepointsixtwo.github_trending_android.entities.GitHubRepository
import com.onepointsixtwo.github_trending_android.retrofit.GitHubService
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import javax.inject.Inject


data class TrendingRepository(val projectName: String,
                              val starsCount: String,
                              val description: String)

interface TrendingRepositoriesPresenterView {
    fun openRepository(repository: GitHubRepository)
    fun updateRepositoriesList(repositories: List<TrendingRepository>)
}

class TrendingRepositoriesPresenter @Inject constructor() {

    @Inject
    lateinit var service: GitHubService
    @Inject
    lateinit var scheduler: Scheduler

    var disposable: Disposable? = null

    var view: TrendingRepositoriesPresenterView? = null
    var repositories: List<GitHubRepository> = listOf()
    var filtered: List<GitHubRepository> = listOf()
    var searchString: String = ""

    //Data binding
    val isLoading = ObservableBoolean(false)
    val isInErrorState = ObservableBoolean(false)
    val filteredRepositories = ObservableField<List<TrendingRepository>>(listOf())

    // Public Interface

    fun loadRepositories() {
        isLoading.set(true)
        isInErrorState.set(false)

        disposable = service.getTrendingRepositories()
                .observeOn(scheduler)
                .subscribe(this::handleSuccessLoadingRepositories, this::handleFailureLoadingRepositories)
    }

    fun cancelLoadingRepositories() {
        disposable?.dispose()
    }

    fun retry() {
        loadRepositories()
    }

    fun repositorySelected(index: Int) {
        val repository = filtered[index]
        view?.openRepository(repository)
    }

    fun searchStringUpdated(searchString: String) {
        this.searchString = searchString
        updateFilteredRepositories()
    }

    private fun handleSuccessLoadingRepositories(repositoriesList: GitHubRepositoriesList) {
        isLoading.set(false)
        repositories = repositoriesList.repositories
        updateFilteredRepositories()
    }

    private fun handleFailureLoadingRepositories(throwable: Throwable) {
        isLoading.set(false)
        isInErrorState.set(true)
    }

    private fun updateFilteredRepositories() {
        var filteredRepos = repositories
        if (searchString.isNotBlank()) {
            filteredRepos = repositories.filter {
                it.description?.contains(searchString, true) == true ||
                        it.name.contains(searchString, true)
            }
        }

        filtered = filteredRepos

        val mapped = filteredRepos.map {
            trendingRepositoryFromGithubRepository(it)
        }

        filteredRepositories.set(mapped)
        view?.updateRepositoriesList(filteredRepositories.get())
    }

    private fun trendingRepositoryFromGithubRepository(repository: GitHubRepository): TrendingRepository {
        val starsCount = String.format("%d stars", repository.stargazersCount)
        var description = ""
        if (repository.description != null) {
            description = repository.description
        }
        return TrendingRepository(repository.name, starsCount, description)
    }
}