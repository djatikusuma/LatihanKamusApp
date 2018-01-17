package me.djatikusuma.kamusku.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.djatikusuma.kamusku.Model.KamusModel;
import me.djatikusuma.kamusku.R;

/**
 * Created by djatikusuma on 03/01/2018.
 */

public class KamusAdapter extends RecyclerView.Adapter<KamusViewHolder> {

    private ArrayList<KamusModel> list = new ArrayList<>();

    public KamusAdapter() {
    }

    public void replaceAll(ArrayList<KamusModel> items) {
        list = items;
        notifyDataSetChanged();
    }

    @Override
    public KamusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new KamusViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.kamus_list, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(KamusViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return list.size();
    }
}
