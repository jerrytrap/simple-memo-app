package com.lunchplay.ui.memo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lunchplay.ui.R
import com.lunchplay.ui.databinding.ItemMemoBinding
import com.lunchplay.ui.memo.model.MemoUiModel

class MemoAdapter : ListAdapter<MemoUiModel, RecyclerView.ViewHolder>(diffUtil) {
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemMemoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_memo,
            parent,
            false
        )
        val holder = MemoViewHolder(binding)
        binding.root.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClickListener?.onItemClick(getItem(position))
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MemoViewHolder).bind(getItem(position))
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    class MemoViewHolder(
        private val binding: ItemMemoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(memo: MemoUiModel) {
            binding.memo = memo
        }
    }

    interface OnItemClickListener {
        fun onItemClick(memo: MemoUiModel)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MemoUiModel>() {
            override fun areItemsTheSame(oldItem: MemoUiModel, newItem: MemoUiModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MemoUiModel, newItem: MemoUiModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}