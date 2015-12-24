package xyz.hanks.smallbang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;

public class MainActivity extends AppCompatActivity {

    private SmallBang mSmallBang;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSmallBang = SmallBang.attach2Window(this);
        mImage = (ImageView)findViewById(R.id.image);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(v);
            }
        });

    }

    public void show(View view){
        mSmallBang.bang(view);
        mSmallBang.setListener(new SmallBangListener() {
            @Override
            public void onAnimationStart() {
                mImage.setImageResource(R.drawable.heart_red);
            }

            @Override
            public void onAnimationEnd() {
                toast("+1");
            }
        });
    }

    private void toast(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}
