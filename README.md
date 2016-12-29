## LikeELMLeftLoad
仿饿了么左滑跳转

![预览](https://github.com/CSnowStack/LikeELMLeftLoad/blob/master/img/preview.gif)

## 膜拜巴神



## 使用

### 加依赖

```java
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:25.1.0'
    compile 'com.android.support:design:25.1.0'
}

```

### 把View文件内的文件放到你们那的项目中，还有一些配置文件也要拉过去


### 代码使用
```java
<csnowstack.load.behavior.view.PullLeftLoadMoreLayout
    android:id="@+id/pull_load_layout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <android.support.v7.widget.RecyclerView
        android:id="@+id/rcv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:overScrollMode="never"/>
</csnowstack.load.behavior.view.PullLeftLoadMoreLayout>


mRecyclerView= (RecyclerView) findViewById(R.id.rcv);
       mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
       mRecyclerView.setAdapter(new ELMAdapter());

       mPullLeftLoadMoreLayout= (PullLeftLoadMoreLayout) findViewById(R.id.pull_load_layout);
       mPullLeftLoadMoreLayout.addView(getResources().getDimensionPixelOffset(R.dimen.item_img));
       mPullLeftLoadMoreLayout.setFillLoadingColor(ContextCompat.getColor(this,R.color.colorAccent));
       mPullLeftLoadMoreLayout.setOnGoListener(new LoadingView.OnNoticeGoListener() {
           @Override
           public void go() {
               Toast.makeText(MainActivity.this,"跳转页面",Toast.LENGTH_SHORT).show();
           }
       });

```
