/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/15/23, 12:43 PM.
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
import com.cbaelectronics.bitacoradefamilia.databinding.ContentItemNotesBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Notes
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.calendarDate
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class NotesRecyclerViewAdapter(private val context: Context): RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder>() {

    private var dataList = mutableListOf<Notes>()

    fun setDataList(data: MutableList<Notes>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.content_item_notes, parent, false)
        val viewHolder = ViewHolder(view)

        // UI
        val binding = viewHolder.binding

        binding.textViewItemControlDate.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))
        binding.textViewItemNotes.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))

        binding.textViewItemNoteDate.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemNotesName.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemNotesAuthor.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notes = dataList[position]
        holder.bindView(notes)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

       val binding = ContentItemNotesBinding.bind(itemView)

        fun bindView(note: Notes){
            binding.textViewItemNoteDate.text = note.date?.calendarDate()
            binding.textViewItemNotesName.text = note.notes
            binding.textViewItemNotesAuthor.text = note.registeredBy.displayName
            Glide.with(context).load(note.registeredBy.photoProfile).into(binding.imageViewNoteAuthor)
        }
    }

}