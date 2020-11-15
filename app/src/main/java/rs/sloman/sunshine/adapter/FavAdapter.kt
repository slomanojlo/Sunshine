package rs.sloman.sunshine.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import rs.sloman.sunshine.databinding.FavItemBinding
import rs.sloman.sunshine.model.Favorite

class FavAdapter (private val onClickListener: OnclickListener) :
        ListAdapter<Favorite, FavAdapter.FavViewHolder>(DiffCallback){

    class FavViewHolder(private var binding: FavItemBinding) :
            RecyclerView.ViewHolder(binding.root){
        fun bind (fav : Favorite){
            binding.fav = fav

            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem.city == newItem.city
        }

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem == newItem
        }
}

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val fav = getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(fav)
        }
        holder.bind(fav)
    }


    class OnclickListener(val clickListener: (fav : Favorite) -> Unit){
        fun onClick(fav: Favorite) = clickListener(fav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        return FavViewHolder(FavItemBinding.inflate(LayoutInflater.from(parent.context)))
    }
}