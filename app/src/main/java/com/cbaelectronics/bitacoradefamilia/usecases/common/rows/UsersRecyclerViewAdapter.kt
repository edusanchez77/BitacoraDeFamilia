/**
 *  Created by CbaElectronics by Eduardo Sanchez on 3/16/23, 2:29 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.common.rows

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ContentItemSharedWithBinding
import com.cbaelectronics.bitacoradefamilia.databinding.ContentItemUsersBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class UsersRecyclerViewAdapter(private val context: Context, private val itemClickListener: UsersRecyclerViewAdapter.onClickUserListener): RecyclerView.Adapter<UsersRecyclerViewAdapter.ViewHolder>(), Filterable {

    interface onClickUserListener{
        fun onItemClick(user: User)
    }

    private var dataList = mutableListOf<User>()
    private var dataListFilter = mutableListOf<User>()

    fun setDataList(data: MutableList<User>) {
        dataList = data
        dataListFilter = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.content_item_users, parent, false)
        val viewHolder = ViewHolder(view)

        // UI
        val binding = viewHolder.binding

        binding.textViewUserName.font(FontSize.SUBHEAD, FontType.REGULAR, ContextCompat.getColor(context, R.color.text))
        binding.textViewUserEmail.font(FontSize.BUTTON, FontType.LIGHT, ContextCompat.getColor(context, R.color.text))

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = dataList[position]
        holder.bindView(user)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ContentItemUsersBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bindView(user: User){

            Glide.with(context).load(user.photoProfile).into(binding.imageViewUserAvatar)
            binding.textViewUserName.text = user.displayName
            binding.textViewUserEmail.text = user.email

            itemView.setOnClickListener {
                itemClickListener.onItemClick(user)
            }

        }
    }

    override fun getFilter(): Filter {

        val filter = object : Filter(){

            override fun performFiltering(CharSequence: CharSequence?): FilterResults {
                var filterResults = FilterResults()

                if(CharSequence == null || CharSequence.isEmpty()){
                    filterResults.count = dataListFilter.size
                    filterResults.values = dataListFilter
                }else{
                    val searchChr = CharSequence.toString().lowercase()
                    var resultData = mutableListOf<User>()

                    for(i in dataList){
                        if(i.displayName!!.lowercase().contains(searchChr)){
                            resultData.add(i)
                        }
                    }
                    filterResults.count = resultData.size
                    filterResults.values = resultData
                }

                return filterResults

            }

            override fun publishResults(CharSequence: CharSequence?, FilterResults: FilterResults?) {

                dataList = FilterResults!!.values as MutableList<User>
                notifyDataSetChanged()

            }
        }

        return filter

    }

}