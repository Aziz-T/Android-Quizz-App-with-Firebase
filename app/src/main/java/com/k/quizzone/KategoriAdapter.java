package com.k.quizzone;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.ViewHolder> {

    private List<KategoriModel> kategoriModelList;

    public KategoriAdapter(List<KategoriModel> kategoriModelList) {
        this.kategoriModelList = kategoriModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kategori_item,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(kategoriModelList.get(position).getUrl(),kategoriModelList.get(position).getBaslik(),kategoriModelList.get(position).getSets());
    }

    @Override
    public int getItemCount() {
        return kategoriModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageView;
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.circleimage);
            textView=itemView.findViewById(R.id.textbaslık);
        }
        private void setData(String url, final String baslik, final int sets){
            Glide.with(itemView.getContext()).load(url).into(imageView);
            this.textView.setText(baslik);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(),SetsActivity.class);
                    intent.putExtra("baslik",baslik);
                    intent.putExtra("sets",sets);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
