package com.rohit.covid19tracker;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import static com.rohit.covid19tracker.CountrywiseView.COUNTRY_NAME ;
import static com.rohit.covid19tracker.CountrywiseView.COUNTRY_ACTIVE ;
import static com.rohit.covid19tracker.CountrywiseView.COUNTRY_CONFIRMED ;
import static com.rohit.covid19tracker.CountrywiseView.COUNTRY_CONFIRMED_NEW ;
import static com.rohit.covid19tracker.CountrywiseView.COUNTRY_RECOVERED ;
import static com.rohit.covid19tracker.CountrywiseView.COUNTRY_DECEASED ;
import static com.rohit.covid19tracker.CountrywiseView.COUNTRY_DECEASED_NEW ;
public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    ArrayList<countries> isList ;
    Context context ;
    OnClickListener clickListener ;

    public CountryAdapter(ArrayList<countries> isList, Context context) {
        this.isList = isList;
        this.context = context;
    }

    public interface OnClickListener {
        void onItemClick(int position) ;
    }
    public void setOnItemClickListener(OnClickListener listener) {
        clickListener = listener ;
    }

    @NonNull
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View isView = LayoutInflater.from(parent.getContext()).inflate(R.layout.countrywise_list_layout,parent,false) ;
        return new ViewHolder(isView) ;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent countryIntent = new Intent(context,CountryData.class) ;
                countries isCountry = isList.get(position) ;

                countryIntent.putExtra(COUNTRY_NAME,isCountry.getCountryName()) ;
                countryIntent.putExtra(COUNTRY_CONFIRMED,isCountry.getConfirmedCases()) ;
                countryIntent.putExtra(COUNTRY_CONFIRMED_NEW,isCountry.getConfirmedCasesNew()) ;
                countryIntent.putExtra(COUNTRY_ACTIVE,isCountry.getActiveCases()) ;
                countryIntent.putExtra(COUNTRY_RECOVERED,isCountry.getRecoveredCases()) ;
                countryIntent.putExtra(COUNTRY_DECEASED,isCountry.getDeceasedCases()) ;
                countryIntent.putExtra(COUNTRY_DECEASED_NEW,isCountry.getDeceasedCasesNew()) ;

                context.startActivity(countryIntent) ;
            }
        });

        String country , active ;
        String url ;
        countries country1 = isList.get(position) ;
        country = country1.getCountryName() ;
        active = country1.getActiveCases() ;
        url = country1.getImageURL() ;

        holder.countryName.setText(country) ;
        int intActive = Integer.parseInt(active) ;
        active = NumberFormat.getInstance().format(intActive);
        holder.activeCases.setText(active) ;
        Picasso.with(context).load(url).resize(30, 18).
                centerCrop().into(holder.imageURL);
    }

    @Override
    public int getItemCount() {
        return this.isList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageURL ;
        TextView countryName , activeCases ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if(pos!=RecyclerView.NO_POSITION){
                        clickListener.onItemClick(pos) ;
                    }
                }
            });

            countryName = itemView.findViewById(R.id.countryName) ;
            activeCases = itemView.findViewById(R.id.countryActive) ;
            imageURL = itemView.findViewById(R.id.countryWiseImage) ;
        }
    }
    public void filterList(ArrayList<countries> filteredList) {
        isList = filteredList;
        notifyDataSetChanged();
    }

}
