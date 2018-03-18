package app.ie.mymagiccards.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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
import java.util.ArrayList;
import java.util.List;
import app.ie.mymagiccards.R;
import app.ie.mymagiccards.adapters.CardRecyclerViewAdapter;
import app.ie.mymagiccards.models.Cards;
import app.ie.mymagiccards.utils.Constants;
import app.ie.mymagiccards.utils.Prefs;



public class MainActivity extends AppCompatActivity {

    private RecyclerView            recyclerView;
    private CardRecyclerViewAdapter cardRecyclerViewAdapter;
    private List<Cards>             cardList;
    private RequestQueue            queue;
    private AlertDialog.Builder     alertDialog;
    private AlertDialog             dialog;
    private EditText                newSearch;
    private Spinner                 spinner, colorSpinner;
    private ArrayAdapter            spinnerAdapter;
    private Button                  searchBtn;
    private boolean                 blankName, blankType, blankColor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setLogo(R.drawable.logomagicnew);
        getSupportActionBar().setTitle("");

        //Get the queue ready for a HTTP request
        queue = Volley.newRequestQueue(this);



        recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //Save a string for your search results
        Prefs prefs = new Prefs(MainActivity.this);
        String search = prefs.getSearch();

        Log.i("URL", search);

        cardList = new ArrayList<>();
        cardList = getCards(search);
        cardRecyclerViewAdapter = new CardRecyclerViewAdapter(this, cardList);
        recyclerView.setAdapter(cardRecyclerViewAdapter);
        cardRecyclerViewAdapter.notifyDataSetChanged();//update the recycler view to the changes

    }//end onCreate Method


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu and show the items.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle menu clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.search :
                showInputDialog();
                break;
            case R.id.delete :
                // TODO: 28/02/2018 add delete method to delete card from deck
                break;
            case R.id.add :
                // TODO: 28/02/2018 Add card to deklist method

            }
        return super.onOptionsItemSelected(item);
    }

    public List<Cards> getCards(String search){
        //Send a http request to server to retrive card list
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                search, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //loop trough the data array to pull out the needed information
                    JSONArray cardInformation = response.getJSONArray("cards");
                    for (int i = 0; i < cardInformation.length(); i++) {
                        JSONObject cardObject = cardInformation.getJSONObject(i);
                        Cards cards = new Cards();
                        cards.setName("Name: "     + cardObject.getString("name"));
                        cards.setColor("Color: "   + cardObject.optString("colors"));
                        cards.setType("Type: "     + cardObject.getString("type"));
                        if(!cardObject.getString("rarity").equals("Special")) {
                            cards.setRarity("Rarity: " + cardObject.getString("rarity"));
                        }else {
                            Log.i("Special",  cardObject.getString("rarity").toString());
                            continue;
                        }

                        cards.setImageUrl(cardObject.optString("imageUrl"));
                        cards.setCardID(cardObject.getString("id"));
                        cardList.add(cards);
                    }

                    cardRecyclerViewAdapter.notifyDataSetChanged();//Important!! refreshes the recyclerview to show changes
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



    public void showInputDialog(){
        alertDialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_view, null);
        newSearch = view.findViewById(R.id.searchEdt);
        searchBtn = view.findViewById(R.id.submitBtn);
        spinner = view.findViewById(R.id.searchTypeBox);
        colorSpinner = view.findViewById(R.id.colorPickerSpinner);
        spinnerAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.type_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.color_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(spinnerAdapter);
        alertDialog.setView(view);
        dialog = alertDialog.create();
        dialog.show();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blankName  =    !newSearch.getText().toString().isEmpty();
                blankType  =    !spinner.getSelectedItem().toString().equals("Please Select a Type");
                blankColor =    !colorSpinner.getSelectedItem().toString().equals("Please Select a Colour");

               if(blankName && !blankType && !blankColor) {
                   searchByName();
               }else if(blankName && blankType && !blankColor){
                    searchByNameAndType();
               }else if(blankName && !blankType && blankColor){
                    searchByNameAndcolor();
               }else if(!blankName && blankType && blankColor){
                    searchByTypeAndcolor();
               }else if(!blankName && blankType && !blankColor){
                   searchByType();
               }else if(!blankName && !blankType && blankColor){
                   searchByColor();
               }else if(blankName && blankType && blankColor){
                   searchByAll();
               }else{
                   Toast.makeText(getApplicationContext(), "Your search could not be completed please fill in some fields", Toast.LENGTH_LONG).show();
               }

                Log.i("Searcher", Constants.SEARCH_CARD);

            }
        });
    }

    public void searchFunction() {
        cardList.clear();
        searchCardsByName();
        cardRecyclerViewAdapter.notifyDataSetChanged(); // update the recycler view to the changes
        Constants.SEARCH_CARD = "https://api.magicthegathering.io/v1/cards?";
        dialog.dismiss();
    }


    public void searchByAll(){
        if(!(newSearch.getText().toString().isEmpty()) && (spinner.getSelectedItem().toString() != "Please Select a Type") && (colorSpinner.getSelectedItem().toString() != "Please Select a Colour")){
            Constants.SEARCH_CARD += "name=" + newSearch.getText().toString() +
                                     "&type=" + spinner.getSelectedItem().toString() +
                                     "&colors=" + colorSpinner.getSelectedItem().toString() ;
            Prefs pref = new Prefs(MainActivity.this);
            String search = Constants.SEARCH_CARD;
            Log.i("Searcher", search);
            pref.setSearch(search);
            searchFunction();

        }else{
            dialog.dismiss();

        }
    }

    public void searchByName(){
        if(!(newSearch.getText().toString().isEmpty())){
            Constants.SEARCH_CARD += "name=" + newSearch.getText().toString()+ "&pageSize=20&orderBy=name";
            Prefs pref = new Prefs(MainActivity.this);
            String search = Constants.SEARCH_CARD;
            Log.i("Searcher", search);
            pref.setSearch(search);
           searchFunction();
        }else{
            dialog.dismiss();

        }
    }

    public void searchByNameAndType(){
        if(!newSearch.getText().toString().isEmpty() && spinner.getSelectedItem().toString() != "Please Select a Type"){
            Constants.SEARCH_CARD += "name="+newSearch.getText().toString()+ "&type=" + spinner.getSelectedItem().toString();
            Prefs pref = new Prefs(MainActivity.this);
            String search = Constants.SEARCH_CARD;

            pref.setSearch(search);
            searchFunction();
        }
    }

    public void searchByNameAndcolor(){
        if(!newSearch.getText().toString().isEmpty() && colorSpinner.getSelectedItem().toString() != "Please Select a Colour"){
            Constants.SEARCH_CARD += "name="+newSearch.getText().toString()+ "&colors=" + colorSpinner.getSelectedItem().toString();
            Prefs pref = new Prefs(MainActivity.this);
            String search = Constants.SEARCH_CARD;

            pref.setSearch(search);
            searchFunction();
        }
    }

    public void searchByTypeAndcolor(){
        if(spinner.getSelectedItem().toString() != "Please Select a Type" && colorSpinner.getSelectedItem().toString() != "Please Select a Colour"){
            Constants.SEARCH_CARD += "type="+spinner.getSelectedItem().toString()+ "&colors=" + colorSpinner.getSelectedItem().toString();
            Prefs pref = new Prefs(MainActivity.this);
            String search = Constants.SEARCH_CARD;

            pref.setSearch(search);
            searchFunction();
        }
    }



    public void searchByType(){
        if(spinner.getSelectedItem().toString() != "Please Select a Type"){
            Constants.SEARCH_CARD += "type=" + spinner.getSelectedItem().toString();
            Prefs pref = new Prefs(MainActivity.this);
            String search = Constants.SEARCH_CARD;
            pref.setSearch(search);
            searchFunction();
        }else{
            dialog.dismiss();

        }
    }

    public void searchByColor(){
        if(colorSpinner.getSelectedItem().toString() != "Please Select a Colour"){
            Constants.SEARCH_CARD += "colors=" + colorSpinner.getSelectedItem().toString();
            Prefs pref = new Prefs(MainActivity.this);
            String search = Constants.SEARCH_CARD;
            pref.setSearch(search);
            searchFunction();
        }else{
            dialog.dismiss();

        }
    }


    public void setUpSpinner(View view){
        spinner = view.findViewById(R.id.searchTypeBox);
        spinnerAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.type_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Constants.SEARCH_CARD += "type=" + adapterView.getItemAtPosition(i).toString();
                Constants.SEARCH_CARD = "https://api.magicthegathering.io/v1/cards?";
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setUpColorSpinner(View view){
        colorSpinner = view.findViewById(R.id.searchTypeBox);
        spinnerAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.type_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(spinnerAdapter);
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Constants.SEARCH_CARD += "colors=" + adapterView.getItemAtPosition(i).toString();
                Constants.SEARCH_CARD = "https://api.magicthegathering.io/v1/cards?";
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }




    //get Cards
    public List<Cards> searchCardsByName(){
        //Clear the list before it get populated
        cardList.clear();
        //Send a http request to server to retrive card list
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.SEARCH_CARD, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //loop trough the data array to pull out the needed information
                    JSONArray cardInformation = response.getJSONArray("cards");

                    for (int i = 0; i < cardInformation.length(); i++) {
                        JSONObject cardObject = cardInformation.getJSONObject(i);
                        Cards cards = new Cards();
                        cards.setName("Name: " + cardObject.getString("name"));
                        cards.setColor("Color: "   + cardObject.optString("colors"));
                        cards.setType("Type: "     + cardObject.getString("type"));
                        if(!cardObject.getString("rarity").equals("Special")) {
                            cards.setRarity("Rarity: " + cardObject.getString("rarity"));
                        }else {
                            continue;
                        }
                        cards.setImageUrl(cardObject.optString("imageUrl"));
                        cards.setCardID(cardObject.getString("id"));
                        cardList.add(cards);


                    }

                    cardRecyclerViewAdapter.notifyDataSetChanged();//Important!!
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


}
