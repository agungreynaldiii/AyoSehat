package com.example.ayosehat

class Dokter {
    var name: String? = null
    var email: String? = null
    var uid: String? = null
    var id: String? = null

    constructor(){}

    constructor(name: String?, email: String?, uid:String?, id:String?){
        this.name = name
        this.email = email
        this.uid = uid
        this.id = id
    }
}