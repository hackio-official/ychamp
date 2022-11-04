package com.hackio.ychamp;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.hackio.ychamp.file_Activity.url_container;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.SafeBrowsingResponse;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Objects;

public class MainActivity extends Base_activity {
    VideEnabledWebView webView;
    View nonvideo, videoload;
    Context context;
    WebSettings webSettings;
    ProgressBar progressbar;
    String useragent = "Mozilla/5.0 (Android 13; Web; rv:68.0) Gecko/68.0 Firefox/104.0";
    String mobile_user_agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36";
    ViewGroup video;
    AudioManager audioManager;
    Activity act;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        context = this;
        webView = findViewById(R.id.webview);
        webSettings = webView.getSettings();
        nonvideo = findViewById(R.id.nonvideo);
        video = findViewById(R.id.videolay);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        Intent intent = getIntent();
        String action = intent.getAction();
        webSettings.setJavaScriptEnabled(false);
        String type = intent.getType();
        webSettings.setDomStorageEnabled(false);
        progressbar = findViewById(R.id.progressBar);


        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                if (sharedText != null) {
                    weburl = sharedText;
                }
            }
        }
        if (startup) {
            finish();
            startActivity(getIntent());
        }
        Bundle bundle1 = getIntent().getExtras();
        if (bundle1 != null) {
            weburl = bundle1.getString("url");
        }
        if(webView!=null) {
            loadurl();
        }


        webSettings.setUserAgentString(useragent);
        videoload = findViewById(R.id.videoload);
        act = MainActivity.this;
        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        VideoEnabledWebChromeClient chromeClient = new VideoEnabledWebChromeClient(nonvideo, video, videoload, webView); // See all available constructors...

        Toast.makeText(context, "Education purpose only and beta release.", Toast.LENGTH_LONG).show();
        webView.getSettings().setAllowUniversalAccessFromFileURLs(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        //    webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setMediaPlaybackRequiresUserGesture(false);


        webView.setWebViewClient(new WebViewClient() {


            @Override
            public void onSafeBrowsingHit(WebView view, WebResourceRequest request, int threatType, SafeBrowsingResponse callback) {
                super.onSafeBrowsingHit(view, request, threatType, callback);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                if (!request.isForMainFrame()) {
                    if (adcheck(request.getUrl().toString()))
                        return true;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (adcheck(url))

                    return shouldInterceptRequest(view, "blcoker");

                return super.shouldInterceptRequest(view, url);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();

                if (url.contains("https://accounts.google.com/v3/signin/")) {
                    url = "sign in blocked";
                    runOnUiThread(() -> {
                        Toast.makeText(getApplicationContext(), "Signin blocked. hackIO not responsible for any damage", Toast.LENGTH_LONG).show();
                        webView.goBack();
                    });

                }

                if (adcheck(url))

                    return super.shouldInterceptRequest(view, "block");


                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (adcheck(url)) url = "blocked";
                return super.shouldOverrideUrlLoading(view, url);

            }

            @Override
            public void onLoadResource(WebView view, String url) {

                super.onLoadResource(view, url);


            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressbar.setVisibility(VISIBLE);
                progressbar.setIndeterminate(true);
                mydbhelp.add_history(view.getUrl(), webView.getTitle());
                super.onPageStarted(view, url, favicon);


            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                progressbar.setIndeterminate(true);
                super.onPageCommitVisible(view, url);


            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                nonvideo.setVisibility(VISIBLE);
                progressbar.setVisibility(GONE);
                progressbar.setIndeterminate(false);

            }

        });


        webView.setWebChromeClient(chromeClient);

        chromeClient.setOnToggledFullscreen(fullscreen -> {
            if (fullscreen) {
                Objects.requireNonNull(getSupportActionBar()).hide();
                WindowManager.LayoutParams attrs = getWindow().getAttributes();
                attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                getWindow().setAttributes(attrs);
                act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                if (Build.VERSION.SDK_INT >= 14) {

                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
                }

            } else {
                Objects.requireNonNull(getSupportActionBar()).show();
                WindowManager.LayoutParams attrs = getWindow().getAttributes();
                attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                getWindow().setAttributes(attrs);
                if (Build.VERSION.SDK_INT >= 14) {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                }
                act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


            }
        });

    }

    public static boolean adcheck(String url) {

        if (url.startsWith("https://rr") || url.startsWith("https://www.youtube.com/")) {
            return false;
        } else {
            for (String check : url_container) {
                if (url.contains("pagead") || url.contains("adunit") || url.contains(check)) {
                    return true;
                }
            }
            return false;

        }
    }

    public void loadurl() {
if(weburl==null) {
   Shared_prefs shared_prefs=new Shared_prefs(this);
    weburl=shared_prefs.get_site_name();

}
            progressbar.setVisibility(VISIBLE);
            progressbar.setIndeterminate(true);
            if (weburl.equals("https://www.youtube.com") || weburl.equals("https://m.youtube.com")) {
                webView.loadUrl(weburl);
            } else {
                webView.loadUrl(weburl);

            }

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                event.startTracking();
                audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                event.startTracking();
                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.history_menu:
                Intent his_intent = new Intent(context, History.class);
                startActivity(his_intent);
                break;
            case R.id.settings1:
                Intent intent = new Intent(getApplicationContext(), Settings_Activity.class);
                startActivity(intent);
                break;
            case R.id.about1:
                Intent intent2 = new Intent(getApplicationContext(), Readme.class);
                startActivity(intent2);
                break;
            case R.id.reload1:
                if (run) {
                    webView.loadUrl(weburl);
                } else {
                    webView.reload();
                }
                break;
            case R.id.real1:
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(webView.getUrl()));
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setPackage("com.android.chrome");
                startActivity(intent1);
                break;

            case R.id.download1:
                Toast.makeText(getApplicationContext(), "Downloading option currently under development", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_VOLUME_DOWN) {
            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
            return true;
        }
        if (keyCode == android.view.KeyEvent.KEYCODE_VOLUME_UP) {
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
            return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
