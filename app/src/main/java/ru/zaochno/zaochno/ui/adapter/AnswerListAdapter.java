package ru.zaochno.zaochno.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.Answer;
import ru.zaochno.zaochno.ui.holder.AnswerListViewHolder;

public class AnswerListAdapter extends RecyclerView.Adapter<AnswerListViewHolder> {
    private List<Answer> answers;
    private Context context;

    public AnswerListAdapter(List<Answer> answers, Context context) {
        this.answers = answers;
        this.context = context;
    }

    @Override
    public AnswerListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AnswerListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer, parent, false));
    }

    @Override
    public void onBindViewHolder(AnswerListViewHolder holder, int position) {
        Answer answer = answers.get(position);

        if (answer == null)
            return;

        if (answer.getText() == null)
            return;

        holder.tvTitle.setText("Вариант ".concat(String.valueOf(position + 1).concat(". ")));
        holder.tvText.setText(answer.getText());

        switch (position) {
            case 0:
                holder.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.md_green_500));
                return;
            case 1:
                holder.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.md_red_500));
                return;
            case 2:
                holder.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.md_amber_500));
                return;
            case 3:
                holder.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.md_indigo_500));
                return;
        }
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }
}
