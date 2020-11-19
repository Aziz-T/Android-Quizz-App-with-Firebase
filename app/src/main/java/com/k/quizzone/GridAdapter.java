package com.k.quizzone;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

    private int sets=0;
    private String baslik;

    public GridAdapter(int sets, String baslik) {
        this.sets = sets;
        this.baslik = baslik;
    }

    @Override
    public int getCount() {
        return sets;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, final ViewGroup viewGroup) {
        View view;

        if(convertView==null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.set_item,viewGroup,false);
        }
        else{
            view=convertView;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(viewGroup.getContext(),SoruActivity.class);
                intent.putExtra("kategori",baslik);
                intent.putExtra("sets",i+1);
                viewGroup.getContext().startActivity(intent);

            }
        });
        ((TextView)view.findViewById(R.id.question_txtview)).setText(String.valueOf(i+1));
        return view;
    }
}
