package com.example.ton.bookstore.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.util.zip.Inflater

/**
 * Created by ton on 2/4/18.
 */
class BookStoreAdapter(private val context:Context, private  val bookList:ArrayList<Book>) : BaseAdapter() {

    private var books = bookList
    var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var book:Book =
    }

    override fun getItem(p0: Int): Book? {
        if(p0 >= bookList.size)  return null
        return bookList[p0]
    }

    override fun getItemId(p0: Int): Long {

    }

    override fun getCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }


}