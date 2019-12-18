package com.MS.applications.UnlimitedServicesDriver.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.MS.applications.UnlimitedServicesDriver.Models.TaxiRequest;
import com.MS.applications.UnlimitedServicesDriver.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class TaxiRequestsAdapter extends RecyclerView.Adapter<TaxiRequestsAdapter.ExampleViewHolder> {
    private Context mContext;
    private TaxiRequest[] mRequestsList;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(FragmentActivity activity) {
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public TaxiRequestsAdapter(Context context, TaxiRequest[] exampleList) {
        mContext = context;
        mRequestsList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.history_taxi_request_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        TaxiRequest currentItem = mRequestsList[position];
        holder.mTextViewName.setText(currentItem.getDriverName());
        holder.mTextViewCapacity.setText(String.format("%d ", currentItem.getStatus()));
        holder.mTextViewModel.setText(currentItem.getTaxiModel());
        holder.mTextViewNumber.setText(String.format("%s ", currentItem.getTaxiNumber()));
        holder.mTextViewLicence.setVisibility(View.INVISIBLE);
        holder.mTextViewLicenceName.setVisibility(View.INVISIBLE);
        holder.mTextViewPriceKm.setText("");

        holder.commT.setText(currentItem.getComment() != null ? currentItem.getComment() : "");
        holder.comT.setText(currentItem.getCompletedAt() != null ? currentItem.getCompletedAt() : "");
        holder.fromT.setText(String.format("%s, %s", currentItem.getFromLat(), currentItem.getFromLng()));
        holder.toT.setText(String.format("%s, %s", currentItem.getToLat(), currentItem.getToLng()));
        holder.arrT.setText(currentItem.getArrivedAt() != null ? currentItem.getArrivedAt() : "");
        holder.onT.setText(currentItem.getOnGoingAt() != null ? currentItem.getOnGoingAt() : "");
        holder.acceptT.setText(currentItem.getAcceptedAt() != null ? currentItem.getAcceptedAt() : "");
        holder.rejectT.setText(currentItem.getRejectedAt() != null ? currentItem.getRejectedAt() : "");

        switch (currentItem.getStatus()){
            case -2:
                holder.background.setBackgroundColor(mContext.getResources().getColor(R.color.red));
                holder.mTextViewCapacity.setText(mContext.getResources().getString(R.string.canceled));
                holder.mTextViewCapacity.setTextColor(mContext.getResources().getColor(R.color.red));
                break;
            case -1:
                holder.background.setBackgroundColor(mContext.getResources().getColor(R.color.red));
                holder.mTextViewCapacity.setText(mContext.getResources().getString(R.string.rejected));
                holder.mTextViewCapacity.setTextColor(mContext.getResources().getColor(R.color.red));
                break;
            case 0:
                holder.background.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                holder.mTextViewCapacity.setText(mContext.getResources().getString(R.string.pending));
                holder.mTextViewCapacity.setTextColor(mContext.getResources().getColor(R.color.yellow));
                break;
            case 1:
                holder.background.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                holder.mTextViewCapacity.setText(mContext.getResources().getString(R.string.accepted));
                holder.mTextViewCapacity.setTextColor(mContext.getResources().getColor(R.color.green));
                break;
            case 2:
                holder.background.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                holder.mTextViewCapacity.setText(mContext.getString(R.string.on_going));
                holder.mTextViewCapacity.setTextColor(mContext.getResources().getColor(R.color.green));
                break;
            case 3:
                holder.background.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                holder.mTextViewCapacity.setText(mContext.getResources().getString(R.string.arrived));
                holder.mTextViewCapacity.setTextColor(mContext.getResources().getColor(R.color.green));
                break;
            case 4:
                holder.background.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                holder.mTextViewCapacity.setText(mContext.getString(R.string.completed));
                holder.mTextViewCapacity.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                break;
            default:
                holder.background.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                holder.mTextViewCapacity.setText(mContext.getResources().getString(R.string.completed));
                holder.mTextViewCapacity.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                break;
        }

        String imgUrl = currentItem.getTaxiImage();
        Glide.with(holder.mImageUser.getContext()).load(imgUrl).apply(
                new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(R.drawable.user)
                        .error(R.drawable.user)
        ).into(holder.mImageUser);

        holder.openDetails.setOnClickListener(view -> {
            holder.openDetails.setVisibility(View.GONE);
            holder.closeDetails.setVisibility(View.VISIBLE);
            holder.loc.setVisibility(View.VISIBLE);
            switch (currentItem.getStatus()){
                case -1:
                    holder.rej.setVisibility(View.VISIBLE);
                    holder.acc.setVisibility(View.GONE);
                    break;
                default:
                    holder.rej.setVisibility(View.GONE);
                    holder.acc.setVisibility(View.VISIBLE);
                    break;
            }
        });

        holder.closeDetails.setOnClickListener(view -> {
            holder.openDetails.setVisibility(View.VISIBLE);
            holder.closeDetails.setVisibility(View.GONE);
            holder.loc.setVisibility(View.GONE);
            holder.rej.setVisibility(View.GONE);
            holder.acc.setVisibility(View.GONE);
        });
    }

    @Override
    public int getItemCount() {
        return mRequestsList.length;
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewName;
        public TextView mTextViewCapacity;
        public TextView mTextViewModel;
        public TextView mTextViewNumber;
        public TextView mTextViewLicence;
        public TextView mTextViewLicenceName;
        public TextView mTextViewPriceKm;
        public ImageView mImageUser;
        public ImageView openDetails;
        public Button background;
        public ImageView closeDetails;
        public TableLayout acc;
        public TableLayout rej;
        public TableLayout loc;
        public TextView fromT, toT, acceptT, rejectT, onT, arrT, comT, commT;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mTextViewName = itemView.findViewById(R.id.name);
            mTextViewCapacity = itemView.findViewById(R.id.capacity);
            mTextViewModel = itemView.findViewById(R.id.model);
            mTextViewNumber = itemView.findViewById(R.id.number);
            mTextViewLicence = itemView.findViewById(R.id.licence_date);
            mTextViewLicenceName = itemView.findViewById(R.id.licence_date_label);
            mTextViewPriceKm = itemView.findViewById(R.id.price_km);
            mImageUser = itemView.findViewById(R.id.img);
            openDetails = itemView.findViewById(R.id.open_details);
            background = itemView.findViewById(R.id.background);
            closeDetails = itemView.findViewById(R.id.close_details);
            acc = itemView.findViewById(R.id.accept_more_table);
            rej = itemView.findViewById(R.id.reject_more);
            loc = itemView.findViewById(R.id.more_loc);
            fromT = itemView.findViewById(R.id.from_txt);
            toT = itemView.findViewById(R.id.to_txt);
            acceptT = itemView.findViewById(R.id.accept_txt);
            rejectT = itemView.findViewById(R.id.reject_txt);
            onT = itemView.findViewById(R.id.going_txt);
            arrT = itemView.findViewById(R.id.arrive_txt);
            commT = itemView.findViewById(R.id.comment_txt);
            comT = itemView.findViewById(R.id.complete_txt);

            openDetails.setOnClickListener(v -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(position);
                    }
                }
            });

            closeDetails.setOnClickListener(v -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(position);
                    }
                }
            });
        }
    }
}
