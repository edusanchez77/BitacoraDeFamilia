/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/7/23, 3:02 PM.
 *  www.cbaelectronics.com.ar
 */

/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/7/23, 11:53 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.provider.services.firebase

import android.net.Uri
import android.provider.ContactsContract.Data
import android.text.style.TtsSpan.DateBuilder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cbaelectronics.bitacoradefamilia.model.domain.*
import com.cbaelectronics.bitacoradefamilia.util.extension.parseFirebase
import com.cbaelectronics.bitacoradefamilia.util.extension.removeFirebaseInvalidCharacters
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

enum class DatabaseField(val key: String) {

    // Schemes
    USERS("users"),
    CHILDREN("children"),
    GROWTH("growth"),
    ILLNESS("illness"),
    PEDIATRIC_CONTROL("pediatric_control"),
    NOTES("notes"),
    ACHIEVEMENTS("achievements"),
    POSIBLE_NAMES("posibleNames"),
    CONTROL_WEIGHT("controlWeight"),
    PREGNANT_INFO("pregnantInformation"),
    MEDICAL_MEETING("medicalMeeting"),
    ECHOGRAPHY("echography"),
    SHARED("shared"),

    // Generic Field
    SETTINGS("settings"),
    REGISTERED_DATE("registerDate"),
    REGISTERED_BY("registeredBy"),
    SHARED_WITH("sharedWith"),
    FIELD_NOTES("notes"),

    // User
    ID("id"),
    DISPLAY_NAME("displayName"),
    EMAIL("email"),
    PROFILE_IMAGE_URL("photoProfile"),
    TOKEN("tokenDevice"),
    TYPE("type"),
    NOTIFICATIONS("notifications"),

    // Children
    CHILDREN_ID("childrenId"),
    NAME("name"),
    GENRE("genre"),
    DATE_OF_BIRTH("dateOfBirth"),
    HOUR_OF_BIRTH("hourOfBirth"),
    WEIGHT("weight"),
    HEIGHT("height"),
    AVATAR("childrenAvatar"),
    PERMISSION("permission"),

    // Growth
    DATE("date"),
    PC("PC"),

    // Illness
    ILLNESS_NAME("illness"),
    SYMPTOM("symptom"),
    DURATION("duration"),
    MEDICATION("medication"),
    OBSERVATIONS("observations"),

    // Pediatric Control
    DOCTOR("doctor"),
    SPECIALTY("specialty"),
    NEXT_CONTROL("nextControl"),

    // Notes
    NOTE_TYPE("type"),

    // Achievements
    ACHIEVEMENT_NAME("achievement"),
    DETAIL("detail"),

    // Control Weight
    WEEK("week"),

    // Info
    WHEN("when"),
    HOW("how"),
    REACTIONS("reactions")

}

object FirebaseDBService {

    // Properties
    private val usersRef = FirebaseFirestore.getInstance().collection(DatabaseField.USERS.key)
    private val childreRef = FirebaseFirestore.getInstance().collection(DatabaseField.CHILDREN.key)
    private val growthRef = FirebaseFirestore.getInstance().collection(DatabaseField.GROWTH.key)
    private val illnessRef = FirebaseFirestore.getInstance().collection(DatabaseField.ILLNESS.key)
    private val controlRef =
        FirebaseFirestore.getInstance().collection(DatabaseField.PEDIATRIC_CONTROL.key)
    private val notesRef = FirebaseFirestore.getInstance().collection(DatabaseField.NOTES.key)
    private val achievementRef =
        FirebaseFirestore.getInstance().collection(DatabaseField.ACHIEVEMENTS.key)
    private val namesRef =
        FirebaseFirestore.getInstance().collection(DatabaseField.POSIBLE_NAMES.key)
    private val controlWeightRef =
        FirebaseFirestore.getInstance().collection(DatabaseField.CONTROL_WEIGHT.key)
    private val pregnantInfoRef =
        FirebaseFirestore.getInstance().collection(DatabaseField.PREGNANT_INFO.key)
    private val medicalMeetingRef =
        FirebaseFirestore.getInstance().collection(DatabaseField.MEDICAL_MEETING.key)
    private val echographyRef =
        FirebaseFirestore.getInstance().collection(DatabaseField.ECHOGRAPHY.key)
    private val sharedRef = FirebaseFirestore.getInstance().collection(DatabaseField.SHARED.key)

