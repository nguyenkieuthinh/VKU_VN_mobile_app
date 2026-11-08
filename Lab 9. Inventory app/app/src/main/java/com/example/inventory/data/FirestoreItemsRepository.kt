package com.example.inventory.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/** ItemsRepository backed by the Cloud Firestore `items` collection. */
class FirestoreItemsRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ItemsRepository {
    private val items = firestore.collection(ITEMS_COLLECTION)

    override fun getAllItemsStream(): Flow<List<Item>> = callbackFlow {
        val registration: ListenerRegistration = items
            .orderBy("name")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val result = snapshot?.documents.orEmpty().mapNotNull { document ->
                    document.toItem()
                }
                trySend(result)
            }

        awaitClose { registration.remove() }
    }

    override fun getItemStream(id: String): Flow<Item> = callbackFlow {
        val registration = items.document(id).addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            snapshot?.toItem()?.let { trySend(it) }
        }

        awaitClose { registration.remove() }
    }

    override suspend fun insertItem(item: Item) {
        val document = items.document()
        document.set(item.copy(id = document.id)).await()
    }

    override suspend fun deleteItem(item: Item) {
        items.document(item.id).delete().await()
    }

    override suspend fun updateItem(item: Item) {
        items.document(item.id).set(item).await()
    }

    private companion object {
        const val ITEMS_COLLECTION = "items"
    }

    private fun com.google.firebase.firestore.DocumentSnapshot.toItem(): Item = Item(
        id = id,
        name = getString("name").orEmpty(),
        price = getDouble("price") ?: 0.0,
        quantity = getLong("quantity")?.toInt() ?: 0
    )
}
