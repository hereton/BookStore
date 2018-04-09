package com.example.ton.bookstore.model

/**
 * Created by ton on 30/3/18.
 */
class Book(val id:Int = 0,
           val title:String = "",
           val price:Double = 0.0,
           val publicationYear:Int= 0,
           val imageUrl:String = "") {

    override fun toString():String{
        return "Book name: $title" + System.getProperty("line.separator") + "$price baht"

    }
}