    val avatarStorageRef = Firebase.storage.reference.child(DatabaseField.AVATAR.key)

    // Public

    fun save(user: User) {

        user.email?.let { login ->
            usersRef.document(login).set(user.toJSON())
        }
    }

    fun load(): LiveData<MutableList<User>>{
        val mutableList = MutableLiveData<MutableList<User>>()

        usersRef.addSnapshotListener { value, _ ->
            val listData = mutableListOf<User>()

            for (document in value!!) {

                val usrEmail = document.get(DatabaseField.EMAIL.key).toString()
                val usrName = document.get(DatabaseField.DISPLAY_NAME.key).toString()
                val usrPhoto = document.get(DatabaseField.PROFILE_IMAGE_URL.key).toString()
                val usrRegisteredDate = document.getDate(DatabaseField.REGISTERED_DATE.key)
                val usrToken = document.get(DatabaseField.TOKEN.key).toString()
                val usrType = document.get(DatabaseField.TYPE.key).toString().toInt()

                val user =
                    User(usrName, usrEmail, usrPhoto, usrToken, usrType, usrRegisteredDate)


                listData.add(user)
            }

            mutableList.value = listData
        }

        return mutableList
    }

    fun save(children: Children) {

        children.name?.let {
            childreRef.document().set(children.toJSON())
        }

    }

    fun update(children: Children) {
        val name = children.name
        val genre = children.genre
        val date = children.date
        val weight = children.weight
        val height = children.height
        val avatar = children.avatar

        children.id.let { id ->
            childreRef.document(id!!).update(
                DatabaseField.NAME.key, name,
                DatabaseField.GENRE.key, genre,
                DatabaseField.DATE_OF_BIRTH.key, date,
                DatabaseField.WEIGHT.key, weight,
                DatabaseField.HEIGHT.key, height,
                DatabaseField.AVATAR.key, avatar
            )
        }
    }


    suspend fun loadChildren(childrenId: String): DocumentSnapshot? {

        return withContext(Dispatchers.IO) {
            childreRef.document(childrenId)
                .get()
                .await()
        }
    }

    fun loadSharedChildren(childrenId: String, user: User): LiveData<MutableList<User>>{
        val mutableList = MutableLiveData<MutableList<User>>()

        sharedRef
            .whereEqualTo(DatabaseField.CHILDREN_ID.key, childrenId)
            .whereEqualTo(
                "${DatabaseField.REGISTERED_BY.key}.${DatabaseField.EMAIL.key}",
                user?.email
            )
            .addSnapshotListener { value, error ->
                val listData = mutableListOf<User>()

                for (document in value!!) {

                    val sharedWithData =
                        document.data[DatabaseField.SHARED_WITH.key] as Map<String, Any>

                    val usrEmail = sharedWithData[DatabaseField.EMAIL.key].toString()
                    val usrName = sharedWithData[DatabaseField.DISPLAY_NAME.key].toString()
                    val usrPhoto = sharedWithData[DatabaseField.PROFILE_IMAGE_URL.key].toString()
                    val usrRegisteredDate =
                        document.getDate("${DatabaseField.SHARED_WITH.key}.${DatabaseField.REGISTERED_DATE.key}")
                    val usrToken = sharedWithData[DatabaseField.TOKEN.key].toString()
                    val usrType = sharedWithData[DatabaseField.TYPE.key].toString().toInt()
                    val id = document.id

                    val user =
                        User(usrName, usrEmail, usrPhoto, usrToken, usrType, usrRegisteredDate, id)


                    listData.add(user)
                }

                mutableList.value = listData
            }

        return mutableList
    }

    fun deleteSharedChildren(id: String){
        id.let {
            sharedRef.document(id).delete()
        }
    }

