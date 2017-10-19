package ru.zaochno.zaochno.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.rey.material.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.Answer;
import ru.zaochno.zaochno.data.model.Question;

public class ChooseAnswerDialog extends DialogFragment {
    private static final String TAG = "ChooseAnswerDialog";

    @BindView(R.id.tv_hint)
    public TextView tvHint;

    @BindView(R.id.btn_next)
    public Button btnNext;

    private OnNextButtonClickListener onNextButtonClickListener;
    private Question question;
    private Answer answer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_choose_answer, container, false);
        ButterKnife.bind(this, rootView);

        if (answer.getCorrect())
            rootView.setBackgroundResource(R.color.md_green_500);
        else
            rootView.setBackgroundResource(R.color.md_red_500);

        if (answer.getCorrect() != null && answer.getCorrect() && question.getHintRight() != null)
            tvHint.setText(question.getHintRight());
        if (answer.getCorrect() != null && !answer.getCorrect() && question.getHintWrong() != null)
            tvHint.setText(question.getHintWrong());

        if (onNextButtonClickListener != null)
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onNextButtonClickListener.onClick();
                    getDialog().cancel();
                }
            });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
    }

    public interface OnNextButtonClickListener {
        void onClick();
    }

    public void setOnNextButtonClickListener(OnNextButtonClickListener onNextButtonClickListener) {
        this.onNextButtonClickListener = onNextButtonClickListener;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
