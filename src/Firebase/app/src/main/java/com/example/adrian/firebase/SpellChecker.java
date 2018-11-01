package com.example.adrian.firebase;

import android.service.textservice.SpellCheckerService;
import android.util.Log;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;


//Spell checker class did not get to fully Implement it 
//Reference tuturial
public class SpellChecker extends SpellCheckerService {

    

    public SpellChecker() {
       
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public Session createSession() {


        return new AndroidSpellCheckerSession();
    }
 
 
    //Create a spell checker session
    private static class AndroidSpellCheckerSession extends SpellCheckerService.Session {

        @Override
        public void onCreate() {

       

        }


   //Array of sugestions
        @Override
        public SentenceSuggestionsInfo[] onGetSentenceSuggestionsMultiple(TextInfo[] textInfos, int suggestionsLimit) {

      

            SentenceSuggestionsInfo[] suggestionsInfos = null;
            
            return suggestionsInfos;
        }

        @Override
        public SuggestionsInfo onGetSuggestions(TextInfo textInfo, int suggestionsLimit) {

           

            SuggestionsInfo suggestionsInfo = null;
         
            return suggestionsInfo;
        }

        @Override
        public void onCancel() {
           
        }


    }
}