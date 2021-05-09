package com.uniovi.justbeer.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
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
                if(image != null){
                    Picasso.get().load(image).resize(400,500).into(binding.beerImageView)
                }
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Crea una nueva vista.
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.beer_card_view, parent, false) as CardView
        // Retorna la vista bajo un objeto que herede de RecyclerView.ViewHolder
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items?.let {
            holder.bindBeer(items[position])
        }
    }


    override fun getItemCount() = items.orEmpty().size

}
