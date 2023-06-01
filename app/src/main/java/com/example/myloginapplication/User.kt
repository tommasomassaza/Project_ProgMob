package com.example.myloginapplication

class User(
    var name: String = "",
    var surname: String = "",
    var email: String = "",
    var password: String = ""
) {
    // Altre funzioni e metodi della classe User
    companion object {
        var id_user : Int? = 0
        var currentUser: User? = null
    }
}
