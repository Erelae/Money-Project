package org.robin.money;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.robin.money.api.ExchangeApi;
import org.robin.money.api.RatesData;
import org.robin.money.api.RatesWrapper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyListActivity extends AppCompatActivity {

    private CurrencyAdapter adapter;
    private List<Currency> currencies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);

        // définition de la source données
        // aujourd'hui en dur, mais peut provenir d'une API, BDD, etc.
//        this.currencies.add(new Currency(R.drawable.flag_usa, 1.08f, "$"));
//        this.currencies.add(new Currency(R.drawable.flag_japan, 118.59f, "Y"));

        // initialisation de l'adapter
        adapter = new CurrencyAdapter(this.currencies);

        // initialisation de la vue (RecyclerView)
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // REQUETE HTTP

        // Création du client retrofit
        // il va donc taper sur la baseUrl donnée
        // et parser le résultat en JSON
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.exchangeratesapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Génération de notre API
        // à partir du client retrofit
        ExchangeApi api = retrofit.create(ExchangeApi.class);

        // Création de la requête
        Call<RatesWrapper> call = api.getCurrencies();

        // Exécution de la requête en asynchrone
        call.enqueue(new Callback<RatesWrapper>() {
            @Override
            public void onResponse(Call<RatesWrapper> call, Response<RatesWrapper> response) {
                RatesData body = response.body().rates;
                Log.i("CurrencyListActivity", "onResponse: " + body);

                currencies.add(new Currency(R.drawable.flag_usa, body.USD, "$"));
                currencies.add(new Currency(R.drawable.flag_uk, body.GBP, "£"));
                currencies.add(new Currency(R.drawable.flag_japan, body.JPY, "Y"));

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RatesWrapper> call, Throwable t) {
                Log.e("CurrencyListActivity", "onFailure: ", t);
            }
        });

    }
}
