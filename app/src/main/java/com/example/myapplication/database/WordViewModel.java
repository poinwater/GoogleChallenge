package com.example.myapplication.database;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private final LiveData<List<Word>> mAllWords;
    private final LiveData<List<Item>> mAllItems;
    private final LiveData<List<Sleep>> mAllSleepDuration;
    private final LiveData<List<Item>> mRareItems;


    public WordViewModel(Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
        mAllItems = mRepository.getAllItems();
        mAllSleepDuration = mRepository.getAllSleepDuration();
        mRareItems = mRepository.getRareItems();

    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insert(Word word) {
        mRepository.insert(word);
    }

    public void insertDuration(Sleep sleep){
        mRepository.insertDuration(sleep);
    }

    public LiveData<List<Item>> getAllItems() {
        return mAllItems;
    }

    public LiveData<List<Item>> getRareItems() { return mRareItems; }
    public void insertItem(Item item){
        mRepository.insertItem(item);
    }

    public LiveData<List<Sleep>> getSleepDuration() {
        return mAllSleepDuration;
    }

    //how to access LiveData
//    ViewModel mWordViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(WordViewModel.class);
//
//    // Add an observer on the LiveData returned by getAlphabetizedWords.
//    // The onChanged() method fires when the observed data changes and the activity is
//    // in the foreground.
//        mWordViewModel.getAllItems().observe(this, item -> {
//        // Update the cached copy of the words in the adapter.
//          //words is List type
            //List<Item>
//    });





}
