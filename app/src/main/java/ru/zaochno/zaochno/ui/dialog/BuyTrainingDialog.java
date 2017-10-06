package ru.zaochno.zaochno.ui.dialog;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.Training;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

public class BuyTrainingDialog extends DialogFragment {
    private Training training;

    @BindView(R.id.tv_title)
    public TextView tvTitle;

    @BindView(R.id.tv_cost)
    public TextView tvCost;

    @BindView(R.id.tv_validity)
    public TextView tvValidity;

    @BindView(R.id.tv_cost_after)
    public TextView tvCostAfter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_buy_training, container, false);
        ButterKnife.bind(this, rootView);

        tvTitle.setText(training.getName());

        String sCost = String.valueOf(training.getHighestPrice().getPrice()).concat(" руб");
        String sCostAfter = String.valueOf(training.getHighestPrice().getPrice() - 10).concat(" руб");

        tvCost.setText(tvCost.getText().toString().concat(sCost));
        tvValidity.setText(tvValidity.getText().toString().concat("67 дней"));
        tvCostAfter.setText(tvCostAfter.getText().toString().concat(sCostAfter));

        SpannableString spanCost = new SpannableString(tvCost.getText());
        SpannableString spanValidity = new SpannableString(tvValidity.getText());
        SpannableString spanCostAfter = new SpannableString(tvCostAfter.getText());

        spanCost.setSpan(
                new StyleSpan(Typeface.BOLD),
                tvCost.getText().length() - sCost.length(),
                tvCost.getText().length(),
                SPAN_INCLUSIVE_INCLUSIVE);
        spanValidity.setSpan(
                new StyleSpan(Typeface.BOLD),
                tvValidity.getText().length() - "67 дней".length(),
                tvValidity.getText().length(),
                SPAN_INCLUSIVE_INCLUSIVE);
        spanCostAfter.setSpan(
                new StyleSpan(Typeface.BOLD),
                tvCostAfter.getText().length() - sCostAfter.length(),
                tvCostAfter.getText().length(),
                SPAN_INCLUSIVE_INCLUSIVE);

        tvCost.setText(spanCost);
        tvValidity.setText(spanValidity);
        tvCostAfter.setText(spanCostAfter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @OnClick(R.id.iv_close)
    public void closeDialog() {
        getDialog().cancel();
    }

    public void setTraining(Training training) {
        this.training = training;
    }
}
