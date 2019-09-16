package com.shinesolutions.poc.overreactive.exceptions.model

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class Error(val status: HttpStatus, val code: String, val message: String)