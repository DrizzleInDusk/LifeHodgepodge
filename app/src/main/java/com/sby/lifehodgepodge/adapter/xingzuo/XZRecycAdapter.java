package com.sby.lifehodgepodge.adapter.xingzuo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sby.lifehodgepodge.R;
import com.sby.lifehodgepodge.activity.XZItemActivity;
import com.sby.lifehodgepodge.activity.XingZuoActivity;
import com.sby.lifehodgepodge.empty.xingzuo.XZRecyclerEmpty;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2017/2/18.
 */

public class XZRecycAdapter extends RecyclerView.Adapter<XZRecycAdapter.MyViewHolder>{
    private ArrayList<XZRecyclerEmpty> arrayList;
    private Context context;

    public XZRecycAdapter(ArrayList<XZRecyclerEmpty> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.xz_recyc_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context).load(arrayList.get(position).getImgid()).into(holder.imageView);
        holder.textView.setText(arrayList.get(position).getText());
        final int item=holder.getAdapterPosition();
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value=(arrayList.get(item).getText()).substring(0,3);
                Intent intent=new Intent(context, XZItemActivity.class);
                intent.putExtra("xz",value);
                context.startActivity(intent);
                if (((XingZuoActivity)context)!=null){
                    ((XingZuoActivity)context).finish();
                }
                if (XZItemActivity.getXZItem()!=null){
                    XZItemActivity.getXZItem().finish();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private ImageView imageView;
        private TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            linearLayout= (LinearLayout) itemView.findViewById(R.id.xz_linearlayout);
            imageView= (ImageView) itemView.findViewById(R.id.xz_item_img);
            textView= (TextView) itemView.findViewById(R.id.xz_item_tv);
        }
    }
}
