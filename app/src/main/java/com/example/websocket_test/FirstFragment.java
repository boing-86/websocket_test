package com.example.websocket_test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.websocket_test.databinding.FragmentFirstBinding;

import java.net.URI;
import java.net.URISyntaxException;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    WebSocketClient mWebsocketClient;
    private static final String TAG = "FirstFragment";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.editTextWebsocketIP.getText().toString().isEmpty()){
                    return;
                }
                else{
                    connectWebSocket(binding.editTextWebsocketIP.getText().toString());
                }
            }
        });
    }


    public void connectWebSocket(String uriInput){
        URI uri;

        try{
            uri = new URI(uriInput);
        } catch(URISyntaxException e){
            e.printStackTrace();
            return;
        }
        Log.i(TAG, uri.toString());
        mWebsocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.i(TAG, "Websocket Connection!");
            }

            @Override
            public void onMessage(String message) {
                Log.i(TAG, "Websocket Msg Received! : " + message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.i(TAG, "Websocket Connection Closed!");
            }

            @Override
            public void onError(Exception ex) {
                Log.e(TAG, "Websocket Error! : " + ex.getMessage());
            }
        };
        mWebsocketClient.connect();
        mWebsocketClient.send("hello!");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}