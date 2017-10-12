package ru.zaochno.zaochno.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.Exam;
import ru.zaochno.zaochno.data.utils.DateUtils;
import ru.zaochno.zaochno.ui.holder.TrainingExamsViewHolder;

public class TrainingExamsAdapter extends RecyclerView.Adapter<TrainingExamsViewHolder> {
    private List<Exam> exams;
    private OnItemClickListener onItemClickListener;

    public TrainingExamsAdapter(List<Exam> exams, OnItemClickListener onItemClickListener) {
        this.exams = exams;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public TrainingExamsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrainingExamsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training_exam, parent, false));
    }

    @Override
    public void onBindViewHolder(TrainingExamsViewHolder holder, int position) {
        final Exam exam = exams.get(position);

        if (exam == null)
            return;

        holder.tvTime.setText(DateUtils.millisToPattern(exam.getDate(), DateUtils.PATTERN_DEFAULT));
        holder.tvMembers.setText(String.valueOf(exam.getMembers()).concat(exam.getMembers() == 1 ? " участник" : " участника"));
        if (onItemClickListener != null)
            holder.btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(exam);
                }
            });
    }

    @Override
    public int getItemCount() {
        return exams.size();
    }

    public interface OnItemClickListener {
        void onClick(Exam exam);
    }
}
