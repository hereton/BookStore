package com.example.ton.bookstore.model

import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ton on 30/3/18.
 */
abstract class BookRepository: Observable() {

     abstract  fun loadAllBooks()
     abstract fun getBooks(): ArrayList<Book>

}