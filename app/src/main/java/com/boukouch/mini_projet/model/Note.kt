package com.boukouch.mini_projet.model

data class Note ( val title:String , val content :String){
    var id :Int=numero++

    companion object{
        var numero = 1
    }

}
