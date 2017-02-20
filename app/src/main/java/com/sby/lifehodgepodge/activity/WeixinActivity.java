package com.sby.lifehodgepodge.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sby.lifehodgepodge.R;
import com.sby.lifehodgepodge.adapter.weixin.WeixinRecyclerAdapter;
import com.sby.lifehodgepodge.empty.weixin.WXGsonEmpty;
import com.sby.lifehodgepodge.empty.weixin.WXRecyclerEmpty;
import com.sby.lifehodgepodge.utils.AllUrlUtil;
import com.sby.lifehodgepodge.utils.GetRandom;
import com.sby.lifehodgepodge.utils.GsonUtil;
import com.sby.lifehodgepodge.utils.IsNetWork;
import com.sby.lifehodgepodge.utils.Ok3Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeixinActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private WeixinRecyclerAdapter adapter;
    private ArrayList<WXRecyclerEmpty> arrayList=new ArrayList<>();
    private static int page;
    private static boolean iscreate=true;
    private Button btn_last,btn_next,btn_jump;
    private EditText editText;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayout linearLayout;
    //判断recyclerview是否正在刷新
    private static boolean isRefresh=false;
    //最大页
    private static int totalPage=388;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixin);
        btn_last= (Button) findViewById(R.id.wx_btn_last);
        btn_next= (Button) findViewById(R.id.wx_btn_next);
        btn_jump= (Button) findViewById(R.id.wx_btn_jump);
        editText= (EditText) findViewById(R.id.wx_edit);
        linearLayout= (LinearLayout) findViewById(R.id.wx_linearlayout);
        refreshLayout= (SwipeRefreshLayout) findViewById(R.id.wx_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrayList.clear();
                isRefresh=true;
                startRequest();
            }
        });
        btn_jump.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_last.setOnClickListener(this);

        recyclerView= (RecyclerView) findViewById(R.id.weixin_recycler);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isRefresh){
                    return true;
                }
                else
                    return false;
            }
        });

        if (!IsNetWork.isNetworkAvailable(this)){
            recyclerView.setBackgroundResource(R.drawable.error);
        }else {
            //发送网络请求设置recyclerview
            recyclerView.setBackgroundResource(0);
            startRequest();
        }

    }




    //设定所需数据并开始网络请求
    private void startRequest(){
        //1-10000随机数
        page=(new GetRandom(388).getI())+1;
        iscreate=true;
        String myurl= AllUrlUtil.AllUrlUtil().getUrlWeixin(page);
        Ok3Util.getok3util().startOK3(myurl,2);
    }


    public void getArraylist(String myresult){
        if (!myresult.isEmpty()){
            try {
                JSONObject object=new JSONObject(myresult);
                String reason=object.getString("reason");
                int error_code=object.getInt("error_code");
                if (reason.equals("请求成功") && error_code==0){
                    JSONObject result=object.getJSONObject("result");
                    //返回的最大页数
                    totalPage=result.getInt("totalPage");
                    JSONArray list=result.getJSONArray("list");
                    List<WXGsonEmpty> emptylist=
                            GsonUtil.getGsonUtil().startGsonToArray(list.toString(), WXGsonEmpty.class);
                    for (WXGsonEmpty empty:emptylist){
                        String title=empty.getTitle();
                        String source=empty.getSoucre();
                        String firstImg=empty.getFirstImg();
                        String url=empty.getUrl();
                        WXRecyclerEmpty wxRecyclerEmpty=new WXRecyclerEmpty(title,source,firstImg,url);
                        arrayList.add(wxRecyclerEmpty);
                    }
                    changeAdapter();
                }else {
                    Toast.makeText(weixinActivity, "获取数据失败", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(weixinActivity, "获取数据失败", Toast.LENGTH_SHORT).show();
        }

    }


    private void changeAdapter(){
        //如果是activity oncreate 就进行设置
        if (iscreate){
            recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            adapter=new WeixinRecyclerAdapter(arrayList,this);
            recyclerView.setAdapter(adapter);
            btn_jump.setText("跳转"+"(共"+totalPage+"页)");
            isRefresh=false;
        }else {
            //如果是跳转其他页就更新适配器
            if (adapter!=null){
                adapter.notifyDataSetChanged();
                //定位到第一行
                recyclerView.scrollToPosition(0);
            }

        }
        refreshLayout.setRefreshing(false);
        linearLayout.setVisibility(View.VISIBLE);
    }


    /**
     * 获取此activity实例
     */
    private static WeixinActivity weixinActivity;
    public WeixinActivity() {
        weixinActivity=this;
    }
    public static WeixinActivity getWeixinActivity(){
        return weixinActivity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wx_btn_last:
                if ((page--)>0){
                    startjump(page);
                }else {
                    Toast.makeText(WeixinActivity.this, "已经是第一页", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.wx_btn_next:
                if ((page++)<=totalPage){
                    startjump(page);
                }else {
                    Toast.makeText(WeixinActivity.this, "已经是最后一页", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.wx_btn_jump:
                String text=editText.getText().toString();
                if (text.isEmpty()){
                    Toast.makeText(this, "请输入页数", Toast.LENGTH_SHORT).show();
                }
                else {
                    Pattern p = Pattern.compile("[0-9]*");
                    Matcher m = p.matcher(text);
                    if(!(m.matches())){
                        Toast.makeText(this,"请输入纯数字页数,不能有空格", Toast.LENGTH_SHORT).show();
                    }else {
                        page=Integer.parseInt(text);
                        if (page<=totalPage){
                            startjump(page);
                        }else {
                            Toast.makeText(weixinActivity, "无此页", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

    //开始跳转的逻辑
    private void startjump(int value){
        iscreate=false;
        arrayList.clear();
        isRefresh=true;
        linearLayout.setVisibility(View.GONE);
        String myurl=AllUrlUtil.AllUrlUtil().getUrlWeixin(value);
        Ok3Util.getok3util().startOK3(myurl,2);
    }
}
