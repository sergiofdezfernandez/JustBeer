package com.uniovi.justbeer.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uniovi.justbeer.R
import com.uniovi.justbeer.databinding.BeerCardViewBinding
import com.uniovi.justbeer.model.domain.Beer

class BeerListAdapter(private val items: List<Beer>?, private val itemClick: (Beer) -> Unit) :
    RecyclerView.Adapter<BeerListAdapter.ViewHolder>() {
    inner class ViewHolder(cardView: CardView, val itemClick: (Beer) -> Unit) :
        RecyclerView.ViewHolder(cardView) {
        fun bindBeer(beer: Beer) {
            val binding = BeerCardViewBinding.bind(itemView)
            with(beer) {
                binding.beerTextView.text = name
                if (image != null) {
                    Picasso.get().load(image).resize(250, 550).into(binding.beerImageView)
                }
                itemView.setOnClickListener { itemClick(this) }
                if(fav == false){
                    binding.favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                } else {
                    binding.favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                }
                binding.favoriteButton.setOnClickListener {
                    if (fav == false) {
                        Toast.makeText(
                            binding.root.context,
                            binding.root.context.getString(R.string.added_beer,name),
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                        fav = true
                    } else {
                        Toast.makeText(
                            binding.root.context,
                            binding.root.context.getString(R.string.deleted_beer,name),
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                        fav = false
                    }


                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.beer_card_view, parent, false) as CardView
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items?.let {
            holder.bindBeer(items[position])
        }
    }


    override fun getItemCount() = items.orEmpty().size

}
