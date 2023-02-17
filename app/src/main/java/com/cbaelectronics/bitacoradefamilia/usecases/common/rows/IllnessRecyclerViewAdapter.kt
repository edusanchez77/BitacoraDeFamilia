/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/17/23, 9:45 AM.
 *  www.cbaelectronics.com.ar
 */

/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/14/23, 1:52 PM.
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
import com.cbaelectronics.bitacoradefamilia.databinding.ContentItemIllnessBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Illness
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.calendarDate
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class IllnessRecyclerViewAdapter(private val context: Context) :
    RecyclerView.Adapter<IllnessRecyclerViewAdapter.ViewHolder>() {

    private var dataList = mutableListOf<Illness>()

    fun setDataList(data: MutableList<Illness>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.content_item_illness, parent, false)
        val viewHolder = ViewHolder(view)

        // UI
        val binding = viewHolder.binding

        binding.textViewItemIllnessDate.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))
        binding.textViewItemIllness.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))
        binding.textViewItemIllnessSymptom.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))
        binding.textViewItemIllnessMedication.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))
        binding.textViewItemIllnessDuration.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))
        binding.textViewItemIllnessObservation.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))

        binding.textViewItemIllnessDateOfIllness.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemIllnessName.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemIllnessSymptomName.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemIllnessMedicationName.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemIllnessDurationName.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemIllnessObservationName.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemIllnessAuthor.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val illness = dataList[position]
        holder.bindView(illness)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ContentItemIllnessBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bindView(illness: Illness) {

            val days = if(illness.duration == 1) R.string.day else R.string.days

            binding.textViewItemIllnessDateOfIllness.text = illness.date?.calendarDate()
            binding.textViewItemIllnessName.text = illness.name
            binding.textViewItemIllnessDurationName.text = "${illness.duration.toString()} ${context.getString(days)}"
            binding.textViewItemIllnessSymptomName.text = illness.symptom
            binding.textViewItemIllnessMedicationName.text = illness.medication
            binding.textViewItemIllnessObservationName.text = if(illness.observation.isNullOrEmpty()) "-" else illness.observation
            binding.textViewItemIllnessAuthor.text = illness.registeredBy.displayName
            Glide.with(context).load(illness.registeredBy.photoProfile).into(binding.imageViewItemIllnessAuthor)
        }
    }

}