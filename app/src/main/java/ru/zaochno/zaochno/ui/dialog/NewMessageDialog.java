package ru.zaochno.zaochno.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.Message;

public class NewMessageDialog extends DialogFragment {
    private OnNewMessageListener onNewMessageListener;

    @BindView(R.id.et_title)
    public EditText etTitle;

    @BindView(R.id.et_text)
    public EditText etText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_message_new, container, false);
        ButterKnife.bind(this, rootView);


        return rootView;
    }

    @OnClick(R.id.iv_close)
    public void closeDialog() {
        getDialog().cancel();
    }

    @OnClick(R.id.btn_send)
    public void send() {
        if (onNewMessageListener != null)
            onNewMessageListener.onNewMessage(getDialog(), new Message(etTitle.getText().toString(), etText.getText().toString()));
    }

    public void setOnNewMessageListener(OnNewMessageListener onNewMessageListener) {
        this.onNewMessageListener = onNewMessageListener;
    }

    public interface OnNewMessageListener {
        void onNewMessage(Dialog dialog, Message message);
    }
}
