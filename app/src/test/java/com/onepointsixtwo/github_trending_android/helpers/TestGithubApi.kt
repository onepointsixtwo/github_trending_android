package com.onepointsixtwo.github_trending_android.helpers

import com.onepointsixtwo.github_trending_android.entities.GitHubReadmeLink
import com.onepointsixtwo.github_trending_android.entities.GitHubRepositoriesList
import com.onepointsixtwo.github_trending_android.entities.GitHubRepository
import com.onepointsixtwo.github_trending_android.entities.GitHubUser
import com.onepointsixtwo.github_trending_android.retrofit.GitHubApi
import io.reactivex.Single


/**
 * A GitHub API to be used for testing purposes. We can set the values to return them as singles,
 * or leave them blank to be returned an error. Allows testing network scenarios for the presenters.
 */
class TestGithubApi: GitHubApi {

    var repositoriesResponse: GitHubRepositoriesList? = null
    var readmeTextResponse: String? = null
    var readmeLink: GitHubReadmeLink? = null

    override fun getRepositories(sort: String, order: String, query: String): Single<GitHubRepositoriesList> {
        if (repositoriesResponse != null) {
            return Single.just(repositoriesResponse)
        }
        return Single.error(Throwable())
    }

    override fun getReadme(url: String): Single<String> {
        if (readmeTextResponse != null) {
            return Single.just(readmeTextResponse)
        }
        return Single.error(Throwable())
    }

    override fun getReadmeLink(owner: String, repository: String): Single<GitHubReadmeLink> {
        if (readmeLink != null) {
            return Single.just(readmeLink)
        }
        return Single.error(Throwable())
    }
}


fun testingGithubRepositoriesList() : GitHubRepositoriesList? {

    var repositories : MutableList<GitHubRepository> = mutableListOf()
    for (i in 1..9) {
        // Not particularly realistic data, but concrete types and easy to test the returned set
        val user = GitHubUser(i, i.toString(), i.toString())
        val repository = GitHubRepository(i, i.toString(), user, i.toString(), i.toString(), i.toString(), i, i)
        repositories.add(repository)
    }

    return GitHubRepositoriesList(repositories)
}

fun testingGithubReadmeLink(): GitHubReadmeLink {
    return GitHubReadmeLink("https://api.github.com")
}

fun testingReadmeText() : String {
    return "This is a test Readme.md"
}