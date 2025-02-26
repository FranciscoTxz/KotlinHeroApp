package com.samaxz.heroapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.samaxz.heroapp.databinding.ItemSuperheroBinding
import com.samaxz.heroapp.dataresponse.SuperHeroItemResponse

class SuperHeroViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemSuperheroBinding.bind(view)

    fun bind(superHeroItemResponse: SuperHeroItemResponse) {
        binding.tvSuperHeroName.text = superHeroItemResponse.superHeroName
    }
}