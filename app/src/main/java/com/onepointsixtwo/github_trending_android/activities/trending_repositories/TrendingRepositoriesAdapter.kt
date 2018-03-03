package com.onepointsixtwo.github_trending_android.activities.trending_repositories

import android.animation.ValueAnimator
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import com.onepointsixtwo.github_trending_android.R
import com.onepointsixtwo.github_trending_android.databinding.RowTrendingRepositoryBinding

interface OnRepositoryClickListener {
    fun onRepositoryClick(index: Int)
}

class TrendingRepositoriesAdapter(private val onClickListener: OnRepositoryClickListener)
    : RecyclerView.Adapter<TrendingRepositoriesRowHolder>() {

    var repositories : List<TrendingRepository> = listOf()

    fun updateRepositories(repositories: List<TrendingRepository>) {
        this.repositories = repositories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TrendingRepositoriesRowHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = RowTrendingRepositoryBinding.inflate(inflater, parent, false)
        return TrendingRepositoriesRowHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: TrendingRepositoriesRowHolder?, position: Int) {
        holder?.bind(repositories[position], position)
    }

    override fun getItemCount(): Int {
        return this.repositories.count()
    }
}

class TrendingRepositoriesRowHolder(val binding: RowTrendingRepositoryBinding,
                                    val itemClickListener: OnRepositoryClickListener)
    : RecyclerView.ViewHolder(binding.root) {

    var index: Int = 0

    init {
        binding.root.setOnClickListener {
            itemClickListener.onRepositoryClick(index)

            val white = binding.root.context.resources.getColor(R.color.white)
            val paleGrey = binding.root.context.resources.getColor(R.color.paleGrey)
            val animator = ValueAnimator.ofArgb(white,  paleGrey, white)
            animator.duration = 300
            animator.addUpdateListener {
                binding.root.setBackgroundColor(it.animatedValue as Int)
            }
            animator.start()
        }
    }

    fun bind(repository: TrendingRepository, index: Int) {
        binding.repository = repository
        binding.executePendingBindings()
        this.index = index
    }
}