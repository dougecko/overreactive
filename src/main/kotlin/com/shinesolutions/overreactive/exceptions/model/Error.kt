package com.shinesolutions.overreactive.exceptions.model

import org.springframework.http.HttpStatus

data class Error(val status: HttpStatus, val code: String, val message: String)