    fun load(user: User): LiveData<MutableList<Children>> {
        val mutableData = MutableLiveData<MutableList<Children>>()

        childreRef
            .whereEqualTo(
                "${DatabaseField.REGISTERED_BY.key}.${DatabaseField.EMAIL.key}",
                user?.email
            )
            .orderBy(DatabaseField.REGISTERED_DATE.key, Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                val listData = mutableListOf<Children>()

                for (document in value!!) {

                    val registeredByData =
                        document.data[DatabaseField.REGISTERED_BY.key] as Map<String, Any>
                    val id = document.id
                    val name = document.get(DatabaseField.NAME.key).toString()
                    val genre = document.get(DatabaseField.GENRE.key).toString()
                    val avatar = document.get(DatabaseField.AVATAR.key).toString()
                    val date = document.getDate(DatabaseField.DATE_OF_BIRTH.key)
                    val weight = document.get(DatabaseField.WEIGHT.key).toString()
                    val height = document.get(DatabaseField.HEIGHT.key).toString()
                    val permission = document.getLong(DatabaseField.PERMISSION.key)?.toInt()
                    val registeredDate = document.getDate(DatabaseField.REGISTERED_DATE.key)

                    val usrEmail = registeredByData.get(DatabaseField.EMAIL.key).toString()
                    val usrName = registeredByData.get(DatabaseField.DISPLAY_NAME.key).toString()
                    val usrPhoto =
                        registeredByData.get(DatabaseField.PROFILE_IMAGE_URL.key).toString()
                    val usrRegisteredDate =
                        document.getDate("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.REGISTERED_DATE.key}")
                    val usrToken = registeredByData.get(DatabaseField.TOKEN.key).toString()
                    val usrType = registeredByData.get(DatabaseField.TYPE.key).toString().toInt()

                    val user =
                        User(usrName, usrEmail, usrPhoto, usrToken, usrType, usrRegisteredDate)
                    val children = Children(
                        id = id,
                        name = name,
                        genre = genre,
                        avatar = avatar,
                        date = date,
                        weight = weight,
                        height = height,
                        registeredDate = registeredDate,
                        registeredBy = user,
                        permission = permission
                    )

                    listData.add(children!!)
                }
                mutableData.value = listData
            }

        return mutableData
    }


    fun save(growth: Growth) {

        growth.childrenId.let {
            growthRef.document().set(growth.toJSON())
        }

    }

    fun load(childrenId: String): LiveData<MutableList<Growth>> {

        val mutableData = MutableLiveData<MutableList<Growth>>()

        growthRef
            .whereEqualTo(DatabaseField.CHILDREN_ID.key, childrenId)
            .orderBy(DatabaseField.DATE.key, Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                val listData = mutableListOf<Growth>()

                for (document in value!!) {
                    val registeredByData =
                        document.data[DatabaseField.REGISTERED_BY.key] as Map<String, Any>
                    val date = document.getDate(DatabaseField.DATE.key)
                    val weight = document.get(DatabaseField.WEIGHT.key).toString()
                    val height = document.getLong(DatabaseField.HEIGHT.key)?.toInt()
                    val pc = document.get(DatabaseField.PC.key).toString().toInt()
                    val registeredDate = document.getDate(DatabaseField.REGISTERED_DATE.key)

                    val usrEmail = registeredByData[DatabaseField.EMAIL.key].toString()
                    val usrName = registeredByData[DatabaseField.DISPLAY_NAME.key].toString()
                    val usrPhoto =
                        registeredByData[DatabaseField.PROFILE_IMAGE_URL.key].toString()
                    val usrRegisteredDate =
                        document.getDate("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.REGISTERED_DATE.key}")
                    val usrToken = registeredByData[DatabaseField.TOKEN.key].toString()
                    val usrType = registeredByData[DatabaseField.TYPE.key].toString().toInt()

                    val user =
                        User(usrName, usrEmail, usrPhoto, usrToken, usrType, usrRegisteredDate)
                    val growth = Growth(childrenId, date, weight, height, pc, user, registeredDate)

                    listData.add(growth)
                }
                mutableData.value = listData
            }

        return mutableData
    }

    fun save(illness: Illness) {

        illness.childrenId.let {
            illnessRef.document().set(illness.toJSON())
        }

    }

