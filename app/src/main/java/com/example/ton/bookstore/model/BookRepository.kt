package com.example.ton.bookstore.model

import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ton on 30/3/18.
 */
abstract class BookRepository: Observable() {

     val allBookList = ArrayList<Book>()
     var filteredBookList = ArrayList<Book>()

     abstract  fun loadAllBooks()
     abstract fun getBooks(): ArrayList<Book>
     fun filter(text:String){
          if(text != ""){
               filteredBookList = if(text.contains("[0-9]+".toRegex())) {
                   ArrayList(allBookList.filter { it -> it.publicationYear.toString().contains(text) })
               } else {
                    ArrayList(allBookList.filter { it -> it.title.toLowerCase().contains(text) })
               }
               setChanged()
               notifyObservers()
          }
     }

     fun update(){
          setChanged()
          notifyObservers()
     }

}