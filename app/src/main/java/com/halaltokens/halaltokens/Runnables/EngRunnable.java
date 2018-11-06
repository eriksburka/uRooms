package com.halaltokens.halaltokens.Runnables;

import android.content.Context;
import android.util.Log;

import com.halaltokens.halaltokens.R;
import com.halaltokens.halaltokens.RoomInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;

public class EngRunnable implements Runnable {

    private static Context context = null;

    public EngRunnable(Context con) {
        context = con;
    }

    @Override
    public void run() {
        ArrayList<String> engArraylist = new ArrayList<String>();
        engArraylist.add(context.getString(R.string.E016));
        engArraylist.add(context.getString(R.string.E121));
        engArraylist.add(context.getString(R.string.E135));
        engArraylist.add(context.getString(R.string.E216));
        engArraylist.add(context.getString(R.string.E234));
        engArraylist.add(context.getString(R.string.E317));
        engArraylist.add(context.getString(R.string.E326));
        for (String page : engArraylist) {
            try {
                Document doc = Jsoup.connect(page).get();
                for (int i = 1; i <= 10; i++) {
                    try {
                        RoomInfo roomInfo = new RoomInfo();
                        roomInfo.setRoomName(doc.title().substring(10));
                        roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                        Log.v("EngRunnable", roomInfo.toString());
                    } catch (NullPointerException e) {
                        break;
                    }
                }
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}