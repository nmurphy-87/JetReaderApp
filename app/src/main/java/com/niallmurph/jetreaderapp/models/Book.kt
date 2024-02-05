package com.niallmurph.jetreaderapp.models

data class Book(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)