package com.example.moviesappdemo.util.extensions

import android.view.View

fun View.hide(){
    this.visibility = View.GONE
}
fun View.chill(){
    this.visibility = View.INVISIBLE
}
fun View.show(){
    this.visibility = View.VISIBLE
}
