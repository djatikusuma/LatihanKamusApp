package me.djatikusuma.kamusku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String WORD = "ITEM_WORD";
    public static final String TRANSLATE = "ITEM_TRANSLATE";

    @BindView(R.id.tv_word)
    TextView tvWord;

    @BindView(R.id.tv_translate)
    TextView tvTranslate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tvWord.setText(getIntent().getStringExtra(WORD));
        tvTranslate.setText(getIntent().getStringExtra(TRANSLATE));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
