package com.xiongxh.cryptocoin.coins;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiongxh.cryptocoin.R;
import com.xiongxh.cryptocoin.data.CoinDbContract.CoinEntry;
import com.xiongxh.cryptocoin.utilities.ConstantsUtils;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinViewHolder> {
    private Cursor mCursor;
    private Context mContext;

    private final CoinAdapterOnclickHandler mClickHandler;

//    public CoinAdapter(@NonNull Context context, @NonNull Cursor cursor) {
//        mContext = context;
//        mCursor = cursor;
//    }

    public CoinAdapter(@NonNull Context context, @NonNull CoinAdapterOnclickHandler handler) {
        this.mContext = context;
        this.mClickHandler = handler;
    }

    @Override
    public long getItemId(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getLong(ConstantsUtils.POSITION_ID);
    }

    public String getSymbolAtPosition(int position){
        mCursor.moveToPosition(position);
        return mCursor.getString(ConstantsUtils.POSITION_SYMBOL);
    }

    @Override
    public CoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_coin_basic, parent, false);

        final CoinViewHolder viewHolder = new CoinViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CoinViewHolder holder, int position) {
        if (!mCursor.moveToFirst()) {
            return;
        }

        mCursor.moveToPosition(position);

        String coinSymbol = mCursor.getString(ConstantsUtils.POSITION_SYMBOL);

        DecimalFormat df = new DecimalFormat(".##");

        double price = mCursor.getDouble(ConstantsUtils.POSITION_PRICE);
        double trend = mCursor.getDouble(ConstantsUtils.POSITION_TREND);

        if (trend >= 0.0 ){
            holder.mChangeTextView.setBackground(
                    mContext.getResources().getDrawable(R.drawable.price_increase_green));
        }else {
            holder.mChangeTextView.setBackground(
                    mContext.getResources().getDrawable(R.drawable.price_decrease_red));
        }

        holder.mSymbolTextView.setText(coinSymbol);
        holder.mPriceTextView.setText("$" + price);
        holder.mChangeTextView.setText(df.format(trend) + "%");
    }

    @Override
    public int getItemCount() {
        if (mCursor != null){
            return mCursor.getCount();
        }
        return 0;
    }

    public void swapCursor(Cursor cursor){
        mCursor = cursor;
        notifyDataSetChanged();
    }

    class CoinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.symbol)
        TextView mSymbolTextView;
        @BindView(R.id.price)
        TextView mPriceTextView;
        @BindView(R.id.change)
        TextView mChangeTextView;

        public CoinViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);

            int symbolIdx = mCursor.getColumnIndex(CoinEntry.COLUMN_SYMBOL);
            int nameIdx = mCursor.getColumnIndex(CoinEntry.COLUMN_NAME);
            mClickHandler.onClick(mCursor.getString(symbolIdx), mCursor.getString(nameIdx));
        }

//        public void enableOnClick(){
//            itemView.setOnClickListener(this);
//        }
//
//        public void disableOnclick(){
//            itemView.setOnClickListener(null);
//        }
    }

    interface CoinAdapterOnclickHandler{
        void onClick(String symbol, String name);
    }
}