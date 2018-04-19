package com.example.ton.bookstore

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.SearchView
import com.example.ton.bookstore.presenter.BookPresenter
import com.example.ton.bookstore.presenter.BookView
import kotlinx.android.synthetic.main.activity_book.*
import android.os.StrictMode
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.View
import android.widget.*
import com.example.ton.bookstore.model.*

class BookActivity : AppCompatActivity(),BookView,AdapterView.OnItemSelectedListener {



    private lateinit var bookPresenter: BookPresenter
    private lateinit var bookRepo: BookRepository
    private lateinit var mainBooksList: List<Book>
    private lateinit var bookAdapter: BookAdapter

    private lateinit var searchView : SearchView
    private lateinit var spinnerView:Spinner
    private lateinit var userButton:Button

    private lateinit var sortByList: List<String>
    private  var sortStyle = 0

    override fun setBookList(books: ArrayList<Book>) {
        mainBooksList = books
        bookAdapter = BookAdapter(applicationContext,sortList(sortStyle,mainBooksList))
        book_listView.adapter = bookAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_book)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        //Create Sort by catagories
        sortByList = listOf("A-Z","IncPublicYear")

        mainBooksList = ArrayList()

        bookRepo = WebBookRepository()
//      bookRepo = MockBookRepository()
        bookPresenter = BookPresenter(this, bookRepo)
        bookPresenter.start()

        book_listView.setOnItemClickListener { parent, view, position, id ->
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Title")
            alert.setMessage("Do you want to add " + mainBooksList[position].title + " to cart ?")

            alert.setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                println("Yes")
                    return@OnClickListener

            })

            alert.setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { _, _ ->
                        // TODO Auto-generated method stub
                        return@OnClickListener
                    })
            alert.show()
        }

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


        searchView.queryHint = "Search"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                bookPresenter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
        })

        userButton = userButtonView
        userButton.setOnClickListener({
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        })
        return true
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, index: Int, p3: Long) {

        if(index == 0) sortStyle = 0
        if(index == 1) sortStyle = 1
        bookPresenter.update()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    private fun sortList(value:Int,unsortedList:List<Book>):List<Book>{
        if (value == 0 ){
            return unsortedList.sortedWith(compareBy(Book::title))
        }
        return unsortedList.sortedWith(compareBy(Book::publicationYear))
    }
}