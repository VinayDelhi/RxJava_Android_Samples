package com.demo.rxjava.ui.disposable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.demo.rxjava.R
import com.demo.rxjava.model.service.ApiService
import com.demo.rxjava.ui.operator.MapExampleActivity
import com.demo.rxjava.utils.AppConstant
import com.demo.rxjava.utils.Utils
import com.rxjava2.android.samples.model.ApiUser
import com.rxjava2.android.samples.model.User
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DisposableExampleActivity : AppCompatActivity() {

    companion object{

        val TAG = "IntervalExampleActivity"
    }

    private lateinit var btn: Button
    private lateinit var textView: TextView
    private var disposable: Disposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        btn = findViewById(R.id.btn)
        textView = findViewById(R.id.textView)

        btn.setOnClickListener{
            doSomeWork()
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }


    private fun doSomeWork(){

            disposable = ApiService.getUserListObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{list ->

                    for(user in list){

                        textView.append(user.firstname)
                        textView.append(AppConstant.LINE_SEPARATOR)
                    }
                }
    }


    private fun getObserver() : Observer<List<ApiUser>>{

        return object :Observer<List<ApiUser>>{
            override fun onSubscribe(d: Disposable) {
                textView.append("onSubscribe")
                textView.append(AppConstant.LINE_SEPARATOR)

                Log.d(MapExampleActivity.TAG, "onSubscribe : ${d.isDisposed}")
            }

            override fun onNext(list: List<ApiUser>) {
                textView.append("onNext()")
                textView.append(AppConstant.LINE_SEPARATOR)
                for(user in list){

                    textView.append("${user.firstname}")
                    textView.append(AppConstant.LINE_SEPARATOR)
                }

                Log.d(MapExampleActivity.TAG, "onNext : ${list.size}")
            }

            override fun onError(e: Throwable) {
                textView.append("onError : ${e.message}")
                textView.append(AppConstant.LINE_SEPARATOR)

                Log.d(MapExampleActivity.TAG, "onError : ${e.message}")
            }

            override fun onComplete() {
                textView.append("onComplete")
                textView.append(AppConstant.LINE_SEPARATOR)

                Log.d(MapExampleActivity.TAG, "onComplete")
            }

        }
    }
}
