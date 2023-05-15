/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/24/23, 12:30 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.common.rows

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ContentItemMedicalMeetingBinding
import com.cbaelectronics.bitacoradefamilia.databinding.ContentItemNotesBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.MedicalMeeting
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.calendarDate
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class MeetingRecyclerViewAdapter(private val context: Context): RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder>() {

    private var dataList = mutableListOf<MedicalMeeting>()

    fun setDataList(data: MutableList<MedicalMeeting>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.content_item_medical_meeting, parent, false)
        val viewHolder = ViewHolder(view)

        // UI
        val binding = viewHolder.binding

        binding.textViewItemMeetingDate.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))
        binding.textViewItemMeetingDoctor.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))
        binding.textViewItemMeetingNotes.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))

        binding.textViewItemMeetingDateOfControl.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemMeetingDoctorName.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemMeetingNotesName.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemMeetingAuthor.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meeting = dataList[position]
        holder.bindView(meeting)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ContentItemMedicalMeetingBinding.bind(itemView)

        fun bindView(meeting: MedicalMeeting){
            binding.textViewItemMeetingDateOfControl.text = meeting.date?.calendarDate()
            binding.textViewItemMeetingDoctorName.text = meeting.doctor
            binding.textViewItemMeetingNotesName.text = meeting.notes
            binding.textViewItemMeetingAuthor.text = meeting.registeredBy.displayName
            Glide.with(context).load(meeting.registeredBy.photoProfile).into(binding.imageViewMeetingAvatar)
        }
    }
}