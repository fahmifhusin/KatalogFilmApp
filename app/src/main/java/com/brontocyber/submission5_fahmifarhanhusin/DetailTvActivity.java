package com.brontocyber.submission5_fahmifarhanhusin;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.brontocyber.submission5_fahmifarhanhusin.adapter.detail.DetailTvAdapter;
import com.brontocyber.submission5_fahmifarhanhusin.model.Results;
import com.brontocyber.submission5_fahmifarhanhusin.rest.ApiViewModel;
import com.brontocyber.submission5_fahmifarhanhusin.rest.DetailTvInterface;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTvActivity extends AppCompatActivity {

    public static String idTv;
    RecyclerView rvDetail;
    DetailTvAdapter adapter;
    Results results;
    ProgressBar progressBar;
    String id, bahasa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);

        rvDetail = findViewById(R.id.rv_details);
        progressBar = findViewById(R.id.progressDetails);
        id = getIntent().getStringExtra(idTv);
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("en")){
            bahasa = "en";
        }else if (Locale.getDefault().getLanguage().equalsIgnoreCase("in")){
            bahasa = "id";
        }

            showLoad(true);

        //menambahkan view model
        ApiViewModel model = ViewModelProviders.of(this).get(ApiViewModel.class);
        model.getDetailTvCache().observe(this, new Observer<Results>() {
            @Override
            public void onChanged(@Nullable Results daftarAcara) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                rvDetail.setLayoutManager(layoutManager);
                adapter = new DetailTvAdapter(daftarAcara);
                rvDetail.setAdapter(adapter);
                showLoad(false);
            }
        });
        loadDetail();

    }

    public void loadDetail() {
        DetailTvInterface apiInterface = ApiViewModel.getClient().create(DetailTvInterface.class);
        Call<Results> call = apiInterface.getAcaraDetail(
                id, bahasa
        );
        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                results = response.body();
                ApiViewModel cache = new ApiViewModel();
                cache.ACARA_SHOW.setValue(results);

            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
//                Toast.makeText(DetailTvActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoad(Boolean state) {
        if (state == true) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
