package com.shinesolutions.overreactive.accounts.model

import java.time.Instant

data class Statement(var balance: Float, var dueDate: Instant)