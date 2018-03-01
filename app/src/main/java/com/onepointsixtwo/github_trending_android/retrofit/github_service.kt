package com.onepointsixtwo.github_trending_android.retrofit

import com.onepointsixtwo.github_trending_android.entities.GitHubReadmeLink
import com.onepointsixtwo.github_trending_android.entities.GitHubRepositoriesList
import com.onepointsixtwo.github_trending_android.entities.GitHubRepository
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import java.text.SimpleDateFormat
import java.util.*


interface GitHubApi {
    @GET("search/repositories")
    @JSON
    fun getRepositories(@Query("sort") sort: String,
                        @Query("order") order: String,
                        @Query("q") query: String): Single<GitHubRepositoriesList>

    @GET("repos/{owner}/{repo}/readme")
    @JSON
    fun getReadmeLink(@Path("owner") owner: String,
                      @Path("repo") repository: String): Single<GitHubReadmeLink>

    @GET
    @SCALAR
    fun getReadme(@Url url: String): Single<String>
}


class GitHubService(private val api: GitHubApi) {

    private val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    fun getTrendingRepositories(): Single<GitHubRepositoriesList> {
        return api.getRepositories("stars", "desc", "created:>" + sevenDaysAgoDate())
    }

    fun getReadmeMarkdown(repository: GitHubRepository): Single<String> {
        return api.getReadmeLink(repository.owner.name, repository.name).flatMap {
            api.getReadme(it.downloadURL)
        }
    }

    private fun sevenDaysAgoDate(): String {
        val millisecondsInDay = 1000 * 60 * 60 * 24
        return formatter.format(Date(System.currentTimeMillis() - (7 * millisecondsInDay)))
    }
}