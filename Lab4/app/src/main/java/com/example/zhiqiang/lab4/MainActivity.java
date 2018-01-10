package com.example.zhiqiang.lab4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

public class MainActivity extends AppCompatActivity {
    private final String STATICACTION = "com.example.t617.Lab4.MyStaticBroadCast";
    private static boolean floatFlag = true;
    private Items items = new Items();
    private static List<Map<String, Object>> data = new ArrayList<>();
    private static List<Map<String, Object>> cartData = new ArrayList<>();
    private ListView itemList;
    private RecyclerView mRecyclerView;
    DynamicReceiver dynamicReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        initialShoppingList();
        updateShoppingList();

        floatButtonListener();
        staticBroadCast();

        dynamicReceiver = new DynamicReceiver();
        dynamicBroadCast();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatActionButton);
            floatingActionButton.performClick();
            floatFlag = true;
        }
    }

    public void dynamicBroadCast() {
        IntentFilter dynamicFilter = new IntentFilter();
        dynamicFilter.addAction("MyDynamicBroCast");
        registerReceiver(dynamicReceiver, dynamicFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
        unregisterReceiver(dynamicReceiver);
    }

    public void staticBroadCast() {
        Random random = new Random();
        String itemString = items.itemName[random.nextInt(items.itemName.length)];

        Bundle bundle = new Bundle();

        bundle.putString("MyStaticBroadCast", itemString);
        Intent intentBroadCast = new Intent(STATICACTION);
        intentBroadCast.putExtras(bundle);
        sendBroadcast(intentBroadCast);
    }

    public void initialShoppingList() {
        for (int i = 0; i < items.itemName.length; i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("name", items.itemName[i]);
            temp.put("firstLetter", items.itemName[i].substring(0, 1));
            data.add(temp);
        }
    }

    public void floatButtonListener() {
        final FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (floatFlag) {
                    floatingActionButton.setImageResource(R.mipmap.mainpage);
                    setContentView(R.layout.activity_shopping_cart);
                    updateItems();
                    floatFlag = false;
                } else {
                    floatingActionButton.setImageResource(R.mipmap.shoplist);
                    setContentView(R.layout.activity_main);
                    updateShoppingList();
                    floatFlag = true;
                }
            }
        });
    }

    public int findPositionOfName(String msg) {
        for (int i = 0; i < items.itemName.length; i++) {
            if (msg.equals(items.itemName[i])) return i;
        }
        return 0;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageEvent event) {

        String msg = event.getMsg();
        Map<String, Object> temp = new LinkedHashMap<>();
        temp.put("tag", msg.substring(0, 1));
        temp.put("name", msg);
        temp.put("price", items.itemPrice[findPositionOfName(msg)]);
        cartData.add(temp);
        updateItems();
    }


    public void updateShoppingList() {

        final CommonAdapter mAdapter = new CommonAdapter<Map<String,Object>>(this, R.layout.item, data) {
            @Override
            public void convert(ViewHolder holder, Map<String, Object> s) {
                TextView name = (TextView) holder.getView(R.id.name);
                name.setText(s.get("name").toString());
                TextView first = (TextView) holder.getView(R.id.tag);
                first.setText(s.get("firstLetter").toString());
            }
            @Override
            public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
                convert((ViewHolder) holder, data.get(position));
                if (mOnItemClickListener != null) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickListener.onClick(holder.getAdapterPosition());
                        }
                    });
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            mOnItemClickListener.onLongClick(holder.getAdapterPosition());
                            return false;
                        }
                    });
                }
            }
        };
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(mAdapter);
        animationAdapter.setDuration(1000);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(animationAdapter);
        mRecyclerView.setItemAnimator(new OvershootInLeftAnimator());

        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, ItemDetailActivity.class);
                intent.putExtra("activityMain", (String) data.get(position).get("name"));
//                Toast.makeText(getApplicationContext(),
//                        (String) data.get(position).get("name"), Toast.LENGTH_SHORT).show();
                startActivityForResult(intent, position);
            }
            @Override
            public void onLongClick(int position) {
                data.remove(position);
                Toast.makeText(getApplicationContext(),
                        "移除第" + String.valueOf(position) + "个商品", Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
            }
        });
        floatButtonListener();
    }


    public void updateItems() {
        if (cartData == null) return;
        itemList = (ListView) findViewById(R.id.shoppingCartList);
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, cartData, R.layout.item,
                new String[] {"tag", "name", "price"}, new int[] {R.id.tag ,R.id.name, R.id.price});
        itemList.setAdapter(simpleAdapter);

        itemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                new AlertDialog.Builder(MainActivity.this).setTitle("移除商品")
                        .setMessage("从购物车移除" + (String) cartData.get(i).get("name") + "?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cartData.remove(i);
                        simpleAdapter.notifyDataSetChanged();
                    }
                }).show();
                return true;
            }
        });

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ItemDetailActivity.class);
                intent.putExtra("activityMain", (String) cartData.get(i).get("name"));
                startActivity(intent);
            }
        });
        floatButtonListener();
    }

    public  abstract static class CommonAdapter<T> extends RecyclerView.Adapter{
        private Context mContext;
        private int mLayoutId;
        private List<T> mDatas;
        public OnItemClickListener mOnItemClickListener;


        public static class ViewHolder extends RecyclerView.ViewHolder{
            private SparseArray<View> mViews;
            private View mConvertView;
            public ViewHolder(Context context, View itemView, ViewGroup parent) {
                super(itemView);
                mConvertView = itemView;
                mViews = new SparseArray<View>();
            }
            public static ViewHolder get(Context context, ViewGroup parent, int layoutId) {
                View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
                ViewHolder holder = new ViewHolder(context, itemView, parent);
                return holder;
            }
            public <T extends View> T getView(int viewId) {
                View view = mViews.get(viewId);
                if (view == null) {
                    view = mConvertView.findViewById(viewId);
                    mViews.put(viewId, view);
                }
                return (T) view;
            }
        }
        public CommonAdapter(Context context, int layoutId, List datas) {
            mContext = context;
            mLayoutId = layoutId;
            mDatas = datas;
        }
        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutId);
            return viewHolder;
        }
        public abstract void convert(ViewHolder holder, T t);

        public interface OnItemClickListener{
            void onClick(int position);
            void onLongClick(int position);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }
        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }
}