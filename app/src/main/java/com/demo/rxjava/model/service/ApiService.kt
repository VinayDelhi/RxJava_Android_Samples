package com.demo.rxjava.model.service

import com.demo.rxjava.utils.Utils
import com.rxjava2.android.samples.model.ApiUser
import com.rxjava2.android.samples.model.User
import io.reactivex.Observable


object  ApiService {

    fun getUserListObservable(): Observable<List<ApiUser>> {

        return Observable.create{emitter ->

            if(!emitter.isDisposed){
                emitter.onNext(Utils.getApiUserList())
                emitter.onComplete()
            }
        }
    }

    fun getUserListWhoLovesCricketObservable(): Observable<List<User>>{

        return Observable.create{emitter ->

            if(!emitter.isDisposed){
                emitter.onNext(Utils.getUserListWhoLovesCricket())
                emitter.onComplete()
            }
        }
    }

    fun getUserListWhoLovesFootball(): Observable<List<User>>{

        return Observable.create{emitter ->
            if(!emitter.isDisposed){
                emitter.onNext(Utils.getUserListWhoLovesFootball())
                emitter.onComplete()
            }

        }
    }
}