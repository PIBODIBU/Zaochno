package ru.zaochno.zaochno.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.Message;

public class MessageDialogShow extends DialogFragment {
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.tv_message_user)
    public TextView tvMessageUser;

    @BindView(R.id.tv_message_answer)
    public TextView tvMessageAnswer;

    private Message message;
    private DialogActionListener dialogActionListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_message_show, container, false);
        ButterKnife.bind(this, rootView);

        if (message == null)
            return null;

        if (message.getMessage() != null)
            tvMessageUser.setText(message.getMessage());

        if (message.getAnswer() != null || !TextUtils.isEmpty(message.getAnswer()))
            tvMessageAnswer.setText(message.getAnswer());
        else
            tvMessageAnswer.setText(getString(R.string.admin_not_answered_yet));

        if (message.getTitle() != null)
            toolbar.setTitle(message.getTitle());

        toolbar.inflateMenu(R.menu.menu_dialog_message_show);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_dialog_close:
                        getDialog().cancel();
                        return true;
                    default:
                        return false;
                }
            }
        });

        if (dialogActionListener != null)
            dialogActionListener.onMessageShow(message);

        return rootView;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (dialogActionListener != null)
            dialogActionListener.onDialogClose();
        super.onCancel(dialog);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setDialogActionListener(DialogActionListener dialogActionListener) {
        this.dialogActionListener = dialogActionListener;
    }

    public interface DialogActionListener {
        void onMessageShow(Message message);

        void onDialogClose();
    }
}
