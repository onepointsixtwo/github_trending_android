package com.onepointsixtwo.github_trending_android.entities

import com.google.gson.annotations.SerializedName

class GitHubRepositoriesList(@SerializedName("items") val repositories: List<GitHubRepository>)

class GitHubUser(@SerializedName("id") val id: Int,
                 @SerializedName("login") val name: String,
                 @SerializedName("avatar_url") val avatarURL: String)

class GitHubRepository(@SerializedName("id") val id: Int,
                       @SerializedName("name") val name: String,
                       @SerializedName("owner") val owner: GitHubUser,
                       @SerializedName("description") val description: String,
                       @SerializedName("forks_url") val forksURL: String,
                       @SerializedName("stargazers_url") val stargazersURL: String,
                       @SerializedName("forks_count") val forksCount: Int,
                       @SerializedName("stargazers_count") val stargazersCount: Int)

class GitHubReadmeLink(@SerializedName("download_url") val downloadURL: String)