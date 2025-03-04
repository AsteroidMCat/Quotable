package com.zybooks.quotable.ui.theme

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class QuotesListViewModel: ViewModel() {
    val quoteList = mutableStateListOf<Quote>()
    private val favoriteQuotes = mutableListOf<Quote>()

    fun addQuote(body:String, author:String){
        quoteList.add(Quote(body = body, author=author))
    }

    fun removeQuote(quote:Quote){
        quoteList.remove(quote)
    }

    fun deleteAllTasks(){
        quoteList.clear()
    }

    val favoriteQuotesExists: Boolean
        get()= favoriteQuotes.isNotEmpty()

    fun deleteFavoriteQuotes(){
        favoriteQuotes.clear()
    }

    fun toggleFavoriteQuote(quote:Quote){
        // Comment from zyBooks: Observer of MutableList not notified when changing a property, so
        // need to replace element in the list for notification to go through
        val index = favoriteQuotes.indexOf(quote)
        quoteList[index] = quoteList[index].copy(favorite = !quote.favorite)
    }
}