package com.zymiapp.apps.Fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.zymiapp.apps.Activities.Activity_Cart;
import com.zymiapp.apps.Activities.Main_Handler_Activity;
import com.zymiapp.apps.Activities.Product_Details;
import com.zymiapp.apps.Activities.Search_Activity;
import com.zymiapp.apps.Adapters.Cat_Image_Adapter_1;
import com.zymiapp.apps.Adapters.Cat_New_Adapter;
import com.zymiapp.apps.Adapters.Home_Adapter_1;
import com.zymiapp.apps.Adapters.Home_Adapter_2;
import com.zymiapp.apps.Adapters.Home_Adapter_3;
import com.zymiapp.apps.Adapters.Home_Adapter_5;
import com.zymiapp.apps.Adapters.MyAdapter;
import com.zymiapp.apps.Applications.AppController;
import com.zymiapp.apps.Model.Home_1;
import com.zymiapp.apps.Model.Selection;
import com.zymiapp.apps.Model.Slider;
import com.zymiapp.apps.R;
import com.zymiapp.apps.Session.Customer_Session;
import com.squareup.picasso.Picasso;
import com.zymiapp.apps.ViewPager.CustomViewPager;

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
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

import static android.content.Context.CLIPBOARD_SERVICE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Fragment_Home extends Fragment {

    private static final String KEY_OFFSET = "offset";
    private int offset = 0;

    public Fragment_Home() {
        // Required empty public constructor
    }

    private ImageView fsatisy;
    private TextView rating;
    private static final String FAV_URL = "https://www.resellingapp.com/apiv2/zymi/rest_server/userlikelist/API-KEY/123456";
    private static final String KEY_ID = "c_id";
    private static final String PRODUCT_ID = "product_id";
    private static final String TAG = Main_Handler_Activity.class.getSimpleName();
    private static final String DATA_CAT_URL = "https://www.resellingapp.com/apiv2/zymi/rest_server/catalogueContent/API-KEY/123456";
    private static final String DATA_CATAGORY_URL = "https://www.resellingapp.com/apiv2/zymi/rest_server/allCategoey/API-KEY/123456";
    private static final String KEY_CAT_ID = "catalogue_id";
    private static final String KEY_CATEGORY_ID = "category_id";
    private static final String KEY_C_ID = "c_id";
    private ArrayList<Selection> images = new ArrayList<>();
    private SwipeRefreshLayout refresh;
    private RecyclerView recyclerView;
    private View rootview;
    private RelativeLayout rl;
    private MyAdapter myAdapter;
    private static ViewPager mPager;
    private List<Home_1> categories = new ArrayList<>();
    private List<Home_1> categories5 = new ArrayList<>();
    private List<Home_1> categoriestop = new ArrayList<>();
    private Home_Adapter_1 mAdapter;
    private Home_Adapter_5 mAdapter5;
    private String cat_name;
    private String cat_id;
    private String tag_name;
    private String tag_name_2;
    private String tag_color_2;
    private String tag_color;
    private String cat_count;
    private String img_url;
    private ProgressBar p_bar;
    //private static final String DATA_URL = "https://www.resellingapp.com/apiv2/zymi/rest_server/homecontent/API-KEY/123456";
    private static final String DATA_URL = "https://www.resellingapp.com/apiv2/zymi/index.php/rest_server/firstContentNew/API-KEY/123456";
    private static final String IMG_URL = "https://www.resellingapp.com/apiv2/zymi/rest_server/getHomeSlider/API-KEY/123456";
    private LinearLayout lin_cat_details;
    private LinearLayout lin_frag_home;
    private RelativeLayout cat_layout;
    private ProgressBar p_bar_1;
    private RecyclerView r_view_1;
    private TextView cat_text;
    private TextView catno;
    private TextView catdesc;
    private TextView tag_name_text;
    // Progress Dialog
    private ProgressDialog mProgressDialog;
    private Button btn_download;
    private Button btn_copy;
    private Button btn_share_desc;
    private Button btn_share_fb;
    private RelativeLayout btn_share;
    private CheckBox share_all_check;
    private int total_selected = 0;
    private ImageView img;
    private ArrayList<Uri> files;
    private List<Slider> sliders = new ArrayList<>();
    private int id = 0;
    private int id_ = 0;
    private int pos;
    private Customer_Session customer_session;
    private Cat_Image_Adapter_1 mAdapter_1;
    //Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public interface onDataLoadListener {
        void onSliderLoaded(String w_no, String wtext, String wb_no, String wb_text);

        void requiredDtata(int id, LinearLayout lin1, LinearLayout lin2, ProgressBar p_bar, NestedScrollView sv1, NestedScrollView sv2, NestedScrollView sv3, RelativeLayout catloguye_layout, TextView fab);
    }

    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    onDataLoadListener onDataloadListener;
    private NestedScrollView scroll_view;
    private NestedScrollView scroll_view_1;
    private NestedScrollView scroll_view_2;
    private LinearLayout frag_catlogue_details;
    private RecyclerView catlogue_rec_view;
    private RelativeLayout catlogue_layout;
    private TextView catlogue_content;
    private TextView catlogue_desc;
    private TextView catlogue_tag_name;
    private TextView catlogue_tag_name_2;
    private TextView catlogue_no;
    private ProgressBar p_bar_2;
    int splash_id;
    String splash_cat;
    public String wp_no = "";
    public String wp_no_b = "";
    public String wp_text = "";
    public String wp_b_text = "";
    private int load_id = 0;
    private String cat_desc = "";
    private String slider_url = "";
    private Home_Adapter_2 mAdapter_2;
    private Home_Adapter_3 mAdapter_3;
    private static final String DATA_CATLOGUE_URL = "https://www.resellingapp.com/apiv2/zymi/rest_server/catCatalogueContent/API-KEY/123456";
    //private List<Home_1> categories=new ArrayList<>();
    private List<String> sizes_avail = new ArrayList<>();
    private CircleImageView topimg1, topimg2, topimg3, topimg4;
    private TextView toptxt1, toptxt2, toptxt3, toptxt4;
    private LinearLayout toplin1, toplin2, toplin3, toplin4, toplin5;
    private Dialog bottom_dialog;
    private RecyclerView size_rec;
    private RecyclerView qty_list;
    private List<String> qty = new ArrayList<>();
    private Cat_New_Adapter size_adapter;
    private Cat_New_Adapter qty_adapter;
    private Button btn;
    private static final String CART_URL = "https://www.resellingapp.com/apiv2/zymi/rest_server/userCart/API-KEY/123456";
    private static final String KEY_PRODUCT_ID = "product_id";
    private static final String KEY_PRODUCT_NAME = "product_name";
    private static final String KEY_ACTUAL_PRICE = "actual_price";
    private static final String KEY_SELLING_PRICE = "selling_price";
    private static final String KEY_IMG_URL = "product_image";
    private static final String KEY_PRODUCT_WEIGHT = "product_weight";
    private static final String KEY_CATLOG_ID = "catalog_id";
    private static final String KEY_RES_ID = "res_id";
    private static final String KEY_COD_AVAILABLE = "is_cod_available";
    private static final String KEY_QTY = "qty";
    private static final String KEY_PRODUCT_SIZE = "product_size";
    private Selection selectedsingle;
    private String ciid = "";
    private EditText edit_margin;
    private RecyclerView catagory_rec_view;
    private ProgressBar p_bar_4;
    private LinearLayout catagory_scroll_view;
    private TextView fab;
    private LinearLayout searchbar;
    private String description = "";
    private ImageView searchbtn;
    private EditText searchtext;
    Timer swipeTimer;
    //private EditText searchTxt;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onDataloadListener = (onDataLoadListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_fragment__home, container, false);

        Intent intent = getActivity().getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            splash_id = extras.getInt("id");
            splash_cat = extras.getString("cat");
        }

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCanceledOnTouchOutside(false);

        Typeface tf1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arial.ttf");

        customer_session = new Customer_Session(getActivity());
        bottom_dialog = new Dialog(getContext(), R.style.BottomDialog);
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
        searchbtn = rootview.findViewById(R.id.searchbtn);
        searchtext = rootview.findViewById(R.id.searchtext);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!searchtext.getText().toString().isEmpty()) {
                    Intent i = new Intent(getActivity(), Search_Activity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("searchtxt", searchtext.getText().toString());
                    getActivity().startActivity(i);
                }
            }
        });

        edit_margin = (EditText) bottom_dialog.findViewById(R.id.input_margin);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
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
        qty_adapter = new Cat_New_Adapter(getContext(), qty);
        qty_list.setAdapter(qty_adapter);

        btn = bottom_dialog.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qty_adapter.getSelectedPosition() != -1 && size_adapter.getSelectedPosition() != -1) {
                    if (!edit_margin.getText().toString().trim().equals("")) {
                        bottom_dialog.dismiss();
                        //showProgressDialog();
                        addToCart(selectedsingle, ciid);
                    } else
                        Toast.makeText(getContext(), "Please add margin", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Please select the size and quantity", Toast.LENGTH_SHORT).show();
                }
            }
        });

        topimg1 = rootview.findViewById(R.id.topimg1);
        topimg2 = rootview.findViewById(R.id.topimg2);
        topimg3 = rootview.findViewById(R.id.topimg3);
        topimg4 = rootview.findViewById(R.id.topimg4);
        toptxt1 = rootview.findViewById(R.id.toptxt1);
        toptxt2 = rootview.findViewById(R.id.toptxt2);
        toptxt3 = rootview.findViewById(R.id.toptxt3);
        toptxt4 = rootview.findViewById(R.id.toptxt4);
        toplin1 = rootview.findViewById(R.id.toplin1);
        toplin2 = rootview.findViewById(R.id.toplin2);
        toplin3 = rootview.findViewById(R.id.toplin3);
        toplin4 = rootview.findViewById(R.id.toplin4);
        toplin5 = rootview.findViewById(R.id.toplin5);
        fab = rootview.findViewById(R.id.fab);
        searchbar = rootview.findViewById(R.id.searchbar);
        // searchTxt = rootview.findViewById(R.id.searchTxt);

