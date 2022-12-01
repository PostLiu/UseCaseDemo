package com.postliu.usecasedemo.software

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dylanc.viewbinding.BindingViewHolder
import com.postliu.usecasedemo.data.Software
import com.postliu.usecasedemo.databinding.ItemSoftwareInfoLayoutBinding
import com.postliu.usecasedemo.util.SoftwareUtils
import javax.inject.Inject

typealias OnItemClickListener<T> = (data: T, position: Int) -> Unit
typealias OnItemLongClickListener<T> = (data: T, position: Int) -> Boolean

class SoftwareAdapter @Inject constructor() :
    ListAdapter<Software, BindingViewHolder<ItemSoftwareInfoLayoutBinding>>(SoftwareDiffCallback()) {

    private var onItemClickListener: OnItemClickListener<Software> = { _, _ -> }

    private var onItemLongClickListener: OnItemLongClickListener<Software> = { _, _ -> false }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<ItemSoftwareInfoLayoutBinding> {
        return BindingViewHolder(parent)
    }

    override fun onBindViewHolder(
        holder: BindingViewHolder<ItemSoftwareInfoLayoutBinding>,
        position: Int
    ) {
        val data = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClickListener(data, position)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClickListener(data, position)
        }
        with(holder.binding) {
            val icon = SoftwareUtils.getPackageIcon(root.context, data.packageName)
            itemIcon.setImageDrawable(icon)
            itemLabel.text = data.labelName
            itemVersionName.text = data.versionName
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<Software>) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener<Software>) {
        this.onItemLongClickListener = onItemLongClickListener
    }
}

class SoftwareDiffCallback : DiffUtil.ItemCallback<Software>() {
    override fun areItemsTheSame(oldItem: Software, newItem: Software): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: Software, newItem: Software): Boolean {
        return oldItem == newItem
    }
}