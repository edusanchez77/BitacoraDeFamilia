/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/23/23, 10:09 AM.
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
import com.cbaelectronics.bitacoradefamilia.databinding.ContentItemWeightBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.ControlWeight
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class WeightRecyclerViewAdapter(private val context: Context): RecyclerView.Adapter<WeightRecyclerViewAdapter.ViewHolder>() {

    private var dataList = mutableListOf<ControlWeight>()

    fun setDataList(data: MutableList<ControlWeight>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.content_item_weight, parent, false)
        val viewHolder = ViewHolder(view)

        // UI
        val binding = viewHolder.binding
        binding.textViewWeightWeek.font(FontSize.BODY, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewWeight.font(FontSize.BODY, FontType.LIGHT, context.getColor(R.color.text))

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weight = dataList[position]
        holder.bindView(weight)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ContentItemWeightBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bindView(weight: ControlWeight){
            binding.textViewWeightWeek.text = weight.week.toString()
            binding.textViewWeight.text = "${weight.weight} kg."
        }
    }

}