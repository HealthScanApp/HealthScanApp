package com.capstone.healthscanapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.databinding.CardHistoryBinding
import com.capstone.healthscanapp.model.RiwayatMakanan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

class RiwayatMakananAdapter(private val context: Context, private var list: MutableList<RiwayatMakanan>) :
    RecyclerView.Adapter<RiwayatMakananAdapter.RiwayatViewHolder>() {

    private val clickThreshold = 3
    private var clickCount = 0
    private var lastClickedPosition = -1

    inner class RiwayatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: CardHistoryBinding = CardHistoryBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiwayatViewHolder {
        return RiwayatViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_history, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RiwayatViewHolder, position: Int) {
        val riwayatMakanan = list[position]
        holder.binding.nutrions.text = riwayatMakanan.nutrition
        holder.binding.nameFood.text = riwayatMakanan.label

        val dateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())
        val formattedDate = dateFormat.format(riwayatMakanan.timestamp)
        holder.binding.tanggal.text = formattedDate

        holder.itemView.setOnClickListener {
            onItemClicked(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun onItemClicked(position: Int) {
        if (position == lastClickedPosition) {
            clickCount++
        } else {
            clickCount = 1
            Toast.makeText(context, "Tekan 2 Kali Lagi Untuk Menghapus Data!", Toast.LENGTH_SHORT).show()
        }

        if (clickCount == clickThreshold) {
            deleteItem(position)
            clickCount = 0
        } else {
            Toast.makeText(context, "Tekan 1 Kali Lagi Untuk Menghapus Data!", Toast.LENGTH_SHORT).show()
            lastClickedPosition = position
        }
    }

    private fun deleteItem(position: Int) {
        if (position < list.size) {
            val deletedItem = list.removeAt(position)
            notifyItemRemoved(position)

            // Remove the document from Firestore
            val currentUserID = FirebaseAuth.getInstance().currentUser?.uid
            if (currentUserID != null) {
                FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(currentUserID)
                    .collection("riwayat_konsumsi")
                    .whereEqualTo("timestamp", deletedItem.timestamp) // Assuming timestamp is unique
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            document.reference.delete()
                                .addOnSuccessListener {
                                    // Document successfully deleted from Firestore
                                    Toast.makeText(context, "Data dihapus di Firestore", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener { e ->
                                    // Handle failure to delete document from Firestore
                                    Toast.makeText(context, "Gagal menghapus data di Firestore", Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
                    .addOnFailureListener { e ->
                        // Handle failure to fetch data from Firestore
                    }
            }
        }
    }

    fun sortDataByDescending() {
        list.sortByDescending { it.timestamp }
        notifyDataSetChanged()
    }
}
