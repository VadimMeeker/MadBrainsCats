package ru.madbrains.cats

import io.realm.RealmObject


data class Cat(val text: String, val id: String){

    val cat = CatDB()

    init{
        cat.text = this.text
        cat.id = this.id
    }

}


open class CatDB : RealmObject() {


    lateinit var text: String
    lateinit var id: String
}
