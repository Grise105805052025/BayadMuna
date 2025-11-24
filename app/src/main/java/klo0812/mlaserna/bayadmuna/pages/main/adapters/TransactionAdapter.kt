package klo0812.mlaserna.bayadmuna.pages.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import klo0812.mlaserna.bayadmuna.databinding.ItemTransactionBinding
import klo0812.mlaserna.bayadmuna.pages.main.models.TransactionItemViewModel

class TransactionAdapter(private val items: List<TransactionItemViewModel>) :
    RecyclerView.Adapter<TransactionAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    class ItemViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(item: TransactionItemViewModel) {
            binding.itemViewModel = item
            binding.executePendingBindings()
        }
    }

}