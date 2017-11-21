package xyz.hanks.smallbang;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import xyz.hanks.library.bang.SmallBangView;

public class ListActivity extends AppCompatActivity {

    private List<Item> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        for (int i = 0; i < 100; i++) {
            Item item = new Item();
            item.content = "this is content : " + i;
            item.liked = i % 5 == 0;
            data.add(item);
        }


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter());
    }


    public static class Item {
        String content;
        boolean liked;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_content;
        SmallBangView smallBang;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
            smallBang = itemView.findViewById(R.id.smallbang);
        }

        public static MyViewHolder newInstance(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new MyViewHolder(view);
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final MyViewHolder holder = MyViewHolder.newInstance(parent, viewType);
            holder.smallBang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Item item = data.get(position);
                    if (item.liked) {
                        item.liked = false;
                        holder.smallBang.setSelected(false);
                    } else {
                        item.liked = true;
                        holder.smallBang.setSelected(true);
                        holder.smallBang.likeAnimation();
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Item item = data.get(position);
            holder.tv_content.setText(item.content);
            holder.smallBang.setSelected(item.liked);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
