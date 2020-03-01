package org.robin.money;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {

    private final List<Currency> currencies;

    public CurrencyAdapter(List<Currency> currencies) {
        this.currencies = currencies;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView symbol;
        final TextView rate;
        final ImageView flag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            flag = itemView.findViewById(R.id.flagImageView);
            symbol = itemView.findViewById(R.id.symbolTextView);
            rate = itemView.findViewById(R.id.rateTextView);
        }
    }

    /**
     * Méthode appelée 1x à l'init pour chaque item
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on converti le fichier XML d'item en objet Java
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_currency, parent, false);

        // instancier le ViewHolder qui sera TOUJOURS lié à cette vue
        return new ViewHolder(view);
    }

    /**
     * appelé à chaque fois qu'un item doit être dessiné à l'écran
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on récupère la donnée associée à cet index
        Currency currency = currencies.get(position);

        // on met à jour l'UI en passant par le ViewHolder
        holder.flag.setImageResource(currency.flagId);
        holder.symbol.setText(currency.symbol);
        holder.rate.setText(currency.rate + "");
    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

}
