package com.demo.rxjava.ui.networking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.demo.rxjava.R
import com.demo.rxjava.utils.Utils
import com.rx2androidnetworking.Rx2AndroidNetworking
import com.rxjava2.android.samples.model.ApiUser
import com.rxjava2.android.samples.model.User
import com.rxjava2.android.samples.model.UserDetail
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class NetworkingActivity : AppCompatActivity() {

    companion object{
        val TAG = "NetworkingActivity"
    }

    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_networking)

        compositeDisposable = CompositeDisposable()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }


    /**
     * Map Operator
     * Use Case : Assume that we get ApiUser Object
     * from server Api a// returning user one by one from usersList.nd our android client database
     * accepts User Object. So after getting ApiUser
     * from server, we are converting it into User Object
     * using Map operator.
     *
     * */

    fun map(view:View){
        Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAnUser/{userId}")
            .addPathParameter("userId","1")
            .build()
            .getObjectObservable(ApiUser::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map{apiUser ->

                // here we get ApiUser from server
                // then by converting, we are returning user
                User(apiUser.id, apiUser.firstname, apiUser.lastname)
            }
            .subscribe(getObserver())
    }

    /**
     * Zip Operator
     * Use Case: Suppose one observable returns list of cricket fans
     * ,second observable returns list of football fans, now we have
     * need to get those fans who like both football & cricket.
     * */

    fun zip(view: View){

         getFansWhoLovesBoth()
    }

    fun getObserver(): Observer<User>{

        return object : Observer<User>{

            override fun onSubscribe(d: Disposable) {

                Log.d(TAG, "onSubscribe : ${d.isDisposed}")
            }

            override fun onNext(user: User) {

                Log.d(TAG, "onNext : $user")
            }

            override fun onError(e: Throwable) {

               Utils.logError(TAG, e)

            }

            override fun onComplete() {

                Log.d(TAG, "onComplete ")
            }

        }
    }

    /**
     * zip Operator Example
     */

    fun getFansWhoLovesBoth(){

        // here we are using zip operator to combine both request
       val disposable= Observable.zip(getCricketFansListObservable(),
                       getFootballFansListObservable(),
            BiFunction<List<User>, List<User>, List<User>>{cricketFans, footballFans ->
                return@BiFunction Utils.getUserListWhoLovesBoth(cricketFans,footballFans)

            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                   Log.d(TAG, it.toString())
                },
                {
                    Utils.logError(TAG, it)
                }

                )

        compositeDisposable.add(disposable)
    }

    /**
     * This observable return the list of User who loves cricket
     */

    fun getCricketFansListObservable(): Observable<List<User>>{

        return Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllCricketFans")
            .build()
            .getObjectListObservable(User::class.java)
    }

    fun getFootballFansListObservable(): Observable<List<User>>{

        return Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllFootballFans")
            .build()
            .getObjectListObservable(User::class.java)
    }


    /**
     * Flat map operator
     * Use Case: Suppose one observable emitting user list & second
     * Observable fetching details of Users which are emitting one by one.
     *
     * Note: Here we need to pass observable object for get user details
     * in emitting sequence.
     * */

    fun flatMap(view: View){

        val disposable = getUserListObservable()
            .flatMap {userList ->
                // flatMap - to return users one by one
                Observable.fromIterable(userList) // returning user one by one from usersList.

            }
            .flatMap {user ->

                // here we get the user one by one
                // and returns corresponding getUserDetailObservable
                // for that userId
                getUserDetailById(user.id)

            }
            .toList()
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {list ->

                    Log.d(TAG, list.toString())
                },
                {

                    Utils.logError(TAG, it)
                }
            )

        compositeDisposable.add(disposable)

    }

    /**
     * flatMapWithZip Operator Example
     */

    fun getUserListObservable(): Observable<List<User>>{
        return  Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllUsers/{pageNumber}")
            .addPathParameter("pageNumber","1")
            .addQueryParameter("limit","10")
            .build()
            .getObjectListObservable(User::class.java)
    }

    fun getUserDetailById(id : Long): Observable<UserDetail>{
        return Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAnUserDetail/{userId}")
            .addPathParameter("userId", id.toString())
            .build()
            .getObjectObservable(UserDetail::class.java)

    }

    /**
     * FlatMap and Filter
     * Use Case: Get All user list who are following me.
     * */

    fun flatMapAndFilter(view: View){
        val disposable = getAllMyFriendsObservable()
                               .flatMap {userList ->
                                   // flatMap - to return users one by one
                                   Observable.fromIterable(userList) // returning user one by one from usersList.
                               }
                              .filter{user ->
                                  // filtering user who follows me.
                                      return@filter user.isFollowing
                              }
                              .toList()
                              .toObservable()
                              .subscribeOn(Schedulers.io())
                              .observeOn(AndroidSchedulers.mainThread())
                              .subscribe(
                                  {

                                      Log.d(TAG, it.toString())
                                  },

                                  {

                                      Utils.logError(TAG, it)
                                  }
                              )

        compositeDisposable.add(disposable)

    }

    /**
     * flatMap and filter Operators Example
     */
    private fun getAllMyFriendsObservable(): Observable<List<User>> {

        return Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllFriends/{userId}")
            .addPathParameter("userId","1")
            .build()
            .getObjectListObservable(User::class.java)
    }
}