/*        searchbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(), Search_Activity.class);
                getContext().startActivity(i);
            }
        });*/

/*        searchTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(), Search_Activity.class);
                getContext().startActivity(i);
            }
        });*/

        toplin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAdapterItemClicktop(0);
            }
        });
        toplin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAdapterItemClicktop(1);
            }
        });
        toplin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAdapterItemClicktop(2);
            }
        });
        toplin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAdapterItemClicktop(3);
            }
        });
        toplin5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll_view.setVisibility(GONE);
                scroll_view_1.setVisibility(GONE);
                fab.setVisibility(GONE);
                scroll_view_2.setVisibility(GONE);
                catagory_scroll_view.setVisibility(VISIBLE);
                p_bar_4.setVisibility(VISIBLE);
                getCatagoryData();
            }
        });


        lin_cat_details = rootview.findViewById(R.id.cat_details);
        lin_frag_home = rootview.findViewById(R.id.fragment_home);
        cat_layout = rootview.findViewById(R.id.cat_layout);
        catlogue_layout = rootview.findViewById(R.id.catlogue_layout);
        frag_catlogue_details = rootview.findViewById(R.id.catlogue_details);
        img = rootview.findViewById(R.id.img_slider);

        scroll_view = rootview.findViewById(R.id.scroll_view);
        scroll_view_1 = rootview.findViewById(R.id.scroll_view_1);
        scroll_view_2 = rootview.findViewById(R.id.catalogue_scroll_view);
        catlogue_rec_view = rootview.findViewById(R.id.catlogue_rec_view);

        catlogue_content = rootview.findViewById(R.id.catlogue_name);
        catlogue_desc = rootview.findViewById(R.id.catlogue_desc);
        catlogue_no = rootview.findViewById(R.id.catlogue_no);
        catlogue_tag_name = rootview.findViewById(R.id.newly_added_2);
        catlogue_tag_name_2 = rootview.findViewById(R.id.newly_added_3);
        p_bar_2 = rootview.findViewById(R.id.p_bar_2);

        cat_text = rootview.findViewById(R.id.cat_name);

        catno = rootview.findViewById(R.id.cat_no);

        catdesc = rootview.findViewById(R.id.cat_desc);

        tag_name_text = rootview.findViewById(R.id.newly_added);
        catagory_rec_view = rootview.findViewById(R.id.catagory_rec_view);
        catagory_scroll_view = rootview.findViewById(R.id.catagory_scroll_view);
        p_bar_4 = rootview.findViewById(R.id.p_bar_4);

        btn_copy = rootview.findViewById(R.id.copy_desc_btn);
        btn_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", categories.get(pos).getCat_desc());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getActivity(), "Description Copied", Toast.LENGTH_SHORT).show();
            }

        });


        share_all_check = rootview.findViewById(R.id.share);
        share_all_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mAdapter_1.selectAll();
                } else {
                    mAdapter_1.deselectAll();
                }
            }
        });

        btn_share_desc = rootview.findViewById(R.id.share_desc_btn);
        btn_share_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id_ = 3;
                mProgressDialog.show();

                total_selected = 0;
                for (int i = 0; i < images.size(); i++) {
                    if (images.get(i).getSelect().equals(true)) {
                        total_selected += 1;
                    }
                }

                files = new ArrayList<>();
                for (int j = 0; j < images.size(); j++) {

                    if (images.get(j).getSelect().equals(true)) {
                        String img_url = images.get(j).getImg_url();
                        description = categories.get(pos).getCat_desc();
                        new ShareTask().execute(img_url);
                    }

                    if (j == images.size() - 1) {
                        if (total_selected == 0) {
                            mProgressDialog.dismiss();
                            Toast.makeText(getActivity(), "Select at least one image", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });

        rating = rootview.findViewById(R.id.rating);

        fsatisy = rootview.findViewById(R.id.fsatisfy);

        btn_share_fb = rootview.findViewById(R.id.share_facebook_btn);
        btn_share_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProgressDialog.show();

                id_ = 2;
                total_selected = 0;
                for (int i = 0; i < images.size(); i++) {
                    if (images.get(i).getSelect().equals(true)) {
                        total_selected += 1;
                    }
                }

                files = new ArrayList<>();
                for (int j = 0; j < images.size(); j++) {

                    if (images.get(j).getSelect().equals(true)) {
                        String img_url = images.get(j).getImg_url();
                        description = categories.get(pos).getCat_desc();
                        new ShareTask().execute(img_url);
                    }

                    if (j == images.size() - 1) {
                        if (total_selected == 0) {
                            mProgressDialog.dismiss();
                            Toast.makeText(getActivity(), "Select at least one image", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });

        btn_download = rootview.findViewById(R.id.download_btn);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < images.size(); i++) {

                    if (images.get(i).getSelect().equals(true)) {

                        mProgressDialog.show();

                        Thread shivThread1 = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(500);

                                    getActivity().runOnUiThread(new Runnable() {
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
                    } else {
                        if (i == images.size() - 1) {
                            Toast.makeText(getActivity(), "Select at least one image", Toast.LENGTH_SHORT).show();
                        }
                    }


                }

            }
        });

        catlogue_rec_view.setNestedScrollingEnabled(false);

        btn_share = rootview.findViewById(R.id.sahre_btn);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check if we have write permission
                int permission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE
                    );
                } else {


                    for (int k = 0; k < images.size(); k++) {

                        if (images.get(k).getSelect().equals(true)) {

                            if (whatsappInstalledOrNot("com.whatsapp") && whatsappInstalledOrNot("com.whatsapp.w4b")) {
                                String title = "Send to :";
                                CharSequence[] itemlist = {"Whatsapp",
                                        "Whatsapp Business",
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                //builder.setIcon(R.drawable.icon_app);
                                builder.setTitle(title);
                                builder.setItems(itemlist, new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:// Take Photo
                                                try {
                                                    id_ = 0;
                                                    total_selected = 0;
                                                    for (int i = 0; i < images.size(); i++) {
                                                        if (images.get(i).getSelect().equals(true)) {
                                                            total_selected += 1;
                                                        }
                                                    }

                                                    files = new ArrayList<>();
                                                    for (int j = 0; j < images.size(); j++) {

                                                        if (images.get(j).getSelect().equals(true)) {
                                                            String img_url = images.get(j).getImg_url();
                                                            description = "";
                                                            new ShareTask().execute(img_url);
                                                        }

                                                    }

                                                } catch (NullPointerException e) {
                                                    e.printStackTrace();
                                                }
                                                break;
                                            case 1:// Choose Existing Photo
                                                try {
                                                    id_ = 1;
                                                    total_selected = 0;
                                                    for (int i = 0; i < images.size(); i++) {
                                                        if (images.get(i).getSelect().equals(true)) {
                                                            total_selected += 1;
                                                        }
                                                    }

                                                    files = new ArrayList<>();
                                                    for (int j = 0; j < images.size(); j++) {

                                                        if (images.get(j).getSelect().equals(true)) {
                                                            String img_url = images.get(j).getImg_url();
                                                            description = "";
                                                            new ShareTask().execute(img_url);
                                                        }

                                                    }

                                                } catch (NullPointerException e) {
                                                    e.printStackTrace();
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

                            } else {
                                id_ = 0;
                                try {
                                    total_selected = 0;
                                    for (int i = 0; i < images.size(); i++) {
                                        if (images.get(i).getSelect().equals(true)) {
                                            total_selected += 1;
                                        }
                                    }

                                    files = new ArrayList<>();
                                    for (int j = 0; j < images.size(); j++) {

                                        if (images.get(j).getSelect().equals(true)) {
                                            String img_url = images.get(j).getImg_url();
                                            description = "";
                                            new ShareTask().execute(img_url);
                                        }

                                    }

                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            }


                            break;

                        } else {
                            if (k == images.size() - 1) {
                                Toast.makeText(getActivity(), "Select at least one image", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                }

            }
        });

        r_view_1 = rootview.findViewById(R.id.recycler_view);
        rl = rootview.findViewById(R.id.home_slider);

        p_bar = rootview.findViewById(R.id.progress_bar);
        p_bar_1 = rootview.findViewById(R.id.p_bar_1);

        recyclerView = rootview.findViewById(R.id.r_view);

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setVisibility(View.VISIBLE);

        r_view_1.setNestedScrollingEnabled(false);

        refresh = rootview.findViewById(R.id.simpleSwipeRefreshLayout);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (scroll_view_1.getVisibility() == VISIBLE) {
                    p_bar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(GONE);
                    rl.setVisibility(GONE);
                    getData();
                    refresh.setRefreshing(false);
                } else if (scroll_view.getVisibility() == VISIBLE) {
                    lin_cat_details.setVisibility(VISIBLE);
                    lin_frag_home.setVisibility(GONE);
                    p_bar_1.setVisibility(View.VISIBLE);
                    scroll_view.setVisibility(VISIBLE);
                    scroll_view_2.setVisibility(GONE);
                    scroll_view_1.setVisibility(View.GONE);
                    fab.setVisibility(GONE);
                    cat_text.setText(categories.get(pos).getCat_name());
                    catdesc.setText(categories.get(pos).getCat_desc());
                    tag_name_text.setText("#" + categories.get(pos).getTag_name());
//                    tag_name_2_text.setText("#"+categories.get(pos).getTag_name_2());
                    if (categories.get(pos).getTag_name().equals("Default") || categories.get(pos).getTag_name().equals("") || categories.get(pos).getTag_name().equals("default")) {
                        tag_name_text.setVisibility(GONE);
                    }

                    if (categories.get(pos).getTag_name_2().equals("Default") || categories.get(pos).getTag_name_2().equals("") || categories.get(pos).getTag_name().equals("default")) {
//                        tag_name_2_text.setVisibility(GONE);
                    }

                    try {
                        tag_name_text.setBackgroundColor(Color.parseColor(categories.get(pos).getTag_color()));
//                        tag_name_2_text.setBackgroundColor(Color.parseColor(categories.get(pos).getTag_color_2()));
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (StringIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    getDetails("");
                    refresh.setRefreshing(false);
                } else {
                    /*catlogue_layout.setVisibility(GONE);
                    p_bar_2.setVisibility(VISIBLE);
                    lin_cat_details.setVisibility(GONE);
                    lin_frag_home.setVisibility(GONE);
                    scroll_view.setVisibility(GONE);
                    scroll_view_1.setVisibility(GONE);
                    scroll_view_2.setVisibility(View.VISIBLE);
                    catlogue_content.setText(categories.get(pos).getCat_name());
                    catlogue_desc.setText(categories.get(pos).getCat_desc());
                    catlogue_tag_name.setText(categories.get(pos).getTag_name());
                    catlogue_tag_name_2.setText(categories.get(pos).getTag_name_2());
                    if (categories.get(pos).getTag_name().equals("Default") || categories.get(pos).getTag_name().equals("") || categories.get(pos).getTag_name().equals("default")){
                        catlogue_tag_name.setVisibility(GONE);
                    }

                    if (categories.get(pos).getTag_name_2().equals("Default") || categories.get(pos).getTag_name_2().equals("")|| categories.get(pos).getTag_name().equals("default")){
                        catlogue_tag_name_2.setVisibility(GONE);
                    }

                    try{
                        catlogue_tag_name.setBackgroundColor(Color.parseColor(categories.get(pos).getTag_color()));
                        catlogue_tag_name_2.setBackgroundColor(Color.parseColor(categories.get(pos).getTag_color_2()));
                    }catch (IllegalStateException e){
                        e.printStackTrace();
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }catch (StringIndexOutOfBoundsException e){
                        e.printStackTrace();
                    }
                    getCatlogues("",0);
                    refresh.setRefreshing(false);*/
                    refresh.setRefreshing(false);
                }

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scroll_view_1.getVisibility() == VISIBLE) {
                    p_bar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(GONE);
                    rl.setVisibility(GONE);
                    getData();
                    refresh.setRefreshing(false);
                } else if (scroll_view.getVisibility() == VISIBLE) {
                    lin_cat_details.setVisibility(VISIBLE);
                    lin_frag_home.setVisibility(GONE);
                    p_bar_1.setVisibility(View.VISIBLE);
                    scroll_view.setVisibility(VISIBLE);
                    scroll_view_2.setVisibility(GONE);
                    scroll_view_1.setVisibility(View.GONE);
                    fab.setVisibility(GONE);
                    cat_text.setText(categories.get(pos).getCat_name());
                    catdesc.setText(categories.get(pos).getCat_desc());
                    tag_name_text.setText("#" + categories.get(pos).getTag_name());
//                    tag_name_2_text.setText("#"+categories.get(pos).getTag_name_2());
                    if (categories.get(pos).getTag_name().equals("Default") || categories.get(pos).getTag_name().equals("") || categories.get(pos).getTag_name().equals("default")) {
                        tag_name_text.setVisibility(GONE);
                    }

                    if (categories.get(pos).getTag_name_2().equals("Default") || categories.get(pos).getTag_name_2().equals("") || categories.get(pos).getTag_name().equals("default")) {
//                        tag_name_2_text.setVisibility(GONE);
                    }

                    try {
                        tag_name_text.setBackgroundColor(Color.parseColor(categories.get(pos).getTag_color()));
//                        tag_name_2_text.setBackgroundColor(Color.parseColor(categories.get(pos).getTag_color_2()));
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (StringIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    getDetails("");
                    refresh.setRefreshing(false);
                } else {
                    /*catlogue_layout.setVisibility(GONE);
                    p_bar_2.setVisibility(VISIBLE);
                    lin_cat_details.setVisibility(GONE);
                    lin_frag_home.setVisibility(GONE);
                    scroll_view.setVisibility(GONE);
                    scroll_view_1.setVisibility(GONE);
                    scroll_view_2.setVisibility(View.VISIBLE);
                    catlogue_content.setText(categories.get(pos).getCat_name());
                    catlogue_desc.setText(categories.get(pos).getCat_desc());
                    catlogue_tag_name.setText(categories.get(pos).getTag_name());
                    catlogue_tag_name_2.setText(categories.get(pos).getTag_name_2());
                    if (categories.get(pos).getTag_name().equals("Default") || categories.get(pos).getTag_name().equals("") || categories.get(pos).getTag_name().equals("default")){
                        catlogue_tag_name.setVisibility(GONE);
                    }

                    if (categories.get(pos).getTag_name_2().equals("Default") || categories.get(pos).getTag_name_2().equals("")|| categories.get(pos).getTag_name().equals("default")){
                        catlogue_tag_name_2.setVisibility(GONE);
                    }

                    try{
                        catlogue_tag_name.setBackgroundColor(Color.parseColor(categories.get(pos).getTag_color()));
                        catlogue_tag_name_2.setBackgroundColor(Color.parseColor(categories.get(pos).getTag_color_2()));
                    }catch (IllegalStateException e){
                        e.printStackTrace();
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }catch (StringIndexOutOfBoundsException e){
                        e.printStackTrace();
                    }
                    getCatlogues("",0);
                    refresh.setRefreshing(false);*/
                    refresh.setRefreshing(false);
                }
            }
        });


        return rootview;
    }

    private void init(View v) {

        mPager = v.findViewById(R.id.pager);
        mPager.setAdapter(myAdapter);

        CircleIndicator indicator = v.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);


        mPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                refresh.setEnabled(false);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        refresh.setEnabled(true);
                        break;
                }
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        NUM_PAGES = sliders.size();

        // Auto start of viewpager
        currentPage = 0;
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        if (swipeTimer != null) {
            swipeTimer.cancel();
            swipeTimer = null;
        }
        swipeTimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        };
        swipeTimer.schedule(timerTask, 5000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mPager.getParent().requestDisallowInterceptTouchEvent(true);
            }
        });

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getTopData();
        getData();
    }

    void getTopData() {

        String topUrl = "https://www.resellingapp.com/apiv2/zymi/index.php/rest_server/topFourCategory/API-KEY/123456";
        String tag_json_obj = "json_obj_req";
        Map<String, String> post_param = new HashMap<>();
        post_param.put(KEY_C_ID, customer_session.getCustomerID());
        post_param.put(KEY_OFFSET, String.valueOf(offset));
        post_param.put("limit", "10");
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                topUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.v("Rabby", response.toString());

                            String status = response.getString("status");

                            if (status.equals("200")) {
                                JSONArray jsonArray = response.getJSONArray("catData");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    if (i == 0) {
                                        Picasso.with(getContext()).load(jsonObject.getString("category_image")).placeholder(R.drawable.phimg).into(topimg1);
                                        toptxt1.setText(jsonObject.getString("category_name"));
                                    }
                                    if (i == 1) {
                                        Picasso.with(getContext()).load(jsonObject.getString("category_image")).placeholder(R.drawable.phimg).into(topimg2);
                                        toptxt2.setText(jsonObject.getString("category_name"));
                                    }
                                    if (i == 2) {
                                        Picasso.with(getContext()).load(jsonObject.getString("category_image")).placeholder(R.drawable.phimg).into(topimg3);
                                        toptxt3.setText(jsonObject.getString("category_name"));
                                    }
                                    if (i == 3) {
                                        Picasso.with(getContext()).load(jsonObject.getString("category_image")).placeholder(R.drawable.phimg).into(topimg4);
                                        toptxt4.setText(jsonObject.getString("category_name"));
                                    }

                                    cat_name = jsonObject.getString("category_name");
                                    cat_id = jsonObject.getString("id");
                                    tag_name = jsonObject.getString("tag_name");
                                    tag_color = jsonObject.getString("tag_color");
                                    tag_name_2 = jsonObject.getString("tag_name");
                                    tag_color_2 = jsonObject.getString("tag_color");
                                    img_url = jsonObject.getString("category_image");
                                    cat_desc = jsonObject.getString("description");
                                    categoriestop.add(new Home_1(cat_name, cat_id, cat_count, tag_name, tag_name_2, tag_color, tag_color_2, cat_desc, img_url, ""));
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.w("Rabby", "0 :" + e.toString());
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("Rabby", "2 :" + error.toString());
                Toast.makeText(getActivity(), "Error due to low internet connection", Toast.LENGTH_SHORT).show();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    void getData() {

        String tag_json_obj = "json_obj_req";
        Map<String, String> post_param = new HashMap<>();
        post_param.put(KEY_C_ID, customer_session.getCustomerID());
        post_param.put(KEY_OFFSET, String.valueOf(offset));
        post_param.put("limit", "10");
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                DATA_URL, new JSONObject(post_param),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            categories.clear();

                            String status = response.getString("status");

                            if (status.equals("200")) {
                                offset = response.getInt("new_offset");
                                JSONArray jsonArray = response.getJSONArray("userData");


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String img1 = "";
                                    String img2 = "";
                                    String img3 = "";
                                    /*"type": "kurtis",
            "catalogue_id": "4461",
            "catalogName": "Miu Miu",
            "category_id": "261",
            "category_name": "Googals",
            "items_count": 4,
            "tag_name": "Hot",
            "tag_color": "blue",
            "is_satisfy": "1",
            "visitor_count": "5",
            "description": "MIU MIU\r\nNEW MODEL \r\nIN STOCK\r\nSAME TO SAME\r\nWOMEN SUNGLASS  \r\nUV-ULTRA VOILET GLASS\r\nGOOD QUALITY VISION GLASS \r\nCOMES WITH MIU MIU BOX\r\nFZ04",
            "catalogue_image": "https://www.orderx7.com/resellers/partner/assets/product_image/front_display/1577042025_106808.jpg",*/


                                    cat_name = jsonObject.getString("catalogName");
                                    cat_id = jsonObject.getString("catalogue_id");
                                    int count = jsonObject.getInt("items_count");
                                    cat_count = String.valueOf(count);
                                    tag_name = jsonObject.getString("tag_name");
                                    tag_color = jsonObject.getString("tag_color");
                                    tag_name_2 = jsonObject.getString("tag_name");
                                    tag_color_2 = jsonObject.getString("tag_color");
                                    img_url = jsonObject.getString("catalogue_image");
                                    cat_desc = jsonObject.getString("description");


                                    JSONObject jsonObject1 = jsonObject.getJSONObject("wa_data");
                                    wp_no = jsonObject1.getString("gen_whatsapp_number");
                                    wp_no_b = jsonObject1.getString("biz_whatsapp_number");
                                    wp_text = jsonObject1.getString("gen_whatsapp_text");
                                    wp_b_text = jsonObject1.getString("biz_whatsapp_text");
                                    slider_url = jsonObject1.getString("img_dest");

                                    JSONObject jsonObject2 = jsonObject.getJSONObject("front_image");
                                    if (onDataloadListener != null) {
                                        onDataloadListener = null;
                                        onAttach(getActivity());
                                    }
                                    onDataloadListener.onSliderLoaded(wp_no, wp_text, wp_no_b, wp_b_text);

                                    JSONArray imageArray = jsonObject2.getJSONArray("images");
                                    ArrayList<String> imagelist = new ArrayList<>();
                                    for (int j = 0; j < imageArray.length(); j++) {
                                        JSONObject jobj = imageArray.getJSONObject(j);
                                        imagelist.add(jobj.getString("thumb_img_dest"));
                                        if (j == 0) {
                                            img1 = jobj.getString("thumb_img_dest");
                                            img2 = jobj.getString("thumb_img_dest");
                                            img3 = jobj.getString("thumb_img_dest");
                                        }
                                        if (j == 1) {
                                            img2 = jobj.getString("thumb_img_dest");
                                            img3 = jobj.getString("thumb_img_dest");
                                        }
                                        if (j == 2)
                                            img3 = jobj.getString("thumb_img_dest");
                                    }

                                    categories.add(new Home_1(cat_name, cat_id, cat_count, tag_name, tag_name_2, tag_color, tag_color_2, cat_desc, img_url, jsonObject.getString("shipment"), img1, img2, img3, jsonObject.getString("starting_price"), imagelist));
                                }

//                                onDataloadListener.onDataLoaded(arrays);

                                mAdapter = new Home_Adapter_1(scroll_view_1, getActivity(), categories, new Home_Adapter_1.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        onAdapterItemClick(position);
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }

                                    @Override
                                    public void onImgCLick(View view, int position) {

                                        pos = position;
                                        startThread();
                                        cat_layout.setVisibility(GONE);
                                        lin_cat_details.setVisibility(VISIBLE);
                                        lin_frag_home.setVisibility(GONE);
                                        p_bar_1.setVisibility(View.VISIBLE);
                                        scroll_view.setVisibility(VISIBLE);
                                        scroll_view_2.setVisibility(GONE);
                                        scroll_view_1.setVisibility(View.GONE);
                                        fab.setVisibility(GONE);
                                        cat_text.setText(categories.get(pos).getCat_name());
                                        catdesc.setText(categories.get(pos).getCat_desc());
                                        tag_name_text.setText("#" + categories.get(pos).getTag_name());
//                                        tag_name_2_text.setText("#"+categories.get(pos).getTag_name_2());
                                        if (categories.get(pos).getTag_name().equals("Default") || categories.get(pos).getTag_name().equals("") || categories.get(pos).getTag_name().equals("default")) {
                                            tag_name_text.setVisibility(GONE);
                                        }

                                        if (categories.get(pos).getTag_name_2().equals("Default") || categories.get(pos).getTag_name_2().equals("") || categories.get(pos).getTag_name().equals("default")) {
//                                            tag_name_2_text.setVisibility(GONE);
                                        }

                                        try {
                                            tag_name_text.setBackgroundColor(Color.parseColor(categories.get(pos).getTag_color()));
//                                            tag_name_2_text.setBackgroundColor(Color.parseColor(categories.get(pos).getTag_color_2()));
                                        } catch (IllegalStateException e) {
                                            e.printStackTrace();
                                        } catch (NullPointerException e) {
                                            e.printStackTrace();
                                        } catch (StringIndexOutOfBoundsException e) {
                                            e.printStackTrace();
                                        } catch (IllegalArgumentException e) {
                                            e.printStackTrace();
                                        }
                                        getDetails("");
                                    }

                                    @Override
                                    public void onDownloadCLick(Home_1 data) {
                                        Toast.makeText(getContext(), "Downloading images", Toast.LENGTH_SHORT).show();
                                        for (int i = 0; i < data.getImageList().size(); i++) {
                                            new DownloadTask().execute(data.getImageList().get(i));
                                        }
                                       /* new DownloadTask().execute(data.getImga());
                                        new DownloadTask().execute(data.getImgb());
                                        new DownloadTask().execute(data.getImgc());*/
                                    }

                                    @Override
                                    public void onShareCLick(Home_1 data) {

                                        Intent i = new Intent(Intent.ACTION_VIEW,
                                                Uri.parse("https://www.youtube.com/channel/UCE2iK9eSZA1S3y-2-eYQirg"));
                                        i.setPackage("com.android.chrome");
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onWhatssAppCLick(Home_1 data, int type) {
                                        if (type == 1) {
                                            files = new ArrayList<>();
                                            total_selected = data.getImageList().size();
                                            id_ = 0;
                                            hitshare(data.getCat_no());
                                            for (int i = 0; i < data.getImageList().size(); i++) {
                                                new ShareTask().execute(data.getImageList().get(i));
                                            }

                                        } else {
                                            files = new ArrayList<>();
                                            total_selected = data.getImageList().size();
                                            id_ = 2;
                                            hitshare(data.getCat_no());
                                            for (int i = 0; i < data.getImageList().size(); i++) {
                                                new ShareTask().execute(data.getImageList().get(i));
                                            }
                                        }
                                    }
                                });
                                mAdapter.setLoadId0();
                                mAdapter.setOnLoadMoreListener(new Home_Adapter_1.OnLoadMoreListener() {
                                    @Override
                                    public void onLoadMore() {
                                        //categories.add(null);
                                        //mAdapter.notifyItemRemoved(categories.size());
                                        // getData();
                                    }
                                });
                                Picasso.with(getActivity()).load(slider_url).placeholder(R.drawable.phimg).into(img);

                                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
                                recyclerView.setLayoutManager(staggeredGridLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(mAdapter);

                                p_bar.setVisibility(GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                rl.setVisibility(GONE);
                                //img.setVisibility(View.VISIBLE);

                                recyclerView.setBackgroundColor(getResources().getColor(R.color.black));
                                getImages();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.w("Rabby", "1 :" + e.toString());
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("Rabby", "2 :" + error.toString());
                Toast.makeText(getActivity(), "Error due to low internet connection", Toast.LENGTH_SHORT).show();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    private void onAdapterItemClick(int position) {

        pos = position;
        startThread();
        cat_layout.setVisibility(GONE);
        lin_cat_details.setVisibility(VISIBLE);
        lin_frag_home.setVisibility(GONE);
        p_bar_1.setVisibility(View.VISIBLE);
        scroll_view.setVisibility(VISIBLE);
        scroll_view_2.setVisibility(GONE);
        scroll_view_1.setVisibility(View.GONE);
        fab.setVisibility(GONE);
        cat_text.setText(categories.get(pos).getCat_name());
        catdesc.setText(categories.get(pos).getCat_desc());
        tag_name_text.setText("#" + categories.get(pos).getTag_name());
//                                        tag_name_2_text.setText("#"+categories.get(pos).getTag_name_2());
        if (categories.get(pos).getTag_name().equals("Default") || categories.get(pos).getTag_name().equals("") || categories.get(pos).getTag_name().equals("default")) {
            tag_name_text.setVisibility(GONE);
        }

        if (categories.get(pos).getTag_name_2().equals("Default") || categories.get(pos).getTag_name_2().equals("") || categories.get(pos).getTag_name().equals("default")) {
//                                            tag_name_2_text.setVisibility(GONE);
        }

        try {
            tag_name_text.setBackgroundColor(Color.parseColor(categories.get(pos).getTag_color()));
//                                            tag_name_2_text.setBackgroundColor(Color.parseColor(categories.get(pos).getTag_color_2()));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        getDetails("");
    }

    private void onAdapterItemClicktop(int position) {

        pos = position;
        catlogue_layout.setVisibility(GONE);
        p_bar_2.setVisibility(VISIBLE);
        lin_cat_details.setVisibility(GONE);
        lin_frag_home.setVisibility(GONE);
        scroll_view.setVisibility(GONE);
        scroll_view_1.setVisibility(GONE);
        fab.setVisibility(GONE);
        scroll_view_2.setVisibility(View.VISIBLE);
        catlogue_content.setText(categoriestop.get(pos).getCat_name());
        catlogue_desc.setText(categoriestop.get(pos).getCat_desc());
        catlogue_tag_name.setText(categoriestop.get(pos).getTag_name());
        catlogue_tag_name_2.setText(categoriestop.get(pos).getTag_name_2());
        if (categoriestop.get(pos).getTag_name().equals("Default") || categoriestop.get(pos).getTag_name().equals("") || categoriestop.get(pos).getTag_name().equals("default")) {
            catlogue_tag_name.setVisibility(GONE);
        }

        if (categoriestop.get(pos).getTag_name_2().equals("Default") || categoriestop.get(pos).getTag_name_2().equals("") || categoriestop.get(pos).getTag_name().equals("default")) {
            catlogue_tag_name_2.setVisibility(GONE);
        }

        try {
            catlogue_tag_name.setBackgroundColor(Color.parseColor(categoriestop.get(pos).getTag_color()));
            catlogue_tag_name_2.setBackgroundColor(Color.parseColor(categoriestop.get(pos).getTag_color_2()));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        getCatlogues(categoriestop.get(pos).getCat_no(), 2);
        refresh.setRefreshing(false);


/*
        pos = position;
        startThread();
        cat_layout.setVisibility(GONE);
        lin_cat_details.setVisibility(VISIBLE);
        lin_frag_home.setVisibility(GONE);
        p_bar_1.setVisibility(View.VISIBLE);
        scroll_view.setVisibility(VISIBLE);
        scroll_view_2.setVisibility(GONE);
        scroll_view_1.setVisibility(View.GONE);
        cat_text.setText(categoriestop.get(pos).getCat_name());
        catdesc.setText(categoriestop.get(pos).getCat_desc());
        tag_name_text.setText("#"+categoriestop.get(pos).getTag_name());
//                                        tag_name_2_text.setText("#"+categoriestop.get(pos).getTag_name_2());
        if (categoriestop.get(pos).getTag_name().equals("Default") || categoriestop.get(pos).getTag_name().equals("") || categoriestop.get(pos).getTag_name().equals("default")){
            tag_name_text.setVisibility(GONE);
        }

        if (categoriestop.get(pos).getTag_name_2().equals("Default") || categoriestop.get(pos).getTag_name_2().equals("")|| categoriestop.get(pos).getTag_name().equals("default")){
//                                            tag_name_2_text.setVisibility(GONE);
        }

        try{
            tag_name_text.setBackgroundColor(Color.parseColor(categoriestop.get(pos).getTag_color()));
//                                            tag_name_2_text.setBackgroundColor(Color.parseColor(categoriestop.get(pos).getTag_color_2()));
        }catch (IllegalStateException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }catch (StringIndexOutOfBoundsException e){
            e.printStackTrace();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        getDetails(categoriestop.get(pos).getCat_no());*/
    }


    void getImages() {

        String tag_json_obj = "json_obj_req";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                IMG_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        sliders.clear();
                        try {
                            String status = response.getString("status");

                            if (status.equals("200")) {
                                JSONArray jsonArray = response.getJSONArray("userData");
                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                                wp_no = jsonObject1.getString("gen_whatsapp_number");
                                wp_no_b = jsonObject1.getString("biz_whatsapp_number");
                                wp_text = jsonObject1.getString("gen_whatsapp_text");
                                wp_b_text = jsonObject1.getString("biz_whatsapp_text");
                                if (onDataloadListener != null) {
                                    onDataloadListener = null;
                                    onAttach(getActivity());
                                }
                                onDataloadListener.onSliderLoaded(wp_no, wp_text, wp_no_b, wp_b_text);

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String img_url = jsonObject.getString("img_dest");
                                    String base_activity = jsonObject.getString("base_activity");
                                    String cat_id = jsonObject.getString("cat_id");


                                    sliders.add(new Slider(img_url, base_activity, cat_id));
                                }

                                myAdapter = new MyAdapter(getActivity(), sliders, new MyAdapter.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        if (sliders.get(position).getBase_activity().equals("cat_details") && !sliders.get(position).getCat_id().equals("")) {
                                            //getDetails(sliders.get(position).getCat_id());
                                        } else if (sliders.get(position).getBase_activity().equals("whatsapp")) {
                                            CustomViewPager customViewPager = Main_Handler_Activity.getV();
                                            customViewPager.setCurrentItem(4);
                                            if (whatsappInstalledOrNot("com.whatsapp.w4b") && whatsappInstalledOrNot("com.whatsapp")) {
                                                String title = "Choose :";
                                                CharSequence[] itemlist = {"Whatsapp",
                                                        "Whatsapp Business",
                                                };

                                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                //builder.setIcon(R.drawable.icon_app);
                                                builder.setTitle(title);
                                                builder.setItems(itemlist, new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        switch (which) {
                                                            case 0:// Take Photo
                                                                openWhatsApp();
                                                                break;
                                                            case 1:// Choose Existing Photo
                                                                openWhatsAppBusiness();
                                                                break;

                                                            default:
                                                                break;
                                                        }
                                                    }
                                                });
                                                AlertDialog alert = builder.create();
                                                alert.setCancelable(true);
                                                alert.show();

                                            } else {
                                                openWhatsApp();
                                            }
                                        }
                                    }
                                });


                                init(rootview);

                                if (splash_id == 1) {

                                } else {
                                    p_bar.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    rl.setVisibility(View.VISIBLE);
                                    init(rootview);
                                }


                            } else {
                                Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    public void downloadImages() {

        for (int i = 0; i < images.size(); i++) {

            if (images.get(i).getSelect().equals(true)) {
                String img_url = images.get(i).getImg_url();
                new DownloadTask().execute(img_url);
            }

            if (i == images.size() - 1) {
                mProgressDialog.hide();
                Toast.makeText(getActivity(), "Images Downloaded Successfully", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private void scanFile(String path) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATA, "" + path);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Log.i("TAG", "Agter scanning" + path);
    }


    private class DownloadTask extends AsyncTask<String, Integer, Bitmap> {

        final String Dir = categories.get(pos).getCat_name();

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
                MediaScannerConnection.scanFile(getActivity(),
                        new String[]{myDir.getAbsolutePath()}, null, null);


                // sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(myDir)));


                Log.i("TAG", "Agter scanning" + myDir.getAbsolutePath());

            } catch (Exception e) {
                // some action
            }


            String root = Environment.getExternalStorageDirectory().toString();

            File filePath = new File(root + "/~AK " + Dir);

            getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(String.valueOf(filePath)))));

            mProgressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
        }
    }

    void shareImages() {

        total_selected = 0;
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i).getSelect().equals(true)) {
                total_selected += 1;
            }
        }

        files = new ArrayList<>();
        for (int j = 0; j < images.size(); j++) {

            if (images.get(j).getSelect().equals(true)) {
                String img_url = images.get(j).getImg_url();
                new ShareTask().execute(img_url);
            }

        }

    }

    private class ShareTask extends AsyncTask<String, Integer, Bitmap> {

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

            Uri uri = getImageUri(getActivity(), bitmap);
            if (uri != null)
                files.add(uri);


            if (total_selected == files.size()) {

                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            Thread.sleep(1000);

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    final Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                                    intent.putExtra(Intent.EXTRA_SUBJECT, description);
                                    intent.setType("image/jpeg");
                                    if (id_ == 0) {
                                        intent.setPackage("com.whatsapp");
                                    } else if (id_ == 1) {
                                        intent.setPackage("com.whatsapp.w4b");
                                    } else if (id_ == 2) {
                                        intent.setPackage("com.facebook.katana");
                                    }
                                    intent.putExtra(Intent.EXTRA_TEXT, description);
                                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
                                    startActivity(Intent.createChooser(intent, "Send these images"));
                                    mProgressDialog.dismiss();
                                    //description="";
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
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            Long tsLong = System.currentTimeMillis() / 1000;
            String ts = tsLong.toString();
            String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
            return Uri.parse(path);
        } catch (Exception e) {
            Log.v("Rabby", e.toString());
            return null;
        }
    }