    fun loadIllness(childrenId: String): LiveData<MutableList<Illness>> {

        val mutableList = MutableLiveData<MutableList<Illness>>()

        illnessRef
            .whereEqualTo(DatabaseField.CHILDREN_ID.key, childrenId)
            .orderBy(DatabaseField.DATE.key)
            .addSnapshotListener { value, error ->

                val listData = mutableListOf<Illness>()

                for (document in value!!) {
                    val registeredByData =
                        document.data[DatabaseField.REGISTERED_BY.key] as Map<String, Any>

                    val date = document.getDate(DatabaseField.DATE.key)
                    val illnessName = document.get(DatabaseField.ILLNESS_NAME.key)
                    val symptom = document.get(DatabaseField.SYMPTOM.key)
                    val duration = document.getLong(DatabaseField.DURATION.key)?.toInt()
                    val medication = document.get(DatabaseField.MEDICATION.key)
                    val observation = document.get(DatabaseField.OBSERVATIONS.key)
                    val registeredDate = document.getDate(DatabaseField.REGISTERED_DATE.key)

                    val usrEmail = registeredByData[DatabaseField.EMAIL.key].toString()
                    val usrName = registeredByData[DatabaseField.DISPLAY_NAME.key].toString()
                    val usrPhoto =
                        registeredByData[DatabaseField.PROFILE_IMAGE_URL.key].toString()
                    val usrRegisteredDate =
                        document.getDate("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.REGISTERED_DATE.key}")
                    val usrToken = registeredByData[DatabaseField.TOKEN.key].toString()
                    val usrType = registeredByData[DatabaseField.TYPE.key].toString().toInt()

                    val user =
                        User(usrName, usrEmail, usrPhoto, usrToken, usrType, usrRegisteredDate)
                    val illness = Illness(
                        childrenId,
                        date,
                        illnessName.toString(),
                        symptom.toString(),
                        duration,
                        medication.toString(),
                        observation.toString(),
                        user,
                        registeredDate
                    )

                    listData.add(illness)
                }

                mutableList.value = listData

            }

        return mutableList

    }

    fun save(control: PediatricControl) {
        control.registeredBy.email.let {
            controlRef.document().set(control.toJSON())
        }
    }

    fun loadControl(childrenId: String): LiveData<MutableList<PediatricControl>> {

        val mutableList = MutableLiveData<MutableList<PediatricControl>>()

        controlRef.whereEqualTo(DatabaseField.CHILDREN_ID.key, childrenId)
            .orderBy(DatabaseField.DATE.key, Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->

                val listData = mutableListOf<PediatricControl>()

                for (document in value!!) {
                    val registeredByData =
                        document.data[DatabaseField.REGISTERED_BY.key] as Map<String, Any>

                    val date = document.getDate(DatabaseField.DATE.key)
                    val doctor = document.get(DatabaseField.DOCTOR.key)
                    val specialty = document.get(DatabaseField.SPECIALTY.key)
                    val weight = document.get(DatabaseField.WEIGHT.key)
                    val height = document.getLong(DatabaseField.HEIGHT.key)
                    val observation = document.get(DatabaseField.OBSERVATIONS.key)
                    val next = document.getDate(DatabaseField.NEXT_CONTROL.key)
                    val notes = document.get(DatabaseField.FIELD_NOTES.key)
                    val registeredDate = document.getDate(DatabaseField.REGISTERED_DATE.key)

                    val usrEmail = registeredByData[DatabaseField.EMAIL.key].toString()
                    val usrName = registeredByData[DatabaseField.DISPLAY_NAME.key].toString()
                    val usrPhoto =
                        registeredByData[DatabaseField.PROFILE_IMAGE_URL.key].toString()
                    val usrRegisteredDate =
                        document.getDate("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.REGISTERED_DATE.key}")
                    val usrToken = registeredByData[DatabaseField.TOKEN.key].toString()
                    val usrType = registeredByData[DatabaseField.TYPE.key].toString().toInt()

                    val user =
                        User(usrName, usrEmail, usrPhoto, usrToken, usrType, usrRegisteredDate)
                    val control = PediatricControl(
                        childrenId,
                        date,
                        doctor.toString(),
                        specialty.toString(),
                        weight.toString(),
                        height?.toInt(),
                        observation.toString(),
                        next,
                        notes.toString(),
                        user,
                        registeredDate
                    )

                    listData.add(control)
                }

                mutableList.value = listData

            }

        return mutableList
    }

