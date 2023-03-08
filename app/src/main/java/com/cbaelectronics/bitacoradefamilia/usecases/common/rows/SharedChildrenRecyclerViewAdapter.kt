/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/17/23, 9:45 AM.
 *  www.cbaelectronics.com.ar
 */

/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/9/23, 4:22 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.common.rows

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ContentItemChildrenBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Children
import com.cbaelectronics.bitacoradefamilia.model.domain.Genre
import com.cbaelectronics.bitacoradefamilia.model.domain.SharedChildren
import com.cbaelectronics.bitacoradefamilia.usecases.menu.MenuRouter
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class SharedChildrenRecyclerViewAdapter(private val context: Context): RecyclerView.Adapter<SharedChildrenRecyclerViewAdapter.ViewHolder>() {

    private var dataList = mutableListOf<SharedChildren>()

    fun setDataList(data: MutableList<SharedChildren>){
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.content_item_children, parent, false)
        val viewHolder = ViewHolder(view)

        // UI
        val binding = viewHolder.binding

        binding.textViewChildren.font(FontSize.HEAD, FontType.GALADA, context.getColor(R.color.text))

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val children = dataList[position]
        holder.bindView(children)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val binding = ContentItemChildrenBinding.bind(itemView)

        fun bindView(children: SharedChildren){
            val colorBackground = when(children.genre){
                Genre.BOY.type -> R.color.boy
                Genre.WOMAN.type -> R.color.woman
                Genre.INDETERMINATE.type -> R.color.indeterminate
                else -> R.color.indeterminate
            }

            // UI
            binding.cardViewChildren.setCardBackgroundColor(context.getColor(colorBackground))

            binding.textViewChildren.text = children.name

            // Click

            itemView.setOnClickListener {
                Log.d("LogEdu", children.toString())
                MenuRouter().launch(context, children.id.toString())
            }
        }

    }

}