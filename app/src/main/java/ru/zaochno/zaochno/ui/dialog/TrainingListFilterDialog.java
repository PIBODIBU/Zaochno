package ru.zaochno.zaochno.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.Thematic;
import ru.zaochno.zaochno.data.model.filter.TrainingFilter;

public class TrainingListFilterDialog extends DialogFragment {
    @BindView(R.id.range_bar)
    public RangeSeekBar<Integer> rangeSeekBar;

    @BindView(R.id.spinner_thematic)
    public Spinner spinnerThematic;

    @BindView(R.id.tv_max_price_label)
    public TextView tvMaxPriceLabel;

    private TrainingFilter filter;
    private TrainingFilter internalFilter = new TrainingFilter();
    private OnFilterApplyListener onFilterApplyListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_training_filter, container, false);
        ButterKnife.bind(this, rootView);

        if (getFilter() != null) {
            rangeSeekBar.setRangeValues(0, getFilter().getPriceEnd());
            tvMaxPriceLabel.setText(String.valueOf(getFilter().getPriceEnd()).concat(" руб"));
        }

        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<Integer> bar, Integer minValue, Integer maxValue) {
                internalFilter.setPriceStart(minValue);
                internalFilter.setPriceEnd(maxValue);
            }
        });

        List<Thematic> thematics = new ArrayList<>();
        thematics.add(new Thematic("Thematic 1"));
        thematics.add(new Thematic("Business Services"));
        thematics.add(new Thematic("Computers"));
        thematics.add(new Thematic("Education"));
        thematics.add(new Thematic("Personal"));
        thematics.add(new Thematic("Travel"));

        ArrayAdapter<Thematic> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, thematics);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerThematic.setAdapter(dataAdapter);
        spinnerThematic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), ((Thematic) adapterView.getItemAtPosition(i)).getName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return rootView;
    }

    @OnClick(R.id.iv_close)
    public void closeDialog() {
        getDialog().cancel();
    }

    @OnClick(R.id.btn_apply)
    public void apply() {
        if (onFilterApplyListener != null)
            onFilterApplyListener.onApply(internalFilter);

        getDialog().cancel();
    }

    public TrainingFilter getFilter() {
        return filter;
    }

    public void setFilter(TrainingFilter filter) {
        this.filter = filter;

        internalFilter.setNumber(filter.getNumber());
        internalFilter.setPriceStart(filter.getPriceStart());
        internalFilter.setPriceEnd(filter.getPriceEnd());
        internalFilter.setToken(filter.getToken());
    }

    public void setOnFilterApplyListener(OnFilterApplyListener onFilterApplyListener) {
        this.onFilterApplyListener = onFilterApplyListener;
    }

    public interface OnFilterApplyListener {
        void onApply(TrainingFilter filter);
    }
}
