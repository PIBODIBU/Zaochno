package ru.zaochno.zaochno.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
import ru.zaochno.zaochno.ui.dialog.NewMessageDialog;

public class MessageListActivity extends BaseNavDrawerActivity {
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.iv_toolbar_logo)
    public ImageView ivToolbarLogo;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

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
        Picasso.with(this)
                .load(R.drawable.logo)
                .into(ivToolbarLogo);
    }

    private void setupRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        adapter = new MessageListAdapter(messages, this);
        adapter.setOnMessageActionListener(new MessageListAdapter.OnMessageActionListener() {
            @Override
            public void onMessageClick(Message message) {
                if (message.getRead())
                    return;

                message.setRead(true);
                Retrofit2Client.getInstance().getApi().markMessageAsRead(message).enqueue(new Callback<BaseErrorResponse>() {
                    @Override
                    public void onResponse(Call<BaseErrorResponse> call, Response<BaseErrorResponse> response) {
                        if (response == null || response.body() == null || response.body().getError() == null || response.body().getError()) {
                            Toast.makeText(MessageListActivity.this, "Ошибка", Toast.LENGTH_LONG).show();
                            return;
                        }

                        fetchData();
                    }

                    @Override
                    public void onFailure(Call<BaseErrorResponse> call, Throwable t) {
                        Toast.makeText(MessageListActivity.this, "Ошибка", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onMessageDelete(Message message) {
                // TODO write smth
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
                            Toast.makeText(MessageListActivity.this, "Ошибка", Toast.LENGTH_LONG).show();
                            return;
                        }

                        adapter.getMessages().clear();
                        adapter.getMessages().addAll(response.body().getResponseObj());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<DataResponseWrapper<List<Message>>> call, Throwable t) {
                        Toast.makeText(MessageListActivity.this, "Ошибка", Toast.LENGTH_LONG).show();
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