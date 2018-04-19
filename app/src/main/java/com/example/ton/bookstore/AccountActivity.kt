package com.example.ton.bookstore

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.ImageButton
import com.example.ton.bookstore.model.*
import com.example.ton.bookstore.presenter.BookPresenter
import com.example.ton.bookstore.presenter.BookView
import kotlinx.android.synthetic.main.activity_account.*
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.EditText
import android.widget.Toast


class AccountActivity : AppCompatActivity(),BookView {

    private lateinit var bookPresenter: BookPresenter
    private lateinit var bookRepo: BookRepository
    private lateinit var mainBooksList: List<Book>
    private lateinit var bookAdapter: BookAdapter

    private lateinit var user:User
    private lateinit var userBalance:Balance

    private lateinit var userName:TextView
    private lateinit var balance: TextView
    private lateinit var addButton:ImageButton


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
        userBalance = Balance(100.0)

        userName = userTextView
        userName.text = user.name

        balance = balanceView
        balance.text = userBalance.getBalace().toString()

        mainBooksList = ArrayList()
        bookRepo = MockBookRepository()
        bookPresenter = BookPresenter(this, bookRepo)
        bookPresenter.start()


        if(supportActionBar != null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_account, menu)

        val addButtonId = menu!!.findItem(R.id.addBalanceId)
        addButton = addButtonId.actionView as ImageButton
        addButton.setImageResource(R.drawable.add)
        addButton.setOnClickListener({

            val alert = AlertDialog.Builder(this)
            alert.setTitle("Title")
            alert.setMessage("How many ?")

            // Set an EditText view to get user input
            val input = EditText(this)
            alert.setView(input)

            alert.setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
                try {
                    userBalance.add(input.text.toString().toDouble())
                    balance.text = userBalance.getBalace().toString()
                }catch (e:NumberFormatException){
                    Toast.makeText(this, "Please input number", Toast.LENGTH_SHORT).show()
                }finally {
                    return@OnClickListener
                }
            })

            alert.setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { _, _ ->
                        // TODO Auto-generated method stub
                        return@OnClickListener
                    })
            alert.show()
        })

        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }
}