    fun save(notes: Notes) {
        notes.childrenId.let {
            notesRef.document().set(notes.toJSON())
        }
    }

    fun load(childrenId: String, type: String): LiveData<MutableList<Notes>> {
        val mutableList = MutableLiveData<MutableList<Notes>>()

        notesRef.whereEqualTo(DatabaseField.CHILDREN_ID.key, childrenId)
            .whereEqualTo(DatabaseField.NOTE_TYPE.key, type)
            .orderBy(DatabaseField.DATE.key, Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->

                val listData = mutableListOf<Notes>()

                for (document in value!!) {
                    val registeredByData =
                        document.data[DatabaseField.REGISTERED_BY.key] as Map<String, Any>

                    val date = document.getDate(DatabaseField.DATE.key)
                    val notes = document.get(DatabaseField.FIELD_NOTES.key)
                    val type = document.get(DatabaseField.NOTE_TYPE.key)
                    val registeredDate = document.getDate(DatabaseField.REGISTERED_DATE.key)

                    val usrEmail = registeredByData[DatabaseField.EMAIL.key].toString()
                    val usrName = registeredByData[DatabaseField.DISPLAY_NAME.key].toString()
                    val usrPhoto =
                        registeredByData[DatabaseField.PROFILE_IMAGE_URL.key].toString()
                    val usrRegisteredDate =
                        document.getDate("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.REGISTERED_DATE.key}")
                    val usrToken = registeredByData[DatabaseField.TOKEN.key].toString()
                    val usrType = registeredByData[DatabaseField.TYPE.key].toString().toInt()

                    val user =
                        User(usrName, usrEmail, usrPhoto, usrToken, usrType, usrRegisteredDate)
                    val note = Notes(
                        childrenId,
                        date,
                        notes.toString(),
                        type.toString(),
                        user,
                        registeredDate
                    )

                    listData.add(note)
                }

                mutableList.value = listData

            }

        return mutableList
    }

    fun save(achievements: Achievements) {
        achievements.childrenId.let {
            achievementRef.document().set(achievements.toJSON())
        }
    }

    fun loadAchievement(childrenId: String): LiveData<MutableList<Achievements>> {
        val mutableList = MutableLiveData<MutableList<Achievements>>()

        achievementRef
            .whereEqualTo(DatabaseField.CHILDREN_ID.key, childrenId)
            .orderBy(DatabaseField.DATE.key, Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->

                val listData = mutableListOf<Achievements>()

                for (document in value!!) {
                    val registeredByData =
                        document.data[DatabaseField.REGISTERED_BY.key] as Map<String, Any>

                    val date = document.getDate(DatabaseField.DATE.key)
                    val achievementName = document.get(DatabaseField.ACHIEVEMENT_NAME.key)
                    val details = document.get(DatabaseField.DETAIL.key)
                    val registeredDate = document.getDate(DatabaseField.REGISTERED_DATE.key)

                    val usrEmail = registeredByData[DatabaseField.EMAIL.key].toString()
                    val usrName = registeredByData[DatabaseField.DISPLAY_NAME.key].toString()
                    val usrPhoto =
                        registeredByData[DatabaseField.PROFILE_IMAGE_URL.key].toString()
                    val usrRegisteredDate =
                        document.getDate("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.REGISTERED_DATE.key}")
                    val usrToken = registeredByData[DatabaseField.TOKEN.key].toString()
                    val usrType = registeredByData[DatabaseField.TYPE.key].toString().toInt()

                    val user =
                        User(usrName, usrEmail, usrPhoto, usrToken, usrType, usrRegisteredDate)
                    val achievement = Achievements(
                        childrenId,
                        date,
                        achievementName.toString(),
                        details.toString(),
                        user,
                        registeredDate
                    )

                    listData.add(achievement)
                }

                mutableList.value = listData

            }

        return mutableList
    }

    public fun save(names: PosibleNames) {
        names.childrenId.let {
            namesRef.document().set(names.toJSON())
        }
    }

