package ru.zaochno.zaochno.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.BaseChapter;
import ru.zaochno.zaochno.data.model.Chapter;
import ru.zaochno.zaochno.data.model.SubChapter;
import ru.zaochno.zaochno.ui.holder.ChapterListViewHolder;
import ru.zaochno.zaochno.ui.holder.SubChapterViewHolder;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

public class ChapterListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE_CHAPTER = 1;
    private final int TYPE_SUB_CHAPTER = 2;

    private LinkedList<BaseChapter> chapters;
    private Context context;
    private OnChapterClickListener onChapterClickListener;

    public ChapterListAdapter(Context context, LinkedList<BaseChapter> chapters) {
        this.chapters = chapters;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CHAPTER)
            return new ChapterListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter, parent, false));
        else if (viewType == TYPE_SUB_CHAPTER)
            return new SubChapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_chapter, parent, false));
        else
            return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (chapters.get(position) instanceof Chapter)
            return TYPE_CHAPTER;
        else if (chapters.get(position) instanceof SubChapter)
            return TYPE_SUB_CHAPTER;
        else
            return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ChapterListViewHolder) {
            if (chapters.get(position) instanceof Chapter) {
                final Chapter chapter = ((Chapter) chapters.get(position));
                ChapterListViewHolder viewHolder = ((ChapterListViewHolder) holder);

                if (chapter == null)
                    return;

                viewHolder.tvChapter.setPaintFlags(viewHolder.tvChapter.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                if (chapter.getName() != null) {
                    String chapterTitle = "Глава " + String.valueOf(chapter.getPosition()) + ". \"" + chapter.getName() + "\"";
                    viewHolder.tvChapter.setText(chapterTitle);
                }

                if (onChapterClickListener != null)
                    viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onChapterClickListener.onChapterClick(chapter);
                        }
                    });
            }
        } else if (holder instanceof SubChapterViewHolder) {
            if (chapters.get(position) instanceof SubChapter) {
                final SubChapter subChapter = ((SubChapter) chapters.get(position));
                SubChapterViewHolder viewHolder = ((SubChapterViewHolder) holder);

                if (subChapter == null)
                    return;

                String title = "Подглава " + String.valueOf(subChapter.getPosition()) + ". ";
                SpannableString spanTitle = new SpannableString(title);
                spanTitle.setSpan(
                        new AbsoluteSizeSpan(context.getResources().getDimensionPixelSize(R.dimen.sub_chapter_title_text_size)),
                        0,
                        title.length(),
                        SPAN_INCLUSIVE_INCLUSIVE);

                String text = "\"" + subChapter.getName() + "\"\n";
                SpannableString spanText = new SpannableString(text);
                spanText.setSpan(new AbsoluteSizeSpan(context.getResources().getDimensionPixelSize(R.dimen.sub_chapter_text_text_size)),
                        0,
                        text.length(),
                        SPAN_INCLUSIVE_INCLUSIVE);

                viewHolder.tvSubChapter.setText(TextUtils.concat(spanTitle, spanText));

                if (onChapterClickListener != null)
                    viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onChapterClickListener.onSubChapterClick(subChapter);
                        }
                    });
            }
        }
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    public void setOnChapterClickListener(OnChapterClickListener onChapterClickListener) {
        this.onChapterClickListener = onChapterClickListener;
    }

    public interface OnChapterClickListener {
        void onChapterClick(Chapter chapter);

        void onSubChapterClick(SubChapter subChapter);
    }
}
