package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import com.example.myapplication.ui.main.CustomOnItemSelectedListener;
import com.example.myapplication.ui.main.ExampleAdapter;
import com.example.myapplication.ui.main.GetData;
import com.example.myapplication.ui.main.MatchExampleItem;
import com.example.myapplication.ui.main.Second_activity;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ui.main.SectionsPagerAdapter;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.champion_mastery.dto.ChampionMastery;
import net.rithms.riot.api.endpoints.match.MatchApiMethod;
import net.rithms.riot.api.endpoints.match.dto.Match;
import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.match.dto.Participant;
import net.rithms.riot.api.endpoints.match.dto.TeamStats;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String API = "RGAPI-4bae6c93-9db6-4b17-9a86-20d18b2ea4db";
    String Summoner_name = "";
    TextView level, id, summName, mostPl;
    EditText ETname;
    View divider, dividers;
    Spinner spinner;
    CircularImageView icon;
    ImageView FirstChampImg;
    ImageView SecondChampImg;
    ImageView ThirdChampImg;
    TextView FirstChampPct;
    TextView SecondChampPct;
    TextView ThirdChampPct;
    RecyclerView recyclerView;
    ExampleAdapter adapterRec;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<MatchExampleItem> items;
    GetData getData;
    ApiConfig config;
    RiotApi api;
    Summoner summoner;
    Platform p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        creatingVariables();
        // al the variables initializations

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.servers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        ETname.setOnEditorActionListener(editorListener);

        ///////////////////////
        items = new ArrayList<>();
/*        items.add(new MatchExampleItem(R.drawable.leesin, "10", "2"));
        items.add(new MatchExampleItem(R.drawable.leesin, "11", "15"));
        items.add(new MatchExampleItem(R.drawable.leesin, "5", "0"));
        items.add(new MatchExampleItem(R.drawable.leesin, "10", "3"));
        items.add(new MatchExampleItem(R.drawable.leesin, "0", "0"));
        items.add(new MatchExampleItem(R.drawable.leesin, "2", "3"));*/

        layoutManager = new LinearLayoutManager(this);
        adapterRec  = new ExampleAdapter(items);
        adapterRec.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), Second_activity.class);

                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterRec);

        recyclerView.setVisibility(View.INVISIBLE);

        DividerItemDecoration itemDecor = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(itemDecor);
        ///////////////////////

    }


    private TextView.OnEditorActionListener editorListener = new TextView.OnEditorActionListener() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            try {
                Summoner_name = ETname.getText().toString();
                summName.setText(Summoner_name);

                config = new ApiConfig().setKey(API);
                api = new RiotApi(config);

                if(spinner.getSelectedItem().toString().equals("EUNE"))
                {
                    p = Platform.EUNE;
                }
                summoner = api.getSummonerByName(Platform.EUNE, Summoner_name);

                List <ChampionMastery> summoner_champ_masteries = null;
                summoner_champ_masteries = api.getChampionMasteriesBySummoner(p, summoner.getId());


                getData = new GetData(API);

                Bitmap img = getData.getIconByID(Integer.toString(summoner.getProfileIconId()));

                icon.setImageBitmap(img);

                FirstChampImg.setImageBitmap(getData.getChampIconByName(getChampNamebyID(Integer.toString(summoner_champ_masteries.get(0).getChampionId())).toLowerCase()));
                SecondChampImg.setImageBitmap(getData.getChampIconByName(getChampNamebyID(Integer.toString(summoner_champ_masteries.get(1).getChampionId())).toLowerCase()));
                ThirdChampImg.setImageBitmap(getData.getChampIconByName(getChampNamebyID(Integer.toString(summoner_champ_masteries.get(2).getChampionId())).toLowerCase()));

                FirstChampPct.setText(Long.toString(summoner_champ_masteries.get(0).getChampionPoints()));
                SecondChampPct.setText(Long.toString(summoner_champ_masteries.get(1).getChampionPoints()));
                ThirdChampPct.setText(Long.toString(summoner_champ_masteries.get(2).getChampionPoints()));

                setViewsVISIBLE();

                String ecryptedSummonerId = summoner.getAccountId();

            } catch (RiotApiException | IOException e) {
                e.printStackTrace();
            }
            vHideKeyboard();


            return false;
        }
    };

    private void vHideKeyboard() {
        /*----[hide the keyboard after input]----*/
        View view = getCurrentFocus();
        if(view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        /*----[hide the keyboard after input]----*/
    }

    private void setViewsVISIBLE() {
        recyclerView.setVisibility(View.VISIBLE);
        FirstChampImg.setVisibility(View.VISIBLE);
        SecondChampImg.setVisibility(View.VISIBLE);
        ThirdChampImg.setVisibility(View.VISIBLE);

        FirstChampPct.setVisibility(View.VISIBLE);
        SecondChampPct.setVisibility(View.VISIBLE);
        ThirdChampPct.setVisibility(View.VISIBLE);

        mostPl.setVisibility(View.VISIBLE);
        divider.setVisibility(View.VISIBLE);
        dividers.setVisibility(View.VISIBLE);
        icon.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String getChampNamebyID(String id) {
        String jsonString;
        String returnValue = "";

        try {
            InputStream input = null;
            AssetManager asseMan = getResources().getAssets();

            input = asseMan.open("champion.json");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            String json = new String(buffer,"UTF-8");

            JSONObject jsonObject = new JSONObject(json);
            // reads the whole champion.json

            JSONObject dataField = jsonObject.getJSONObject("data");
            // reads the data object

                Iterator<String> keys = dataField.keys();
                // read the keys (Champion names)
                while(keys.hasNext())
                {
                    String key = keys.next();
                    JSONObject ase = dataField.getJSONObject(key);
                    // gets the key field

                    if(ase.get("key").toString().equals(id))
                        returnValue =  key.toString();
                }



            } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return returnValue;
    }

    private void LogMsg(String message) {

            Log.d("tet", message);

    }
    private void toastMessage(String message)
    {
        //Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void creatingVariables()
    {
        ETname = (EditText) findViewById(R.id.summonerName);
        summName = (TextView) findViewById(R.id.summName);
        mostPl = (TextView) findViewById(R.id.MostPlayed);
        divider = (View) findViewById(R.id.divider);
        dividers = (View) findViewById(R.id.dividers);
        spinner = (Spinner) findViewById(R.id.spinner);

        icon = (CircularImageView) findViewById(R.id.circularIcon);
        FirstChampImg = (ImageView) findViewById(R.id.FirstChampImg);
        SecondChampImg = (ImageView) findViewById(R.id.SecondChampImg);
        ThirdChampImg = (ImageView) findViewById(R.id.ThirdChampImg);

        FirstChampPct = (TextView) findViewById(R.id.FirstChampPct);
        SecondChampPct = (TextView) findViewById(R.id.SecondChampPct);
        ThirdChampPct = (TextView) findViewById(R.id.ThirdChampPct);

        divider.setVisibility(View.INVISIBLE);
        icon.setVisibility(View.INVISIBLE);

        FirstChampImg.setVisibility(View.INVISIBLE);
        SecondChampImg.setVisibility(View.INVISIBLE);
        ThirdChampImg.setVisibility(View.INVISIBLE);

        FirstChampPct.setVisibility(View.INVISIBLE);
        SecondChampPct.setVisibility(View.INVISIBLE);
        ThirdChampPct.setVisibility(View.INVISIBLE);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}