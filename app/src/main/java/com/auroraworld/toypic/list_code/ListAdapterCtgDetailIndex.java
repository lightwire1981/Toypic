package com.auroraworld.toypic.list_code;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auroraworld.toypic.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListAdapterCtgDetailIndex extends RecyclerView.Adapter<ListAdapterCtgDetailIndex.ViewHolder> {
    private final ArrayList<ListItemCtgDetailIndex> mData;
    private static final ArrayList<CheckBox> indexBoxList = new ArrayList<>();

    public ListAdapterCtgDetailIndex(ArrayList<ListItemCtgDetailIndex> list) {
        mData = list;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.index_item_radio_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ListItemCtgDetailIndex item = mData.get(position);

        indexBoxList.add(position, holder.checkBox);
        holder.checkBox.setText(item.getIndexName());
        holder.checkBox.setTag(item.getIndexId());
        holder.checkBox.setOnCheckedChangeListener(listener);
        if (position ==0) {
            holder.checkBox.performClick();
        }
    }

    private final CompoundButton.OnCheckedChangeListener listener = (checkbox, isChecked) -> {
        if (isChecked) {
            String label = checkbox.getText().toString();
            for (CheckBox ckb : indexBoxList) {
                String temp = ckb.getText().toString();
                if (!temp.equals(label)) {
                    ckb.setChecked(false);
                }
            }
        }
//        Log.i("<<<<<<<<<< Parent Layout ID : ",((View)checkbox.getParent()).getId()+"");
    };

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.cKbxIndexItem);
        }
    }
}
