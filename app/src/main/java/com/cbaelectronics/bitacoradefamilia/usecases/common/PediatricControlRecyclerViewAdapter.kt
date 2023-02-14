/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/14/23, 4:12 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.common

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ContentItemPediatricControlBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.PediatricControl
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.calendarDate
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class PediatricControlRecyclerViewAdapter(private val context: Context): RecyclerView.Adapter<PediatricControlRecyclerViewAdapter.ViewHolder>() {

    private var dataList = mutableListOf<PediatricControl>()

    fun setDataList(data: MutableList<PediatricControl>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.content_item_pediatric_control, parent, false)
        val viewHolder = ViewHolder(view)

        // UI
        val binding = viewHolder.binding

        binding.textViewItemControlDate.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))
        binding.textViewItemControlDoctor.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))
        binding.textViewItemControlSpecialty.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))
        binding.textViewItemControlWeigth.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))
        binding.textViewItemControlHeigth.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))
        binding.textViewItemControlObservation.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))
        binding.textViewItemControlNextControl.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))
        binding.textViewItemControlNotes.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))

        binding.textViewItemControlDateOfControl.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemControlDoctorName.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemDoctorSpecialtyName.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemControlWeigthName.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemControlHeigthName.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemControlObservationName.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemControlNextControlName.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemControlNotesName.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemControlAuthor.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val control = dataList[position]
        holder.bindView(control)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ContentItemPediatricControlBinding.bind(itemView)


        @SuppressLint("SetTextI18n")
        fun bindView(control: PediatricControl) {

            binding.textViewItemControlDateOfControl.text = control.date?.calendarDate()
            binding.textViewItemControlDoctorName.text = control.doctor
            binding.textViewItemDoctorSpecialtyName.text = control.specialty
            binding.textViewItemControlWeigthName.text = control.weight
            binding.textViewItemControlHeigthName.text = control.height.toString()
            binding.textViewItemControlObservationName.text = control.observation
            binding.textViewItemControlNextControlName.text = control.nextControl?.calendarDate()
            binding.textViewItemControlNotesName.text = control.notes
            binding.textViewItemControlAuthor.text = control.registeredBy.displayName
            Glide.with(context).load(control.registeredBy.photoProfile).into(binding.imageViewControlAvatar)

        }
    }

}