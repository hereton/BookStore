package com.example.ton.bookstore.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.view.LayoutInflater
import com.example.ton.bookstore.R
import java.io.InputStream
import java.net.URL


/**
 * Created by ton on 12/4/18.
 */
class BookAdapter(
        private var context:Context, private var booksList:List<Book>):BaseAdapter() {

    private class ViewHolder(row: View?){
        var textName = row?.findViewById<TextView>(R.id.name)
        var ivImage = row?.findViewById<ImageView>(R.id.imgView)
        var priceName = row?.findViewById<TextView>(R.id.price)
    }


    override fun getCount(): Int {
       return this.booksList.size
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getItem(p0: Int): Any {
        return this.booksList[p0]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view:View?
            val viewHolder:ViewHolder
        if(convertView == null){
            val layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.lv_item,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        val book = getItem(position) as Book
        viewHolder.textName!!.text =  book.title
        viewHolder.priceName!!.text = book.price.toString()
//        try{
//            val inputStream = URL(book.imageUrl).content as InputStream
//            viewHolder.ivImage!!.setImageDrawable(Drawable.createFromStream(inputStream,"src name"))
//
//        }
//        catch (e : Exception){
//            println("no image url")
//            val inputStream = URL("https://support.appsflyer.com/hc/article_attachments/115011109089/android.png").content as InputStream
//            val d = Drawable.createFromStream(inputStream,"src name") as Drawable
//            viewHolder.ivImage!!.setImageDrawable(d)
//        }
        return view as View
    }


}