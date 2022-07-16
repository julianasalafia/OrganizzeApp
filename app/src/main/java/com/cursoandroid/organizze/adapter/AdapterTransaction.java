package com.cursoandroid.organizze.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cursoandroid.organizze.R;
import com.cursoandroid.organizze.model.Transaction;

import java.util.List;

public class AdapterTransaction extends RecyclerView.Adapter<AdapterTransaction.MyViewHolder> {

    List<Transaction> transactions;
    Context context;

    public AdapterTransaction(List<Transaction> transactions, Context context) {
        this.transactions = transactions;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_transaction, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        holder.title.setText(transaction.getDescription());
        holder.amount.setText(String.valueOf(transaction.getAmount()));
        holder.category.setText(transaction.getCategory());

        if (transaction.getType() == "e" || transaction.getType().equals("e")) {
            holder.amount.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.amount.setText("-" + transaction.getAmount());
        }
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, amount, category;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textAdapterTitle);
            amount = itemView.findViewById(R.id.textAdapterAmount);
            category = itemView.findViewById(R.id.textAdapterCategory);
        }

    }

}
