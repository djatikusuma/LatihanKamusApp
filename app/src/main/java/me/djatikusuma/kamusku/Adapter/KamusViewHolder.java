package me.djatikusuma.kamusku.Adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.djatikusuma.kamusku.DetailActivity;
import me.djatikusuma.kamusku.Model.KamusModel;
import me.djatikusuma.kamusku.R;

/**
 * Created by djatikusuma on 03/01/2018.
 *
 */

public class KamusViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_word)
    TextView tvWord;

    @BindView(R.id.tv_translate)
    TextView tvTranslate;

    @BindView(R.id.cardView)
    CardView cardView;

    public KamusViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final KamusModel item) {
        tvWord.setText(item.getWord());
        tvTranslate.setText(item.getTranslate());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.WORD, item.getWord());
                intent.putExtra(DetailActivity.TRANSLATE, item.getTranslate());
                itemView.getContext().startActivity(intent);
            }
        });
    }
}
