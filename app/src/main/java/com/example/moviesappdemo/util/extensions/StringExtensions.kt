package com.example.moviesappdemo.util.extensions

import java.text.DecimalFormat


fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.toLowerCase().capitalize() }
