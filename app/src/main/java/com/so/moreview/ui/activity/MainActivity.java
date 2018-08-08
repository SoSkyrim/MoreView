package com.so.moreview.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.so.moreview.R;
import com.so.moreview.uitls.UIUtil;

import java.util.LinkedList;

import static com.so.moreview.R.id.ll_content;

public class MainActivity extends AppCompatActivity {

    @android.support.annotation.IdRes
    private int mBtnAddId = 1000;
    @android.support.annotation.IdRes
    private int mEtId = 2000;

    private int mEtHeight;
    private LinkedList<ImageButton> mAddList;
    private LinkedList<ImageButton> mDelList;
    private LinearLayout mLlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    /**
     * 初始化自定义ui
     */
    private void initUI() {
        mLlContent = (LinearLayout) findViewById(ll_content);
        ImageButton ibAdd1 = (ImageButton) findViewById(R.id.ib_add1);

        mAddList = new LinkedList<>();
        mDelList = new LinkedList<>();

        // 添加第一个按钮
        mAddList.add(ibAdd1);
        mDelList.add(null);

        ibAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取输入框高度
                mEtHeight = findViewById(R.id.et_item1).getHeight();

                addItem(v);
            }
        });
    }

    /**
     * @param v 添加一个新条目
     */
    private void addItem(View v) {
        if (v == null) {
            return;
        }

        // 1. 根据传入的v, 判断是mListAddBtn中的哪一个
        int curView = -1;
        for (int i = 0; i < mAddList.size(); i++) {
            if (mAddList.get(i) == v) {
                curView = i;
                break;
            }
        }

        // 2. 根据获取的值添加控件
        if (curView >= 0) {
            curView++;

            // ll_item
            LinearLayout ll = new LinearLayout(MainActivity.this);
            LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            llParams.setMargins(0, UIUtil.dp2px(8), 0, 0);

            ll.setLayoutParams(llParams);
            ll.setBackgroundColor(ContextCompat.getColor(this,
                    android.R.color.darker_gray));
            ll.setPadding(UIUtil.dp2px(8), UIUtil.dp2px(8),
                    UIUtil.dp2px(8), UIUtil.dp2px(8));
            ll.setOrientation(LinearLayout.VERTICAL);

            // et_item
            final EditText et = new EditText(MainActivity.this);
            LinearLayout.LayoutParams etParams =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, mEtHeight);

            et.setLayoutParams(etParams);

            et.setBackgroundColor(ContextCompat.getColor(this,
                    android.R.color.white));
            et.setGravity(Gravity.CENTER_VERTICAL);
            et.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            et.setTextSize(16);
            et.setId(mEtId);

            ll.addView(et);

            RelativeLayout rl = new RelativeLayout(MainActivity.this);
            RelativeLayout.LayoutParams rlParams =
                    new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);

            rlParams.setMargins(0, UIUtil.dp2px(8), 0, 0);
            rl.setLayoutParams(rlParams);

            // ib_add
            ImageButton btAdd = new ImageButton(MainActivity.this);
            RelativeLayout.LayoutParams btAddParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            btAddParams.addRule(RelativeLayout.ALIGN_PARENT_END);

            btAdd.setLayoutParams(btAddParams);
            btAdd.setBackgroundResource(R.drawable.add);
            btAdd.setId(mBtnAddId);
            btAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addItem(v);
                }
            });

            rl.addView(btAdd);
            mAddList.add(curView, btAdd);

            // ib_del
            ImageButton btDel = new ImageButton(MainActivity.this);
            RelativeLayout.LayoutParams btDelParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            btDelParams.setMargins(0, 0, UIUtil.dp2px(8), 0);
            btDelParams.addRule(RelativeLayout.LEFT_OF, mBtnAddId);

            btDel.setLayoutParams(btDelParams);
            btDel.setBackgroundResource(R.drawable.del);
            btDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delItem(v);
                }
            });

            rl.addView(btDel);
            mDelList.add(curView, btDel);

            ll.addView(rl);

            mLlContent.addView(ll, curView);

            mBtnAddId++;
            mEtId++;
        }
    }

    /**
     * @param v 删除一个新的EditText条目
     */
    private void delItem(View v) {
        if (v == null) {
            return;
        }

        int curView = -1;
        for (int i = 0; i < mDelList.size(); i++) {
            if (mDelList.get(i) == v) {
                curView = i;
                break;
            }
        }

        if (curView >= 0) {
            mAddList.remove(curView);
            mDelList.remove(curView);

            mLlContent.removeViewAt(curView);
        }
    }
}
