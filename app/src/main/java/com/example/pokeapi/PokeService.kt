package com.example.pokeapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeService {

    @GET("pokemon/{pokemon}/")
    fun buscarPOKEMON(@Path("pokemon") pokemon: String) : Call<POKEMON>

}