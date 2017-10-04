package ru.zaochno.zaochno.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.api.Retrofit2Client;
import ru.zaochno.zaochno.data.model.Message;
import ru.zaochno.zaochno.data.model.filter.TrainingFilter;
import ru.zaochno.zaochno.data.model.request.TokenRequest;
import ru.zaochno.zaochno.data.model.response.BaseErrorResponse;
import ru.zaochno.zaochno.data.model.response.DataResponseWrapper;
import ru.zaochno.zaochno.data.provider.AuthProvider;
import ru.zaochno.zaochno.ui.adapter.MessageListAdapter;
import ru.zaochno.zaochno.ui.dialog.MessageDialogShow;
import ru.zaochno.zaochno.ui.dialog.NewMessageDialog;

public class MessageListActivity extends BaseNavDrawerActivity {
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.iv_toolbar_logo)
    public ImageView ivToolbarLogo;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    private MessageDialogShow messageDialogShow = new MessageDialogShow();
    private MessageListAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<Message> messages = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        ButterKnife.bind(this);

        setToolbar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setTitle("");
        setupDrawer();

        setupUi();
        setupRecyclerView();
        fetchData();
    }

    private void setupUi() {
        // Load toolbar logo
        Picasso.with(this)
                .load(R.drawable.logo)
                .into(ivToolbarLogo);
    }

    private void setupRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        adapter = new MessageListAdapter(messages, this);

        // Setup adapter's callbacks
        adapter.setOnMessageActionListener(new MessageListAdapter.OnMessageActionListener() {
            @Override
            public void onMessageClick(Message message) {
                messageDialogShow.setMessage(message);
                messageDialogShow.show(getSupportFragmentManager(), "messageDialogShow");
                messageDialogShow.setDialogActionListener(new MessageDialogShow.DialogActionListener() {
                    @Override
                    public void onMessageShow(Message message) {
                        if (message.getRead()) {
                            // Message is read
                            return;
                        } else if (!message.getRead() && TextUtils.isEmpty(message.getAnswer())) {
                            // Message is not read & has no answer
                            return;
                        } /*else if (!message.getRead() && !TextUtils.isEmpty(message.getAnswer())) {
                            // Message is not read & has answer

                        }*/

                        // Set properties
                        message.setRead(true);
                        message.setToken(AuthProvider.getInstance(MessageListActivity.this).getCurrentUser().getToken());

                        // Call API
                        Retrofit2Client.getInstance().getApi().markMessageAsRead(message).enqueue(new Callback<BaseErrorResponse>() {
                            @Override
                            public void onResponse(Call<BaseErrorResponse> call, Response<BaseErrorResponse> response) {
                                if (response == null || response.body() == null || response.body().getError() == null || response.body().getError()) {
                                    Toast.makeText(MessageListActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                                    return;
                                }

                                // Message successfully marked as read
                                fetchData();
                            }

                            @Override
                            public void onFailure(Call<BaseErrorResponse> call, Throwable t) {
                                Toast.makeText(MessageListActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onDialogClose() {

                    }
                });
            }

            @Override
            public void onMessageDelete(Message message) {
                message.setToken(AuthProvider.getInstance(MessageListActivity.this).getCurrentUser().getToken());

                Retrofit2Client.getInstance().getApi().deleteMessage(message).enqueue(new Callback<BaseErrorResponse>() {
                    @Override
                    public void onResponse(Call<BaseErrorResponse> call, Response<BaseErrorResponse> response) {
                        if (response == null || response.body() == null || response.body().getError() == null || response.body().getError()) {
                            Toast.makeText(MessageListActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                            return;
                        }

                        Toast.makeText(MessageListActivity.this, R.string.message_deleted, Toast.LENGTH_LONG).show();
                        fetchData();
                    }

                    @Override
                    public void onFailure(Call<BaseErrorResponse> call, Throwable t) {
                        Toast.makeText(MessageListActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void fetchData() {
        Retrofit2Client.getInstance().getApi().getMessages(new TokenRequest(AuthProvider.getInstance(this).getCurrentUser().getToken()))
                .enqueue(new Callback<DataResponseWrapper<List<Message>>>() {
                    @Override
                    public void onResponse(Call<DataResponseWrapper<List<Message>>> call, Response<DataResponseWrapper<List<Message>>> response) {
                        if (response == null || response.body() == null || response.body().getResponseObj() == null) {
                            Toast.makeText(MessageListActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                            return;
                        }

                        adapter.getMessages().clear();
                        Collections.sort(response.body().getResponseObj(), new Comparator<Message>() {
                            @Override
                            public int compare(Message message, Message t1) {
                                Boolean isRead1 = message.getRead();
                                Boolean isRead2 = t1.getRead();
                                int resIsRead = isRead1.compareTo(isRead2);

                                if (resIsRead != 0) {
                                    return resIsRead;
                                } else {
                                    Long date1 = message.getDate();
                                    Long date2 = t1.getDate();
                                    return date2.compareTo(date1);
                                }

//                                return message.getRead().compareTo(t1.getRead());
                            }
                        });
                        adapter.getMessages().addAll(response.body().getResponseObj());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<DataResponseWrapper<List<Message>>> call, Throwable t) {
                        Toast.makeText(MessageListActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                    }
                });
    }

    @OnClick(R.id.container_new_message)
    public void openNewMessageDialog() {
        NewMessageDialog newMessageDialog = new NewMessageDialog();
        newMessageDialog.setOnNewMessageListener(new NewMessageDialog.OnNewMessageListener() {
            @Override
            public void onNewMessage(final Dialog dialog, Message message) {
                if (message.getToken() == null)
                    message.setToken(AuthProvider.getInstance(MessageListActivity.this).getCurrentUser().getToken());

                Retrofit2Client.getInstance().getApi().sendMessage(message).enqueue(new Callback<BaseErrorResponse>() {
                    @Override
                    public void onResponse(Call<BaseErrorResponse> call, Response<BaseErrorResponse> response) {
                        if (response == null || response.body() == null || response.body().getError()) {
                            Toast.makeText(MessageListActivity.this, "Ошибка отправки", Toast.LENGTH_LONG).show();
                            return;
                        }

                        dialog.cancel();
                        Toast.makeText(MessageListActivity.this, "Сообщение отправлено", Toast.LENGTH_LONG).show();
                        fetchData();
                    }

                    @Override
                    public void onFailure(Call<BaseErrorResponse> call, Throwable t) {
                        Toast.makeText(MessageListActivity.this, "Ошибка отправки", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        newMessageDialog.show(getSupportFragmentManager(), "NewMessageDialog");
    }
}