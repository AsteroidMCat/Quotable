package com.zybooks.quotable.ui.theme

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable

@Composable
fun QuoteListScreen(getQuotesList: () -> List<Quote>, toggleFavoriteQuote: (Quote) -> Unit) {
    LazyColumn {
        items(
            items = getQuotesList(),
            key = { quote: Quote -> quote.id }
        ) { quote ->
            QuoteCard(
                quote = quote,
                onFavoriteClick = { toggleFavoriteQuote(quote) }
            )
        }
    }
}