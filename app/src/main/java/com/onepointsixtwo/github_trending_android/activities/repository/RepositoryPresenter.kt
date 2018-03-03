package com.onepointsixtwo.github_trending_android.activities.repository

import com.onepointsixtwo.github_trending_android.retrofit.GitHubService
import javax.inject.Inject


class RepositoryPresenter @Inject constructor() {

    @Inject
    lateinit var githubService: GitHubService


}