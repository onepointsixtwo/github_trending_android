package com.onepointsixtwo.github_trending_android.activities.trending_repositories

import com.nhaarman.mockito_kotlin.*
import com.onepointsixtwo.github_trending_android.helpers.DaggerTestComponent
import com.onepointsixtwo.github_trending_android.helpers.TestGithubApi
import com.onepointsixtwo.github_trending_android.helpers.testingGithubRepositoriesList
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import javax.inject.Inject


class TrendingRepositoriesPresenterTests {

    @Inject
    lateinit var presenter: TrendingRepositoriesPresenter
    @Inject
    lateinit var testApi: TestGithubApi
    lateinit var view: TrendingRepositoriesPresenterView

    @Before
    fun setUp() {
        val component = DaggerTestComponent.create()
        component.inject(this)

        view = mock<TrendingRepositoriesPresenterView> {}
        presenter.view = view
    }

    @Test
    fun testLoadingRepositoriesWithSuccess() {
        //set the response
        testApi.repositoriesResponse = testingGithubRepositoriesList()

        // load the repositories
        presenter.loadRepositories()

        // check results
        assertThat(presenter.isInErrorState.get(), `is`(false))
        assertThat(presenter.isLoading.get(), `is`(false))
        assertThat(presenter.filteredRepositories.get()?.count(), `is`(9))

        presenter.filteredRepositories.get()?.forEachIndexed { index, trendingRepository ->
            val i = index + 1
            assertThat(trendingRepository.projectName, `is`(i.toString()))
            assertThat(trendingRepository.starsCount, `is`(String.format("%d stars", i)))
            assertThat(trendingRepository.description, `is`(i.toString()))
        }

        verify(view).updateRepositoriesList(presenter.filteredRepositories.get())
    }

    @Test
    fun testLoadingRepositoriesWithFailure() {
        // load the repositories
        presenter.loadRepositories()

        assertThat(presenter.isInErrorState.get(), `is`(true))
        assertThat(presenter.isLoading.get(), `is`(false))
    }

    @Test
    fun testLoadingRepositoriesWithRetry() {
        presenter.loadRepositories()

        assertThat(presenter.isInErrorState.get(), `is`(true))

        testApi.repositoriesResponse = testingGithubRepositoriesList()
        presenter.retry()

        assertThat(presenter.isInErrorState.get(), `is`(false))
        assertThat(presenter.isLoading.get(), `is`(false))
        assertThat(presenter.filteredRepositories.get()?.count(), `is`(9))
        verify(view).updateRepositoriesList(presenter.filteredRepositories.get())
    }

    @Test
    fun testFilteringRepositoriesBySearch() {
        testApi.repositoriesResponse = testingGithubRepositoriesList()
        presenter.loadRepositories()

        for (i in 1..9) {
            presenter.searchStringUpdated(i.toString())
            assertThat(presenter.filteredRepositories.get()?.count(), `is`(1))
            verify(view).updateRepositoriesList(presenter.filteredRepositories.get())
        }

        presenter.searchStringUpdated("a")
        assertThat(presenter.filteredRepositories.get()?.count(), `is`(0))
    }

    @Test
    fun testOpeningRepository() {
        testApi.repositoriesResponse = testingGithubRepositoriesList()
        presenter.loadRepositories()

        presenter.repositorySelected(2)
        verify(view).openRepository(presenter.filtered[2])
    }
}