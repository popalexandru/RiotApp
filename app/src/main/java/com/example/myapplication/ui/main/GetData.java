package com.example.myapplication.ui.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;

public class GetData {
    private String API;
    OkHttpClient client = new OkHttpClient();

    public GetData(String API)
    {
        this.API = API;
    }

    public Bitmap getIconByID(String ID) throws IOException {

        Request req = new Request.Builder()
                .url("http://raw.communitydragon.org/latest/game/assets/ux/summonericons/profileicon"+ID+".png")
                .addHeader("key", API)
                .build();

        Response response = client.newCall(req).execute();
        InputStream iS = response.body().byteStream();
        Bitmap img = BitmapFactory.decodeStream(iS);

        return img;
    }

    public Bitmap getChampIconByName(String name) throws IOException {
        Request req = new Request.Builder()
                .url("        http://raw.communitydragon.org/latest/game/assets/characters/"+name+"/hud/"+name+"_square.png")

                .addHeader("key", API)
                .build();
        Response response = client.newCall(req).execute();
        InputStream iS = response.body().byteStream();
        Bitmap img = BitmapFactory.decodeStream(iS);

        return img;

    }

}
