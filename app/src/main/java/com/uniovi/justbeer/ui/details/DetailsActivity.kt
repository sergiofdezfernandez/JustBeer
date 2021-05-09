package com.uniovi.justbeer.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import com.uniovi.justbeer.databinding.ActivityDetailsBinding
import com.uniovi.justbeer.model.domain.Beer

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    companion object{
        const val EXTRA_BEER = "DetailBeer::beer"
        const val TITLE = "Beer Details"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = TITLE
        initialize()
    }
    private fun initialize(){
        intent.getParcelableExtra<Beer>(EXTRA_BEER)?.apply {
            Picasso.get().load(image).into(binding.beerDetailsImageView)
            binding.nameTextView.text = name
        }
    }
}