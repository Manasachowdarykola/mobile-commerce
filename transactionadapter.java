{
    Context ctx;

    // List containing data for recyclerview
    ArrayList<TransactionClass> transactionList;

    // Constructor for TransactionAdapter
    public TransactionAdapter(Context ctx, ArrayList<TransactionClass> transactionList) {
        this.ctx = ctx;
        this.transactionList = transactionList;
    }

    // On Create View Holder to Inflate transaction row layout
    @NonNull
    @Override
    public TransactionAdapter.TViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.transaction_row_layout,parent,false);
        return new TransactionAdapter.TViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.TViewHolder holder, int position) {

        // Setting Message to a TextView in Row Layout
        holder.tvMessage.setText(transactionList.get(holder.getAdapterPosition()).getMessage());

        // If the transaction is Positive (Received Money) set Text Color to Green
        if(transactionList.get(holder.getAdapterPosition()).isPositive())
        {
            holder.tvAmount.setTextColor(Color.parseColor("#00c853"));
            // Setting Amount to a TextView in the row layout
            holder.tvAmount.setText("+₹"+Integer.toString(transactionList.get(holder.getAdapterPosition()).getAmount()));
        }

        // If the transaction is Negative (Spent Money) set Text Color to Red
        else {
            holder.tvAmount.setTextColor(Color.parseColor("#F44336"));

            // Setting Amount to a TextView in the row layout
            holder.tvAmount.setText("-₹"+Integer.toString(transactionList.get(holder.getAdapterPosition()).getAmount()));
        }

        // On Click Listener for Delete Icon
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Confirmation Alert to delete a Transaction
                AlertDialog dialog = new AlertDialog.Builder(ctx)
                        .setCancelable(false)
                        .setTitle("Are you sure? The transaction will be deleted.")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                transactionList.remove(holder.getAdapterPosition());
                                dialogInterface.dismiss();
                                notifyDataSetChanged();
                                checkIfEmpty(getItemCount());
                                setBalance(transactionList);
                            }
                        })
                        .create();
                dialog.show();
            }
        });
    }


    // To get size of the list
    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    // View Holder for a Transaction
    public static class TViewHolder extends RecyclerView.ViewHolder{
        TextView tvAmount,tvMessage;
        ImageView ivDelete;

        public TViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }
}
