package com.samaxz.heroapp

import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.samaxz.heroapp.DetailSuperHeroActivity.Companion.EXTRA_ID
import com.samaxz.heroapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: SuperHeroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        retrofit = getRetrofit()
        initUI()
    }

    private fun initUI() {
        binding.svHeros.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?) = false
        })
        adapter = SuperHeroAdapter { superHeroId -> navigateToDetail(superHeroId) }
        binding.rvSuperHero.setHasFixedSize(true)
        binding.rvSuperHero.layoutManager = LinearLayoutManager(this)
        binding.rvSuperHero.adapter = adapter
    }

    private fun searchByName(query: String) {
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse = retrofit.create(ApiService::class.java).getSuperHeroes(query)
            if (myResponse.isSuccessful) {
                var response = myResponse.body()
                if (response != null) {
                    Log.i("API_SS", response.toString())
                    if (response.response != "error") {
                        runOnUiThread {
                            adapter.updateList(response.superHeroes)
                            binding.progressBar.isVisible = false
                        }
                    } else {
                        runOnUiThread {
                            adapter.updateList(emptyList())
                            binding.progressBar.isVisible = false
                            showDialog()
                        }
                    }
                }
            } else {
                Log.i("API_SS", "NOT WORK")
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://www.superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun navigateToDetail(id: String) {
        val intent = Intent(this, DetailSuperHeroActivity::class.java)
        intent.putExtra(EXTRA_ID, id)
        startActivity(intent)
    }

    private var isDialogShown = false

    private fun showDialog() {
        if (isDialogShown) return
        Log.d("DialogTest", "showDialog() llamado")
        isDialogShown = true
        var dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_warning)

        val btnTryAgain: Button = dialog.findViewById(R.id.btnTryAgain)

        btnTryAgain.setOnClickListener {
            dialog.dismiss()
            isDialogShown = false
        }
        dialog.setOnDismissListener {
            isDialogShown = false
        }
        dialog.show()
    }
}

// Access T 30ffb09b14f2d45d96f9df1f103d333e
// https://www.superheroapi.com/api/30ffb09b14f2d45d96f9df1f103d333e/search/ironman