package com.rohit.covid19tracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import static com.rohit.covid19tracker.CitywiseView.DISTRICT_NAME ;
import static com.rohit.covid19tracker.CitywiseView.DISTRICT_ACTIVE ;
import static com.rohit.covid19tracker.CitywiseView.DISTRICT_ACTIVE_NEW ;
import static com.rohit.covid19tracker.CitywiseView.DISTRICT_CONFIRMED ;
import static com.rohit.covid19tracker.CitywiseView.DISTRICT_CONFIRMED_NEW ;
import static com.rohit.covid19tracker.CitywiseView.DISTRICT_RECOVERED ;
import static com.rohit.covid19tracker.CitywiseView.DISTRICT_RECOVERED_NEW ;
import static com.rohit.covid19tracker.CitywiseView.DISTRICT_DECEASED ;
import static com.rohit.covid19tracker.CitywiseView.DISTRICT_DECEASED_NEW ;
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    OnCLickListener onCLickListener ;
    Context isContext ;
    ArrayList<cities> citiesArrayList ;

    public CityAdapter(Context isContext, ArrayList<cities> citiesArrayList) {
        this.isContext = isContext;
        this.citiesArrayList = citiesArrayList;
    }
    public interface OnCLickListener {
        void onClick(int position) ;
    }
    public void setOnClickListener(OnCLickListener listener){
        onCLickListener = listener ;
    }

    @NonNull
    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.citywise_list_layout,parent ,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   Intent intent = new Intent(isContext,CityData.class) ;
                                                   cities city = citiesArrayList.get(position) ;
                                                   intent.putExtra(DISTRICT_NAME,city.getDistrictName()) ;
                                                   intent.putExtra(DISTRICT_ACTIVE,city.getActiveCases()) ;
                                                   intent.putExtra(DISTRICT_ACTIVE_NEW,city.getActiveCasesNew()) ;
                                                   intent.putExtra(DISTRICT_CONFIRMED,city.getConfirmedCases()) ;
                                                   intent.putExtra(DISTRICT_CONFIRMED_NEW,city.getConfirmedCasesNew()) ;
                                                   intent.putExtra(DISTRICT_RECOVERED,city.getRecoveredCases()) ;
                                                   intent.putExtra(DISTRICT_RECOVERED_NEW,city.getRecoveredCasesNew()) ;
                                                   intent.putExtra(DISTRICT_DECEASED,city.getDeceasedCases()) ;
                                                   intent.putExtra(DISTRICT_DECEASED_NEW,city.getDeceasedCasesNew()) ;


                                                   isContext.startActivity(intent) ;
                                               }
                                           });

        cities isCity = citiesArrayList.get(position) ;
        holder.districtActive.setText(NumberFormat.getInstance().format(Integer.parseInt(isCity.getActiveCases()))) ;
        holder.districtName.setText(isCity.getDistrictName()) ;
    }

    @Override
    public int getItemCount() {
        return citiesArrayList.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView districtName , districtActive ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if(pos!=RecyclerView.NO_POSITION){
                        onCLickListener.onClick(pos) ;
                    }
                }
            });

            districtName = itemView.findViewById(R.id.districtName) ;
            districtActive = itemView.findViewById(R.id.districtActive) ;
        }
    }
    public void filterList(ArrayList<cities> filteredList) {
        citiesArrayList = filteredList;
        notifyDataSetChanged();
    }
}
