package ankuranurag2.blockemall.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ankuranurag2.blockemall.data.local.ContactData
import ankuranurag2.blockemall.databinding.ItemContactBinding

class ContactAdapter :
    ListAdapter<ContactData, ContactAdapter.ContactVH>(ContactDiffUtilCallBack()) {

    inner class ContactVH(val itemContactBinding: ItemContactBinding) :
        RecyclerView.ViewHolder(itemContactBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactVH {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactVH(binding)
    }

    override fun onBindViewHolder(holder: ContactVH, position: Int) {
        with(holder.itemContactBinding) {
            contact = getItem(position)
            executePendingBindings()
        }
    }
}

private class ContactDiffUtilCallBack : DiffUtil.ItemCallback<ContactData>() {
    override fun areItemsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
        return oldItem.number == newItem.number
    }

    override fun areContentsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
        return oldItem == newItem
    }
}