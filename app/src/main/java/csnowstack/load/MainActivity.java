package csnowstack.load;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import csnowstack.load.view.PullLeftLoadMoreLayout;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private PullLeftLoadMoreLayout mPullLeftLoadMoreLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView= (RecyclerView) findViewById(R.id.rcv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mRecyclerView.setAdapter(new ELMAdapter());

        mPullLeftLoadMoreLayout= (PullLeftLoadMoreLayout) findViewById(R.id.pull_load_layout);
        mPullLeftLoadMoreLayout.addView(getResources().getDimensionPixelOffset(R.dimen.item_img));
        mPullLeftLoadMoreLayout.setFillLoadingColor(ContextCompat.getColor(this,R.color.colorAccent));
        mPullLeftLoadMoreLayout.setOnGoListener(new PullLeftLoadMoreLayout.OnNoticeGoListener() {
            @Override
            public void go() {
                Toast.makeText(MainActivity.this,"跳转页面",Toast.LENGTH_SHORT).show();
            }
        });


    }






    static class ELMAdapter extends RecyclerView.Adapter<ELMAdapter.ViewHolder>{


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_elm,parent,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 8;
        }

        static class ViewHolder extends RecyclerView.ViewHolder{

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
