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
import com.brontocyber.submission5_fahmifarhanhusin.adapter.main.ListMoviesAdapter;
import com.brontocyber.submission5_fahmifarhanhusin.database.FavMoviesDb;
import com.brontocyber.submission5_fahmifarhanhusin.model.RequestAcara;
import com.brontocyber.submission5_fahmifarhanhusin.model.Results;
import com.brontocyber.submission5_fahmifarhanhusin.rest.ApiViewModel;
import com.brontocyber.submission5_fahmifarhanhusin.rest.MoviesInterface;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {


    static RecyclerView rvMovies;
    static ProgressBar progressMovies;
    ListMoviesAdapter adapterMovies;
    RecyclerView.LayoutManager layoutManager;
    String bahasa;
    private List<Results> dataMovies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        progressMovies = view.findViewById(R.id.progressMovie);
        rvMovies = view.findViewById(R.id.rv_movies);

        if (Locale.getDefault().getLanguage().equalsIgnoreCase("en")) {
            bahasa = "en";
        } else if (Locale.getDefault().getLanguage().equalsIgnoreCase("in")) {
            bahasa = "id";
        }
        showLoading(true);
        //menambahkan view model
        ApiViewModel model = ViewModelProviders.of(this).get(ApiViewModel.class);
        model.getMovieCache().observe(this, new Observer<List<Results>>() {
            @Override
            public void onChanged(@Nullable List<Results> daftarAcara) {
                layoutManager = new LinearLayoutManager(getActivity());
                rvMovies.setLayoutManager(layoutManager);
                adapterMovies = new ListMoviesAdapter(daftarAcara);
                rvMovies.setAdapter(adapterMovies);
                showLoading(false);
            }
        });

        loadMovies();
        return view;

    }

    //search data
    public void searchDataMovie(String data) {
        MoviesInterface apiSearch = ApiViewModel.getClient().create(MoviesInterface.class);
        Call<RequestAcara> cari = apiSearch.searchMovie(
                bahasa, data
        );
        cari.enqueue(new Callback<RequestAcara>() {
            @Override
            public void onResponse(Call<RequestAcara> call, Response<RequestAcara> response) {
                try{List<Results> hasil = response.body().getListAcara();
                layoutManager = new LinearLayoutManager(getActivity());
                rvMovies.setLayoutManager(layoutManager);
                adapterMovies = new ListMoviesAdapter(hasil);
                rvMovies.setAdapter(adapterMovies);
                }catch (Exception e){
                    e.getMessage();
                    loadMovies();
                }
            }

            @Override
            public void onFailure(Call<RequestAcara> call, Throwable t) {

            }
        });
    }

    public void loadMovies() {
        MoviesInterface apiInterface = ApiViewModel.getClient().create(MoviesInterface.class);
        Call<RequestAcara> call = apiInterface.getMovies(
                bahasa
        );
        call.enqueue(new Callback<RequestAcara>() {
            @Override
            public void onResponse(Call<RequestAcara> call, Response<RequestAcara> response) {
                dataMovies = response.body().getListAcara();

                ApiViewModel cache = new ApiViewModel();
                cache.ACARA_MOVIE.setValue(dataMovies);
            }

            @Override
            public void onFailure(Call<RequestAcara> call, Throwable t) {
                try{
                    Toast.makeText(getContext(), "Something Wrong", Toast.LENGTH_LONG).show();
                    Log.d("status", "FAILED");
                }
                catch (Exception e){
                    e.getMessage();
                }

            }
        });
    }

    private void showLoading(Boolean state) {
        if (state == true) {
            progressMovies.setVisibility(View.VISIBLE);
        } else {
            progressMovies.setVisibility(View.GONE);
        }
    }

}
