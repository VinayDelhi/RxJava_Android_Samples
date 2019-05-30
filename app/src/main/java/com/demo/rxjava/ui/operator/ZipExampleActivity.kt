package com.demo.rxjava.ui.operator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.demo.rxjava.R
import com.demo.rxjava.model.service.ApiService
import com.demo.rxjava.utils.AppConstant
import com.demo.rxjava.utils.Utils
import com.rxjava2.android.samples.model.User
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import okhttp3.internal.Util


class ZipExampleActivity : AppCompatActivity() {

    companion object{
        val TAG = "ZipExampleActivity"
    }

    private lateinit var btn: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        btn = findViewById(R.id.btn)
        textView = findViewById(R.id.textView)

        btn.setOnClickListener{

            doSomeWork()
        }
    }

    fun doSomeWork(){

        Observable.zip(ApiService.getUserListWhoLovesCricketObservable()
                      ,ApiService.getUserListWhoLovesFootball(),
                      BiFunction<List<User>, List<User>, List<User>>{cricketFans, footballFans ->

                          return@BiFunction Utils.getUserListWhoLovesBoth(cricketFans, footballFans)
                      })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver())

        }

    fun getObserver(): Observer<List<User>>{

        return object : Observer<List<User>>{

            override fun onSubscribe(d: Disposable) {

                textView.append("onSubscribe : ${d.isDisposed}")
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, "onSubscribe : ${d.isDisposed}")

            }

            override fun onNext(list: List<User>) {

                textView.append(" onNext ")
                textView.append(AppConstant.LINE_SEPARATOR)

                for(user in list){
                    textView.append("${user.firstname}")
                    textView.append(AppConstant.LINE_SEPARATOR)
                }

                Log.d(TAG, "onNext : ${list.size}")

            }

            override fun onError(e: Throwable) {

                textView.append(" onError : ${e.message} ")
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, "onError : ${e.message}")
            }

            override fun onComplete() {

                textView.append(" onComplete ")
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, "onComplete")
            }


        }
    }

    }

