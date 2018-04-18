package com.example.ton.bookstore

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.SearchView
import com.example.ton.bookstore.presenter.BookPresenter
import com.example.ton.bookstore.presenter.BookView
import kotlinx.android.synthetic.main.activity_book.*
import android.os.StrictMode
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.ton.bookstore.model.*

class BookActivity : AppCompatActivity(),BookView,AdapterView.OnItemSelectedListener {



    private lateinit var bookPresenter: BookPresenter
    private lateinit var bookRepo: BookRepository
    private lateinit var searchView : SearchView
    private lateinit var mainBooksList: List<Book>
    private lateinit var tempBookList: List<Book>
    private lateinit var bookAdapter: BookAdapter
    private lateinit var task:TextLoader
    private lateinit var sortByList: List<String>
    private lateinit var spinnerView:Spinner
    private  var sortStyle = 0

    override fun setBookList(books: ArrayList<Book>) {
        mainBooksList = books
        mainBooksList = mainBooksList.sortedWith(compareBy(Book::title))
        bookAdapter = BookAdapter(applicationContext,mainBooksList)
        book_listView.adapter = bookAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_book)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        //Create Sort by catagories
        sortByList = listOf("A-Z","IncPublicYear")

        tempBookList = listOf()

        mainBooksList = ArrayList()

        bookRepo = WebBookRepository()
//        bookRepo = MockBookRepository()
        bookPresenter = BookPresenter(this, bookRepo)
        bookPresenter.start()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)

        val search = menu!!.findItem(R.id.action_search)
        searchView = search.actionView as SearchView

        val spinner = menu.findItem(R.id.spinner)
        spinnerView = spinner.actionView as Spinner
        spinnerView.onItemSelectedListener = this
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,sortByList)
        spinnerView.adapter = adapter


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
    inner class TextLoader(private var text:String): AsyncTask<String, String, List<Book>>(){

        override fun doInBackground(vararg p0: String?): List<Book> {
           val tempBookList:List<Book>

            if(text != ""){
                tempBookList = if(text.contains("[0-9]+".toRegex())){
                    mainBooksList.filter { it -> it.publicationYear.toString().contains(text) }
                }else{
                    mainBooksList.filter { it -> it.title.toLowerCase().contains(text) }
                }
                return  tempBookList
            }
            return mainBooksList
        }

        override  fun onPostExecute(result:List<Book>){
            tempBookList = result
            book_listView.adapter = BookAdapter(applicationContext,sortList(sortStyle,result))
        }

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, index: Int, p3: Long) {
        if(index == 0) sortStyle = 0
        if(index == 1) sortStyle = 1
        if (tempBookList.isEmpty()){
            book_listView.adapter = BookAdapter(applicationContext,sortList(sortStyle,mainBooksList))
        }else{
            book_listView.adapter = BookAdapter(applicationContext,sortList(sortStyle,tempBookList))
        }

    }
    override fun onNothingSelected(p0: AdapterView<*>?) {}

    fun sortList(value:Int,unsortedList:List<Book>):List<Book>{
        if (value == 0 ){
            return unsortedList.sortedWith(compareBy(Book::title))
        }
        return unsortedList.sortedWith(compareBy(Book::publicationYear))

    }





}