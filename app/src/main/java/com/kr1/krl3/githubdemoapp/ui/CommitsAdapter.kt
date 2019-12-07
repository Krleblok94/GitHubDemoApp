package com.kr1.krl3.githubdemoapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kr1.krl3.githubdemoapp.R
import com.kr1.krl3.githubdemoapp.datasource.model.view.CommitView
import kotlinx.android.synthetic.main.adapter_commits.view.*

class CommitsAdapter(private val commitsList: List<CommitView>) : RecyclerView.Adapter<CommitsAdapter.ViewHolder>() {

    init {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_commits, parent, false))

    override fun getItemCount() = commitsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val commit = commitsList[position].commit
        val author = commit.author
        val committer = commit.committer

        holder.tvAuthorName.text = author.name
        holder.tvAuthorEmail.text = author.email
        holder.tvAuthorDate.text = author.date

        holder.tvCommitterName.text = committer.name
        holder.tvCommitterEmail.text = committer.email
        holder.tvCommitterDate.text = committer.date

        holder.tvCommitMessage.text = commit.message
        holder.tvCommentCount.text = commit.comment_count
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvAuthorName: TextView = view.tvAuthorName
        val tvAuthorEmail: TextView = view.tvAuthorEmail
        val tvAuthorDate: TextView = view.tvAuthorDate

        val tvCommitterName: TextView = view.tvCommitterName
        val tvCommitterEmail: TextView = view.tvCommitterEmail
        val tvCommitterDate: TextView = view.tvCommitterDate

        val tvCommitMessage: TextView = view.tvCommitMessage
        val tvCommentCount: TextView = view.tvCommentCount
    }
}
