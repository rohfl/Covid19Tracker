package com.rohit.covid19tracker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import static com.rohit.covid19tracker.StatewiseView.DATE_AND_TIME ;
import static com.rohit.covid19tracker.StatewiseView.STATE_ACTIVE ;
import static com.rohit.covid19tracker.StatewiseView.STATE_CONFIRMED ;
import static com.rohit.covid19tracker.StatewiseView.STATE_CONFIRMED_NEW ;
import static com.rohit.covid19tracker.StatewiseView.STATE_DECEASED ;
import static com.rohit.covid19tracker.StatewiseView.STATE_DECEASED_NEW ;
import static com.rohit.covid19tracker.StatewiseView.STATE_NAME ;
import static com.rohit.covid19tracker.StatewiseView.STATE_RECOVERED ;
import static com.rohit.covid19tracker.StatewiseView.STATE_RECOVERED_NEW ;
import static com.rohit.covid19tracker.StatewiseView.STATE_ACTIVE_NEW ;
public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {
  //  public static String TAG = "Error" ;
    private ArrayList<states> isList ;
    private Context isContext ;
    private OnItemClickListner mListner;



    public interface OnItemClickListner {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListner listner) {
        mListner = listner;
    }
    public StateAdapter (Context context , ArrayList<states> list){
        this.isContext = context ;
        this.isList = list ;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View isView = LayoutInflater.from(parent.getContext()).inflate(R.layout.statewise_list_layout, parent , false) ;
        return new ViewHolder(isView) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stateData = new Intent(isContext, StateData.class);

                states item = isList.get(position);

                stateData.putExtra(STATE_NAME, item.getStateName());
                stateData.putExtra(STATE_CONFIRMED, item.getConfirmedCases());
                stateData.putExtra(STATE_ACTIVE, item.getActiveCases());
                stateData.putExtra(STATE_RECOVERED, item.getRecoveredCases());
                stateData.putExtra(STATE_DECEASED, item.getDeceasedCases());
                stateData.putExtra(STATE_CONFIRMED_NEW, item.getConfirmedCasesNew());
                stateData.putExtra(STATE_RECOVERED_NEW, item.getRecoveredCasesNew());
                stateData.putExtra(STATE_DECEASED_NEW, item.getDeceasedCasesNew());
                stateData.putExtra(DATE_AND_TIME, item.getDateAndTime());
                stateData.putExtra(STATE_ACTIVE_NEW,item.getActiveCasesNew()) ;


                isContext.startActivity(stateData);
            }
        });

        states list1 = this.isList.get(position) ;

        holder.stateName.setText(list1.getStateName()) ;
        String temp =list1.getActiveCases() ;
        int intActive = Integer.parseInt(temp) ;
        temp = NumberFormat.getInstance().format(intActive);
        holder.stateActive.setText(temp) ;

    }

    @Override
    public int getItemCount() {
        return this.isList.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView stateName , stateActive ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stateName = itemView.findViewById(R.id.stateName) ;
            stateActive = itemView.findViewById(R.id.stateActive) ;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListner != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                          //  Toast.makeText(isContext , "StateWiseView" , Toast.LENGTH_SHORT).show();
                            mListner.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public void filterList(ArrayList<states> filteredList) {
        isList = filteredList;
        notifyDataSetChanged();
    }
}
