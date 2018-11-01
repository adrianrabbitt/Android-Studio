package com.example.adrian.firebase;

import android.service.textservice.SpellCheckerService;
import android.util.Log;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;

public class SpellChecker extends SpellCheckerService {

    public static final String LOG_TAG = SpellChecker.class.getSimpleName();

    public SpellChecker() {
        Log.d(LOG_TAG, "SampleSpellCheckerService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(LOG_TAG, "SampleSpellCheckerService.onCreate");
    }

    @Override
    public Session createSession() {

        Log.d(LOG_TAG, "createSession");

        return new AndroidSpellCheckerSession();
    }

    private static class AndroidSpellCheckerSession extends SpellCheckerService.Session {

        @Override
        public void onCreate() {

            Log.d(LOG_TAG, "AndroidSpellCheckerSession.onCreate");

        }



        @Override
        public SentenceSuggestionsInfo[] onGetSentenceSuggestionsMultiple(TextInfo[] textInfos, int suggestionsLimit) {

            Log.d(LOG_TAG, "onGetSentenceSuggestionsMultiple");

            SentenceSuggestionsInfo[] suggestionsInfos = null;
            //suggestionsInfo = new SuggestionsInfo();
            //... // look up suggestions for TextInfo
            return suggestionsInfos;
        }

        @Override
        public SuggestionsInfo onGetSuggestions(TextInfo textInfo, int suggestionsLimit) {

            Log.d(LOG_TAG, "onGetSuggestions");

            SuggestionsInfo suggestionsInfo = null;
            //suggestionsInfo = new SuggestionsInfo();
            //... // look up suggestions for TextInfo
            return suggestionsInfo;
        }

        @Override
        public void onCancel() {
            Log.d(LOG_TAG, "onCancel");
        }


    }
}