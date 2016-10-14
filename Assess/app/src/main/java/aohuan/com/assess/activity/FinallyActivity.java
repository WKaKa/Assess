package aohuan.com.assess.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import aohuan.com.assess.R;
import aohuan.com.assess.baseactivity.AhView;
import aohuan.com.assess.baseactivity.BaseActivity;
import butterknife.InjectView;
import butterknife.OnClick;

@AhView(R.layout.activity_finally)
public class FinallyActivity extends BaseActivity {

    @InjectView(R.id.m_title_return)
    ImageView mTitleReturn;
    @InjectView(R.id.m_title)
    TextView mTitle;
    @InjectView(R.id.m_right)
    TextView mRight;
    @InjectView(R.id.m_text)
    TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle.setText("结果");
        mText.setText("数据为：\n"+getIntent().getStringExtra("result"));
    }

    @OnClick(R.id.m_title_return)
    public void onClick() {
        finish();
    }
}
