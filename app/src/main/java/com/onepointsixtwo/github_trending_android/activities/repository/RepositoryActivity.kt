package com.onepointsixtwo.github_trending_android.activities.repository

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.onepointsixtwo.github_trending_android.R
import com.onepointsixtwo.github_trending_android.databinding.ActivityRepositoryBinding
import com.onepointsixtwo.github_trending_android.entities.GitHubRepository
import javax.inject.Inject


class RepositoryActivity : AppCompatActivity() {

    @Inject
    lateinit var repositoryPresenter: RepositoryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityRepositoryBinding>(this, R.layout.activity_repository)
    }

    companion object {

        val repositoryExtraKey = "repository_key"

        fun createIntent(context: Context, repository: GitHubRepository): Intent {
            val intent = Intent(context, RepositoryActivity::class.java)
            intent.putExtra(repositoryExtraKey, repository)
            return intent
        }

        fun getRepositoryFromInstance(repositoryActivity: RepositoryActivity): GitHubRepository? {
            val intent = repositoryActivity.intent
            if (intent != null) {
                return intent.getSerializableExtra(repositoryExtraKey) as? GitHubRepository
            }
            return null
        }
    }
}