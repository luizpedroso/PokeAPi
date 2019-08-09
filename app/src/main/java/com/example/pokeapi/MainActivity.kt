package com.example.pokeapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Url
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.os.AsyncTask
import android.widget.*
import java.net.URL


class MainActivity : AppCompatActivity() {

    var URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pokemon = findViewById<AutoCompleteTextView>(R.id.etMain_pokemon)
        val resposta = findViewById<TextView>(R.id.etMain_resposta)
        val ivpokemon = findViewById<ImageView>(R.id.ivMain_pokemon)

        val namePokemonArray = arrayOf(
            "bulbasaur","squirtle","charmander","pikachu"
        )

        val adapter = ArrayAdapter<String>(
            this, // Context
            android.R.layout.simple_dropdown_item_1line, // Layout
            namePokemonArray // Array
        )

        pokemon.setAdapter(adapter)

        pokemon.setOnDismissListener {
            val valor = pokemon.text.toString()
            val call: Call<POKEMON> = RetrofitInitializer().POKEService().buscarPOKEMON(valor)
            call.enqueue(object: Callback<POKEMON> {
                override fun onResponse(call: Call<POKEMON>?, response: Response<POKEMON>?) {
                    val poke = response!!.body()
                    if(poke != null){
                        resposta.text = poke.toString()
                        val URLFull = "$URL${poke.id}.png"
                        DownLoadImageTask(ivpokemon).execute(URLFull)
                    }else{
                        resposta.text = "Pokemon não encontrado!"
                        ivpokemon.setImageBitmap(null)
                    }
                }
                override fun onFailure(call: Call<POKEMON>?, t: Throwable?) {
                    Log.e("onFailure error", t?.message)
                }
            })
        }
    }

    private class DownLoadImageTask(internal val imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        override fun doInBackground(vararg urls: String): Bitmap? {
            val urlOfImage = urls[0]
            return try {
                val inputStream = URL(urlOfImage).openStream()
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) { // Catch the download exception
                e.printStackTrace()
                null
            }
        }
        override fun onPostExecute(result: Bitmap?) {
            if(result!=null){
                // Display the downloaded image into image view
                //Toast.makeText(imageView.context,"download success",Toast.LENGTH_SHORT).show()
                imageView.setImageBitmap(result)
            }else{
                Toast.makeText(imageView.context,"Error downloading",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
