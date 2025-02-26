package com.samaxz.heroapp

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.samaxz.heroapp.databinding.ActivityDetailSuperHeroBinding
import com.samaxz.heroapp.dataresponse.PowerStatsResponse
import com.samaxz.heroapp.dataresponse.SuperHeroDetailResponse
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class DetailSuperHeroActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityDetailSuperHeroBinding
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSuperHeroBinding.inflate(layoutInflater)
        retrofit = getRetrofit()
        setContentView(binding.root)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        val id: String = intent.getStringExtra(EXTRA_ID).orEmpty()
        getSuperHeroInformation(id)
    }

    private fun getSuperHeroInformation(id: String) {
        binding.progressBarDetail.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            var superHeroDetail: Response<SuperHeroDetailResponse> =
                retrofit.create(ApiService::class.java).getSuperHeroInformation(id)
            if (superHeroDetail.body() != null) {
                runOnUiThread {
                    createUI(superHeroDetail.body()!!)
                    binding.progressBarDetail.isVisible = false
                }
            } else {
                Log.i("API_SS", "Error")
            }
        }
    }

    private fun createUI(superHero: SuperHeroDetailResponse) {
        Picasso.get().load(superHero.image.url).into(binding.ivSuperHero2)
        binding.tvSuperHeroNameDetail.text = superHero.name
        binding.tvSuperHeroRealName.text = superHero.biography.full_name
        binding.tvSuperHeroPublisher.text = superHero.biography.publisher
        binding.tvSuperHeroAlterEgos.text = superHero.biography.alter_egos
        binding.tvSuperHeroFirstAppearance.text = superHero.biography.first_appearance
        binding.tvSuperHeroPlaceOfBirth.text = superHero.biography.place_of_birth
        binding.tvOccupation.text = superHero.work.occupation
        binding.tvSuperHeroBase.text = superHero.work.base
        prepareStats(superHero.powerstats)
    }

    private fun prepareStats(powerstats: PowerStatsResponse) {
        updateHeight(binding.vIntelligence, powerstats.intelligence)
        updateHeight(binding.vSpeed, powerstats.speed)
        updateHeight(binding.vPower, powerstats.power)
        updateHeight(binding.vCombat, powerstats.combat)
        updateHeight(binding.vStrength, powerstats.speed)
        updateHeight(binding.vDurability, powerstats.durability)
    }

    private fun updateHeight(view: View, stat: String) {
        val params = view.layoutParams
        params.height = pxToDp(stat.toFloat())
        view.layoutParams = params
    }

    private fun pxToDp(px: Float): Int {
        val x = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics) * 1.5
        return x.roundToInt()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://www.superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}