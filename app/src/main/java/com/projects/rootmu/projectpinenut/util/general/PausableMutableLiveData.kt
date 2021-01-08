package com.projects.rootmu.projectpinenut.util.general

import androidx.lifecycle.MutableLiveData

class PausableMutableLiveData<T>: MutableLiveData<T>() {

    var isPaused = false
        private set
    
    fun pause(){
        isPaused = true
    }    
    
    fun resume(){
        isPaused = false
    }

    override fun postValue(value: T) {
        resume()
        super.postValue(value)
    }

    override fun setValue(value: T) {
        resume()
        super.setValue(value)
    }
}