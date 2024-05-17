package com.samuelokello.trashtrack.di

import android.content.Context
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.samuelokello.trashtrack.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn( SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePlacesClient(@ApplicationContext context: Context): PlacesClient {
        // initialize Places client
        Places.initialize(context, BuildConfig.PLACES_API_KEY)

        // create Places client
        return Places.createClient(context)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

}