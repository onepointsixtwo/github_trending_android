package com.onepointsixtwo.github_trending_android.activities.repository

import com.onepointsixtwo.github_trending_android.entities.GitHubRepository
import com.onepointsixtwo.github_trending_android.entities.GitHubUser
import com.onepointsixtwo.github_trending_android.helpers.DaggerTestComponent
import com.onepointsixtwo.github_trending_android.helpers.TestGithubApi
import com.onepointsixtwo.github_trending_android.helpers.testingGithubReadmeLink
import com.onepointsixtwo.github_trending_android.helpers.testingReadmeText
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test
import javax.inject.Inject


class RepositoryPresenterTests {

    @Inject
    lateinit var presenter: RepositoryPresenter
    @Inject
    lateinit var testApi: TestGithubApi

    @Before
    fun setUp() {
        val component = DaggerTestComponent.create()
        component.inject(this)

        val owner = GitHubUser(1,
                "Ted",
                "https://image.com/image")
        val repository = GitHubRepository(1,
                "Android-Lib",
                owner,
                "This is an Android lib",
                "https://url.com/forks",
                "https://url.com/stars",
                2,
                3)
        presenter.setGitHubRepository(repository)
    }

    @Test
    fun testSetsUpInitialValuesCorrectlyFromRepository() {
        assertThat(presenter.title.get(), `is`("Android-Lib"))
        assertThat(presenter.imageURL.get(), `is`("https://image.com/image"))
        assertThat(presenter.userName.get(), `is`("Ted"))
        assertThat(presenter.description.get(), `is`("This is an Android lib"))
        assertThat(presenter.starsCount.get(), `is`("3 Stars"))
        assertThat(presenter.forksCount.get(), `is`("2 Forks"))
    }

    @Test
    fun testSuccessfullyFetchesReadme() {
        testApi.readmeLink = testingGithubReadmeLink()
        testApi.readmeTextResponse = testingReadmeText()

        presenter.loadReadme()

        assertThat(presenter.readmeContentMarkdown.get(), `is`("This is a test Readme.md"))
        assertThat(presenter.loadingReadme.get(), `is`(false))
        assertThat(presenter.errorLoadingReadme.get(), `is`(false))
    }

    @Test
    fun testFailsToFetchReadme() {
        presenter.loadReadme()

        assertThat(presenter.loadingReadme.get(), `is`(false))
        assertThat(presenter.errorLoadingReadme.get(), `is`(true))
    }

    @Test
    fun testReadmeFetchRetry() {
        presenter.loadReadme()

        assertThat(presenter.errorLoadingReadme.get(), `is`(true))

        testApi.readmeLink = testingGithubReadmeLink()
        testApi.readmeTextResponse = testingReadmeText()

        presenter.loadReadme()

        assertThat(presenter.readmeContentMarkdown.get(), `is`("This is a test Readme.md"))
        assertThat(presenter.loadingReadme.get(), `is`(false))
        assertThat(presenter.errorLoadingReadme.get(), `is`(false))
    }
}