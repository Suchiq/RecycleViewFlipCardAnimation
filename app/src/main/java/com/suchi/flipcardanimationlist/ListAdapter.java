package com.suchi.flipcardanimationlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder> {

    private List<ListData> list;
    private Context context;
    RelativeLayout[] card_back_arr, card_front_arr;

    public ListAdapter(Context context, List<ListData> equipos) {
        this.list = equipos;
        this.context = context;
        card_back_arr = new RelativeLayout[equipos.size()];
        card_front_arr = new RelativeLayout[equipos.size()];

        EventBus.getDefault().register(this);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_front, txt_back;
        RelativeLayout card_back, card_front;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txt_front = (TextView) itemView.findViewById(R.id.item_tx__front);
            txt_back = (TextView) itemView.findViewById(R.id.item_tx_back);
            card_front = (RelativeLayout) itemView.findViewById(R.id.card_front);
            card_back = (RelativeLayout) itemView.findViewById(R.id.card_back);
        }

        @Override
        public void onClick(View v) {

        }
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.txt_front.setText(list.get(position).getFrontText());
        holder.txt_back.setText(list.get(position).getBackText());
        card_back_arr[position] = holder.card_back;
        card_front_arr[position] = holder.card_front;

    }

    @Subscribe
    public void onEvent(EventData data) {
        FlipAnimator.flipView(context, card_back_arr[data.getPosition()], card_front_arr[data.getPosition()], data.isFront(), data.getPosition());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
