package com.demo.rxjava.ui.operator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.demo.rxjava.R
import com.demo.rxjava.utils.AppConstant
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import com.demo.rxjava.utils.Utils
import com.rxjava2.android.samples.model.User


class DelayExampleActivity : AppCompatActivity() {

    companion object{
        val TAG = "DelayExampleActivity"
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

         getObservable()
             .delay(2, TimeUnit.SECONDS)
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe(getObserver())

        // Note: You don't need .subscribeOn(Schedulers.io())
        // to start in background because Observable.interval emits on Schedulers.computation() by default.

    }

    private fun getObservable() : Observable<List<User>>{

        return Observable.create{emitter ->
            if(!emitter.isDisposed){
                emitter.onNext(Utils.getUserList())
                emitter.onComplete()
            }

        }
    }

    private fun getObserver(): Observer<List<User>>{

        return object : Observer<List<User>>{

            override fun onSubscribe(d: Disposable) {
                textView.append(" onSubscribe : ${d.isDisposed}")
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, "onSubscribe : ${d.isDisposed}")
            }

            override fun onNext(userList: List<User>) {

                textView.append(" onNext : ${userList.size}")
                textView.append(AppConstant.LINE_SEPARATOR)

                for(user in userList){
                    textView.append(user.firstname)
                    textView.append(AppConstant.LINE_SEPARATOR)
                }
                Log.d(TAG, "onNext : ${userList.size}")
            }

            override fun onError(e: Throwable) {
                textView.append(" onError : ${e.message}")
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, "onError : ${e.message}")
            }

            override fun onComplete() {
                textView.append(" onComplete ")
                textView.append(AppConstant.LINE_SEPARATOR)
            }
        }
    }
}
