package app.ie.mymagiccards.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import app.ie.mymagiccards.R;
import app.ie.mymagiccards.models.Cards;
import app.ie.mymagiccards.utils.Constants;

public class SearchActivity extends AppCompatActivity{

    private List<Cards> cardList;
    private RequestQueue queue;
    Spinner spinner;
    ArrayAdapter adapter;
    Button searchcards;
    StringBuilder urlParams;
    EditText searchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        urlParams = new StringBuilder();
        queue = Volley.newRequestQueue(this);
        searchcards = findViewById(R.id.searchButton);
        searchName = findViewById(R.id.searchNameBox);
        setUpSpinner();

        searchcards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    cardList = new ArrayList<>();
                if(!searchName.getText().toString().isEmpty()) {
                    Constants.URL += "name=" + searchName.getText().toString();
                }


            }
        });
    }


    //get Cards
    public List<Cards> searchCardsByName(String search){
        //Clear the list before it get populated
        //cardList.clear();

        Log.i("Searhing", Constants.URL);
        //Send a http request to server to retrive card list
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.URL , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                cardList = new ArrayList<>();
                try {
                    Log.i("Inside Try", "Hello");
                    //loop trough the data array to pull out the needed information
                    JSONArray cardInformation = response.getJSONArray("cards");

                    for (int i = 0; i < cardInformation.length(); i++) {
                        JSONObject cardObject = cardInformation.getJSONObject(i);
                        Cards cards = new Cards();
                        cards.setName("Name: " + cardObject.getString("name"));

                        cards.setColor("Color: "   + cardObject.optString("colors"));
                        cards.setType("Type: "     + cardObject.optString("type"));
                        cards.setRarity("Rarity: " + cardObject.optString("rarity"));
                        cards.setImageUrl(cardObject.optString("imageUrl"));
                        cards.setCardID(cardObject.getString("id"));
                        cardList.add(cards);

                        Log.i("Card Information", cardObject.toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);

        return cardList;

    }



        //cardRecyclerViewAdapter.notifyDataSetChanged(); // update the recycler view to the changes

    public void setUpSpinner(){
        spinner = findViewById(R.id.searchBoosterBox);
        adapter = ArrayAdapter.createFromResource(SearchActivity.this, R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), adapterView.getItemIdAtPosition(i) + "is Selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }



}
