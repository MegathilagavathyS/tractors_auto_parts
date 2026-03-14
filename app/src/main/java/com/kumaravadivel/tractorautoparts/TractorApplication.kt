package com.kumaravadivel.tractorautoparts

import android.app.Application
import com.google.firebase.FirebaseApp

class TractorApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Firebase
        try {
            FirebaseApp.initializeApp(this)
            println("Firebase initialized successfully")
        } catch (e: Exception) {
            println("Firebase initialization failed: ${e.message}")
        }
    }
}
