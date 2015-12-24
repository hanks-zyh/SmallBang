package xyz.hanks.smallbang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import xyz.hanks.library.SmallBang;

public class MainActivity extends AppCompatActivity {

    private SmallBang smallBang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smallBang = (SmallBang)findViewById(R.id.smallbang);

    }

    public void show(View view){
        smallBang.bang(findViewById(R.id.image));
    }
}