/*    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        final ContentValues  contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Title"+ts);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        //contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation);

        final ContentResolver resolver = getContext().getContentResolver();
        final Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri path = resolver.insert(contentUri, contentValues);
        //String path = MediaStore.Images.ImageColumns.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return path;
    }*/

    public void getDetails(String poss) {

        share_all_check.setChecked(false);

        String tag_json_obj = "json_obj_req";

        Map<String, String> post_param = new HashMap<>();
        if (!TextUtils.isEmpty(poss))
            post_param.put(KEY_CAT_ID, poss);
        else
            post_param.put(KEY_CAT_ID, categories.get(pos).getCat_no());
        post_param.put(KEY_C_ID, customer_session.getCustomerID());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                DATA_CAT_URL, new JSONObject(post_param),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        images.clear();
                        try {
                            String status = response.getString("status");
                            if (status.equals("200")) {
                                JSONArray jsonArray = response.getJSONArray("userData");
                                String cat_desc = response.getString("cat_desc");
                                String is_size_avail = response.getString("is_size_ava");
                                if (is_size_avail.equals("1")) {
                                    sizes_avail.clear();
                                    JSONArray size_array = response.getJSONArray("available_sizes");
                                    for (int i = 0; i < size_array.length(); i++) {
                                        JSONObject sizes_object = size_array.getJSONObject(i);
                                        String size = sizes_object.getString("size");
                                        sizes_avail.add(size);
                                    }
                                } else {
                                    sizes_avail.add("false");
                                }

                                String ava_cat_ra = response.getString("ava_cata_rate");
                                rating.setText(ava_cat_ra);

                                if (categories.get(pos).getFsatisfy().equals("1")) {
                                    fsatisy.setVisibility(View.VISIBLE);
                                } else {
                                    fsatisy.setVisibility(View.GONE);
                                }

                                String cod_available = response.getString("is_cod_ava");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    JSONObject properties = jsonObject.getJSONObject("product_data");

                                    String is_satisfy = jsonObject.getString("is_satisfy");
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

                                    Selection selection = new Selection(img_url, false, img_id, price, actual_price, cat_name, count_all, isLiked, image_name, sizes_avail, cod_available, cat_desc, product_weight, rate_count, is_satisfy);
                                    images.add(selection);
                                }

                                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);

                                mAdapter_1 = new Cat_Image_Adapter_1(recyclerView, getActivity(), images, new Cat_Image_Adapter_1.ClickListener() {

                                    @Override
                                    public void onFavClick(View v, int pos, View v2) {
                                        addToFav((ImageView) v, images.get(pos).getImg_id(), (TextView) v2);
                                    }

                                    @Override
                                    public void onClick(View view, int position) {

                                    }

                                    @Override
                                    public void onImgClick(View view, int position) {
                                        Intent intent = new Intent(getActivity(), Product_Details.class);
                                        Bundle data = new Bundle();
                                        data.putSerializable("images", images);
                                        intent.putExtra("ca", data);
                                        intent.putExtra("pos", position);
                                        intent.putExtra("cat_name", images.get(position).getCat_name());
                                        intent.putExtra("id", 1);
                                        intent.putExtra("pNo", wp_no);
                                        intent.putExtra("catlog_id", categories.get(pos).getCat_no());
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }

                                    @Override
                                    public void onDownloadClick(Selection selection) {
                                        new DownloadTask().execute(selection.getImg_url());
                                    }

                                    @Override
                                    public void onSharedClick(Selection selection) {
                                        files = new ArrayList<>();
                                        total_selected = 1;
                                        id_ = 1;
                                        new ShareTask().execute(selection.getImg_url());
                                    }

                                    @Override
                                    public void onBuyClick(Selection selection) {
                                        selectedsingle = selection;
                                        ciid = categories.get(pos).getCat_no();
                                        showSIzeAndQtyDiaog(selection);
                                    }
                                });


                                r_view_1.setLayoutManager(staggeredGridLayoutManager);
                                r_view_1.setItemAnimator(new DefaultItemAnimator());
                                r_view_1.setAdapter(mAdapter_1);

                                mAdapter_1.setOnLoadMoreListener(new Cat_Image_Adapter_1.OnLoadMoreListener() {
                                    @Override
                                    public void onLoadMore() {
                                        images.add(null);
                                        mAdapter.notifyItemRemoved(categories.size());
                                        loadDetails();
                                    }
                                });

                                catno.setText(images.size() + " Designs");

                                id = 1;
                                load_id = 1;
                                onDataloadListener.requiredDtata(id, lin_cat_details, lin_frag_home, p_bar, scroll_view, scroll_view_1, scroll_view_2, catlogue_layout, fab);

                                p_bar_1.setVisibility(GONE);
                                cat_layout.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_SHORT).show();
                                p_bar.setVisibility(View.VISIBLE);
                                cat_layout.setVisibility(GONE);
                                lin_frag_home.setVisibility(View.VISIBLE);
                                lin_cat_details.setVisibility(GONE);
                                scroll_view.setVisibility(GONE);
                                scroll_view_1.setVisibility(View.VISIBLE);
                                fab.setVisibility(VISIBLE);
                            }

                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_SHORT).show();
                            p_bar.setVisibility(View.VISIBLE);
                            cat_layout.setVisibility(GONE);
                            lin_frag_home.setVisibility(View.VISIBLE);
                            lin_cat_details.setVisibility(GONE);
                            scroll_view.setVisibility(GONE);
                            scroll_view_1.setVisibility(View.VISIBLE);
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_SHORT).show();
                p_bar.setVisibility(View.VISIBLE);
                cat_layout.setVisibility(GONE);
                lin_frag_home.setVisibility(View.VISIBLE);
                lin_cat_details.setVisibility(GONE);
                scroll_view.setVisibility(GONE);
                scroll_view_1.setVisibility(View.VISIBLE);
                fab.setVisibility(VISIBLE);
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    private void loadDetails() {

        share_all_check.setChecked(false);

        String tag_json_obj = "json_obj_req";

        Map<String, String> post_param = new HashMap<>();
        post_param.put(KEY_OFFSET, String.valueOf(offset));
        if (splash_id == 1) {
            post_param.put(KEY_CAT_ID, splash_cat);
        } else {
            post_param.put(KEY_CAT_ID, categories.get(pos).getCat_no());
        }
        post_param.put(KEY_C_ID, customer_session.getCustomerID());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                DATA_CAT_URL, new JSONObject(post_param),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        images.clear();
                        try {
                            String status = response.getString("status");
                            offset = response.getInt("offset");

                            if (status.equals("200")) {
                                offset = response.getInt("offset");
                                JSONArray jsonArray = response.getJSONArray("userData");
                                String cat_desc = response.getString("cat_desc");
                                String is_size_avail = response.getString("is_size_ava");
                                if (is_size_avail.equals("1")) {
                                    sizes_avail.clear();
                                    JSONArray size_array = response.getJSONArray("available_sizes");
                                    for (int i = 0; i < size_array.length(); i++) {
                                        JSONObject sizes_object = size_array.getJSONObject(i);
                                        String size = sizes_object.getString("size");
                                        sizes_avail.add(size);
                                    }
                                } else {
                                    sizes_avail.add("false");
                                }
                                String cod_available = response.getString("is_cod_ava");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    JSONObject properties = jsonObject.getJSONObject("product_data");

                                    String is_satisfy = jsonObject.getString("is_satisfy");
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

                                    Selection selection = new Selection(img_url, false, img_id, price, actual_price, cat_name, count_all, isLiked, image_name, sizes_avail, cod_available, cat_desc, product_weight, rate_count, is_satisfy);
                                    images.add(selection);
                                }

                                mAdapter_1.setLoaded();
                                mAdapter_1.notifyDataSetChanged();

                            } else {
                            }

                        } catch (JSONException e) {
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);


    }

    private void addToFav(final ImageView v, String img_id, final TextView t) {


        String tag_json_obj = "json_obj_req";

        Map<String, String> postParam = new HashMap<>();
        postParam.put(KEY_ID, customer_session.getCustomerID());
        postParam.put(PRODUCT_ID, img_id);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                FAV_URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String status = response.getString("status");

                            if (status.equals("200")) {
                                v.setImageResource(R.drawable.heart_re);
                                t.setText(response.getString("all_like_counter"));
                            } else if (status.equals("208")) {
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
                Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getActivity().getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    private void openWhatsApp() {

        String smsNumber = "919836005273";
        if (wp_no.equals("")) {
            //do nothing
        } else {
            smsNumber = wp_no;
        }

        String message = "Hi,\n" +
                "I got your Number from AK Kurtis APP\n" +
                "\n" +
                "My Requirement is :";
        if (!wp_text.equals("")) {
            message = wp_text;
        }
        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
        if (isWhatsappInstalled) {

            try {

                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, message);
                whatsappIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
                startActivity(whatsappIntent);

            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            try {
                Uri uri = Uri.parse("market://details?id=com.whatsapp");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                Toast.makeText(getActivity(), "WhatsApp not Installed",
                        Toast.LENGTH_SHORT).show();
                startActivity(goToMarket);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

    }

    private void openWhatsAppBusiness() {

        String smsNumber = "919836005273";
        if (wp_no.equals("")) {
            //do nothing
        } else {
            smsNumber = wp_no;
        }

        String message = "Hi,\n" +
                "I got your Number from AK Kurtis APP\n" +
                "\n" +
                "My Requirement is :";
        if (!wp_b_text.equals("")) {
            message = wp_b_text;
        }
        try {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp.w4b");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, message);
            whatsappIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
            startActivity(whatsappIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    void startThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (load_id == 0) {
                            Toast.makeText(getActivity(), "Image Loading.. , Your internet connection is slow", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        thread.start();

    }

    void getCatlogues(String categoriestop, final int indicator) {

        String tag_json_obj = "json_obj_req";
        offset = 0;
        Log.v("Rabby", categoriestop);
        Map<String, String> postParam = new HashMap<>();
        if (TextUtils.isEmpty(categoriestop))
            postParam.put(KEY_CATEGORY_ID, categories.get(pos).getCat_no());
        else
            postParam.put(KEY_CATEGORY_ID, categoriestop);

        postParam.put(KEY_C_ID, customer_session.getCustomerID());
        postParam.put(KEY_OFFSET, String.valueOf(offset));


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                DATA_CATLOGUE_URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            categories.clear();

                            String status = response.getString("status");
                            String startingPrice = response.getString("cod_charge");
                            String shipmentPrice = response.getString("shipment_charge");

                            if (status.equals("200")) {

                                offset = response.getInt("new_offset");
                                JSONArray jsonArray = response.getJSONArray("userData");


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String img_b = "a", img_c = "s", img_a = "a";
                                    cat_name = jsonObject.getString("catalogName");
                                    cat_id = jsonObject.getString("catalogue_id");
                                    String count = jsonObject.getString("items_count");
                                    cat_count = String.valueOf(count);
                                    tag_name = jsonObject.getString("tag_name");
                                    tag_color = jsonObject.getString("tag_color");
                                    img_url = jsonObject.getString("catalogue_image");
                                    cat_desc = jsonObject.getString("description");
                                    String is_satisfy = jsonObject.getString("is_satisfy");
                                    try {
                                        JSONArray jArray = jsonObject.getJSONArray("front_image");
                                        JSONObject jsonObj = jArray.getJSONObject(1);
                                        img_a = jsonObj.getString("thumb_img_dest");
                                        JSONObject jsonObj_b = jArray.getJSONObject(2);
                                        img_b = jsonObj_b.getString("thumb_img_dest");
                                        JSONObject jsonObj_c = jArray.getJSONObject(3);
                                        img_c = jsonObj_c.getString("thumb_img_dest");
                                    } catch (Exception e) {
                                        Log.w("Rabby", e.toString());
                                    }

                                    categories.add(new Home_1(cat_name, cat_id, cat_count, tag_name, "default", tag_color, "", cat_desc, img_url, is_satisfy, img_a, img_b, img_c, startingPrice, shipmentPrice));
                                }

//                                onDataloadListener.onDataLoaded(arrays);

                                id = 2;
                                load_id = 2;
                                onDataloadListener.requiredDtata(id, lin_cat_details, lin_frag_home, p_bar, scroll_view_2, scroll_view_1, scroll_view_1, catlogue_layout, fab);


                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                catlogue_rec_view.setLayoutManager(mLayoutManager);
                                catlogue_rec_view.setItemAnimator(new DefaultItemAnimator());

                                mAdapter_2 = new Home_Adapter_2(scroll_view_1, getActivity(), categories, new Home_Adapter_2.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        pos = position;
                                        startThread();
                                        cat_layout.setVisibility(GONE);
                                        lin_cat_details.setVisibility(VISIBLE);
                                        lin_frag_home.setVisibility(GONE);
                                        p_bar_1.setVisibility(View.VISIBLE);
                                        scroll_view.setVisibility(VISIBLE);
                                        scroll_view_2.setVisibility(GONE);
                                        scroll_view_1.setVisibility(View.GONE);
                                        fab.setVisibility(GONE);
                                        cat_text.setText(categories.get(pos).getCat_name());
                                        catdesc.setText(categories.get(pos).getCat_desc());
                                        tag_name_text.setText("#" + categories.get(pos).getTag_name());
//                                        tag_name_2_text.setText("#"+categories.get(pos).getTag_name_2());
                                        if (categories.get(pos).getTag_name().equals("Default") || categories.get(pos).getTag_name().equals("") || categories.get(pos).getTag_name().equals("default")) {
                                            tag_name_text.setVisibility(GONE);
                                        }

                                        if (categories.get(pos).getTag_name_2().equals("Default") || categories.get(pos).getTag_name_2().equals("") || categories.get(pos).getTag_name().equals("default")) {
//                                            tag_name_2_text.setVisibility(GONE);
                                        }

                                        try {
                                            tag_name_text.setBackgroundColor(Color.parseColor(categories.get(pos).getTag_color()));
//                                            tag_name_2_text.setBackgroundColor(Color.parseColor(categories.get(pos).getTag_color_2()));
                                        } catch (IllegalStateException e) {
                                            e.printStackTrace();
                                        } catch (NullPointerException e) {
                                            e.printStackTrace();
                                        } catch (StringIndexOutOfBoundsException e) {
                                            e.printStackTrace();
                                        } catch (IllegalArgumentException e) {
                                            e.printStackTrace();
                                        }
                                        getDetails("");
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }

                                    @Override
                                    public void onImgCLick(View view, int position) {
                                        pos = position;
                                        startThread();
                                        cat_layout.setVisibility(GONE);
                                        lin_cat_details.setVisibility(VISIBLE);
                                        lin_frag_home.setVisibility(GONE);
                                        p_bar_1.setVisibility(View.VISIBLE);
                                        scroll_view.setVisibility(VISIBLE);
                                        scroll_view_2.setVisibility(GONE);
                                        scroll_view_1.setVisibility(View.GONE);
                                        fab.setVisibility(GONE);
                                        cat_text.setText(categories.get(pos).getCat_name());
                                        catdesc.setText(categories.get(pos).getCat_desc());
                                        tag_name_text.setText("#" + categories.get(pos).getTag_name());
//                                        tag_name_2_text.setText("#"+categories.get(pos).getTag_name_2());
                                        if (categories.get(pos).getTag_name().equals("Default") || categories.get(pos).getTag_name().equals("") || categories.get(pos).getTag_name().equals("default")) {
                                            tag_name_text.setVisibility(GONE);
                                        }

                                        if (categories.get(pos).getTag_name_2().equals("Default") || categories.get(pos).getTag_name_2().equals("") || categories.get(pos).getTag_name().equals("default")) {
//                                            tag_name_2_text.setVisibility(GONE);
                                        }

                                        try {
                                            tag_name_text.setBackgroundColor(Color.parseColor(categories.get(pos).getTag_color()));
//                                            tag_name_2_text.setBackgroundColor(Color.parseColor(categories.get(pos).getTag_color_2()));
                                        } catch (IllegalStateException e) {
                                            e.printStackTrace();
                                        } catch (NullPointerException e) {
                                            e.printStackTrace();
                                        } catch (StringIndexOutOfBoundsException e) {
                                            e.printStackTrace();
                                        } catch (IllegalArgumentException e) {
                                            e.printStackTrace();
                                        }
                                        getDetails("");
                                    }
                                });
                                mAdapter_3 = new Home_Adapter_3(scroll_view_1, getActivity(), categories, new Home_Adapter_3.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        pos = position;
                                        startThread();
                                        cat_layout.setVisibility(GONE);
                                        lin_cat_details.setVisibility(VISIBLE);
                                        lin_frag_home.setVisibility(GONE);
                                        p_bar_1.setVisibility(View.VISIBLE);
                                        scroll_view.setVisibility(VISIBLE);
                                        scroll_view_2.setVisibility(GONE);
                                        scroll_view_1.setVisibility(View.GONE);
                                        fab.setVisibility(GONE);
                                        cat_text.setText(categories.get(pos).getCat_name());
                                        catdesc.setText(categories.get(pos).getCat_desc());
                                        tag_name_text.setText("#" + categories.get(pos).getTag_name());
//                                        tag_name_2_text.setText("#"+categories.get(pos).getTag_name_2());
                                        if (categories.get(pos).getTag_name().equals("Default") || categories.get(pos).getTag_name().equals("") || categories.get(pos).getTag_name().equals("default")) {
                                            tag_name_text.setVisibility(GONE);
                                        }

                                        if (categories.get(pos).getTag_name_2().equals("Default") || categories.get(pos).getTag_name_2().equals("") || categories.get(pos).getTag_name().equals("default")) {
//                                            tag_name_2_text.setVisibility(GONE);
                                        }

                                        try {
                                            tag_name_text.setBackgroundColor(Color.parseColor(categories.get(pos).getTag_color()));
//                                            tag_name_2_text.setBackgroundColor(Color.parseColor(categories.get(pos).getTag_color_2()));
                                        } catch (IllegalStateException e) {
                                            e.printStackTrace();
                                        } catch (NullPointerException e) {
                                            e.printStackTrace();
                                        } catch (StringIndexOutOfBoundsException e) {
                                            e.printStackTrace();
                                        } catch (IllegalArgumentException e) {
                                            e.printStackTrace();
                                        }
                                        getDetails("");
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }

                                    @Override
                                    public void onImgCLick(View view, int position) {
                                        pos = position;
                                        startThread();
                                        cat_layout.setVisibility(GONE);
                                        lin_cat_details.setVisibility(VISIBLE);
                                        lin_frag_home.setVisibility(GONE);
                                        p_bar_1.setVisibility(View.VISIBLE);
                                        scroll_view.setVisibility(VISIBLE);
                                        scroll_view_2.setVisibility(GONE);
                                        scroll_view_1.setVisibility(View.GONE);
                                        fab.setVisibility(GONE);
                                        cat_text.setText(categories.get(pos).getCat_name());
                                        catdesc.setText(categories.get(pos).getCat_desc());
                                        tag_name_text.setText("#" + categories.get(pos).getTag_name());
//                                        tag_name_2_text.setText("#"+categories.get(pos).getTag_name_2());
                                        if (categories.get(pos).getTag_name().equals("Default") || categories.get(pos).getTag_name().equals("") || categories.get(pos).getTag_name().equals("default")) {
                                            tag_name_text.setVisibility(GONE);
                                        }

                                        if (categories.get(pos).getTag_name_2().equals("Default") || categories.get(pos).getTag_name_2().equals("") || categories.get(pos).getTag_name().equals("default")) {
//                                            tag_name_2_text.setVisibility(GONE);
                                        }

                                        try {
                                            tag_name_text.setBackgroundColor(Color.parseColor(categories.get(pos).getTag_color()));
//                                            tag_name_2_text.setBackgroundColor(Color.parseColor(categories.get(pos).getTag_color_2()));
                                        } catch (IllegalStateException e) {
                                            e.printStackTrace();
                                        } catch (NullPointerException e) {
                                            e.printStackTrace();
                                        } catch (StringIndexOutOfBoundsException e) {
                                            e.printStackTrace();
                                        } catch (IllegalArgumentException e) {
                                            e.printStackTrace();
                                        }
                                        getDetails("");
                                    }

                                    @Override
                                    public void onDownloadCLick(Home_1 data) {
                                        Toast.makeText(getContext(), "Downloading images", Toast.LENGTH_SHORT).show();
                                        new DownloadTask().execute(data.getImga());
                                       /* new DownloadTask().execute(data.getImgb());
                                        new DownloadTask().execute(data.getImgc());*/
                                    }

                                    @Override
                                    public void onShareCLick(Home_1 data) {

                                       /* Intent i = new Intent(Intent.ACTION_SEND);
                                        i.setType("text/plain");
                                        i.putExtra(Intent.EXTRA_SUBJECT, data.getCat_name());
                                        i.putExtra(Intent.EXTRA_TEXT, data.getImga());
                                        i.putExtra(Intent.EXTRA_TEXT, data.getImgb());
                                        i.putExtra(Intent.EXTRA_TEXT, data.getImgc());
                                        startActivity(Intent.createChooser(i, "Share URL"));*/
                                        files = new ArrayList<>();
                                        total_selected = 1;
                                        id_ = 3;
                                        hitshare(data.getCat_no());
                                        description = data.getCat_desc();
                                        new ShareTask().execute(data.getImga());
                                        /*new ShareTask().execute(data.getImgb());
                                        new ShareTask().execute(data.getImgc());*/

                                    }

                                    @Override
                                    public void onWhatssAppCLick(Home_1 data) {
                                        files = new ArrayList<>();
                                        total_selected = 1;
                                        id_ = 0;
                                        hitshare(data.getCat_no());
                                        new ShareTask().execute(data.getImga());
                                       /* new ShareTask().execute(data.getImgb());
                                        new ShareTask().execute(data.getImgc());*/
                                    }


                                });

                                if (indicator == 0) {
                                    catlogue_rec_view.setAdapter(mAdapter_2);
                                    mAdapter_2.setLoadId0();
                                    mAdapter_2.setOnLoadMoreListener(new Home_Adapter_2.OnLoadMoreListener() {
                                        @Override
                                        public void onLoadMore() {
                                            categories.add(null);
                                            mAdapter_2.notifyItemRemoved(categories.size());
                                            loadCatlogues();
                                        }
                                    });
                                } else {
                                    catlogue_rec_view.setAdapter(mAdapter_3);
                                    mAdapter_3.setLoadId0();
                                    mAdapter_3.setOnLoadMoreListener(new Home_Adapter_3.OnLoadMoreListener() {
                                        @Override
                                        public void onLoadMore() {
                                            categories.add(null);
                                            mAdapter_3.notifyItemRemoved(categories.size());
                                            loadCatlogues3();
                                        }
                                    });

                                }


                                catlogue_layout.setVisibility(View.VISIBLE);
                                p_bar_2.setVisibility(GONE);
                            }

                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "Error due to low internet connection", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error due to low internet connection", Toast.LENGTH_LONG).show();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    private void loadCatlogues() {

        String tag_json_obj = "json_obj_req";

        Map<String, String> postParam = new HashMap<>();
        postParam.put(KEY_CATEGORY_ID, categories.get(pos).getCat_no());
        postParam.put(KEY_C_ID, customer_session.getCustomerID());
        postParam.put(KEY_OFFSET, String.valueOf(offset));

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                DATA_CATLOGUE_URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            categories.remove(categories.size() - 1);
                            mAdapter_2.notifyItemRemoved(categories.size());

                            String status = response.getString("status");
                            offset = response.getInt("new_offset");

                            if (status.equals("200")) {
                                JSONArray jsonArray = response.getJSONArray("userData");
                                if (jsonArray == null)
                                    mAdapter_2.setLoadID1();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    cat_name = jsonObject.getString("catalogue_name");
                                    cat_id = jsonObject.getString("catalogue_id");
                                    tag_name = jsonObject.getString("catalogue_tag");
                                    tag_color = jsonObject.getString("catalogue_tag_color");
                                    img_url = jsonObject.getString("catalogue_image");
                                    cat_desc = jsonObject.getString("catalogue_description");
                                    String is_satisfy = jsonObject.getString("is_satisfy");

                                    categories.add(new Home_1(cat_name, cat_id, cat_count, tag_name, "default", tag_color, "", cat_desc, img_url, is_satisfy));
                                }

                                catlogue_no.setText(categories.size() + " designs");

                                mAdapter_2.setLoaded();
                                mAdapter_2.notifyDataSetChanged();

                            }

                        } catch (JSONException e) {
                            mAdapter_2.setLoadID1();
                            mAdapter_2.setLoaded();
                            mAdapter_2.notifyDataSetChanged();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mAdapter_2.setLoadID1();
                mAdapter_2.setLoaded();
                mAdapter_2.notifyDataSetChanged();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    private void loadCatlogues3() {

        String tag_json_obj = "json_obj_req";

        Map<String, String> postParam = new HashMap<>();
        postParam.put(KEY_CATEGORY_ID, categories.get(pos).getCat_no());
        postParam.put(KEY_C_ID, customer_session.getCustomerID());
        postParam.put(KEY_OFFSET, String.valueOf(offset));

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                DATA_CATLOGUE_URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            categories.remove(categories.size() - 1);
                            mAdapter_3.notifyItemRemoved(categories.size());

                            String status = response.getString("status");
                            offset = response.getInt("new_offset");

                            if (status.equals("200")) {
                                JSONArray jsonArray = response.getJSONArray("userData");
                                if (jsonArray == null)
                                    mAdapter_3.setLoadID1();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    cat_name = jsonObject.getString("catalogue_name");
                                    cat_id = jsonObject.getString("catalogue_id");
                                    tag_name = jsonObject.getString("catalogue_tag");
                                    tag_color = jsonObject.getString("catalogue_tag_color");
                                    img_url = jsonObject.getString("catalogue_image");
                                    cat_desc = jsonObject.getString("catalogue_description");
                                    String is_satisfy = jsonObject.getString("is_satisfy");

                                    categories.add(new Home_1(cat_name, cat_id, cat_count, tag_name, "default", tag_color, "", cat_desc, img_url, is_satisfy));
                                }

                                catlogue_no.setText(categories.size() + " designs");

                                mAdapter_3.setLoaded();
                                mAdapter_3.notifyDataSetChanged();

                            }

                        } catch (JSONException e) {
                            mAdapter_3.setLoadID1();
                            mAdapter_3.setLoaded();
                            mAdapter_3.notifyDataSetChanged();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mAdapter_3.setLoadID1();
                mAdapter_3.setLoaded();
                mAdapter_3.notifyDataSetChanged();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    void showSIzeAndQtyDiaog(Selection item) {

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        size_rec.setLayoutManager(horizontalLayoutManager);
        size_rec.setItemAnimator(new DefaultItemAnimator());

        size_adapter = new Cat_New_Adapter(getContext(), item.getSize_avail());
        size_rec.setAdapter(size_adapter);

        bottom_dialog.show();
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
                CART_URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //hideProgressDialog();
                        try {
                            String status = response.getString("status");

                            if (status.equals("202")) {
                                Toast.makeText(getContext(), "Item Added to Cart", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getContext(), Activity_Cart.class).putExtra("margin", edit_margin.getText().toString().trim()));
                                //getCartProducts();
                            } else if (status.equals("208")) {
                                Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //hideProgressDialog();
                Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    private void hitshare(String catno) {

        String tag_json_obj = "json_obj_req";

        Map<String, String> postParam = new HashMap<>();
        postParam.put("catalog_id", catno);
        postParam.put("res_id", customer_session.getCustomerID());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                "https://www.resellingapp.com/apiv2/zymi//index.php/rest_server/shareHistory/API-KEY/123456", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            String status = response.getString("status");

                            if (status.equals("200")) {


                            }

                        } catch (JSONException e) {

                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    void getCatagoryData() {

        String tag_json_obj = "json_obj_req";
        p_bar_4.setVisibility(VISIBLE);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                DATA_CATAGORY_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            categories5.clear();

                            String status = response.getString("status");

                            if (status.equals("200")) {

                                JSONArray jsonArray = response.getJSONArray("userData");


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    cat_name = jsonObject.getString("category_name");
                                    cat_id = jsonObject.getString("id");
                                    tag_name = jsonObject.getString("tag_name");
                                    tag_color = jsonObject.getString("tag_color");
                                    tag_name_2 = jsonObject.getString("tag2_name");
                                    tag_color_2 = jsonObject.getString("tag2_color");
                                    img_url = jsonObject.getString("category_image");
                                    cat_desc = jsonObject.getString("description");

                                    categories5.add(new Home_1(cat_name, cat_id, cat_count, tag_name, tag_name_2, tag_color, tag_color_2, cat_desc, img_url, ""));
                                }

//                                onDataloadListener.onDataLoaded(arrays);

                                mAdapter5 = new Home_Adapter_5(getActivity(), categories5, new Home_Adapter_5.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        pos = position;
                                        startThread();
                                        lin_cat_details.setVisibility(GONE);
                                        lin_frag_home.setVisibility(GONE);
                                        scroll_view.setVisibility(GONE);
                                        scroll_view_1.setVisibility(GONE);
                                        fab.setVisibility(GONE);
                                        scroll_view_2.setVisibility(View.VISIBLE);
                                        catlogue_content.setText(categories5.get(pos).getCat_name());
                                        catlogue_desc.setText(categories5.get(position).getCat_desc());
                                        catlogue_tag_name.setText(categories5.get(pos).getTag_name());
                                        catlogue_tag_name_2.setText(categories5.get(pos).getTag_name_2());
                                        if (categories5.get(pos).getTag_name().equals("Default") || categories5.get(pos).getTag_name().equals("") || categories5.get(pos).getTag_name().equals("default")) {
                                            catlogue_tag_name.setVisibility(GONE);
                                        }
                                        load_id = 2;
                                        if (categories5.get(pos).getTag_name_2().equals("Default") || categories5.get(pos).getTag_name_2().equals("") || categories5.get(pos).getTag_name().equals("default")) {
                                            catlogue_tag_name_2.setVisibility(GONE);
                                        }

                                        try {
                                            catlogue_tag_name.setBackgroundColor(Color.parseColor(categories5.get(pos).getTag_color()));
                                            catlogue_tag_name_2.setBackgroundColor(Color.parseColor(categories5.get(pos).getTag_color_2()));
                                        } catch (IllegalStateException e) {
                                            e.printStackTrace();
                                        } catch (NullPointerException e) {
                                            e.printStackTrace();
                                        } catch (StringIndexOutOfBoundsException e) {
                                            e.printStackTrace();
                                        }
                                        getCatlogues(categories5.get(pos).getCat_no(), 2);
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }

                                    @Override
                                    public void onImgCLick(View view, int position) {
                                        pos = position;
                                        startThread();
                                        lin_cat_details.setVisibility(GONE);
                                        lin_frag_home.setVisibility(GONE);
                                        scroll_view.setVisibility(GONE);
                                        scroll_view_1.setVisibility(GONE);
                                        fab.setVisibility(GONE);
                                        scroll_view_2.setVisibility(View.VISIBLE);
                                        catlogue_content.setText(categories5.get(pos).getCat_name());
                                        catlogue_desc.setText(categories5.get(position).getCat_desc());
                                        catlogue_tag_name.setText(categories5.get(pos).getTag_name());
                                        catlogue_tag_name_2.setText(categories5.get(pos).getTag_name_2());
                                        if (categories5.get(pos).getTag_name().equals("Default") || categories5.get(pos).getTag_name().equals("") || categories5.get(pos).getTag_name().equals("default")) {
                                            catlogue_tag_name.setVisibility(GONE);
                                        }

                                        if (categories5.get(pos).getTag_name_2().equals("Default") || categories5.get(pos).getTag_name_2().equals("") || categories5.get(pos).getTag_name().equals("default")) {
                                            catlogue_tag_name.setVisibility(GONE);
                                        }

                                        try {
                                            tag_name_text.setBackgroundColor(Color.parseColor(categories5.get(pos).getTag_color()));
//                                            tag_name_2_text.setBackgroundColor(Color.parseColor(categories5.get(pos).getTag_color_2()));
                                        } catch (IllegalStateException e) {
                                            e.printStackTrace();
                                        } catch (NullPointerException e) {
                                            e.printStackTrace();
                                        } catch (StringIndexOutOfBoundsException e) {
                                            e.printStackTrace();
                                        }
                                        load_id = 2;
                                        getCatlogues(categories5.get(pos).getCat_no(), 2);
                                    }
                                });

                                id = 7;
                                onDataloadListener.requiredDtata(id, catagory_scroll_view, lin_frag_home, p_bar_4, scroll_view_1, scroll_view_1, scroll_view, catlogue_layout, fab);

                                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
                                catagory_rec_view.setLayoutManager(staggeredGridLayoutManager);
                                catagory_rec_view.setItemAnimator(new DefaultItemAnimator());
                                catagory_rec_view.setAdapter(mAdapter5);

                                p_bar_4.setVisibility(GONE);
                                catagory_rec_view.setVisibility(View.VISIBLE);

                            }

                        } catch (Exception e) {
                            Log.w("Rabby", e.toString());
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error due to low internet connection", Toast.LENGTH_SHORT).show();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

}
