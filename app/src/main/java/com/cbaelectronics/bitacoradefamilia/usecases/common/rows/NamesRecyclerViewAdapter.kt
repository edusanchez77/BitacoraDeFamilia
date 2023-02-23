/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/22/23, 12:31 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.common.rows

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ContentItemNamesBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.PosibleNames
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class NamesRecyclerViewAdapter(private val context: Context): RecyclerView.Adapter<NamesRecyclerViewAdapter.ViewHolder>() {

    private var dataList = mutableListOf<PosibleNames>()

    fun setDataList(data: MutableList<PosibleNames>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.content_item_names, parent, false)
        val viewHolder = ViewHolder(view)

        // UI
        val binding = viewHolder.binding

        binding.textViewItemName.font(FontSize.BUTTON, FontType.LIGHT, context.getColor(R.color.light))

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = dataList[position]
        holder.bindView(name)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ContentItemNamesBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bindView(name: PosibleNames){
            binding.textViewItemName.text = name.name
        }
    }

}