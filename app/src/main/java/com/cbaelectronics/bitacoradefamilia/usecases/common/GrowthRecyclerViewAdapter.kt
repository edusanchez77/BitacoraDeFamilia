/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/14/23, 8:55 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.common

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ContentItemGrowthBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Growth
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.calendarDate
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import com.cbaelectronics.bitacoradefamilia.util.extension.mediumFormat
import com.cbaelectronics.bitacoradefamilia.util.extension.shortFormat

class GrowthRecyclerViewAdapter(private val context: Context): RecyclerView.Adapter<GrowthRecyclerViewAdapter.ViewHolder>() {

    private var dataList = mutableListOf<Growth>()

    fun setDataList(data: MutableList<Growth>){
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.content_item_growth, parent, false)
        val viewHolder = ViewHolder(view)

        // UI
        val binding = viewHolder.binding
        binding.textViewGrowthDate.font(FontSize.BODY, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewGrowthWeight.font(FontSize.BODY, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewGrowthHeight.font(FontSize.BODY, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewGrowthPc.font(FontSize.BODY, FontType.LIGHT, context.getColor(R.color.text))

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val growth = dataList[position]
        holder.bindView(growth)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val binding = ContentItemGrowthBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bindView(growth: Growth){
            binding.textViewGrowthDate.text = growth.date?.calendarDate()
            binding.textViewGrowthWeight.text = "${growth.weight} kg."
            binding.textViewGrowthHeight.text = "${growth.height?.toString()} cm."
            binding.textViewGrowthPc.text = if (growth.pc != 0) "${growth.pc?.toString()} cm." else "-"
        }

    }

}