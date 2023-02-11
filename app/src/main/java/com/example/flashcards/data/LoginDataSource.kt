package com.example.flashcards.data

import android.util.Log
import com.example.flashcards.data.model.LoggedInUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            if(username.equals("test@123.com") && password.equals("12345678")){
                val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Test User")
                return Result.Success(fakeUser)
            }else{
                return Result.Error(IOException("Incorrect Username or Password"))
            }
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}