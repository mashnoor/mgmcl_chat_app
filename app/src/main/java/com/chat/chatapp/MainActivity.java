package com.chat.chatapp;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.tamir7.contacts.Contact;
import com.github.tamir7.contacts.Contacts;
import com.github.tamir7.contacts.Query;
import com.hugomatilla.audioplayerview.AudioPlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import nl.changer.audiowife.AudioWife;
import wseemann.media.FFmpegMediaPlayer;

public class MainActivity extends Activity {

    String myPhoneNumber;




    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    AsyncHttpClient client;
    ListView newsFeedList;
    Button record_btn;
    TextView curretnStatus;


    ArrayList<Post> posts;
     boolean record_mode_on = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myPhoneNumber = getIntent().getExtras().getString("user_phone");
       // showToast(myPhoneNumber);
        client = new AsyncHttpClient();
        newsFeedList = (ListView) findViewById(R.id.newsfeedListView);
        curretnStatus = (TextView) findViewById(R.id.txtStatus);

        record_btn = (Button) findViewById(R.id.btn_record);

        posts = new ArrayList<Post>();




        sendContactsToServer();

        getLatestPosts();



        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";;










    }

    private void getLatestPosts() {

        client.get(HelperClass.FRIENDS_LATEST_POSTS + myPhoneNumber, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
              //  showToast(result);

                try {
                    JSONArray all_post_array = new JSONArray(result);
                    for (int i = 0; i<all_post_array.length(); i++)
                    {
                        JSONObject curr_post = all_post_array.getJSONObject(i);
                        Post tmp_post = new Post(curr_post.getString("user_name"), curr_post.getString("number"), curr_post.getString("post_id"), curr_post.getString("time"));
                        showToast(curr_post.getString("user_name"));
                        posts.add(tmp_post);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                NewsFeedAdapter adapter = new NewsFeedAdapter(getApplicationContext(), posts);

                newsFeedList.setAdapter(adapter);


                Log.d("----------", result);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void showToast(String s)
    {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    public void record(View v)
    {
        if(record_mode_on) {

            try {
                myAudioRecorder=new MediaRecorder();
                myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                myAudioRecorder.setOutputFile(outputFile);


                myAudioRecorder.prepare();
                myAudioRecorder.start();
                curretnStatus.setText("Recording...");
                record_mode_on = false;

                showToast("Started audio recording");
            } catch (Exception e) {
                e.printStackTrace();
            }
            record_btn.setText("Stop");
        }
        else
        {
            myAudioRecorder.stop();
            myAudioRecorder.release();
            myAudioRecorder = null;
            curretnStatus.setText("");

        }

    }






    public class NewsFeedAdapter extends ArrayAdapter<Post>
    {
        public NewsFeedAdapter(Context context, ArrayList<Post> posts) {
            super(context, 0, posts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

           final Post currpost = getItem(position);
            if(convertView==null)
            {
                convertView = getLayoutInflater().inflate(R.layout.newsfeed, parent, false);
            }


            TextView username = (TextView) convertView.findViewById(R.id.tvUserName);
            username.setText(currpost.getUserName());

            TextView postTime = (TextView) convertView.findViewById(R.id.tvTime);
            postTime.setText(currpost.getTime());
            CircleImageView imageView = (CircleImageView) convertView.findViewById(R.id.profile_image);

            Picasso.with(getApplicationContext()).load(HelperClass.GET_IMAGE_FILE + currpost.getUserPhone()).into(imageView);


           //AudioPlayerView audioPlayerView = (AudioPlayerView) convertView.findViewById(R.id.player);
          // audioPlayerView.withUrl(HelperClass.GET_AUDIO_FILE + currpost.getVoiceFile());


            final Button btn = (Button) convertView.findViewById(R.id.btnPlay);
            final Button pauseBtn = (Button) convertView.findViewById(R.id.btnPause);
            final SeekBar bar = (SeekBar) convertView.findViewById(R.id.seekBar);
            final TextView pos = (TextView) convertView.findViewById(R.id.txtPos);


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showToast("something happedn");
                    Uri downloadUri = Uri.parse(HelperClass.GET_AUDIO_FILE + currpost.getVoiceFile());
                    final Uri destinationUri = Uri.parse(getApplicationContext().getExternalCacheDir().toString()+ currpost.getVoiceFile() + ".3gp");
                    DownloadRequest downloadRequest = new DownloadRequest(downloadUri)

                            .setRetryPolicy(new DefaultRetryPolicy())
                            .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)

                            .setDownloadListener(new DownloadStatusListener() {
                                @Override
                                public void onDownloadComplete(int id) {

                                    AudioWife wife = new AudioWife();
                                    wife.init(getApplicationContext(), Uri.parse(getApplicationContext().getExternalCacheDir().toString() + currpost.getVoiceFile() + ".3gp"));
                                            wife.setPlayView(btn)
                                            .setPauseView(pauseBtn)
                                            .setSeekBar(bar)
                                            .setRuntimeView(pos);
                                    wife.play();










                                }

                                @Override
                                public void onDownloadFailed(int id, int errorCode, String errorMessage) {

                                    showToast("Failed");
                                }

                                @Override
                                public void onProgress(int id, long totalBytes, long downlaodedBytes, int progress) {


                                }
                            });
                    ThinDownloadManager downloadManager;


                    downloadManager = new ThinDownloadManager();
                    int downloadId = downloadManager.add(downloadRequest);
                    showToast(downloadId + "");
                }




            });


            return convertView;
        }
    }






    public void share(View v)
    {


        //Upload that media file
       uploadRecordedFile();



    }





    private void uploadRecordedFile() {

            try {
                String uploadId =
                        new MultipartUploadRequest(MainActivity.this, HelperClass.UPLOAD_FILE + myPhoneNumber)
                                .addFileToUpload(outputFile, "audio")
                                .setNotificationConfig(new UploadNotificationConfig())
                                .setMaxRetries(2)
                                .startUpload();
            } catch (Exception exc) {
                Log.e("AndroidUploadService", exc.getMessage(), exc);
            }
        }


    public void play(View v)
    {
        if(outputFile==null)
        {
            showToast("You haven't recorded any message yet!");
            return;
        }

        final MediaPlayer m = new MediaPlayer();

        try {
            m.setDataSource(outputFile);
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            m.prepare();
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        m.start();


        Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
    }




    private void sendContactsToServer()
    {
        Contacts.initialize(MainActivity.this);

        Query q = Contacts.getQuery();
        q.hasPhoneNumber();
        List<Contact> contacts = q.find();

        JSONArray all_contacts = new JSONArray();

        for (int i = 0; i<contacts.size(); i++)
        {
            JSONObject temp = new JSONObject();
            try
            {
                temp.put("number", contacts.get(i).getBestPhoneNumber().getNormalizedNumber().replace("+88", ""));
                all_contacts.put(temp);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        Log.d("Chat", all_contacts.toString());


        RequestParams params = new RequestParams("list", all_contacts.toString());
        client.post(HelperClass.ADD_FRIEND_URL + myPhoneNumber, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                Log.d("Chat", "starting");

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Chat", "success");
               // showToast("Friends Added");
            }

            @Override

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Chat", "failed");
                Log.d("Chat", error.toString());
                showToast("Failed");
            }
        });

    }




}
