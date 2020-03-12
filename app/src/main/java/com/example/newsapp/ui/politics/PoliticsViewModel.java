package com.example.newsapp.ui.politics;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PoliticsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PoliticsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Politics fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}