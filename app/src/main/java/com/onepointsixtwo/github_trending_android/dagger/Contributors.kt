package com.onepointsixtwo.github_trending_android.dagger

import com.onepointsixtwo.github_trending_android.activities.trending_repositories.TrendingRepositoriesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class Contributors {

    @ContributesAndroidInjector
    abstract fun contributeTrendingRepositoriesActivity() : TrendingRepositoriesActivity
}