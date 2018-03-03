package com.onepointsixtwo.github_trending_android.helpers

import com.onepointsixtwo.github_trending_android.activities.repository.RepositoryPresenter
import com.onepointsixtwo.github_trending_android.activities.repository.RepositoryPresenterTests
import com.onepointsixtwo.github_trending_android.activities.trending_repositories.TrendingRepositoriesPresenterTests
import com.onepointsixtwo.github_trending_android.retrofit.GitHubApi
import com.onepointsixtwo.github_trending_android.retrofit.GitHubService
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import javax.inject.Singleton


@Module
class TestModule {

    @Provides
    @Singleton
    fun provideTestGithubApi(): TestGithubApi {
        return TestGithubApi()
    }

    @Provides
    @Singleton
    fun provideTestGithubService(api: TestGithubApi): GitHubService {
        return GitHubService(api)
    }

    @Provides
    @Singleton
    fun provideTestScheduler(): Scheduler {
        return TestScheduler()
    }
}

@Singleton
@Component(modules = arrayOf(TestModule::class))
interface TestComponent {
    fun inject(test: TrendingRepositoriesPresenterTests)
    fun inject(test: RepositoryPresenterTests)
}