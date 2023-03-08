package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.Notes
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.model.domain.UserSettings
import com.cbaelectronics.bitacoradefamilia.model.session.Session
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.FirebaseDBService
import com.cbaelectronics.bitacoradefamilia.util.Constants.TYPE_NOTEBOOK

class NotebookNotesViewModel : ViewModel() {

    // Properties

    var user = Session.instance.user ?: User()
    var settings = Session.instance.user?.settings ?: UserSettings()
    var children = Session.instance.children
    var permission = Session.instance.permission

    // Localization

    val title = R.string.note_title
    val button = R.string.note_button

    // Public

    fun load(): LiveData<MutableList<Notes>>{
        val mutableList = MutableLiveData<MutableList<Notes>>()
        FirebaseDBService.load(children?.id!!, TYPE_NOTEBOOK).observeForever {
            mutableList.value = it
        }
        return mutableList
    }

}