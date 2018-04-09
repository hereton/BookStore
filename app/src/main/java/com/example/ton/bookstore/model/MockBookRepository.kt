package com.example.ton.bookstore.model

/**
 * Created by ton on 30/3/18.
 */
class MockBookRepository : BookRepository(){

    private val bookList = ArrayList<Book>()
    override fun getBooks(): ArrayList<Book> {
        return bookList
    }

    override fun loadAllBooks() {
        bookList.clear()
        bookList.add(Book(1,"How to win election",500.0))
        bookList.add(Book(2,"How to win elephant",500.0))
        bookList.add(Book(5,"How to win pink panther",500.0))
        setChanged()
        notifyObservers()

    }

}