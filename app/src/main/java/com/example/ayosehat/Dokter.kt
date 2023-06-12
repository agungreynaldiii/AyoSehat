package com.example.ayosehat

class Dokter {
    var name: String? = null
    var email: String? = null
    var uid: String? = null
    var id: String? = null
    var pengalaman: String? = null
    var lulusan: String? = null
    var spesialis: String? = null

    constructor(){}

    constructor(name: String?, email: String?, uid:String?, id:String?, pengalaman: String?, lulusan: String?, spesialis: String?){
        this.name = name
        this.email = email
        this.uid = uid
        this.id = id
        this.pengalaman = pengalaman
        this.lulusan = lulusan
        this.spesialis = spesialis
    }
}