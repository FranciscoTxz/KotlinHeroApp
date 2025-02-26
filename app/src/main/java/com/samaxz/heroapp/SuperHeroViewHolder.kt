package com.samaxz.heroapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.samaxz.heroapp.databinding.ItemSuperheroBinding
import com.samaxz.heroapp.dataresponse.SuperHeroItemResponse
import com.squareup.picasso.Picasso

class SuperHeroViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemSuperheroBinding.bind(view)

    fun bind(superHeroItemResponse: SuperHeroItemResponse, onItemSelected: (String) -> Unit) {
        binding.tvSuperHeroName.text = superHeroItemResponse.superHeroName
        Picasso.get().load(superHeroItemResponse.image.url).into(binding.ivSuperHero);
        binding.root.setOnClickListener { onItemSelected(superHeroItemResponse.superHeroID) }
    }
}