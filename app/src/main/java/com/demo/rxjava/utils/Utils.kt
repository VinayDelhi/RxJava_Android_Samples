package com.demo.rxjava.utils

import android.util.Log
import com.androidnetworking.error.ANError
import com.rxjava2.android.samples.model.ApiUser
import com.rxjava2.android.samples.model.User
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by amitshekhar on 27/08/16.
 */
object Utils {


    fun getUserList() : List<User>{

        val userList = ArrayList<User>()

        userList.add(User(firstname = "Amit", lastname = "Shekhar"))
        userList.add(User(firstname = "Vinay", lastname = "Gupta"))
        userList.add(User(firstname = "Ajay", lastname = "Kumar"))
        userList.add(User(firstname = "Amit", lastname = "Tomar"))
        userList.add(User(firstname = "Sunil", lastname = "kushwaha"))

        return userList
    }

    fun getApiUserList(): List<ApiUser> {

        val apiUserList = ArrayList<ApiUser>()

        val apiUserOne = ApiUser(firstname = "Amit", lastname = "Shekhar")
        apiUserList.add(apiUserOne)

        val apiUserTwo = ApiUser(firstname = "Manish", lastname = "Kumar")
        apiUserList.add(apiUserTwo)

        val apiUserThree = ApiUser(firstname = "Sumit", lastname = "Kumar")
        apiUserList.add(apiUserThree)

        return apiUserList
    }

    fun convertApiUserListToUserList(apiUserList: List<ApiUser>): List<User> {

        val userList = ArrayList<User>()

        for (apiUser in apiUserList) {
            val user = User(apiUser.id, apiUser.firstname, apiUser.lastname)
            userList.add(user)
        }

        return userList
    }

    fun getUserListWhoLovesCricket(): List<User>{

        val list = ArrayList<User>()

        list.add(User(1, "Vinay","Gupta"))
        list.add(User(1, "Ajay","Kumar"))
        list.add(User(1, "Amit","Shekhar"))

        return list
    }

    fun getUserListWhoLovesFootball(): List<User>{

        val list = ArrayList<User>()

        list.add(User(1, "Vinay","Gupta"))
        list.add(User(1, "Pankay","Kumar"))
        list.add(User(1, "Amit","Shekhar"))

        return list
    }

    fun getUserListWhoLovesBoth(userListWhoLovesCricket: List<User>, userListWhoLovesFootball: List<User>): List<User>{

        val userList = ArrayList<User>()

        if(userListWhoLovesCricket!=null && userListWhoLovesFootball!=null){
            for(user in userListWhoLovesFootball){

                if(userListWhoLovesCricket.contains(user)){
                    userList.add(user)
                }

            }
        }

        return userList;

    }
}
