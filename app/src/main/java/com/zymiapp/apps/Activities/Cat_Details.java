package com.zymiapp.apps.Activities;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import com.zymiapp.apps.Adapters.Cat_Image_Adapter;
import com.zymiapp.apps.Adapters.Cat_New_Adapter;
import com.zymiapp.apps.Applications.AppController;
import com.zymiapp.apps.Fragments.Fragment_Home;
import com.zymiapp.apps.Model.Selection;
import com.zymiapp.apps.R;
import com.zymiapp.apps.Session.Customer_Session;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zymiapp.apps.Fragments.Fragment_Home.KEY_ACTUAL_PRICE;
import static com.zymiapp.apps.Fragments.Fragment_Home.KEY_CATLOG_ID;
import static com.zymiapp.apps.Fragments.Fragment_Home.KEY_COD_AVAILABLE;
import static com.zymiapp.apps.Fragments.Fragment_Home.KEY_IMG_URL;
import static com.zymiapp.apps.Fragments.Fragment_Home.KEY_PRODUCT_ID;
import static com.zymiapp.apps.Fragments.Fragment_Home.KEY_PRODUCT_NAME;
import static com.zymiapp.apps.Fragments.Fragment_Home.KEY_PRODUCT_SIZE;
import static com.zymiapp.apps.Fragments.Fragment_Home.KEY_PRODUCT_WEIGHT;
import static com.zymiapp.apps.Fragments.Fragment_Home.KEY_QTY;
import static com.zymiapp.apps.Fragments.Fragment_Home.KEY_RES_ID;
import static com.zymiapp.apps.Fragments.Fragment_Home.KEY_SELLING_PRICE;


public class Cat_Details extends AppCompatActivity {


