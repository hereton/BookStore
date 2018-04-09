package com.example.ton.bookstore

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SearchView
import com.example.ton.bookstore.model.Book
import com.example.ton.bookstore.model.BookRepository
import com.example.ton.bookstore.model.WebBookRepository
import com.example.ton.bookstore.presenter.BookPresenter
import com.example.ton.bookstore.presenter.BookView
import kotlinx.android.synthetic.main.activity_book.*

class BookActivity : AppCompatActivity(),BookView {

    private lateinit var bookPresenter: BookPresenter
    private lateinit var bookRepo: BookRepository
    private lateinit var  adapter : ArrayAdapter<String>
    private lateinit var booksDetail: ArrayList<String>
    private lateinit var searchView : SearchView

    override fun setBookList(books: ArrayList<Book>) {
        books.forEach { book -> booksDetail.add(book.toString())}
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, booksDetail)
        bookListView.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        booksDetail = ArrayList()
        bookRepo = WebBookRepository()
        bookPresenter = BookPresenter(this, bookRepo)
        bookPresenter.start()
        searchView = search_bar
        searchView.queryHint = "Test"
        println(searchView.query)


    }

    fun onLoadButtonClicked(view:View){
        bookPresenter.refresh()
        adapter.clear()

    }


}