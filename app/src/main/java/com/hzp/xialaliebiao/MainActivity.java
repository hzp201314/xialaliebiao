package com.hzp.xialaliebiao;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.hzp.xialaliebiao.adapter.MyAdapter;

public class MainActivity extends Activity implements View.OnClickListener,OnItemClickListener {
    private EditText mNumber;
    private ImageView mArrow;
    private ListView mListView;
    private PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        initView();
    }
    /**
     * 初始化控件
     *
     * 2016-10-28 下午4:38:41
     */
    private void initView() {
        mNumber = (EditText) findViewById(R.id.number);
        mArrow = (ImageView) findViewById(R.id.arrow);

        //设置箭头的点击事件
        mArrow.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow:
                //显示气泡
                showPopupwindow();
                break;
        }
    }
    /**
     * 显示popupwindow
     *
     * 2016-10-28 下午4:40:29
     */
    private void showPopupwindow() {
        View contentView = createView();
        //focusable : popupwindow是否可以获取焦点
        popupWindow = new PopupWindow(contentView, mNumber.getWidth()-4, 300, true);

        //设置气泡点击空白处消失
        popupWindow.setOutsideTouchable(true);//设置气泡是否可以点击空白处消息
        //如果想要让popupWindow实现点击空白处消失的效果，必须给popupWindow设置一个背景
        popupWindow.setBackgroundDrawable(new ColorDrawable( Color.TRANSPARENT));

        //popupWindow.showAsDropDown(mNumber);
        //xoff : 距离mNumber的左边框和顶边框的距离
        popupWindow.showAsDropDown(mNumber, 2, -5);
    }
    /**
     * 创建气泡所需的布局对象
     *@return
     * 2016-10-28 下午4:42:32
     */
    private View createView() {
        mListView = (ListView) View.inflate(getApplicationContext(), R.layout.pop_listview, null);

        //设置adapter显示数据
        mListView.setAdapter(new MyAdapter(this));
        //设置listview的条目点击事件
        mListView.setOnItemClickListener(this);

        return mListView;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        //获取点击条目对应的数据，将数据添加到输入框中显示，同时隐藏气泡
        String msg = (String) mListView.getItemAtPosition(position);//根据条目的索引获取条目对应的数据，调用的是adapter的getItem方法
        mNumber.setText(msg);
        popupWindow.dismiss();
    }
}
