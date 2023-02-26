/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/26/23, 5:26 PM.
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
import com.cbaelectronics.bitacoradefamilia.databinding.ContentItemEchographyBinding
import com.cbaelectronics.bitacoradefamilia.databinding.ContentItemIllnessBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Echography
import com.cbaelectronics.bitacoradefamilia.model.domain.Illness
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.calendarDate
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class EchographyRecyclerViewAdapter(private val context: Context): RecyclerView.Adapter<EchographyRecyclerViewAdapter.ViewHolder>() {

    private var dataList = mutableListOf<Echography>()

    fun setDataList(data: MutableList<Echography>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.content_item_echography, parent, false)
        val viewHolder = ViewHolder(view)

        // UI
        val binding = viewHolder.binding

        binding.textViewItemEchographyDate.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))
        binding.textViewItemEchographyWeek.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))
        binding.textViewItemEchographyNotes.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))

        binding.textViewItemEchographyDateOfControl.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemEchographyWeekName.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemEchographyNotesName.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemEchographyAuthor.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val echography = dataList[position]
        holder.bindView(echography)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ContentItemEchographyBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bindView(echography: Echography){
            binding.textViewItemEchographyDateOfControl.text = echography.date?.calendarDate()
            binding.textViewItemEchographyWeekName.text = echography.week.toString()
            binding.textViewItemEchographyNotesName.text = echography.notes
            binding.textViewItemEchographyAuthor.text = echography.registeredBy.displayName
            Glide.with(context).load(echography.registeredBy.photoProfile).into(binding.imageViewEchographyAvatar)
        }
    }

}