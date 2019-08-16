package com.example.pokeapi

class POKEMON {

    var id: String? = null
    var weight: Double? = null
    var height: Double? = null

    override fun toString(): String {
        return ("Altura:  ${height?.div(100)}m\nPeso: ${weight?.div(100)}kg")
    }

}
