package pl.patrykdolata.lifemanager.model

data class NewUser(val username: String, val password: String, val confirmPassword: String, val email: String,
                   val firstName: String, val lastName: String)
