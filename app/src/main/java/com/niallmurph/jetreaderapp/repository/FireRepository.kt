package com.niallmurph.jetreaderapp.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.niallmurph.jetreaderapp.data.DataOrException
import com.niallmurph.jetreaderapp.models.MBook
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireRepository @Inject constructor(private val queryBook : Query) {

    suspend fun getAllBooksFromDatabase(): DataOrException<List<MBook>,  Boolean,Exception>{
        val dataOrException = DataOrException<List<MBook>, Boolean, Exception>()

        try {

            dataOrException.loading = true
            dataOrException.data = queryBook.get().await().documents.map {docSnap ->
                docSnap.toObject(MBook::class.java)!!
            }
            Log.d("FIRE_SEARCH","FireRepository - Data : ${dataOrException.data.toString()}")
            if(!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false

        } catch (e: FirebaseFirestoreException){
            Log.d("FIRE_SEARCH","FireRepository -Exception : $e")
            dataOrException.e = e
        }

        return dataOrException
    }
}