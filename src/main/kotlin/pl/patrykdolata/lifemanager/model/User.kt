package pl.patrykdolata.lifemanager.model

data class User(val id: Long, val username: String, val email: String, val firstName: String, val lastName: String,
                val token: String)
