package com.mati.launcher.activity;

import static com.mati.game.core.Config.BG_URL;
import static com.mati.game.core.Config.DOWNLOAD_URL;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.hzy.libp7zip.P7ZipApi;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.mati.game.R;
import com.mati.game.core.Config;
import com.mati.launcher.utils.Utils;
import com.mati.launcher.view.MainActivity;

import java.io.File;
import java.util.Formatter;

public class LoaderActivity extends AppCompatActivity {

    File folder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_loader);
        FileDownloader.setup(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        startDownload();

        //Glide.get(this).clearDiskCache();
        //Glide.get(this).clearMemory();

        ImageView background = findViewById(R.id.loader_bg);
        Glide
                .with(this)
                .load(BG_URL)
                .into(background);

    }

    public void startDownload() {
        folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        createDownloadTask(DOWNLOAD_URL, folder.getPath()).start();
    }

    private BaseDownloadTask createDownloadTask(String url, String path) {
        return FileDownloader.getImpl().create(url)
                .setPath(path, true)
                .setCallbackProgressTimes(100)
                .setMinIntervalUpdateSpeed(100)
                .setListener(new FileDownloadSampleListener() {

                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.pending(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.progress(task, soFarBytes, totalBytes);
                        long progressPercent = soFarBytes * 100L / totalBytes;

                        RoundCornerProgressBar progressbar = (RoundCornerProgressBar) findViewById(R.id.progress_bar);

                        TextView textprogress = (TextView) findViewById(R.id.loading_percent);
                        TextView textloading = (TextView) findViewById(R.id.loading_text);

                        textloading.setText("در حال بارگیری فایل های بازی...");
                        textprogress.setText(new Formatter().format("%s از %s", new Object[]{Utils.bytesIntoHumanReadable(soFarBytes), Utils.bytesIntoHumanReadable(totalBytes)}).toString());
                        progressbar.setProgress((int) progressPercent);
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        super.error(task, e);
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), "خطایی روی داد، لطفاً دوباره نصب را امتحان کنید", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoaderActivity.this, MainActivity.class));

                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String et, boolean isContinue, int soFarBytes, int totalBytes) {
                        super.connected(task, et, isContinue, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.paused(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        super.completed(task);
                        TextView textprogress = (TextView) findViewById(R.id.loading_percent);
                        TextView textloading = (TextView) findViewById(R.id.loading_text);

                        textloading.setText("جعبه گشایی......");
                        textprogress.setText("2/2");
                        UnZipCache();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        super.warn(task);
                    }
                });
    }

    public void UnZipCache() {
        String mInputFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/PersianRp.7z";
        String mOutputPath = Environment.getExternalStorageDirectory().toString();
        new Thread() {
            @Override
            public void run() {
                P7ZipApi.executeCommand(String.format("7z x '%s' '-o%s' -aoa", mInputFilePath, mOutputPath));
                Utils.delete(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/PersianRp.7z"));
                Utils.delete(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/PersianRp.7z.temp"));
                runOnUiThread(() -> {
                    afterDownload();
                });
            }
        }.start();
    }

    public void afterDownload() {
        Toast.makeText(this, "بازی با موفقیت نصب شد!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
    }
}