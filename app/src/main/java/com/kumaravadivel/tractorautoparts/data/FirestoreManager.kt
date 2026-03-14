package com.kumaravadivel.tractorautoparts.data

import com.google.firebase.firestore.FirebaseFirestore
import com.kumaravadivel.tractorautoparts.data.models.AutoPart
import com.kumaravadivel.tractorautoparts.data.models.TractorBrand
import com.kumaravadivel.tractorautoparts.data.models.Dealer
import kotlinx.coroutines.tasks.await

class FirestoreManager {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getTractorBrands(): Result<List<TractorBrand>> {
        return try {
            val snapshot = firestore.collection("tractor_brands").get().await()
            val brands = snapshot.toObjects(TractorBrand::class.java)
            Result.success(brands)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAutoParts(brandId: String? = null): Result<List<AutoPart>> {
        return try {
            val query = if (brandId != null) {
                firestore.collection("auto_parts").whereEqualTo("brandId", brandId)
            } else {
                firestore.collection("auto_parts")
            }
            val snapshot = query.get().await()
            val parts = snapshot.toObjects(AutoPart::class.java)
            Result.success(parts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchAutoParts(query: String): Result<List<AutoPart>> {
        return try {
            val snapshot = firestore.collection("auto_parts")
                .orderBy("name")
                .startAt(query)
                .endAt(query + "\uf8ff")
                .get().await()
            val parts = snapshot.toObjects(AutoPart::class.java)
            Result.success(parts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getDealers(): Result<List<Dealer>> {
        return try {
            val snapshot = firestore.collection("dealers").get().await()
            val dealers = snapshot.toObjects(Dealer::class.java)
            Result.success(dealers)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addAutoPart(part: AutoPart): Result<Unit> {
        return try {
            firestore.collection("auto_parts").add(part).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateAutoPart(partId: String, part: AutoPart): Result<Unit> {
        return try {
            firestore.collection("auto_parts").document(partId).set(part).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteAutoPart(partId: String): Result<Unit> {
        return try {
            firestore.collection("auto_parts").document(partId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
