package com.uniovi.justbeer.ui.activities.details
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.squareup.picasso.Picasso
import com.uniovi.justbeer.R
import com.uniovi.justbeer.databinding.ActivityDetailsBinding
import com.uniovi.justbeer.databinding.ReviewCardViewBinding
import com.uniovi.justbeer.model.domain.Beer
import com.uniovi.justbeer.model.domain.Review
import com.uniovi.justbeer.model.domain.ReviewList
import com.uniovi.justbeer.ui.dialogs.AddReviewDialog

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private val viewModel: ReviewsViewModel by viewModels()
    private var beerId: Long = 0L

    companion object {
        const val EXTRA_BEER = "DetailBeer::beer"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.beer_details)
        initialize()
        setReviewsRecyclerView()
        binding.addReviewFloatingButton.setOnClickListener { onNewReview() }
    }

    private fun onNewReview() {
        AddReviewDialog.newInstance(beerId)
            .show(supportFragmentManager, AddReviewDialog.TAG)
    }

    private fun setReviewsRecyclerView() {
        val options = FirestoreRecyclerOptions.Builder<Review>()
            .setQuery(ReviewList.requestReviews(beerId), Review::class.java).setLifecycleOwner(this)
            .build()
        val adapter = object : FirestoreRecyclerAdapter<Review, ReviewViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
                val view = LayoutInflater.from(this@DetailsActivity)
                    .inflate(R.layout.review_card_view, parent, false)
                return ReviewViewHolder(view as CardView)
            }

            override fun onBindViewHolder(holder: ReviewViewHolder, position: Int, model: Review) {
                holder.bindReview(model)
            }
        }
        binding.reviewsRecycledView.adapter = adapter
        binding.reviewsRecycledView.layoutManager = LinearLayoutManager(this)
    }

    private fun initialize() {
        intent.getParcelableExtra<Beer>(EXTRA_BEER)?.apply {
            Picasso.get().load(image).resize(200,600).into(binding.beerDetailsImageView)
            binding.nameTextView.text = name
            binding.abvTextView.text = "$alcohol%"
            binding.descriptionTextView.text = description
            beerId = id
        }
    }

    inner class ReviewViewHolder(cardView: CardView) :
        RecyclerView.ViewHolder(cardView) {
        fun bindReview(review: Review) {
            val binding = ReviewCardViewBinding.bind(itemView)
            with(review) {
                binding.usernameTextView.text = username
                binding.reviewDescriptionTextView.text = comment
                binding.ratingBar.rating = score.toFloat()
            }
        }
    }
}