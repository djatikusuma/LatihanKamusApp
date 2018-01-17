package me.djatikusuma.kamusku;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.database.SQLException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.djatikusuma.kamusku.Helper.KamusHelper;
import me.djatikusuma.kamusku.Helper.PreferenceHelper;
import me.djatikusuma.kamusku.Model.KamusModel;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.progress_bar)
    MaterialProgressBar progressBar;

    @BindView(R.id.tv_load)
    TextView tvLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);
        new SetDataLoad().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class SetDataLoad extends AsyncTask<Void, Integer, Void> {
        KamusHelper kamusHelper;
        PreferenceHelper preferenceHelper;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            kamusHelper = new KamusHelper(SplashActivity.this);
            preferenceHelper = new PreferenceHelper(SplashActivity.this);
        }

        @SuppressWarnings("WrongThread")
        @Override
        protected Void doInBackground(Void... params) {
            Boolean isFirstRun = preferenceHelper.getFirstRun();
            if (isFirstRun) {
                ArrayList<KamusModel> kamusEnglish = preLoadRaw(R.raw.english_indonesia);
                ArrayList<KamusModel> kamusIndonesia = preLoadRaw(R.raw.indonesia_english);

                publishProgress((int) progress);

                try {
                    kamusHelper.open();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Double progressMaxInsert = 100.0;
                Double progressDiff = (progressMaxInsert - progress) / (kamusEnglish.size() + kamusIndonesia.size());

                kamusHelper.insertTransaction(kamusEnglish, true);
                progress += progressDiff;
                publishProgress((int) progress);

                kamusHelper.insertTransaction(kamusIndonesia, false);
                progress += progressDiff;
                publishProgress((int) progress);

                kamusHelper.close();
                preferenceHelper.setFirstRun(false);

                publishProgress((int) maxprogress);
            } else {
                tvLoad.setVisibility(View.INVISIBLE);
                try {
                    synchronized (this) {
                        this.wait(1000);
                        publishProgress(50);

                        this.wait(300);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception ignored) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);

            finish();
        }
    }

    public ArrayList<KamusModel> preLoadRaw(int data) {
        ArrayList<KamusModel> kamusModels = new ArrayList<>();
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(data);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            String rdline;
            do {
                rdline = reader.readLine();
                String[] wordSplit = rdline.split("\t");
                KamusModel kamusModel;
                kamusModel = new KamusModel(wordSplit[0], wordSplit[1]);
                kamusModels.add(kamusModel);
            } while (rdline != null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return kamusModels;
    }
}
