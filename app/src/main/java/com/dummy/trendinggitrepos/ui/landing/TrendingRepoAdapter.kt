package com.dummy.trendinggitrepos.ui.landing

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.dummy.trendinggitrepos.R
import com.dummy.trendinggitrepos.databinding.TrendingRepoBinding
import com.dummy.trendinggitrepos.model.TrendingReposData
import com.dummy.trendinggitrepos.utility.Utils.click
import com.dummy.trendinggitrepos.utility.Utils.hide
import com.dummy.trendinggitrepos.utility.Utils.orDefaultString
import com.dummy.trendinggitrepos.utility.Utils.show
import java.util.*
import kotlin.collections.ArrayList

class TrendingRepoAdapter(private val trendingRepos: ArrayList<TrendingReposData>) :
    RecyclerView.Adapter<TrendingRepoViewHolder>(), Filterable {

    private var filteredList: ArrayList<TrendingReposData>? = null

    init {
        filteredList = ArrayList(trendingRepos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingRepoViewHolder {
        val trendingReposView = DataBindingUtil.inflate<TrendingRepoBinding>(
            LayoutInflater.from(parent.context), R.layout.trending_repo, parent, false
        )
        return TrendingRepoViewHolder(trendingReposView)
    }

    override fun onBindViewHolder(holder: TrendingRepoViewHolder, position: Int) {
        with(trendingRepos[position]) {
            with(holder.trendingRepo) {
                //setting image
                Glide.with(repoImage.context).load(trendingRepoImage).transform(CenterCrop())
                    .error(R.drawable.ic_launcher_foreground)
                    .placeholder(R.drawable.ic_launcher_foreground).into(repoImage)
                //setting name
                repoName.text = trendingRepoName
                //handling selection
                if (isSelected) selected.show() else selected.hide()
                trendingRepoParent.click {
                    if (isSelected) selected.hide() else selected.show()
                    isSelected = !isSelected
                    notifyItemChanged(position)
                }
            }
        }
    }

    override fun getItemCount(): Int = trendingRepos.size

    override fun getFilter(): Filter = trendingReposFilter

    private val trendingReposFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteringList: MutableList<TrendingReposData> = ArrayList()
            if (constraint.isEmpty()) filteringList.addAll(filteredList!!)
            else {
                val filterPattern =
                    constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (trendingRepo in filteredList!!) {
                    if (trendingRepo.trendingRepoName.orDefaultString()
                            .lowercase(Locale.getDefault()).contains(filterPattern)
                    ) {
                        filteringList.add(trendingRepo)
                    }
                }
            }
            return FilterResults().apply {
                values = filteringList
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            trendingRepos.clear()
            trendingRepos.addAll(results.values as Collection<TrendingReposData>)
            notifyDataSetChanged()
        }
    }

}