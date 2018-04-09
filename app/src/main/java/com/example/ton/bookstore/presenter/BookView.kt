package com.example.ton.bookstore.presenter

import com.example.ton.bookstore.model.Book

/**
 * Created by ton on 30/3/18.
 */
interface BookView {
    fun setBookList(books:ArrayList<Book>)
}