package com.example.ton.bookstore.model

class Balance(var balance: Double) {

    fun add(value:Double){
        balance += value
    }
    fun getBalace():Double{
        return balance
    }
}