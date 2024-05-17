package com.example.elderlyui;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    Context context;
    Joke[] jokeTitles;
    LayoutInflater layoutInflater;
    public CustomAdapter(Context context, Joke[] jokeTitles){

        this.context = context;
        this.jokeTitles = jokeTitles;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return jokeTitles.length;
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        /*design view and return it*/
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.activity_jokes_list_item,null);
        }
        TextView jTitle = (TextView) convertView.findViewById(R.id.jokesListText);
        ImageView img = (ImageView) convertView.findViewById(R.id.jokeImg);

        img.setImageResource(R.drawable.listitem);
        jTitle.setText(jokeTitles[i].getTitle());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), JokeSingleActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //pass the title and the body to the next activity
                myIntent.putExtra("title",jokeTitles[i].getTitle());
                myIntent.putExtra("joke",jokeTitles[i].getBody());
                context.startActivity(myIntent);
            }
        });

        return convertView;
    }
}
