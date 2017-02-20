package com.sby.lifehodgepodge.adapter.weixin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sby.lifehodgepodge.R;
import com.sby.lifehodgepodge.activity.WeixinItemActivity;
import com.sby.lifehodgepodge.empty.weixin.WXRecyclerEmpty;

import java.util.ArrayList;


/**
 * Created by Lenovo on 2017/2/15.
 */

public class WeixinRecyclerAdapter extends RecyclerView.Adapter<WeixinRecyclerAdapter.MyViewholder>{
    private ArrayList<WXRecyclerEmpty> arrayList;
    private Context context;

    public WeixinRecyclerAdapter(ArrayList<WXRecyclerEmpty> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.weixin_recyclerview_item,parent,false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, int position) {
        holder.title.setText(arrayList.get(position).getTitle());
        holder.source.setText("来源:"+arrayList.get(position).getSource());
        Glide.with(context).load(Uri.parse(arrayList.get(position).getFirstImg())).into(holder.firstimg);
        int item=holder.getAdapterPosition();
        final String url=arrayList.get(item).getUrl();
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList.size()>0){
//                    holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorhuise));
                    Intent intent=new Intent(context, WeixinItemActivity.class);
                    intent.putExtra("url",url);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewholder extends RecyclerView.ViewHolder {
        private TextView title,source;
        private ImageView firstimg;
        private LinearLayout linearLayout;
        public MyViewholder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.wx_recyc_item_title);
            source= (TextView) itemView.findViewById(R.id.wx_recyc_item_source);
            firstimg= (ImageView) itemView.findViewById(R.id.wx_recyc_item_img);
            linearLayout= (LinearLayout) itemView.findViewById(R.id.wx_recyc_item_ll);
        }
    }
}
