package com.sby.lifehodgepodge.adapter.xiaohua;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sby.lifehodgepodge.R;
import com.sby.lifehodgepodge.activity.XhItemActivity;
import com.sby.lifehodgepodge.empty.xiaohua.XhRecyclerEmpty;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2017/2/13.
 */

public class XiaohuaRecyclerAdapter extends RecyclerView.Adapter<XiaohuaRecyclerAdapter.MyViewholder>{
    private ArrayList<XhRecyclerEmpty> arrayList;
    private Context context;

    public XiaohuaRecyclerAdapter(ArrayList<XhRecyclerEmpty> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.xiaohua_recyclerview_item,parent,false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {
        holder.textView.setText((arrayList.get(position).getContent()).replaceAll(" ",""));
        //当前点的是哪个
        final int item=holder.getAdapterPosition();
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList.size()>0){
                    Intent intent=new Intent(context, XhItemActivity.class);
                    intent.putExtra("content",(arrayList.get(item).getContent()).replaceAll(" ",""));
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
        private TextView textView;
        public MyViewholder(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.xiaohua_recycler_item_tv);
        }
    }
}
