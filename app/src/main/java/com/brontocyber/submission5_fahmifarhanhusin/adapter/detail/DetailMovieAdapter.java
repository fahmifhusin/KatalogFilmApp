package com.brontocyber.submission5_fahmifarhanhusin.adapter.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brontocyber.submission5_fahmifarhanhusin.R;
import com.brontocyber.submission5_fahmifarhanhusin.model.Results;
import com.brontocyber.submission5_fahmifarhanhusin.rest.ApiViewModel;
import com.squareup.picasso.Picasso;

public class DetailMovieAdapter extends RecyclerView.Adapter<DetailMovieAdapter.DetailViewHolder> {
    Results respon;

    public DetailMovieAdapter(Results respon){
        this.respon = respon;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        try {
            Picasso.with(holder.itemView.getContext())
                    .load(ApiViewModel.IMAGE_URL + respon.getPoster_path())
                    .resize(342, 342).into(holder.poster);
            holder.title.setText(respon.getTitle());
            holder.release.setText(respon.getRelease_date());
            holder.overview.setText(respon.getOverview());
            holder.overview.setMovementMethod(new ScrollingMovementMethod());
        }catch (Exception e){
            Log.d("isi_adapter",respon.getTitle());
        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView title, release, overview;
        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.detail_gambar);
            title = itemView.findViewById(R.id.detail_judul);
            release = itemView.findViewById(R.id.detail_rilis);
            overview = itemView.findViewById(R.id.detail_deskripsi);

        }
    }
}