    public fun loadNames(childrenId: String, genre: String): LiveData<MutableList<PosibleNames>> {
        val mutableList = MutableLiveData<MutableList<PosibleNames>>()

        namesRef
            .whereEqualTo(DatabaseField.CHILDREN_ID.key, childrenId)
            .whereEqualTo(DatabaseField.GENRE.key, genre)
            .orderBy(DatabaseField.REGISTERED_DATE.key, Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                val listData = mutableListOf<PosibleNames>()

                for (document in value!!) {
                    val registeredByData =
                        document.data[DatabaseField.REGISTERED_BY.key] as Map<String, Any>

                    val name = document.get(DatabaseField.NAME.key)
                    val genre = document.get(DatabaseField.GENRE.key)
                    val registeredDate = document.getDate(DatabaseField.REGISTERED_DATE.key)

                    val usrEmail = registeredByData[DatabaseField.EMAIL.key].toString()
                    val usrName = registeredByData[DatabaseField.DISPLAY_NAME.key].toString()
                    val usrPhoto =
                        registeredByData[DatabaseField.PROFILE_IMAGE_URL.key].toString()
                    val usrRegisteredDate =
                        document.getDate("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.REGISTERED_DATE.key}")
                    val usrToken = registeredByData[DatabaseField.TOKEN.key].toString()
                    val usrType = registeredByData[DatabaseField.TYPE.key].toString().toInt()

                    val user =
                        User(usrName, usrEmail, usrPhoto, usrToken, usrType, usrRegisteredDate)
                    val names = PosibleNames(
                        childrenId,
                        name.toString(),
                        genre.toString(),
                        user,
                        registeredDate
                    )

                    listData.add(names)
                }

                mutableList.value = listData
            }
        return mutableList
    }

    fun save(weight: ControlWeight) {
        weight.childrenId.let {
            controlWeightRef.document().set(weight.toJSON())
        }
    }

    fun loadWeight(childrenId: String): LiveData<MutableList<ControlWeight>> {
        val mutableList = MutableLiveData<MutableList<ControlWeight>>()

        controlWeightRef
            .whereEqualTo(DatabaseField.CHILDREN_ID.key, childrenId)
            .orderBy(DatabaseField.WEEK.key, Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                val listData = mutableListOf<ControlWeight>()

                for (document in value!!) {
                    val registeredByData =
                        document.data[DatabaseField.REGISTERED_BY.key] as Map<String, Any>

                    val week = document.getLong(DatabaseField.WEEK.key)
                    val weight = document.get(DatabaseField.WEIGHT.key)
                    val registeredDate = document.getDate(DatabaseField.REGISTERED_DATE.key)

                    val usrEmail = registeredByData[DatabaseField.EMAIL.key].toString()
                    val usrName = registeredByData[DatabaseField.DISPLAY_NAME.key].toString()
                    val usrPhoto =
                        registeredByData[DatabaseField.PROFILE_IMAGE_URL.key].toString()
                    val usrRegisteredDate =
                        document.getDate("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.REGISTERED_DATE.key}")
                    val usrToken = registeredByData[DatabaseField.TOKEN.key].toString()
                    val usrType = registeredByData[DatabaseField.TYPE.key].toString().toInt()

                    val user =
                        User(usrName, usrEmail, usrPhoto, usrToken, usrType, usrRegisteredDate)
                    val controlWeight = ControlWeight(
                        childrenId,
                        week?.toInt(),
                        weight.toString(),
                        user,
                        registeredDate
                    )

                    listData.add(controlWeight)
                }

                mutableList.value = listData
            }

        return mutableList
    }

    fun save(pregnantInfo: PregnantInfo) {
        val childrenId = pregnantInfo.childrenId

        pregnantInfo.childrenId.let {
            pregnantInfoRef.document(childrenId.toString()).set(pregnantInfo.toJSON())
        }
    }

    suspend fun loadPregnantInformation(childrenId: String): DocumentSnapshot? {

        return withContext(Dispatchers.IO) {
            pregnantInfoRef.document(childrenId)
                .get()
                .await()
        }

    }

