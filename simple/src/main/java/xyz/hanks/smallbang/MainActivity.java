package xyz.hanks.smallbang;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import xyz.hanks.library.bang.SmallBangView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(this, "ca-app-pub-8165670162444117~2721303476");
        setContentView(R.layout.activity_main);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        final SmallBangView like_heart = findViewById(R.id.like_heart);
        like_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (like_heart.isSelected()) {
                    like_heart.setSelected(false);
                } else {
                    like_heart.setSelected(true);
                    like_heart.likeAnimation(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            toast("heart+1");
                        }
                    });
                }
            }
        });

        final SmallBangView like_text = findViewById(R.id.like_text);
        like_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (like_text.isSelected()) {
                    like_text.setSelected(false);
                } else {
                    like_text.setSelected(true);
                    like_text.likeAnimation();
                }
            }
        });




    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list:
                startActivity(new Intent(this, ListActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
