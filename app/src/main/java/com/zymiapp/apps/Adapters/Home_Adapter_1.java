package com.zymiapp.apps.Adapters;

import android.content.Context;
import android.graphics.Typeface;

import com.zymiapp.apps.Model.Home_1;
import com.zymiapp.apps.R;
import android.os.Build;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Home_Adapter_1 extends RecyclerView.Adapter<Home_Adapter_1.MyViewHolder> {

    private List<Home_1> home;
    private Context mContext;
    private ClickListener listener;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private int load_id = 0;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cat_name,shipmentprice,startingprice,count;
        ImageView image_preview,image_preview_b,image_preview_c;
        LinearLayout lin_layout,downloadbtn,sharebtn;
        ImageView whatsappshare,facebookShare;
        CardView cardview;

        public MyViewHolder(final View view) {
            super(view);
            cat_name=(TextView) view.findViewById(R.id.cat_name);
            count=(TextView) view.findViewById(R.id.count);
            cardview=(CardView) view.findViewById(R.id.cardview);
            image_preview=(ImageView) view.findViewById(R.id.image_preview);
            image_preview_b=(ImageView) view.findViewById(R.id.image_preview_b);
            image_preview_c=(ImageView) view.findViewById(R.id.image_preview_c);
            downloadbtn=(LinearLayout) view.findViewById(R.id.downloadbtn);
            sharebtn=(LinearLayout) view.findViewById(R.id.sharebtn);
            lin_layout=(LinearLayout) view.findViewById(R.id.lin_layout);
            whatsappshare=(ImageView) view.findViewById(R.id.whatsappshare);
            facebookShare=(ImageView) view.findViewById(R.id.facebookhare);
            shipmentprice=(TextView) view.findViewById(R.id.shipmentprice);
            startingprice=(TextView) view.findViewById(R.id.startingprice);
            lin_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onImgCLick(v,getAdapterPosition());
                }
            });

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
                    listener.onWhatssAppCLick(home.get(getAdapterPosition()),1);
                }
            });
            facebookShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onWhatssAppCLick(home.get(getAdapterPosition()),2);
                }
            });
        }
    }
    public void setLoadId0() {
        load_id = 0;
    }

    public Home_Adapter_1(final NestedScrollView scrollView, Context mContext, List<Home_1> home, ClickListener listener) {
        this.mContext = mContext;
        this.home = home;
        this.listener = listener;

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
                int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
                if (diff == 0) {

                    if (isLoading == false && load_id == 0) {

                        isLoading = true;
                        //code to fetch more data for endless scrolling
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }

                    } else {
                        if (load_id == 1) {
                            setLoaded();
                            notifyDataSetChanged();
                        }

                    }

                }
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_layout_design_two, parent, false);

        return new MyViewHolder(itemView);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Home_1 image = home.get(position);

        holder.cat_name.setText(image.getCat_name());
        holder.count.setText("+"+image.getCat_count()+"\n Design");

        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/arial.ttf");
        holder.cat_name.setTypeface(tf);
        holder.shipmentprice.setText("shipment : "+image.getFsatisfy());
        holder.startingprice.setText("Price Starting from : "+image.getStartingprice());

        Picasso.with(mContext).load(image.getImga()).placeholder(R.drawable.phimg).into(holder.image_preview);
        Picasso.with(mContext).load(image.getImgb()).placeholder(R.drawable.phimg).into(holder.image_preview_b);
        Picasso.with(mContext).load(image.getImgc()).placeholder(R.drawable.phimg).into(holder.image_preview_c);

    }

    @Override
    public int getItemCount() {
        return home.size();
    }


    public void setOnLoadMoreListener(Home_Adapter_1.OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);

        void onImgCLick(View view,int position);

        void onDownloadCLick(Home_1 data);

        void onShareCLick(Home_1 data);
        void onWhatssAppCLick(Home_1 data,int sharetype);
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