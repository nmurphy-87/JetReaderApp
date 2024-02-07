package com.niallmurph.jetreaderapp.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.niallmurph.jetreaderapp.network.BooksApi
import com.niallmurph.jetreaderapp.repository.BookRepository
import com.niallmurph.jetreaderapp.repository.FireRepository
import com.niallmurph.jetreaderapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBookRepository(api: BooksApi) = BookRepository(api)

    @Singleton
    @Provides
    fun provideBooksApi() : BooksApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }

    @Singleton
    @Provides
    fun provideFireBookRepository() = FireRepository(queryBook = FirebaseFirestore.getInstance().collection("books"))

}