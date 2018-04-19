package com.example.ton.bookstore

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.MenuItem
import android.widget.TextView
import com.example.ton.bookstore.model.*
import com.example.ton.bookstore.presenter.BookPresenter
import com.example.ton.bookstore.presenter.BookView
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity : AppCompatActivity(),BookView {

    private lateinit var bookPresenter: BookPresenter
    private lateinit var bookRepo: BookRepository
    private lateinit var mainBooksList: List<Book>
    private lateinit var bookAdapter: BookAdapter

    private lateinit var user:User

    private lateinit var  userName:TextView


    override fun setBookList(books: ArrayList<Book>) {
        mainBooksList = books
        bookAdapter = BookAdapter(applicationContext,mainBooksList)
        userBooks_listView.adapter = bookAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        user = User("Ton")
        userName = userTextView
        userName.text = user.name


        mainBooksList = ArrayList()
        bookRepo = MockBookRepository()
        bookPresenter = BookPresenter(this, bookRepo)
        bookPresenter.start()


        if(supportActionBar != null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }
}
