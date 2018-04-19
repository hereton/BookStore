package com.example.ton.bookstore.model

import android.os.AsyncTask
import org.json.JSONArray
import java.net.URL

/**
 * Created by ton on 30/3/18.
 */
class WebBookRepository : BookRepository(){
    private var stringJson = ""

    override fun getBooks(): ArrayList<Book> {
        if(filteredBookList.isEmpty()) return allBookList
        return filteredBookList
    }

    override fun loadAllBooks() {
        allBookList.clear()
        val task = BookLoader()
        task.execute()

    }

    inner class BookLoader: AsyncTask<String, String, String>(){
        override fun doInBackground(vararg p0: String?): String {
            val url = URL("https://theory.cpe.ku.ac.th/~jittat/courses/sw-spec/ebooks/books.json")
            return url.readText()
        }

        override  fun onPostExecute(result:String){
            stringJson = result
            val jsonArray = JSONArray(stringJson)
            (0..(jsonArray.length() - 1))
                    .map { jsonArray.getJSONObject(it) }
                    .mapTo(allBookList) {
                        Book(it.getInt("id"),
                                it.getString("title"),
                                it.getDouble("price"),
                                it.getInt("pub_year"),
                                it.getString("img_url"))
                    }
            setChanged()
            notifyObservers()
        }

    }

}