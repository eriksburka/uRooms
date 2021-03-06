/*
 *  Scrapes every room for this building
 */

package com.halaltokens.halaltokens.Runnables;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.halaltokens.halaltokens.R;
import com.halaltokens.halaltokens.Helpers.RoomInfo;
import com.halaltokens.halaltokens.Data.ScraperWorker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class SciSouthRunnable implements Runnable {

    private static Context context = null;

    public SciSouthRunnable(Context con) {
        context = con;
    }

    @Override
    public void run() {
        ArrayList<String> sciSouthArraylist = new ArrayList<String>();
        sciSouthArraylist.add(context.getString(R.string.S1_67));
        sciSouthArraylist.add(context.getString(R.string.S3_56));

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("");

        for (String page : sciSouthArraylist) {
            try {
                Document doc = Jsoup.connect(page).cookies(ScraperWorker.response.cookies()).get();
                for (int i = 1; i <= 10; i++) {
                    try {
                        RoomInfo roomInfo = new RoomInfo();
                        roomInfo.setRoomName(doc.title().substring(10));
                        roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                        Log.v("SciSouthRunnable", roomInfo.toString());
                        ref.child("SciSouth").push().setValue(roomInfo);
                    } catch (NullPointerException e) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
