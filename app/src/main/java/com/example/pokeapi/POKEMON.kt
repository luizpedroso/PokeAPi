package com.example.pokeapi

class POKEMON {

    var id: String? = null
    var name: String? = null
    var weight: String? = null
    var height: String? = null

    override fun toString(): String {
        return ("Altura: ${height}m   \nPeso: ${weight}kg")
    }

}