    fun loadPregnantInfo(childrenId: String): LiveData<MutableList<PregnantInfo>> {
        val mutableList = MutableLiveData<MutableList<PregnantInfo>>()

        pregnantInfoRef.whereEqualTo(DatabaseField.CHILDREN_ID.key, childrenId)
            .addSnapshotListener { value, error ->
                val listData = mutableListOf<PregnantInfo>()

                for (document in value!!) {
                    val registeredByData =
                        document.data[DatabaseField.REGISTERED_BY.key] as Map<String, Any>

                    val mWhen = document.get(DatabaseField.WHEN.key)
                    val how = document.get(DatabaseField.HOW.key)
                    val reactions = document.get(DatabaseField.REACTIONS.key)
                    val registeredDate = document.getDate(DatabaseField.REGISTERED_DATE.key)

                    val usrEmail = registeredByData[DatabaseField.EMAIL.key].toString()
                    val usrName = registeredByData[DatabaseField.DISPLAY_NAME.key].toString()
                    val usrPhoto =
                        registeredByData[DatabaseField.PROFILE_IMAGE_URL.key].toString()
                    val usrRegisteredDate =
                        document.getDate("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.REGISTERED_DATE.key}")
                    val usrToken = registeredByData[DatabaseField.TOKEN.key].toString()
                    val usrType = registeredByData[DatabaseField.TYPE.key].toString().toInt()

                    val user =
                        User(usrName, usrEmail, usrPhoto, usrToken, usrType, usrRegisteredDate)
                    val info = PregnantInfo(
                        childrenId,
                        mWhen.toString(),
                        how.toString(),
                        reactions.toString(),
                        user,
                        registeredDate
                    )

                    listData.add(info)
                }

                mutableList.value = listData
            }

        return mutableList
    }

    fun save(meeting: MedicalMeeting) {
        meeting.childrenId.let {
            medicalMeetingRef.document().set(meeting.toJSON())
        }
    }

    fun loadMedicalMeeting(childrenId: String): LiveData<MutableList<MedicalMeeting>> {
        val mutableList = MutableLiveData<MutableList<MedicalMeeting>>()

        medicalMeetingRef.whereEqualTo(DatabaseField.CHILDREN_ID.key, childrenId)
            .orderBy(DatabaseField.DATE.key, Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                val listData = mutableListOf<MedicalMeeting>()

                for (document in value!!) {
                    val registeredByData =
                        document.data[DatabaseField.REGISTERED_BY.key] as Map<String, Any>

                    val date = document.getDate(DatabaseField.DATE.key)
                    val doctor = document.get(DatabaseField.DOCTOR.key)
                    val note = document.get(DatabaseField.FIELD_NOTES.key)
                    val registeredDate = document.getDate(DatabaseField.REGISTERED_DATE.key)

                    val usrEmail = registeredByData[DatabaseField.EMAIL.key].toString()
                    val usrName = registeredByData[DatabaseField.DISPLAY_NAME.key].toString()
                    val usrPhoto =
                        registeredByData[DatabaseField.PROFILE_IMAGE_URL.key].toString()
                    val usrRegisteredDate =
                        document.getDate("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.REGISTERED_DATE.key}")
                    val usrToken = registeredByData[DatabaseField.TOKEN.key].toString()
                    val usrType = registeredByData[DatabaseField.TYPE.key].toString().toInt()

                    val user =
                        User(usrName, usrEmail, usrPhoto, usrToken, usrType, usrRegisteredDate)
                    val meeting = MedicalMeeting(
                        childrenId,
                        date,
                        doctor.toString(),
                        note.toString(),
                        user,
                        registeredDate
                    )

                    listData.add(meeting)
                }

                mutableList.value = listData
            }

        return mutableList
    }

    fun save(echography: Echography) {
        echography.childrenId.let {
            echographyRef.document().set(echography.toJSON())
        }
    }

