package com.example.ton.bookstore.presenter

import com.example.ton.bookstore.model.Book
import com.example.ton.bookstore.model.BookRepository
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ton on 30/3/18.
 */
class BookPresenter(private val view:BookView,
                    private val repository: BookRepository): Observer {


    fun start(){
        repository.addObserver(this)
        repository.loadAllBooks()
    }
    override fun update(obj: Observable?, p1: Any?) {
        if(obj == repository) {
            view.setBookList(repository.getBooks())
        }
    }

    fun refresh(){
        repository.loadAllBooks()
    }


}