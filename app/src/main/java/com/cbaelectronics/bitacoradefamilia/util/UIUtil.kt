/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/9/23, 12:50 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.util

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.cbaelectronics.bitacoradefamilia.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope

object UIUtil {

    fun showAlert(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(view: View, message: String){
        Snackbar.make(view, message,
            Snackbar.LENGTH_LONG).setAction(null, null).show()
    }

    // Alert

    fun showAlert(context: Context, title: String? = null, message: String, positive: String, positiveAction: (() -> Unit)? = null, negative: String? = null) {

        val builder = AlertDialog.Builder(context, R.style.CustomDialogTheme)
        title.let {
            builder.setTitle(it)
        }
        builder.setMessage(message)
        builder.setPositiveButton(positive) { _, _ ->
            positiveAction?.let {
                it()
            }
        }
        negative?.let {
            builder.setNegativeButton(it) { _, _ ->
                // Do nothing
            }
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.light))
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.dark))
    }

}