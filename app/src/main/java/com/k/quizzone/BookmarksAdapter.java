package com.k.quizzone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.viewHolder>  {


   private List<SoruModel> soruModelList;


   public BookmarksAdapter(List<SoruModel> sorumodeli) {
           this.soruModelList = sorumodeli;
           }

   @NonNull
   @Override
   public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmarks_item,parent,false);
           return new viewHolder(view);
           }

   @Override
   public void onBindViewHolder(@NonNull viewHolder holder, int position) {

           holder.setData(soruModelList.get(position).getSoru(),soruModelList.get(position).getCevap(),position);
           }

   @Override
   public int getItemCount() {
           return soruModelList.size();
           }

   class viewHolder extends RecyclerView.ViewHolder{

       private TextView sorutxt,cevaptxt;
       private ImageButton deleteBtn;

       public viewHolder(@NonNull View itemView) {
           super(itemView);
           sorutxt=itemView.findViewById(R.id.soru);
           cevaptxt=itemView.findViewById(R.id.cevap);
           deleteBtn=itemView.findViewById(R.id.deletebtn);
       }

       private void setData(String soru,String cevap,final int position){

           this.sorutxt.setText(soru);
           this.cevaptxt.setText(cevap);
           deleteBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   soruModelList.remove(position);
                   notifyItemRemoved(position);
               }
           });

       }
   }

   }
