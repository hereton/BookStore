package com.example.ton.bookstore

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import com.example.ton.bookstore.presenter.BookPresenter
import com.example.ton.bookstore.presenter.BookView
import kotlinx.android.synthetic.main.activity_book.*
import android.os.StrictMode
import android.view.Menu
import android.widget.BaseAdapter
import com.example.ton.bookstore.model.*


class BookActivity : AppCompatActivity(),BookView {

    private lateinit var bookPresenter: BookPresenter
    private lateinit var bookRepo: BookRepository
    private lateinit var searchView : SearchView
    private lateinit var mainBooksList: List<Book>
    private lateinit var bookAdapter: BookAdapter
    private lateinit var task:TextLoader

    override fun setBookList(books: ArrayList<Book>) {
        mainBooksList = books
        bookAdapter = BookAdapter(applicationContext,mainBooksList)
        book_listView.adapter = bookAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


        mainBooksList = ArrayList()

        bookRepo = WebBookRepository()
//        bookRepo = MockBookRepository()
        bookPresenter = BookPresenter(this, bookRepo)
        bookPresenter.start()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        searchView = search_bar
        searchView.queryHint = "Search View Hint"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                task = TextLoader(newText)
                task.execute()
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

        })

        return true
    }


    @SuppressLint("StaticFieldLeak")
    inner class TextLoader(private var text:String): AsyncTask<String, String, BaseAdapter>(){

        override fun doInBackground(vararg p0: String?): BaseAdapter {
           val tempBookList:List<Book>

            if(text != ""){
                tempBookList = if(text.contains("[0-9]+".toRegex())){
                    mainBooksList.filter { it -> it.publicationYear.toString().contains(text) }
                }else{
                    mainBooksList.filter { it -> it.title.toLowerCase().contains(text) }
                }
                return  BookAdapter(applicationContext,tempBookList)
            }
            return BookAdapter(applicationContext,mainBooksList)
        }

        override  fun onPostExecute(result:BaseAdapter){
            book_listView.adapter = result
        }

    }






}