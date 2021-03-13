package com.brontocyber.submission5_fahmifarhanhusin.adapter.favorite;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brontocyber.submission5_fahmifarhanhusin.DetailMovieActivity;
import com.brontocyber.submission5_fahmifarhanhusin.DetailTvActivity;
import com.brontocyber.submission5_fahmifarhanhusin.R;
import com.brontocyber.submission5_fahmifarhanhusin.database.pojo.Favorite;
import com.brontocyber.submission5_fahmifarhanhusin.provider.DataProvider;
import com.brontocyber.submission5_fahmifarhanhusin.rest.ApiViewModel;
import com.squareup.picasso.Picasso;

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.FavMovieHolder> {

    Cursor mCursor;

    public FavMovieAdapter(Cursor cursor){
        mCursor = cursor;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv_movies, parent, false);
        final FavMovieHolder holder = new FavMovieHolder(mView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailAcara = new Intent(v.getContext(), DetailMovieActivity.class);
                mCursor.moveToPosition(holder.getAdapterPosition());
                    detailAcara.putExtra(DetailTvActivity.idTv,mCursor.getString(mCursor.getColumnIndexOrThrow(Favorite.COLUMN_MOVIES_ID)));
                    v.getContext().startActivity(detailAcara);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FavMovieHolder holder, final int position) {
        final Favorite fav = new Favorite();
        if (mCursor.moveToPosition(position)) {
                    Picasso.with(holder.itemView.getContext())
                .load(ApiViewModel.IMAGE_URL +
                        mCursor.getString(mCursor.getColumnIndexOrThrow(Favorite.COLUMN_MOVIES_POSTER)))
                .resize(342, 342).into(holder.imageView);
            holder.titleAcara.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(Favorite.COLUMN_MOVIES_TITLE)));
            holder.rilisAcara.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(Favorite.COLUMN_MOVIES_RELEASE)));
            Log.d("ID_COLUMN",mCursor.getString(mCursor.getColumnIndexOrThrow(Favorite.COLUMN_MOVIES_ID)));
            //get id favorite

            fav.setId(mCursor.getInt(mCursor.getColumnIndexOrThrow(Favorite.COLUMN_MOVIES_ID)));
        }
        //remove favorite
        holder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().getContentResolver().delete(Uri.parse(DataProvider.CONTENT_URI_MOVIE +"/"+fav.getId()),null, null);
                Toast.makeText(v.getContext(),v.getContext().getString(R.string.remove_fav), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    public class FavMovieHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageButton favBtn;
        TextView titleAcara, rilisAcara;


        public FavMovieHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleAcara = itemView.findViewById(R.id.judul);
            rilisAcara = itemView.findViewById(R.id.rilis);
            favBtn = itemView.findViewById(R.id.fav_button);
        }
    }
}
