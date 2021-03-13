package com.brontocyber.submission5_fahmifarhanhusin.adapter.main;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brontocyber.submission5_fahmifarhanhusin.DetailMovieActivity;
import com.brontocyber.submission5_fahmifarhanhusin.R;
import com.brontocyber.submission5_fahmifarhanhusin.database.pojo.Favorite;
import com.brontocyber.submission5_fahmifarhanhusin.model.Results;
import com.brontocyber.submission5_fahmifarhanhusin.provider.DataProvider;
import com.brontocyber.submission5_fahmifarhanhusin.rest.ApiViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListMoviesAdapter extends RecyclerView.Adapter<ListMoviesAdapter.AcaraViewHolder> {

    private List<Results> apiModelList;
    public ListMoviesAdapter(List<Results> apiModelList) {
        this.apiModelList = apiModelList;
    }

    @Override
    public AcaraViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv_movies, parent, false);
       final AcaraViewHolder holder = new AcaraViewHolder(mView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(v.getContext(), DetailMovieActivity.class);
                detail.putExtra(DetailMovieActivity.idAcara, apiModelList.get(holder.getAdapterPosition()).getId());
                v.getContext().startActivity(detail);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final AcaraViewHolder holder, final int position) {
        final Results results = apiModelList.get(position);
        Picasso.with(holder.itemView.getContext())
                .load(ApiViewModel.IMAGE_URL + results.getPoster_path())
                .resize(342, 342).into(holder.imageView);
        holder.titleAcara.setText(results.getTitle());
        holder.rilisAcara.setText(results.getRelease_date());
        //add | remove favorite
        holder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(results.getId());
                String favPoster = results.getPoster_path();
                String favTitle = results.getTitle();
                String favRelease = results.getRelease_date();

                ContentValues values = new ContentValues();
                values.put(Favorite.COLUMN_MOVIES_ID, id);
                values.put(Favorite.COLUMN_MOVIES_POSTER, favPoster);
                values.put(Favorite.COLUMN_MOVIES_TITLE, favTitle);
                values.put(Favorite.COLUMN_MOVIES_RELEASE, favRelease);
                try{
                        v.getContext().getContentResolver().insert(DataProvider.CONTENT_URI_MOVIE,values);
                        Toast.makeText(v.getContext(), v.getContext().getString(R.string.add_fav), Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                    e.getMessage();
                    v.getContext().getContentResolver().delete(Uri.parse(DataProvider.CONTENT_URI_MOVIE +"/"+id),null, null);
                    Toast.makeText(v.getContext(), v.getContext().getString(R.string.remove_fav), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return apiModelList.size();
    }

    public class AcaraViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ImageButton favBtn;
        TextView titleAcara, rilisAcara;

        public AcaraViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleAcara = itemView.findViewById(R.id.judul);
            rilisAcara = itemView.findViewById(R.id.rilis);
            favBtn = itemView.findViewById(R.id.fav_button);
        }
    }
}
