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
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MapExampleActivity : AppCompatActivity() {

    companion object{
        val TAG = "MapExampleActivity"
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

        ApiService.getUserListObservable()
            .map { useList ->

                return@map Utils.convertApiUserListToUserList(useList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver())
    }

    fun getObserver(): Observer<List<User>>{

        return object : Observer<List<User>>{

            override fun onSubscribe(d: Disposable) {
                textView.append("onSubscribe")
                textView.append(AppConstant.LINE_SEPARATOR)

                Log.d(TAG, "onSubscribe : ${d.isDisposed}")
            }

            override fun onNext(list: List<User>) {
                textView.append("onNext()")
                textView.append(AppConstant.LINE_SEPARATOR)
                for(user in list){

                    textView.append("${user.firstname}")
                    textView.append(AppConstant.LINE_SEPARATOR)
                }

                Log.d(TAG, "onNext : ${list.size}")
            }

            override fun onError(e: Throwable) {
                textView.append("onError : ${e.message}")
                textView.append(AppConstant.LINE_SEPARATOR)

                Log.d(TAG, "onError : ${e.message}")
            }

            override fun onComplete() {
                textView.append("onComplete")
                textView.append(AppConstant.LINE_SEPARATOR)

                Log.d(TAG, "onComplete")
            }
        }
    }
}
