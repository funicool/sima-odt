package com.jnbulls.simaodt.UI.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jnbulls.simaodt.R;

import java.util.ArrayList;

public class MaterialesAdapter extends RecyclerView.Adapter<MaterialesAdapter.MaterialesViewHolder> {

        private final ArrayList<MaterialesItem> materialesItemList;
        private OnItemClickListener mListener;


        public interface OnItemClickListener {
            void onDeleteClick(int position);
        }
        public void setOnItemClickListener(OnItemClickListener listener) {
            mListener = listener;
        }

        public static class MaterialesViewHolder extends RecyclerView.ViewHolder {
            public TextView mIdMaterial;
            public TextView mMaterial;
            public TextView mCantidad;
            public ImageView mDeleteImage;

            public MaterialesViewHolder(View itemView, final OnItemClickListener listener) {
                super(itemView);
                mIdMaterial = itemView.findViewById(R.id.idMaterial);
                mMaterial = itemView.findViewById(R.id.material);
                mCantidad = itemView.findViewById(R.id.cantidad);
                mDeleteImage = itemView.findViewById(R.id.delete);

                mDeleteImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onDeleteClick(position);
                            }
                        }
                    }
                });
            }
        }

        public MaterialesAdapter(ArrayList<MaterialesItem> data) {
            this.materialesItemList = data;
        }
        @Override
        public MaterialesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_recyclerview, parent, false);
            MaterialesViewHolder evh = new MaterialesViewHolder(view, mListener);
            return evh;
        }
        @Override
        public void onBindViewHolder(MaterialesViewHolder holder, int position) {
            holder.mIdMaterial.setText(String.valueOf(materialesItemList.get(position).getId()));
            holder.mMaterial.setText(materialesItemList.get(position).getDescripcion());
            holder.mCantidad.setText(String.valueOf(materialesItemList.get(position).getCantidad()));
        }
        @Override
        public int getItemCount() {
            return materialesItemList.size();
        }
}

