package com.zymiapp.apps.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zymiapp.apps.Model.Home_1;
import com.zymiapp.apps.R;

import java.util.List;

public class Home_Adapter_4 extends RecyclerView.Adapter<Home_Adapter_4.MyViewHolder> {

    private List<Home_1> home;
    private Context mContext;
    private ClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cat_name;
        ImageView image_preview;
        LinearLayout lin_layout;


        public MyViewHolder(final View view) {
            super(view);
            cat_name=(TextView) view.findViewById(R.id.cat_name);

            image_preview=(ImageView) view.findViewById(R.id.image_preview);

            lin_layout=(LinearLayout) view.findViewById(R.id.lin);

            lin_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onImgCLick(v,getAdapterPosition());
                }
            });


        }
    }


    public Home_Adapter_4(Context mContext, List<Home_1> home, ClickListener listener) {
        this.mContext = mContext;
        this.home = home;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.refresh_layout_design, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Home_1 image = home.get(position);
        holder.cat_name.setText(image.getCat_name());
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/arial.ttf");
        holder.cat_name.setTypeface(tf);
        Picasso.with(mContext).load(image.getImg_url()).placeholder(R.drawable.phimg).into(holder.image_preview);


    }

    @Override
    public int getItemCount() {
        return home.size();
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