package com.sby.lifehodgepodge.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sby.lifehodgepodge.R;
import com.sby.lifehodgepodge.adapter.xiaohua.XiaohuaRecyclerAdapter;
import com.sby.lifehodgepodge.empty.xiaohua.XhRecyclerEmpty;
import com.sby.lifehodgepodge.utils.AllUrlUtil;
import com.sby.lifehodgepodge.utils.GetOkResultListener;
import com.sby.lifehodgepodge.utils.GetRandom;
import com.sby.lifehodgepodge.utils.IsNetWork;
import com.sby.lifehodgepodge.utils.Ok3Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XiaohuaActivity extends AppCompatActivity implements View.OnClickListener{
    private GetOkResultListener listener=new GetOkResultListener();
    private CollapsingToolbarLayout colltoolbar;
    private Toolbar toolbar;
    private ImageView iv;
    private RecyclerView recyclerView;
    private XiaohuaRecyclerAdapter adapter;
    private ArrayList<XhRecyclerEmpty> arrayList=new ArrayList<>();
    private Button btn_last,btn_next,btn_jump;
    private EditText editText;
    private static int page;
    private static boolean iscreate=true;
    private SwipeRefreshLayout refreshLayout;
    //判断recyclerview是否正在刷新
    private static boolean isRefresh=false;
    private int[] img=new int[]{R.drawable.xh1,R.drawable.xh2,R.drawable.xh3,R.drawable.xh4,R.drawable.xh5};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiaohua);
        //绑定组件
        colltoolbar= (CollapsingToolbarLayout) findViewById(R.id.xiaohua_colltoolbra);
        toolbar= (Toolbar) findViewById(R.id.xioahua_toolbar);
        iv= (ImageView) findViewById(R.id.xiaohua_iv);
        recyclerView= (RecyclerView) findViewById(R.id.xiaohua_recycler);
        btn_next= (Button) findViewById(R.id.xh_btn_next);
        btn_jump= (Button) findViewById(R.id.xh_btn_jump);
        btn_last= (Button) findViewById(R.id.xh_btn_last);
        editText= (EditText) findViewById(R.id.xh_edit);
        refreshLayout= (SwipeRefreshLayout) findViewById(R.id.xh_refresh);
        //设置按钮监听
        btn_last.setOnClickListener(this);
        btn_jump.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        //是否在加载时能够滑动
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isRefresh){
                    return true;
                }
                return false;
            }
        });
        //设置组件
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrayList.clear();
                isRefresh=true;
                startRequest();

            }
        });

        colltoolbar.setTitle("每日一笑");
        setSupportActionBar(toolbar);

        if (!IsNetWork.isNetworkAvailable(this)){
            recyclerView.setBackgroundResource(R.drawable.error2);
        }else {
            //发送网络请求设置recyclerview
            recyclerView.setBackgroundResource(0);
            startRequest();
        }
        //设置可变背景图
        int bgid=new GetRandom(5).getI();
        iv.setImageResource(img[bgid]);
//        系统toolbar返回键，暂时先不加了
//        ActionBar actionBar=getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    //设定所需数据并开始网络请求
    private void startRequest(){
        //1-10000随机数
        page=(new GetRandom(10000).getI())+1;
        iscreate=true;
        //由于我用的系统时间，参数可能出错
        String myurl=AllUrlUtil.AllUrlUtil().getUrlXiaohua(page);
        Ok3Util.getok3util().startOK3(myurl,1);

    }

    /**
     * ok3开子线程获取返回字符串之后无法直接传递到主线程，该方法在handler回调之后自动调用
     * @param myresult ok3请求来的字符串
     */
    public void getArraylist(String myresult){
        //进行json解析
        if (!(myresult.isEmpty())){
            try {
                JSONObject object=new JSONObject(myresult);
                //判断错误码和reason
                int code=object.getInt("error_code");
                String reason=object.getString("reason");
                //如果成功，解析主体部分
                if (code==0 && reason.equals("Success")){
                    JSONObject result=object.getJSONObject("result");
                    //判断这个类型到底是array还是objcet，因为成功和失败返回的类型不同，所以需判断
                    Object ob=new JSONTokener(result.getString("data")).nextValue();
                    //如果是array，即查询成功(接口文档中如果返回array为成功)
                    if (ob instanceof JSONArray){
                        //进行解析
                        JSONArray data=result.getJSONArray("data");
                        if (data!=null && !(data.equals(""))){
                            for (int i=0;i<20;i++){
                                JSONObject mycontent= (JSONObject) data.get(i);
                                String content=mycontent.getString("content");
                                arrayList.add(new XhRecyclerEmpty(content.replaceAll(" ","")));
                            }
                            //设置recyclerview的内容
                            changeAdapter();
                        }else {
                            Toast.makeText(xiaohuaActivity, "服务器此条数据为空", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Log.i("aa", "+++++++++++++++++");
                        Toast.makeText(xiaohuaActivity, "没有此页", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(xiaohuaActivity, "解析失败", Toast.LENGTH_SHORT).show();
                    Log.i("ok3", "json解析失败");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(xiaohuaActivity, "未查询到数据", Toast.LENGTH_SHORT).show();
            Log.i("ok3", "ok3请求来的的是空数据");
        }
    }

    /**
     * 由于我写的方法耦合性不好，为了避免多次创建adapter导致占用内存，所以用这个参数进行判断
     * 判断是oncreate时的请求还是点击按钮跳转的请求
     */
    private void changeAdapter(){
        //如果是activity oncreate 就进行设置
        if (iscreate){
            adapter=new XiaohuaRecyclerAdapter(arrayList,this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            recyclerView.setAdapter(adapter);
            //关闭刷新圈
            refreshLayout.setRefreshing(false);
            isRefresh=false;

        }else {
            //如果是跳转其他页就更新适配器
            adapter.notifyDataSetChanged();
            //定位到第一行
            recyclerView.scrollToPosition(0);
        }

    }

    /**
     * 非Activity类调用该Activity内的方法需使用以下逻辑
     */
    private static XiaohuaActivity xiaohuaActivity;

    public static XiaohuaActivity getXhActivity(){
        return xiaohuaActivity;
    }

    public XiaohuaActivity() {
        xiaohuaActivity=this;
    }


    /**
     *跳转其他页的按钮监听事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.xh_btn_jump:
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
                        startjump(page);
                    }
                }
                break;
            case R.id.xh_btn_last:
                if ((page--)>0){
                   startjump(page);
                }else {
                    Toast.makeText(xiaohuaActivity, "已经是第一页", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.xh_btn_next:
                page++;
               startjump(page);
                break;
        }
    }
    //开始跳转的逻辑
    private void startjump(int value){
        iscreate=false;
        arrayList.clear();
        isRefresh=true;
        String myurl=AllUrlUtil.AllUrlUtil().getUrlXiaohua(value);
        Ok3Util.getok3util().startOK3(myurl,1);
    }

}
