package com.example.demoasynctask;

import android.Manifest;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
    Activity contextParent;

    public MyAsyncTask(Activity contextParent){
        this.contextParent =  contextParent;
    }

    //y ở background thread, nơi bạn thực hiện các tác vụ tốn thời gian (không ảnh hưởng UI).
    @Override
    protected Void doInBackground(Void... voids) {
        for (int i = 0; i<=100; i++){
            SystemClock.sleep(200);
            //publishProgress(i) → gửi dữ liệu về để hiển thị tiến độ.
            publishProgress(i);
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(contextParent, "onPreExecute AsyncTask", Toast.LENGTH_SHORT).show();
    }

    //Cập nhật ProgressBar và TextView theo phần trăm đang chạy.
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        ProgressBar progressBar = contextParent.findViewById(R.id.prbDemo);

        int number = values[0];
        progressBar.setProgress(number);

        TextView textView = contextParent.findViewById(R.id.txtStatus);
        textView.setText(number + "%");
    }

    //Gọi sau khi doInBackground() hoàn thành.
    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        Toast.makeText(contextParent, "Da hoan thanh, Finished", Toast.LENGTH_SHORT).show();
    }
}