    private static final String FAV_URL = "https://www.resellingapp.com/apiv2/zymi/rest_server/userlikelist/API-KEY/123456";
    private static final String KEY_ID = "c_id";
    private static final String PRODUCT_ID = "product_id";
    private static final String TAG = Main_Handler_Activity.class.getSimpleName();
    private static final String DATA_CAT_URL = "https://www.resellingapp.com/apiv2/zymi/rest_server/catalogueContent/API-KEY/123456";
    private static final String KEY_CAT_ID = "catalogue_id";
    private static final String KEY_C_ID = "c_id";
    private ArrayList<Selection> images=new ArrayList<>();
    private RelativeLayout cat_layout;
    private ProgressBar p_bar_1;
    private RecyclerView r_view_1;
    private TextView cat_text;
    private TextView catno;
    private TextView catdesc;
    private EditText edit_margin;
    private ProgressDialog mProgressDialog;
    private Button btn_download;
    private RelativeLayout btn_share;
    private CheckBox share_all_check;
    private int total_selected=0;
    private ArrayList<Uri> files;
    private int position;
    private Cat_Image_Adapter mAdapter;
    private Selection selectedsingle;
    private int pos;
    private Cat_New_Adapter size_adapter;
    private RecyclerView size_rec, qty_list;
    private Cat_New_Adapter qty_adapter;
    private Dialog bottom_dialog;
    private List<String> qty = new ArrayList<>();
    private String ciid = "";
    //Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    private  int id =0;
    private String cat_id;
    private Toolbar toolbar;
    private Customer_Session customer_session;
    private AdView adView;
    private List<String> sizes_avail=new ArrayList<>();
    private String catlogue_name;
    private String catlogue_id;
    private String catlogue_desc;
    private TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat__details);

        verifyStoragePermissions(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar_title = toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customer_session = new Customer_Session(getApplicationContext());

        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/arial.ttf");

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras!=null){
            cat_id = extras.getString("cat_id");
        }

        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        cat_layout = (RelativeLayout) findViewById(R.id.cat_layout);

        cat_text = (TextView) findViewById(R.id.cat_name);
        cat_text.setTypeface(tf1);

        catno = (TextView) findViewById(R.id.cat_no);
        catno.setTypeface(tf1);

        catdesc = (TextView) findViewById(R.id.cat_desc);
        catdesc.setTypeface(tf1);

        share_all_check = (CheckBox) findViewById(R.id.share);
        share_all_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mAdapter.selectAll();
                }else {
                    mAdapter.deselectAll();
                }
            }
        });


        bottom_dialog = new Dialog(this, R.style.BottomDialog);
        bottom_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        bottom_dialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogAnimation;
        layoutParams.copyFrom(bottom_dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.BOTTOM;
        bottom_dialog.getWindow().setAttributes(layoutParams);
        bottom_dialog.setCancelable(true);
        bottom_dialog.setContentView(R.layout.quantity_dialog_two);
        size_rec = bottom_dialog.findViewById(R.id.r_view);
        qty_list = bottom_dialog.findViewById(R.id.r_view_1);
        edit_margin = (EditText) bottom_dialog.findViewById(R.id.input_margin);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        qty_list.setLayoutManager(horizontalLayoutManager);
        qty_list.setItemAnimator(new DefaultItemAnimator());

        qty.add("1");
        qty.add("2");
        qty.add("3");
        qty.add("4");
        qty.add("5");
        qty.add("6");
        qty.add("7");
        qty.add("8");
        qty.add("9");
        qty.add("10");
        qty_adapter = new Cat_New_Adapter(this, qty);
        qty_list.setAdapter(qty_adapter);

        //btn = bottom_dialog.findViewById(R.id.btn);
        bottom_dialog.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qty_adapter.getSelectedPosition() != -1 && size_adapter.getSelectedPosition() != -1) {
                    if (!edit_margin.getText().toString().trim().equals("")) {
                        bottom_dialog.dismiss();
                        //showProgressDialog();
                        addToCart(selectedsingle, ciid);
                    } else
                        Toast.makeText(getApplicationContext(), "Please add margin", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please select the size and quantity", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //size_rec = bottom_dialog.findViewById(R.id.r_view);
        btn_download = (Button) findViewById(R.id.download_btn);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<images.size();i++){

                    if(images.get(i).getSelect().equals(true)){

                        showProgressDialog();

                        Thread shivThread1 = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(500);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            downloadImages();
                                        }
                                    });

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                        };

                        shivThread1.start();

                        break;
                    }else {
                        if(i==images.size()-1){
                            Toast.makeText(getApplicationContext(), "Select at least one image", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });



        btn_share = (RelativeLayout) findViewById(R.id.sahre_btn);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if we have write permission
                int permission = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    ActivityCompat.requestPermissions(
                            Cat_Details.this,
                            PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE
                    );
                }else {

                    if (whatsappInstalledOrNot("com.whatsapp") && whatsappInstalledOrNot("com.whatsapp.w4b")){
                        String title = "Send to :";
                        CharSequence[] itemlist ={"Whatsapp",
                                "Whatsapp Business",
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                        //builder.setIcon(R.drawable.icon_app);
                        builder.setTitle(title);
                        builder.setItems(itemlist, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:// Take Photo
                                        id=0;
                                        total_selected=0;
                                        for (int i=0;i<images.size();i++){
                                            if (images.get(i).getSelect().equals(true)){
                                                total_selected+=1;
                                            }
                                        }

                                        files=new ArrayList<>();
                                        for(int j=0;j<images.size();j++){

                                            if(images.get(j).getSelect().equals(true)){
                                                String img_url = images.get(j).getImg_url();
                                                new ShareTask().execute(img_url);
                                            }

                                        }
                                        break;
                                    case 1:// Choose Existing Photo
                                        id =1;
                                        total_selected=0;
                                        for (int i=0;i<images.size();i++){
                                            if (images.get(i).getSelect().equals(true)){
                                                total_selected+=1;
                                            }
                                        }

                                        files=new ArrayList<>();
                                        for(int j=0;j<images.size();j++){

                                            if(images.get(j).getSelect().equals(true)){
                                                String img_url = images.get(j).getImg_url();
                                                new ShareTask().execute(img_url);
                                            }

                                        }
                                        break;

                                    default:
                                        break;
                                }
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.setCancelable(true);
                        alert.show();

                    }else {
                        total_selected=0;
                        for (int i=0;i<images.size();i++){
                            if (images.get(i).getSelect().equals(true)){
                                total_selected+=1;
                            }
                        }

                        files=new ArrayList<>();
                        for(int j=0;j<images.size();j++){

                            if(images.get(j).getSelect().equals(true)){
                                String img_url = images.get(j).getImg_url();
                                new ShareTask().execute(img_url);
                            }

                        }
                    }

                }

            }
        });

        r_view_1 = (RecyclerView) findViewById(R.id.recycler_view);
        p_bar_1 = (ProgressBar) findViewById(R.id.p_bar_1);
        r_view_1.setNestedScrollingEnabled(false);


        getDetails();
    }


    public void downloadImages() {

        for (int i = 0; i < images.size(); i++) {

            if(images.get(i).getSelect().equals(true)){
                String img_url = images.get(i).getImg_url();
                new DownloadTask().execute(img_url);
            }

            if(i==images.size()-1){
                mProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Images Downloaded Successfully", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private void addToCart(Selection item, String catlog_id) {

        String tag_json_obj = "json_obj_req";

        Map<String, String> postParam = new HashMap<>();
        postParam.put(KEY_PRODUCT_ID, item.getImg_id());
        postParam.put(KEY_PRODUCT_NAME, item.getImage_name());
        postParam.put(KEY_QTY, qty.get(qty_adapter.getSelectedPosition()));
        postParam.put(KEY_SELLING_PRICE, item.getPrice());
        postParam.put(KEY_ACTUAL_PRICE, item.getActual_price());
        postParam.put(KEY_IMG_URL, item.getImg_url());
        postParam.put(KEY_PRODUCT_WEIGHT, item.getWeight());
        postParam.put(KEY_CATLOG_ID, catlog_id);
        postParam.put(KEY_RES_ID, customer_session.getCustomerID());
        postParam.put(KEY_COD_AVAILABLE, item.getCod_avail());
        postParam.put(KEY_PRODUCT_SIZE, item.getSize_avail().get(size_adapter.getSelectedPosition()));

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Fragment_Home.CART_URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //hideProgressDialog();
                        try {
                            String status = response.getString("status");

                            if (status.equals("202")) {
                                Toast.makeText(getApplicationContext(), "Item Added to Cart", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Activity_Cart.class).putExtra("margin", edit_margin.getText().toString().trim()));
                                //getCartProducts();
                            } else if (status.equals("208")) {
                                Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //hideProgressDialog();
                Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }


    private void scanFile(String path) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATA,""+path);
        values.put(MediaStore.Images.Media.MIME_TYPE,"image/jpeg");
        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Log.i("TAG", "Agter scanning" +path);
    }


    private class DownloadTask extends AsyncTask<String,Integer,Bitmap> {

        final String Dir = images.get(position).getCat_name();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Bitmap doInBackground(String... param) {
            return getBitmapFromURL(param[0]);

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            try {
                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/~AK " + Dir);

                if (!myDir.exists()) {
                    myDir.mkdirs();
                }

                String name = new Date().getTime() + ".jpg";
                myDir = new File(myDir, name);
                FileOutputStream out = new FileOutputStream(myDir);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

                out.flush();
                out.close();
                Log.i("TAG", "scanning File " + myDir.getAbsolutePath());
                MediaScannerConnection.scanFile(getApplicationContext(),
                        new String[]{myDir.getAbsolutePath()}, null, null);


                // sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(myDir)));


                Log.i("TAG", "Agter scanning" + myDir.getAbsolutePath());

            } catch (Exception e) {
                // some action
            }


            String root = Environment.getExternalStorageDirectory().toString();

            File filePath = new File(root + "/~AK " + Dir);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(String.valueOf(filePath)))));

            //mProgressDialog.hide();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
        }
    }

    void shareImages(){

        total_selected=0;
        for (int i=0;i<images.size();i++){
            if (images.get(i).getSelect().equals(true)){
                total_selected+=1;
            }
        }

        files=new ArrayList<>();
        for(int j=0;j<images.size();j++){

            if(images.get(j).getSelect().equals(true)){
                String img_url = images.get(j).getImg_url();
                new ShareTask().execute(img_url);
            }

        }

    }

    private  class ShareTask extends AsyncTask<String,Integer,Bitmap>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... param) {
            return getBitmapFromURL(param[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            Uri uri = getImageUri(getApplicationContext(), bitmap);
            files.add(uri);


            if (total_selected==files.size()){

                final Thread thread= new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            Thread.sleep(1000);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    final Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                                    intent.putExtra(Intent.EXTRA_SUBJECT, "Here are some files.");
                                    intent.setType("image/jpeg"); /* This example is sharing jpeg images. */
                                    if(id==0){
                                        intent.setPackage("com.whatsapp");
                                    }else {
                                        intent.setPackage("com.whatsapp.w4b");
                                    }
                                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
                                    startActivity(Intent.createChooser(intent, "Send these images"));
                                    //mProgressDialog.hide();
                                }
                            });


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });

                thread.start();


            }
        }

    }


    public static Bitmap getBitmapFromURL(String src) {

        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

//    void setUpNavDrawer(){
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar , R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
//                super.onDrawerClosed(drawerView);
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
//                super.onDrawerOpened(drawerView);
//            }
//        };
//
//        //Setting the actionbarToggle to drawer layout
//        drawerLayout.setDrawerListener(actionBarDrawerToggle);
//
//        //calling sync state is necessary or else your hamburger icon wont show up
//        actionBarDrawerToggle.syncState();
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    public void getDetails(){

        String tag_json_obj = "json_obj_req";

        Map<String,String> post_param = new HashMap<>();
        post_param.put(KEY_CAT_ID,cat_id);
        post_param.put(KEY_C_ID,customer_session.getCustomerID());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                DATA_CAT_URL,new JSONObject(post_param),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String status = response.getString("status");

                            if (status.equals("200")){

                                JSONArray jsonArray = response.getJSONArray("userData");

                                String cat_desc = response.getString("cat_desc");
                                String is_size_avail = response.getString("is_size_ava");

                                if (is_size_avail.equals("1")){
                                    sizes_avail.clear();
                                    JSONArray size_array= response.getJSONArray("available_sizes");
                                    for (int i=0;i<size_array.length();i++){
                                        JSONObject sizes_object = size_array.getJSONObject(i);
                                        String size = sizes_object.getString("size");
                                        sizes_avail.add(size);
                                    }
                                }else {
                                    sizes_avail.add("false");
                                }

                                String cod_available = response.getString("is_cod_ava");

                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    JSONObject properties= jsonObject.getJSONObject("product_data");

                                    String rate_count = jsonObject.getString("rate_count");
                                    Boolean isLiked = jsonObject.getBoolean("isLiked");
                                    String img_url = properties.getString("original_img_dest");
                                    String img_id = properties.getString("img_id");
                                    String price = properties.getString("selling_price");
                                    String actual_price = properties.getString("actual_price");
                                    String cat_name = properties.getString("catalogue_name");
                                    String count_all = properties.getString("like_count");
                                    String image_name = properties.getString("image_name");
                                    String product_weight = properties.getString("product_weight");
                                    String is_satisfy = jsonObject.getString("is_satisfy");
                                    catlogue_name = properties.getString("catalogue_name");
                                    catlogue_id = properties.getString("catalogue_id");
                                    catlogue_desc = properties.getString("catalogue_description");

                                    Selection selection = new Selection(img_url,false,img_id,price,actual_price,cat_name,count_all,isLiked,image_name,sizes_avail,cod_available,cat_desc,product_weight,rate_count,is_satisfy);
                                    images.add(selection);
                                }

                                cat_text.setText(catlogue_name);
                                catdesc.setText(catlogue_desc);

                                toolbar_title.setText(catlogue_name);

                                mAdapter = new Cat_Image_Adapter(getApplicationContext(), images,  new Cat_Image_Adapter.ClickListener() {
                                    @Override
                                    public void onFavClick(View v, int pos, View v2) {
                                        addToFav((ImageView) v,images.get(pos).getImg_id(),(TextView) v2);
                                    }

                                    @Override
                                    public void onClick(View view, int position) {

                                    }

                                    @Override
                                    public void onImgClick(View view, int position) {
                                        Intent intent = new Intent(getApplicationContext(),Product_Details.class);
                                        Bundle data = new Bundle();
                                        data.putSerializable("images", images);
                                        intent.putExtra("ca",data);
                                        intent.putExtra("pos",position);
                                        intent.putExtra("cat_name",images.get(position).getCat_name());
                                        intent.putExtra("id",1);
                                        intent.putExtra("pNo","");
                                        intent.putExtra("catlog_id",cat_id);
                                        startActivity(intent);
                                    }


                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }

                                    @Override
                                    public void onBuyClick(Selection selection) {
                                        selectedsingle = selection;
                                        //ciid = categories.get(pos).getCat_no();
                                        showSIzeAndQtyDiaog(selection);
                                    }
                                });

                                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
                                r_view_1.setLayoutManager(staggeredGridLayoutManager);
                                r_view_1.setItemAnimator(new DefaultItemAnimator());
                                r_view_1.setAdapter(mAdapter);

                                catno.setText(images.size()+" Designs");
                                p_bar_1.setVisibility(View.GONE);
                                r_view_1.setVisibility(View.VISIBLE);
                                cat_layout.setVisibility(View.VISIBLE);

                            }else {
                                Toast.makeText(Cat_Details.this, "Connection Error", Toast.LENGTH_SHORT).show();
                                p_bar_1.setVisibility(View.VISIBLE);
                                r_view_1.setVisibility(View.GONE);
                                cat_layout.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            Toast.makeText(Cat_Details.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }



                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Cat_Details.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    void showSIzeAndQtyDiaog(Selection item) {

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        size_rec.setLayoutManager(horizontalLayoutManager);
        size_rec.setItemAnimator(new DefaultItemAnimator());

        size_adapter = new Cat_New_Adapter(getApplicationContext(), item.getSize_avail());
        size_rec.setAdapter(size_adapter);

        bottom_dialog.show();
    }

    private void addToFav(final ImageView v, String img_id, final TextView t) {


        String tag_json_obj = "json_obj_req";

        Map<String, String> postParam= new HashMap<>();
        postParam.put(KEY_ID,customer_session.getCustomerID());
        postParam.put(PRODUCT_ID,img_id);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                FAV_URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String status = response.getString("status");

                            if (status.equals("200")){
                                v.setImageResource(R.drawable.heart_re);
                                t.setText(response.getString("all_like_counter"));
                            }else if (status.equals("208")){
                                v.setImageResource(R.drawable.heart_outline);
                                t.setText(response.getString("all_like_counter"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(Cat_Details.this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }



}

