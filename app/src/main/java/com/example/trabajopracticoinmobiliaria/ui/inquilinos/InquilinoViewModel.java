package com.example.trabajopracticoinmobiliaria.ui.inquilinos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InquilinoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public InquilinoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is settings fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}