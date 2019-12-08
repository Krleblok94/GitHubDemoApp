package com.kr1.krl3.githubdemoapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kr1.krl3.githubdemoapp.R
import com.kr1.krl3.githubdemoapp.common.VisibilitySwitcher
import com.kr1.krl3.githubdemoapp.datasource.model.view.CommitView
import com.kr1.krl3.githubdemoapp.datasource.model.view.RepoView
import kotlinx.android.synthetic.main.adapter_repos.view.*

class ReposAdapter : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    private val reposAndCommitsSet = mutableSetOf<Map.Entry<RepoView, List<CommitView>>>()
    private var expandedPosition = -1
    private var previousExpandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.adapter_repos, parent, false), this)

    override fun getItemCount() = reposAndCommitsSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = reposAndCommitsSet.elementAt(position)
        val repo = entry.key
        val commits = entry.value

        holder.tvRepoName.text = repo.name
        holder.tvOpenIssues.text = repo.open_issues

        if (commits.isEmpty()) {
            VisibilitySwitcher.switchVisibility(false, holder.rvCommits)
            VisibilitySwitcher.switchVisibility(true, holder.tvNoCommits)
        } else {
            holder.rvCommits.adapter = CommitsAdapter(commits)
            VisibilitySwitcher.switchVisibility(true, holder.rvCommits)
            VisibilitySwitcher.switchVisibility(false, holder.tvNoCommits)
        }

        val isExpanded = expandedPosition == position
        if (isExpanded) { previousExpandedPosition = expandedPosition }

        VisibilitySwitcher.switchVisibility(isExpanded, holder.clCommits)

        holder.ivExpand.setImageResource(if (isExpanded) R.drawable.ic_collapse else R.drawable.ic_drop_down)
    }

    fun addReposAndCommits(map: Map<RepoView, List<CommitView>>) {
        reposAndCommitsSet.clear()
        reposAndCommitsSet.addAll(map.entries)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View, private val reposAdapter: ReposAdapter) : RecyclerView.ViewHolder(view) {

        val tvRepoName: TextView = view.tvRepoName
        val tvOpenIssues: TextView = view.tvOpenIssues
        val rvCommits: RecyclerView = view.rvCommits
        val tvNoCommits: TextView = view.tvNoCommits
        val ivExpand: ImageView = view.ivExpand
        val clCommits: ConstraintLayout = view.clCommits

        private val tvCommits: TextView = view.tvCommits

        private val clickListener = View.OnClickListener {
            when (it.id) {
                R.id.ivExpand, R.id.tvCommits -> toggleCommitsVisibility()
            }
        }

        init {
            ivExpand.setOnClickListener(clickListener)
            tvCommits.setOnClickListener(clickListener)

            val context = view.context

            rvCommits.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            rvCommits.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }

        private fun toggleCommitsVisibility() {
            val isExpanded = reposAdapter.expandedPosition == adapterPosition
            reposAdapter.expandedPosition = if (isExpanded) -1 else adapterPosition
            reposAdapter.notifyItemChanged(reposAdapter.previousExpandedPosition)
            reposAdapter.notifyItemChanged(adapterPosition)
        }
    }
}