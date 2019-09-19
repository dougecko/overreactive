package com.shinesolutions.overreactive.accounts.model

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id

@Data
@NoArgsConstructor
@AllArgsConstructor
class Thing(@Id val id: Long, val name: String)