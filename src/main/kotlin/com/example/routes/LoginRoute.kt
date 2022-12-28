package com.example.routes

import com.example.data.checkPasswordForEmail
import com.example.data.requests.AccountRequest
import com.example.data.responses.SimpleResponse
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.plugins.ContentTransformationException
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.loginRoute(){
    route("/login"){
        post{
            val request = try {
                call.receive<AccountRequest>()
            } catch (e: ContentTransformationException) {
                call.respond(BadRequest)
                return@post
            }
            val isPasswordCorrect = checkPasswordForEmail(request.email, request.password)
            if(isPasswordCorrect){
                call.respond(OK, SimpleResponse(true, "You are now logged in!"))
            }else{
                call.respond(OK, SimpleResponse(false, "The email or password is incorrect"))
            }
        }
    }
}