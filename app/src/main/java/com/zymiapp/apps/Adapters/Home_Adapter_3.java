package com.zymiapp.apps.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.zymiapp.apps.Model.Home_1;
import com.zymiapp.apps.R;

import java.util.List;
import java.util.Random;


public class Home_Adapter_3 extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Home_1> home;
    private Context mContext;
    private ClickListener listener;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private int load_id =0;
    private int fromOther=0;

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ship;
        TextView cat_text;
        TextView cat_no;
        TextView cat_desc;
        TextView tag_name;
        TextView tag_name_2;
        TextView ratingtxt;
        TextView price;
        TextView count;
        ImageView imageView1;
        ImageView imageViewb;
        ImageView imageViewc;
        ImageView imageViewd;
        LinearLayout relativeLayout;
        LinearLayout whatsappshare;
        LinearLayout downloadbtn;
        LinearLayout sharebtn;

        public MyViewHolder(final View view) {
            super(view);
            ship = (TextView) view.findViewById(R.id.ship);
            price = (TextView) view.findViewById(R.id.price);
            count = (TextView) view.findViewById(R.id.count);
            cat_text = (TextView) view.findViewById(R.id.cat_name);
            cat_no = (TextView) view.findViewById(R.id.cat_no);
            cat_desc = (TextView) view.findViewById(R.id.cat_desc);
            ratingtxt = (TextView) view.findViewById(R.id.ratingtxt);
            imageView1 = (ImageView) view.findViewById(R.id.image_preview);
            imageViewb = (ImageView) view.findViewById(R.id.image_preview_b);
            imageViewc = (ImageView) view.findViewById(R.id.image_preview_c);
            imageViewd = (ImageView) view.findViewById(R.id.image_preview_d);
            relativeLayout = (LinearLayout) view.findViewById(R.id.rl1);
            tag_name_2 = (TextView) view.findViewById(R.id.newly_added_1);
            whatsappshare = (LinearLayout) view.findViewById(R.id.whatsappshare);
            downloadbtn = (LinearLayout) view.findViewById(R.id.downloadbtn);
            sharebtn = (LinearLayout) view.findViewById(R.id.sharebtn);

            downloadbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDownloadCLick(home.get(getAdapterPosition()));
                }
            });

            sharebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onShareCLick(home.get(getAdapterPosition()));
                }
            });
            whatsappshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onWhatssAppCLick(home.get(getAdapterPosition()));
                }
            });
        }
    }


    public Home_Adapter_3(final NestedScrollView scrollView, Context mContext, List<Home_1> home, ClickListener listener) {
        this.mContext = mContext;
        this.home = home;
        this.listener = listener;

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
                int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
                if (diff == 0) {

                    if(isLoading ==false && load_id==0){

                        isLoading = true;
                        //code to fetch more data for endless scrolling
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }

                    }else{
                        if (load_id==1){
                            setLoaded();
                            notifyDataSetChanged();
                        }
                    }
                }
            }
        });

    }

    public Home_Adapter_3(int i,final NestedScrollView scrollView, Context mContext, List<Home_1> home, ClickListener listener) {
        this.mContext = mContext;
        this.home = home;
        this.listener = listener;
        this.fromOther=i;

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
                int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
                if (diff == 0) {

                    if(isLoading ==false && load_id==0){

                        isLoading = true;
                        //code to fetch more data for endless scrolling
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }

                    }else{
                        if (load_id==1){
                            setLoaded();
                            notifyDataSetChanged();
                        }
                    }
                }
            }
        });

    }

    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.refresh_layout_design_three, parent, false);
            if(fromOther!=0) {
                view = LayoutInflater.from(mContext).inflate(R.layout.refresh_layout_design_four, parent, false);
            }

            return new MyViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MyViewHolder) {
            MyViewHolder holder1 = (MyViewHolder) holder;
            Home_1 image = home.get(position);

            holder1.price.setText("₹"+image.getStartingprice());
            holder1.ship.setText(image.getShipment());
            holder1.count.setText(image.getCat_count()+"+");

            holder1.cat_text.setText(image.getCat_name());
            holder1.ratingtxt.setText((4+new Random().nextFloat())+"");

            if (image.getCat_count().equals("43")){
                //holder1.cat_no.setVisibility(View.GONE);
               // holder1.cat_no.setText(image.getCat_count()+" Designs");
            }else {
                //holder1.cat_no.setText(image.getCat_count()+" Designs");
            }
            holder1.cat_desc.setText(image.getCat_desc());

            Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/arial.ttf");
            holder1.cat_text.setTypeface(tf);
            holder1.cat_no.setTypeface(tf);
            holder1.cat_desc.setTypeface(tf);


            holder1.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v,position);
                }
            });

            if (image.getImg_url().isEmpty())
                return;
            Picasso.with(mContext).load(image.getImg_url()).placeholder(R.drawable.phimg).fit().centerCrop().into(holder1.imageView1);
            Picasso.with(mContext).load(image.getImga()).placeholder(R.drawable.phimg).fit().centerCrop().into(holder1.imageViewb);
            Picasso.with(mContext).load(image.getImgb()).placeholder(R.drawable.phimg).fit().centerCrop().into(holder1.imageViewc);
            Picasso.with(mContext).load(image.getImgc()).placeholder(R.drawable.phimg).fit().centerCrop().into(holder1.imageViewd);


        }else {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return home == null ? 0 : home.size();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = mContext.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void setLoadId0(){
        load_id =0;
    }

    public void setLoadID1(){
        load_id=1;
    }

    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public int getItemViewType(int position) {
        return home.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);

        void onImgCLick(View view, int position);

        void onDownloadCLick(Home_1 data);

        void onShareCLick(Home_1 data);
        void onWhatssAppCLick(Home_1 data);

    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (child != null && clickListener != null) {
                            clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                        }
                    }
                });
            }
        }



        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e){

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}