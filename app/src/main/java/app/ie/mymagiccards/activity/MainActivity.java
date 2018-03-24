package app.ie.mymagiccards.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    //Set up variables
    private FirebaseDatabase                database;
    private DatabaseReference               databaseReference;
    private FirebaseAuth                    mAuth;
    private FirebaseAuth.AuthStateListener  mAuthListener;
    private RecyclerView                    recyclerView;
    private CardRecyclerViewAdapter         cardRecyclerViewAdapter;
    private List<Cards>                     cardList;
    private RequestQueue                    queue;
    private AlertDialog.Builder             alertDialog;
    private AlertDialog                     dialog;
    private EditText                        newSearch;
    private Spinner                         spinner, colorSpinner;
    private ArrayAdapter                    spinnerAdapter;
    private Button                          searchBtn;
    private boolean                         blankName, blankType, blankColor;
    private static final String TAG =       "MainActivity";



    // all looks the same.








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logomagicnew);
        getSupportActionBar().setTitle("");

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Eoin");

        databaseReference.child("testing").setValue("Hello Eoin There");




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Toast.makeText(MainActivity.this, value, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.i(TAG, "User signed in");
                }else{
                    Log.i(TAG, "User signed out");
                }
            }
        };

        //Get the queue ready for a HTTP request
        queue = Volley.newRequestQueue(this);




        //Set up recyclerview for input
        recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //Save a string for your search results
        Prefs prefs = new Prefs(MainActivity.this);
        String search = prefs.getSearch();


        //add cardlist to recyclerview
        cardList = new ArrayList<>();
        cardList = getCards();
        cardRecyclerViewAdapter = new CardRecyclerViewAdapter(this, cardList);
        recyclerView.setAdapter(cardRecyclerViewAdapter);
        cardRecyclerViewAdapter.notifyDataSetChanged();//update the recycler view to the changes

    }//end onCreate Method

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

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
                // TODO: 18/03/2018 Delete card to Decks method
                break;
            case R.id.add :
                String email = "20074820@mail.wit.ie";
                String password = "123456";

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()){
                                      //  Log.i(TAG, databaseReference.toString());
                                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                        databaseReference.setValue("It Didnt Work");
                                    }else{
                                        Toast.makeText(MainActivity.this, "Signed in", Toast.LENGTH_SHORT).show();
                                        databaseReference.setValue("Hey I Got In");
                                    }
                                }
                            });
            }
        return super.onOptionsItemSelected(item);
    }

    public List<Cards> getCards(){
        //Send a http request to server to retrive card list
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //loop trough the data array to pull out the needed information
                    JSONArray cardInformation = response.getJSONArray("data");

                    //Loop trough the cards array and pull back information and attach it to setters
                    for (int i = 0; i < cardInformation.length(); i++) {
                        JSONObject cardObject = cardInformation.getJSONObject(i);
                        JSONObject images = cardObject.optJSONObject("image_uris");
                        Cards cards = new Cards();
                        cards.setName("Name: "     + cardObject.getString("name"));
                        cards.setColor("Color: "   + cardObject.optString("colors"));
                        //cards.setType("Type: "     + cardObject.getString("type"));

                        if(!cardObject.getString("image_uris").isEmpty()) {
                            cards.setImageUrl(images.optString("normal"));
                        }else {

                            continue;
                        }
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
                Toast.makeText(MainActivity.this, "Request could not be gotten, Please try again later", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);

        return cardList;

    }



    public void showInputDialog(){
        //Build custom alert dialog
        alertDialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_view, null);
        newSearch = view.findViewById(R.id.searchEdt);
        searchBtn = view.findViewById(R.id.submitBtn);
        spinner = view.findViewById(R.id.searchTypeBox);
        colorSpinner = view.findViewById(R.id.colorPickerSpinner);

        //attach spinners to an addapter
        spinnerAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.type_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.color_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(spinnerAdapter);
        alertDialog.setView(view);
        dialog = alertDialog.create();
        dialog.show();

        //Search for cards
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
        Constants.SEARCH_CARD = "https://api.scryfall.com/cards/search?q=";
        dialog.dismiss();
    }


    //search cards by all fields
    public void searchByAll(){
        if(!(newSearch.getText().toString().isEmpty()) && (spinner.getSelectedItem().toString() != "Please Select a Type") && (colorSpinner.getSelectedItem().toString() != "Please Select a Colour")){
            Constants.SEARCH_CARD += "type:" + spinner.getSelectedItem().toString() +
                                     "&color:" + colorSpinner.getSelectedItem().toString() ;
            Prefs pref = new Prefs(MainActivity.this);
            String search = Constants.SEARCH_CARD;
            Log.i("Searcher", search);
            pref.setSearch(search);
            searchFunction();

        }else{
            dialog.dismiss();

        }
    }

    //search card by name
    public void searchByName(){
        if(!(newSearch.getText().toString().isEmpty())){
            Constants.SEARCH_CARD += "name&q=" + newSearch.getText().toString();
            Prefs pref = new Prefs(MainActivity.this);
            String search = Constants.SEARCH_CARD;
            Log.i("Searcher", search);
            pref.setSearch(search);
           searchFunction();
        }else{
            dialog.dismiss();

        }
    }

    //search card by name and type
    public void searchByNameAndType(){
        if(!newSearch.getText().toString().isEmpty() && spinner.getSelectedItem().toString() != "Please Select a Type"){
            Constants.SEARCH_CARD += "name="+newSearch.getText().toString()+ "&type=" + spinner.getSelectedItem().toString();
            Prefs pref = new Prefs(MainActivity.this);
            String search = Constants.SEARCH_CARD;

            pref.setSearch(search);
            searchFunction();
        }
    }

    //search card by name and color
    public void searchByNameAndcolor(){
        if(!newSearch.getText().toString().isEmpty() && colorSpinner.getSelectedItem().toString() != "Please Select a Colour"){
            Constants.SEARCH_CARD += "name="+newSearch.getText().toString()+ "&color=" + colorSpinner.getSelectedItem().toString();
            Prefs pref = new Prefs(MainActivity.this);
            String search = Constants.SEARCH_CARD;

            pref.setSearch(search);
            searchFunction();
        }
    }

    //search card by type and color
    public void searchByTypeAndcolor(){
        if(spinner.getSelectedItem().toString() != "Please Select a Type" && colorSpinner.getSelectedItem().toString() != "Please Select a Colour"){
            Constants.SEARCH_CARD += "type:"+spinner.getSelectedItem().toString()+ "+color:" + colorSpinner.getSelectedItem().toString();
            Prefs pref = new Prefs(MainActivity.this);
            String search = Constants.SEARCH_CARD;
            Log.d("URL Search", search + " claaed from searchbytypeand color");

            pref.setSearch(search);
            searchFunction();
        }
    }




    //search card by type
    public void searchByType(){
        if(spinner.getSelectedItem().toString() != "Please Select a Type"){
            Constants.SEARCH_CARD += "t:" + spinner.getSelectedItem().toString();
            Prefs pref = new Prefs(MainActivity.this);
            String search = Constants.SEARCH_CARD;
            Log.d("URL Search", search);
            pref.setSearch(search);
            searchFunction();
        }else{
            dialog.dismiss();

        }
    }

    //search card by color
    public void searchByColor(){
        String myColor = "";
        if(colorSpinner.getSelectedItem().toString() != "Please Select a Colour"){
            Constants.SEARCH_CARD += "color:" + colorSpinner.getSelectedItem().toString();
            Prefs pref = new Prefs(MainActivity.this);
            String search = Constants.SEARCH_CARD;
            pref.setSearch(search);
            searchFunction();
        }else{
            dialog.dismiss();

        }
    }


    //not sure if needed
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

    //not sure if needed
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
                    JSONArray cardInformation = response.getJSONArray("data");


                    for (int i = 0; i < cardInformation.length(); i++) {
                        JSONObject cardObject = cardInformation.getJSONObject(i);
                        JSONObject images = cardObject.optJSONObject("image_uris");
                        Cards cards = new Cards();
                        cards.setName("Name: " + cardObject.getString("name"));
                        cards.setColor("Color: "   + cardObject.optString("colors"));
//                        cards.setType("Type: "     + cardObject.getString("type"));
//                        if(!cardObject.getString("rarity").equals("Special")) {
//                            cards.setRarity("Rarity: " + cardObject.getString("rarity"));
//                        }else {
//                            continue;
//                        }
                        if(!cardObject.optString("image_uris").equals("")){
                            cards.setImageUrl(images.optString("normal"));
                        }else{
                            continue;
                        }

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
