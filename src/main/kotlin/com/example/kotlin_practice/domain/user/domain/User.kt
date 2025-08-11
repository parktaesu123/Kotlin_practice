package com.example.kotlin_practice.domain.user.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 20)
    var userName: String,

    @Column(nullable = false, length = 20)
    var accountId: String,

    @Column(nullable = false)
    var password: String
) {

}