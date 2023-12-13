package com.example.sqlapp.ui.slideshow;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sqlapp.R;
import com.example.sqlapp.databinding.FragmentSlideshowBinding;
import com.example.sqlapp.entities.Machine;
import com.example.sqlapp.entities.Marque;
import com.example.sqlapp.services.MachineService;
import com.example.sqlapp.services.MarqueService;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    MachineService ms;
    MarqueService mqs;
    List<Marque> marques;
    List<Machine> machines;

    List<Integer> nbrs = new ArrayList<>();
    List<String> codes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);

        ms = new MachineService(getContext());
        mqs = new MarqueService(getContext());

        marques = mqs.findAll();
        for (Marque m : marques) {
            codes.add(m.getLibelle());
        }

        machines = ms.findAll();
        for (String code : codes) {
            int i = 0;
            for (Machine mq : machines) {
                if (code.equals(mq.getMarqueCode())) {
                    i++;
                }
            }
            nbrs.add(i);
        }

        BarChart barChart = view.findViewById(R.id.barChart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < nbrs.size(); i++) {
            entries.add(new BarEntry(i, nbrs.get(i)));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Nombre de machines par marque");

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        // Titre du graphique
        Description description = new Description();
        description.setText("Statistiques des machines par marque");
        description.setTextSize(18f);
        description.setTextColor(Color.BLACK);
        description.setTypeface(Typeface.DEFAULT_BOLD);
        barChart.setDescription(description);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(codes));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(codes.size());
        xAxis.setTextSize(14f);
        xAxis.setTypeface(Typeface.DEFAULT_BOLD);
        xAxis.setTextColor(Color.BLACK);

        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setTextSize(14f);
        yAxisLeft.setTypeface(Typeface.DEFAULT_BOLD);
        yAxisLeft.setTextColor(Color.BLACK);

        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);

        Legend legend = barChart.getLegend();
        legend.setTextSize(14f);
        legend.setTypeface(Typeface.DEFAULT_BOLD);
        legend.setTextColor(Color.BLACK);

        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBorders(false);

        barChart.animateY(1000);

        return view;
    }

}