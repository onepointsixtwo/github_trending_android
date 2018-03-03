package com.onepointsixtwo.github_trending_android.activities.repository

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.onepointsixtwo.github_trending_android.entities.GitHubRepository
import com.onepointsixtwo.github_trending_android.retrofit.GitHubService
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class RepositoryPresenter @Inject constructor() {

    @Inject
    lateinit var githubService: GitHubService
    @Inject
    lateinit var scheduler: Scheduler
    var repository: GitHubRepository? = null

    var loadingReadmeDisposable: Disposable? = null

    val title = ObservableField<String>("")
    val imageURL = ObservableField<String>("")
    val userName = ObservableField<String>("")
    val description = ObservableField<String>("")
    val starsCount = ObservableField<String>("")
    val forksCount = ObservableField<String>("")
    val loadingReadme = ObservableBoolean(false)
    val errorLoadingReadme = ObservableBoolean(false)
    val readmeContentMarkdown = ObservableField<String>("")

    fun setGitHubRepository(repository: GitHubRepository?) {
        this.repository = repository
        if (repository != null) {
            setupInitialValuesFromRepository(repository)
        }
    }

    fun loadReadme() {
        if (readmeContentMarkdown.get().isNotEmpty()) {
            return
        }

        val repository = repository
        if (repository != null) {
            loadingReadme.set(true)
            errorLoadingReadme.set(false)

            loadingReadmeDisposable = githubService.getReadmeMarkdown(repository)
                    .observeOn(scheduler)
                    .subscribe(this::didLoadReadmeSuccessfully, this::didFailToLoadReadme)
        } else {
            didFailToLoadReadme(null)
        }
    }

    fun retry() {
        loadReadme()
    }

    fun cancelLoading() {
        loadingReadmeDisposable?.dispose()
    }

    private fun setupInitialValuesFromRepository(repository: GitHubRepository) {
        title.set(repository.name)
        imageURL.set(repository.owner.avatarURL)
        userName.set(repository.owner.name)
        description.set(repository.description)
        starsCount.set(String.format("%d Stars", repository.stargazersCount))
        forksCount.set(String.format("%d Forks", repository.forksCount))
    }

    private fun didLoadReadmeSuccessfully(readmeMarkdown: String) {
        readmeContentMarkdown.set(readmeMarkdown)
        loadingReadme.set(false)
    }

    private fun didFailToLoadReadme(throwable: Throwable?) {
        loadingReadme.set(false)
        errorLoadingReadme.set(true)
    }
}