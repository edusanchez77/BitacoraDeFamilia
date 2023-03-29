/**
 *  Created by CbaElectronics by Eduardo Sanchez on 3/29/23, 11:02 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.common.rows

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ContentItemSharedWithBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.User

class SharedWithRecyclerViewAdapter(private val context: Context, private val itemClickListener: UsersRecyclerViewAdapter.onClickUserListener): RecyclerView.Adapter<SharedWithRecyclerViewAdapter.ViewHolder>() {

    interface onClickUserListener{
        fun onItemClick(user: User)
    }

    private var dataList = mutableListOf<User>()

    fun setDataList(data: MutableList<User>) {
        dataList = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SharedWithRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.content_item_shared_with, parent, false)
        val viewHolder = ViewHolder(view)

        // UI
        val binding = viewHolder.binding

        return viewHolder
    }

    override fun onBindViewHolder(holder: SharedWithRecyclerViewAdapter.ViewHolder, position: Int) {
        val user = dataList[position]
        holder.bindView(user)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ContentItemSharedWithBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bindView(user: User){

            Glide.with(context).load(user.photoProfile).into(binding.imageViewSharedWithAvatar)
            binding.buttonSharedWithDelete.setOnClickListener {
                itemClickListener.onItemClick(user)
            }

        }
    }
}