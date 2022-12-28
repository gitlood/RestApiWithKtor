package com.example

import com.example.data.checkPasswordForEmail
import com.example.routes.loginRoute
import com.example.routes.registerRoute
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        registerRoute()
        loginRoute()
    }
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    install(Authentication) {
        configureAuth()
    }
}

private fun AuthenticationConfig.configureAuth() {
    basic {
        realm = "Note Server"
        validate { credentials ->
            val email = credentials.name
            val password = credentials.password
            if (checkPasswordForEmail(email, password)) {
                UserIdPrincipal(email)
            } else null
        }
    }
}
