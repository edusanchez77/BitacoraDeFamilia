/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/16/23, 11:43 AM.
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
import com.cbaelectronics.bitacoradefamilia.databinding.ContentItemAchievementBinding
import com.cbaelectronics.bitacoradefamilia.databinding.ContentItemIllnessBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Achievements
import com.cbaelectronics.bitacoradefamilia.model.domain.Illness
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil.showAlert
import com.cbaelectronics.bitacoradefamilia.util.extension.calendarDate
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class AchievementRecyclerViewAdapter(private val context: Context): RecyclerView.Adapter<AchievementRecyclerViewAdapter.ViewHolder>() {

    private var dataList = mutableListOf<Achievements>()

    fun setDataList(data: MutableList<Achievements>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.content_item_achievement, parent, false)
        val viewHolder = ViewHolder(view)

        // UI
        val binding = viewHolder.binding

        binding.textViewItemAchievement.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))
        binding.textViewItemAchievementDate.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))
        binding.textViewAchievementDetails.font(FontSize.BUTTON, FontType.REGULAR, context.getColor(R.color.text))

        binding.textViewItemAchievementName.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemAchievementDateName.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewAchievementDetailsName.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))
        binding.textViewItemAchievementAuthor.font(FontSize.CAPTION, FontType.LIGHT, context.getColor(R.color.text))

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val achievement = dataList[position]
        holder.bindView(achievement)

        holder.binding.cardView.setOnClickListener {
            isAnyItemExpanded(position)
            achievement.isExpandable = !achievement.isExpandable
            notifyItemChanged(position, Unit)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ContentItemAchievementBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bindView(achievements: Achievements){
            binding.textViewItemAchievementName.text = achievements.achievement
            binding.textViewItemAchievementDateName.text = achievements.date?.calendarDate()
            binding.textViewAchievementDetailsName.text = achievements.detail
            binding.textViewItemAchievementAuthor.text = achievements.registeredBy.displayName
            Glide.with(context).load(achievements.registeredBy.photoProfile).into(binding.imageViewAchievementAuthor)

            binding.constraintLayoutAchievementsDetails.visibility = if(achievements.isExpandable) View.VISIBLE else View.GONE
        }

        fun collapseExpandedView(){
            binding.constraintLayoutAchievementsDetails.visibility = View.GONE
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty() && payloads[0] == 0){
            holder.collapseExpandedView()
        }else{
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    private fun isAnyItemExpanded(position: Int){
        val temp = dataList.indexOfFirst {
            it.isExpandable
        }

        if(temp >= 0 && temp != position){
            dataList[temp].isExpandable = false
            notifyItemChanged(temp, 0)
        }
    }

}