    fun loadEchography(childrenId: String): LiveData<MutableList<Echography>> {
        val mutableList = MutableLiveData<MutableList<Echography>>()

        echographyRef.whereEqualTo(DatabaseField.CHILDREN_ID.key, childrenId)
            .orderBy(DatabaseField.DATE.key, Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                val listData = mutableListOf<Echography>()

                for (document in value!!) {
                    val registeredByData =
                        document.data[DatabaseField.REGISTERED_BY.key] as Map<String, Any>

                    val date = document.getDate(DatabaseField.DATE.key)
                    val week = document.get(DatabaseField.WEEK.key)
                    val note = document.get(DatabaseField.FIELD_NOTES.key)
                    val registeredDate = document.getDate(DatabaseField.REGISTERED_DATE.key)

                    val usrEmail = registeredByData[DatabaseField.EMAIL.key].toString()
                    val usrName = registeredByData[DatabaseField.DISPLAY_NAME.key].toString()
                    val usrPhoto =
                        registeredByData[DatabaseField.PROFILE_IMAGE_URL.key].toString()
                    val usrRegisteredDate =
                        document.getDate("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.REGISTERED_DATE.key}")
                    val usrToken = registeredByData[DatabaseField.TOKEN.key].toString()
                    val usrType = registeredByData[DatabaseField.TYPE.key].toString().toInt()

                    val user =
                        User(usrName, usrEmail, usrPhoto, usrToken, usrType, usrRegisteredDate)
                    val echography = Echography(
                        childrenId,
                        date,
                        week.toString().toInt(),
                        note.toString(),
                        user,
                        registeredDate
                    )

                    listData.add(echography)
                }

                mutableList.value = listData
            }

        return mutableList
    }

    fun save(sharedChildren: SharedChildren) {
        sharedChildren.id.let {
            sharedRef.document().set(sharedChildren.toJSON())
        }
    }

    fun loadShared(email: String): LiveData<MutableList<SharedChildren>> {
        val mutableList = MutableLiveData<MutableList<SharedChildren>>()

        sharedRef
            .whereEqualTo("${DatabaseField.SHARED_WITH.key}.${DatabaseField.EMAIL.key}", email)
            .orderBy(DatabaseField.REGISTERED_DATE.key, Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                val listData = mutableListOf<SharedChildren>()

                for (document in value!!) {

                    val registeredByData = document.data[DatabaseField.REGISTERED_BY.key] as Map<String, Any>
                    val sharedUser = document.data[DatabaseField.SHARED_WITH.key] as Map<String, Any>

                    val id = document.get(DatabaseField.CHILDREN_ID.key).toString()
                    val name = document.get(DatabaseField.NAME.key).toString()
                    val genre = document.get(DatabaseField.GENRE.key).toString()
                    val avatar = document.get(DatabaseField.AVATAR.key).toString()
                    val permission = document.getLong(DatabaseField.PERMISSION.key)?.toInt()
                    val registeredDate = document.getDate(DatabaseField.REGISTERED_DATE.key)

                    val sharedEmail = sharedUser.get(DatabaseField.EMAIL.key).toString()
                    val sharedName = sharedUser.get(DatabaseField.DISPLAY_NAME.key).toString()
                    val sharedPhoto = sharedUser.get(DatabaseField.PROFILE_IMAGE_URL.key).toString()
                    val sharedRegisteredDate = document.getDate("${DatabaseField.SHARED_WITH.key}.${DatabaseField.REGISTERED_DATE.key}")
                    val sharedToken = sharedUser.get(DatabaseField.TOKEN.key).toString()
                    val sharedType = sharedUser.get(DatabaseField.TYPE.key).toString().toInt()

                    val usrEmail = registeredByData.get(DatabaseField.EMAIL.key).toString()
                    val usrName = registeredByData.get(DatabaseField.DISPLAY_NAME.key).toString()
                    val usrPhoto =
                        registeredByData.get(DatabaseField.PROFILE_IMAGE_URL.key).toString()
                    val usrRegisteredDate =
                        document.getDate("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.REGISTERED_DATE.key}")
                    val usrToken = registeredByData.get(DatabaseField.TOKEN.key).toString()
                    val usrType = registeredByData.get(DatabaseField.TYPE.key).toString().toInt()

                    val sharedWith =
                        User(sharedName, sharedEmail, sharedPhoto, sharedToken, sharedType, sharedRegisteredDate)

                    val user =
                        User(usrName, usrEmail, usrPhoto, usrToken, usrType, usrRegisteredDate)
                    val sharedChildren = SharedChildren(
                        id = id,
                        name = name,
                        genre = genre,
                        avatar = avatar,
                        registeredDate = registeredDate,
                        registeredBy = user,
                        user = sharedWith,
                        permission = permission!!.toInt()
                    )

                    listData.add(sharedChildren)
                }

                mutableList.value = listData
            }

        return mutableList
    }



}