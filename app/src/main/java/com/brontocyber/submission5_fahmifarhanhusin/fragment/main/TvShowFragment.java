package com.brontocyber.submission5_fahmifarhanhusin.fragment.main;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.brontocyber.submission5_fahmifarhanhusin.R;
import com.brontocyber.submission5_fahmifarhanhusin.adapter.main.ListTvAdapter;
import com.brontocyber.submission5_fahmifarhanhusin.database.FavMoviesDb;
import com.brontocyber.submission5_fahmifarhanhusin.model.RequestAcara;
import com.brontocyber.submission5_fahmifarhanhusin.model.Results;
import com.brontocyber.submission5_fahmifarhanhusin.rest.ApiViewModel;
import com.brontocyber.submission5_fahmifarhanhusin.rest.TvShowInterface;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    static RecyclerView rvTvShow;
    ListTvAdapter adapterTv;
    RecyclerView.LayoutManager layoutManager;
    private List<Results> dataTv;
    static ProgressBar progressTv;
    String bahasa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        progressTv = view.findViewById(R.id.progressTv);
        rvTvShow = view.findViewById(R.id.rv_tv_show);

        if (Locale.getDefault().getLanguage().equalsIgnoreCase("en")){
            bahasa = "en";
        }else if (Locale.getDefault().getLanguage().equalsIgnoreCase("in")){
            bahasa = "id";
        }
        showLoading(true);
        ApiViewModel model = ViewModelProviders.of(this).get(ApiViewModel.class);
        model.getTvShowCache().observe(this, new Observer<List<Results>>() {
            @Override
            public void onChanged(@Nullable List<Results> daftarAcara) {
                layoutManager = new LinearLayoutManager(getActivity());
                rvTvShow.setLayoutManager(layoutManager);
                adapterTv = new ListTvAdapter(daftarAcara);
                rvTvShow.setAdapter(adapterTv);
                showLoading(false);
            }
        });

        loadTvShow();
        return view;
    }

    public void searchDataTv(String data) {
        TvShowInterface apiSearch = ApiViewModel.getClient().create(TvShowInterface.class);
        Call<RequestAcara> cari = apiSearch.searchTv(
                bahasa, data
        );
        cari.enqueue(new Callback<RequestAcara>() {
            @Override
            public void onResponse(Call<RequestAcara> call, Response<RequestAcara> response) {
                try{List<Results> hasil = response.body().getListAcara();
                    adapterTv = new ListTvAdapter(hasil);
                    rvTvShow.setAdapter(adapterTv);
                }catch (Exception e){
                    e.getMessage();
                    loadTvShow();
                }
            }

            @Override
            public void onFailure(Call<RequestAcara> call, Throwable t) {

            }
        });
    }

    public void loadTvShow() {
        TvShowInterface apiInterface = ApiViewModel.getClient().create(TvShowInterface.class);
        Call<RequestAcara> call = apiInterface.getTvShow(
                bahasa
        );
        call.enqueue(new Callback<RequestAcara>() {
            @Override
            public void onResponse(Call<RequestAcara> call, Response<RequestAcara> response) {
                dataTv = response.body().getListAcara();
                ApiViewModel cache = new ApiViewModel();
                cache.ACARA_TV.setValue(dataTv);
            }

            @Override
            public void onFailure(Call<RequestAcara> call, Throwable t) {
                Toast.makeText(getContext(), "Something Wrong", Toast.LENGTH_LONG).show();
                Log.d("status", "FAILED");
            }
        });

    }

    private void showLoading(Boolean state) {
        if (state == true) {
            progressTv.setVisibility(View.VISIBLE);
        } else {
            progressTv.setVisibility(View.GONE);
        }
    }

}
