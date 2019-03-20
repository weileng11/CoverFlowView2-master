package com.missmess.demo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.missmess.coverflowview.CoverFlowView;
import com.missmess.demo.adapter.MyCoverFlowAdapter;
import com.missmess.demo.adapter.MyVpAdapter;
import com.missmess.demo.adapter.NewCoverFlowAdapter;


public class MainActivity extends AppCompatActivity {
    private CoverFlowView coverflow_view;
    private TabLayout tabl;
    private ViewPager vp;
    private MyCoverFlowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        coverflow_view = (CoverFlowView) findViewById(R.id.coverflow_view);
        tabl = (TabLayout) findViewById(R.id.tabl);
        vp = (ViewPager) findViewById(R.id.vp);

        adapter = new MyCoverFlowAdapter();
        coverflow_view.setAdapter(adapter);
        coverflow_view.setOnViewOnTopListener(new CoverFlowView.OnViewOnTopListener() {
            @Override
            public void viewOnTop(int position, View itemView) {
                Log.d("viewOnTop", "position==" + position);
                if (adapter == null)
                    return;
                if (position == 0) {
                    vp.setCurrentItem(1);
                } else if (position == 2) {
                    adapter.animProgress(itemView);
                }
            }
        });
        coverflow_view.setOnTopViewClickListener(new CoverFlowView.OnTopViewClickListener() {
            @Override
            public void onClick(int position, View itemView) {
                Toast.makeText(MainActivity.this, "position " + position + " clicked", Toast.LENGTH_SHORT).show();
            }
        });
        coverflow_view.setOnTopViewLongClickListener(new CoverFlowView.OnTopViewLongClickListener() {
            @Override
            public void onLongClick(int position, View itemView) {
                Toast.makeText(MainActivity.this, "position " + position + " long clicked", Toast.LENGTH_SHORT).show();
            }
        });

        vp.setAdapter(new MyVpAdapter(coverflow_view));
        tabl.setupWithViewPager(vp);
    }

    public void vp1Clicker(View v) {
        int paddingTop = coverflow_view.getPaddingTop();
        int paddingBottom = coverflow_view.getPaddingBottom();
        int paddingLeft = coverflow_view.getPaddingLeft();
        int paddingRight = coverflow_view.getPaddingRight();
        int maxPadding = 250;
        int minPadding = -300;
        int stepPadding = 50;
        switch (v.getId()) {
            case R.id.button:
                paddingLeft += stepPadding;
                paddingLeft = Math.min(maxPadding, Math.max(minPadding, paddingLeft));
                paddingRight += stepPadding;
                paddingRight = Math.min(maxPadding, Math.max(minPadding, paddingRight));
                coverflow_view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                break;
            case R.id.button2:
                paddingLeft -= stepPadding;
                paddingLeft = Math.min(maxPadding, Math.max(minPadding, paddingLeft));
                paddingRight -= stepPadding;
                paddingRight = Math.min(maxPadding, Math.max(minPadding, paddingRight));
                coverflow_view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                break;
        }
    }

    public void vp2Clicker(View v) {
        switch (v.getId()) {
            case R.id.button:
                coverflow_view.gotoPrevious();
                break;
            case R.id.button2:
                coverflow_view.gotoForward();
                break;
        }
    }

    public void vp3Clicker(View v) {
        switch (v.getId()) {
            case R.id.button:
                if(adapter != null) {
                    if (adapter.getTips() == MyCoverFlowAdapter.InitialTips) {
                        adapter.setTips(MyCoverFlowAdapter.NewTips);
                    } else {
                        adapter.setTips(MyCoverFlowAdapter.InitialTips);
                    }
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.button2:
                if(adapter != null) {
                    coverflow_view.setAdapter(new NewCoverFlowAdapter());
                    adapter = null;
                } else {
                    adapter = new MyCoverFlowAdapter();
                    coverflow_view.setAdapter(adapter);
                }
                break;
            case R.id.button3:
                coverflow_view.setSelection(3, true);
                break;
            case R.id.button4:
                coverflow_view.setSelection(2, false);
                break;
        }
    }
}
