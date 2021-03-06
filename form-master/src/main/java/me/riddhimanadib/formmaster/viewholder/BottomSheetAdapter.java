package me.riddhimanadib.formmaster.viewholder;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.riddhimanadib.formmaster.R;

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.ItemHolder> {

    private Context mContext;
    private CharSequence[] mList;
    private String mDefaultValue;
    private OnItemClickListener onItemClickListener;
    private boolean[] mSelectedOptions;

    public BottomSheetAdapter(Context context, CharSequence[] list) {
        this.mContext = context;
        this.mList = list;
    }

    public BottomSheetAdapter(Context context, CharSequence[] list, boolean[] selectedOptions) {
        this.mContext = context;
        this.mList = list;
        this.mSelectedOptions = selectedOptions;
    }

    /**
     * Getter for on item click listener
     *
     * @return
     */
    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    /**
     * Setter for on item click listener
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
    /**
     * Setting default value for selected option.
     *
     * @param defaultValue
     */
    public void setDefaultKey(String defaultValue) {
        mDefaultValue = defaultValue;
    }

    @Override
    public BottomSheetAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_list_item, parent, false);
        return new ItemHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(final BottomSheetAdapter.ItemHolder holder, int position) {

        final CharSequence item = mList[position];
        holder.tvMenuTitle.setText(item);

        if (mSelectedOptions != null){
            holder.checkBox.setVisibility(View.VISIBLE);
        }else {
            holder.checkBox.setVisibility(View.GONE);
        }

        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final OnItemClickListener listener = holder.adapter.getOnItemClickListener();
                if (holder.checkBox.isChecked()) {
                    holder.checkBox.setChecked(false);
                }else{
                    holder.checkBox.setChecked(true);
                }
                if (listener != null) {
                    listener.onItemClick(item, holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.length;
    }


    /**
     * @ItemHolder for handling adaptor views
     */
    public static class ItemHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvMenuTitle;
        AppCompatCheckBox checkBox;
        View mainView;
        private BottomSheetAdapter adapter;

        public ItemHolder(View itemView, BottomSheetAdapter parent) {
            super(itemView);
            this.adapter = parent;
            mainView = itemView;
            tvMenuTitle = (AppCompatTextView) itemView.findViewById(R.id.tvMenuTitle);
            checkBox = (AppCompatCheckBox) itemView.findViewById(R.id.tvCheckbox);

        }
    }

    /**
     * @OnItemClickListener interface for handling on option change event.
     */
    public interface OnItemClickListener {
        void onItemClick(CharSequence value, int position);
    }
}
