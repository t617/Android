package com.example.zhiqiang.electronicdictionary.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhiqiang.electronicdictionary.Adapter.CommonAdapter;
import com.example.zhiqiang.electronicdictionary.Data.Present;
import com.example.zhiqiang.electronicdictionary.DataBase.SQLiteHelper;
import com.example.zhiqiang.electronicdictionary.R;
import com.example.zhiqiang.electronicdictionary.ViewHolder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

public class
PresentList extends AppCompatActivity implements View.OnClickListener{
    private SQLiteHelper db;
    private ListView peopleList;
    private List<Present> data = new ArrayList<>();
    private List<Map<String, Object>> listItems = new ArrayList<>();
//    private List<Present> peoplelist = new ArrayList<>();
    private SimpleAdapter simpleAdapter;
    private AlertDialog.Builder builder;
    private RecyclerView mRecyclerView;
    private CommonAdapter<Map<String, Object>> commonAdapter;
    private com.getbase.floatingactionbutton.FloatingActionsMenu fab_menu;
    private com.getbase.floatingactionbutton.FloatingActionButton fab1, fab2, fab4;
    private EditText mEtSearch ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findView();
        db = new SQLiteHelper(this);
        initialData();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
//                mRecyclerView.setVisibility(View.VISIBLE);
//                mEtSearch.setVisibility(View.GONE);
            }
        });
    }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent d)
    {
        if(requestCode==0)
        {
            if(resultCode==0)
            {
                Present c= (Present) d.getExtras().get("present");
                Map<String, Object> listItem = new LinkedHashMap<>();
                assert c != null;
                listItem.put("firstLetter", c.getFirstLetter());
                listItem.put("name", c.getName());
//                data.add(c);
                db.createPerson(c);
                listItems.add(listItem);
                commonAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode==2) {
            if (resultCode==0) {
                Present c= (Present) d.getExtras().get("present");
                Map<String, Object> listItem = new LinkedHashMap<>();
                assert c != null;
                db.updatePerson(c);
                for (Present i : data) {
                    if (i.getName()==c.getName()) {
                        i = c;
                        data.add(c);
                        commonAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
    }
    private void findView() {
        setContentView(R.layout.list);
//        peopleList = (ListView) findViewById(R.id.peoplelist);
        mRecyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        peopleList = (ListView) findViewById(R.id.peoplelist);
        builder = new AlertDialog.Builder(this);
        fab_menu = (com.getbase.floatingactionbutton.FloatingActionsMenu) findViewById(R.id.fab_menu);
        fab1 = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_1);
        fab2 = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_2);
//        fab3 = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_3);
        fab4 = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_4);
        fab1.setOnClickListener((View.OnClickListener) this);
        fab2.setOnClickListener((View.OnClickListener) this);
//        fab3.setOnClickListener((View.OnClickListener) this);
        fab4.setOnClickListener((View.OnClickListener) this);
        mEtSearch = (EditText) findViewById(R.id.filter_edit);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_1: // 查询
                mEtSearch.setVisibility(View.VISIBLE);
                fab_menu.collapse();
                mRecyclerView.setVisibility(View.GONE);
                break;
            case R.id.fab_2: // 修改
                Intent intent2 = new Intent(PresentList.this, UpdatePresent.class);
                startActivityForResult(intent2, 2);
                break;
//            case R.id.fab_3: // 删除
//                break;
            case R.id.fab_4: // 添加
                Log.e("fab2", "click");
                Intent intent4 = new Intent(PresentList.this, UpdatePresent.class);
                startActivityForResult(intent4, 0);
                break;
        }
    }

    private void initialData() {
        Present t1, t2, t3, t4, t5, t6, t7, t8, t9, t10;
        t1 = new Present("曹操", "男","155~220年", "势力：曹魏", "沛国谯人（今安徽亳州）", R.mipmap.caocao,"曹操（155年－220年正月庚子），字孟德，一名吉利，小字阿瞒，沛国谯人（今安徽亳州），汉族。东汉末年著名政治家、军事家、文学家、书法家。三国中曹魏政权的缔造者，先为东汉大将军、丞相，后为魏王。其子曹丕称帝后，追尊其为魏武帝。一生以汉朝大将军、丞相的名义征讨四方割据政权，为统一中国北方做出重大贡献，同时在北方屯田，对农业生产恢复有很大作用。曹操的诗作具有创新精神，开启并繁荣了建安文学，给后人留下了宝贵的精神财富，史称建安风骨，鲁迅评价其为“改造文章的祖师”。在书法方面，曹操尤工章草，雄逸绝伦，唐朝张怀瓘在《书断》中评其为汉末章草五大家之一");
        t2 = new Present("关羽", "男","约160或162~220年", "势力：蜀汉", "河东解县（今山西运城）人", R.mipmap.guanyu,"关羽（约160或162－220年），字云长（本字长生），河东解县（今山西运城）人。东汉末年的名将。刘备起兵时，关羽跟随刘备，忠心不二，深受刘备信任。刘备、诸葛亮等入蜀，关羽镇守荆州，刘备夺取汉中后，关羽乘势北伐曹魏，曾围襄樊、擒于禁、斩庞德、威震华夏，中原震动，但是东吴偷袭荆州，关羽兵败被害。关羽去世后，逐渐被神化，被民间尊为“关公”；历代朝廷多有褒封，清代奉为“忠义神武灵佑仁勇威显关圣大帝”，崇为“武圣”，与“文圣” 孔子齐名。《三国演义》尊其为“五虎上将”之首，毛宗岗称其为“《演义》三绝”之“义绝”");
        t3 = new Present("吕布", "男","？~199年", "势力：多方", "五原郡九原县人（今内蒙古包头九原区）", R.mipmap.lvbu,"吕布（？－199年2月7日），字奉先，汉族，东汉末年名将，汉末群雄之一，五原郡九原县人（今内蒙古包头九原区）。先后为丁原、董卓的部将，也曾为袁术效力，曾被封为徐州牧，后自成一方势力，于建安三年（198）在下邳被曹操击败并处死。由于《三国演义》及各种民间艺术的演绎，吕布向来是以“三国第一猛将”的形象存在于人们的心目之中");
        t4 = new Present("赵云", "男","？~229年", "势力：蜀汉", "常山郡真定人", R.mipmap.zhaoyun,"赵云·字子龙（？－229），汉末三国常山郡真定人，汉族。初从公孙瓒，后归刘备。曹操取荆州，刘备败于当阳县长阪，他力战救护甘夫人和备子刘禅。刘备得益州，任为翊军将军，从攻汉中。建兴六年（228年），从诸葛亮攻关中，分兵拒曹真主力，以众寡不敌，退回汉中。次年卒。他曾以数十骑拒曹操大军，被刘备誉为“一身都是胆”。");
        t5 = new Present("夏侯渊", "男","？~219年", "势力：曹魏", "沛国谯（今安徽亳州）人", R.mipmap.xiahouyuan,"夏侯渊（？－219年），东汉末年名将。字妙才，夏侯惇族弟，沛国谯（今安徽亳州）人，擅长千里奔袭。初期随曹操征伐，官渡之战为曹操督运粮草，又督诸将先后平定昌豨、徐和、雷绪、商曜等叛乱。后率军驻凉州，逐马超、破韩遂、灭宋建、横扫羌、氐，虎步关右。张鲁降曹操后夏侯渊留守汉中，于定军山被刘备部将黄忠所袭，战死。官至征西将军，封博昌亭侯，谥曰愍侯");
        t6 = new Present("黄忠", "男","？~220年 ", "势力：蜀汉", "南阳（治今河南南阳）人", R.mipmap.huangz,"黄忠（？－220年），字汉升（《太平御览》卷二百三十八引《蜀志》中作“汉叔”），南阳（治今河南南阳）人。东汉末年名将。本为刘表部下中郎将，后归刘备，并助刘备攻益州刘璋。公元219年，黄忠在定军山一战中阵斩曹操部下名将夏侯渊，升任征西将军，刘备称汉中王后改封后将军，赐关内侯。次年，黄忠病逝，谥曰刚侯。黄忠在后世多以勇猛的老将形象出现于各类文学艺术作品中，在小说《三国演义》中是蜀汉五虎大将之一，他的名字在中国也逐渐成为了老当益壮的代名词。");
        t7 = new Present("张飞", "男","？~221年", "势力：蜀汉", "涿郡（治今河北涿州）人", R.mipmap.zhangfei,"张飞（？－221年），字益德（《三国演义》中字翼德），涿郡（治今河北涿州）人，三国时期蜀汉重要将领。官至车骑将军，封西乡侯。史书记载张飞是贵族，有智有谋。在中国传统文化中，张飞以其勇猛、鲁莽、嫉恶如仇而著称，虽然此形象主要来源于小说和戏剧等民间艺术，但已深入人心。");
        t8 = new Present("诸葛亮", "男","?~234年", "势力：蜀汉", "琅琊阳都（今山东临沂市沂南县）人", R.mipmap.zhugl,"诸葛亮，字孔明、号卧龙（也作伏龙），汉族，琅琊阳都（今山东临沂市沂南县）人，三国时期蜀汉丞相、杰出的政治家、军事家、发明家、文学家。在世时被封为武乡侯，死后追谥忠武侯，东晋政权特追封他为武兴王。诸葛亮为匡扶蜀汉政权，呕心沥血，鞠躬尽瘁，死而后已。其代表作有《前出师表》、《后出师表》、《诫子书》等。曾发明木牛流马等，并改造连弩，可一弩十矢俱发。于234年在宝鸡五丈原逝世。诸葛亮在后世受到极大尊崇，成为后世忠臣楷模，智慧化身。成都、宝鸡、汉中、南阳等地有武侯祠，杜甫作《蜀相》赞诸葛亮。");
        t9 = new Present("夏侯惇", "女","？~220年", "势力：曹魏", "沛国谯（今安徽亳州）人", R.mipmap.xiahoudun,"夏侯惇（？－220年），字元让，沛国谯（今安徽亳州）人。东汉末年曹操部下大将，汉朝开国功臣之一夏侯婴的后代。历任折冲校尉、济阴太守、建武将军，大将军等，封高安乡侯，谥号忠侯");
        t10 = new Present("刘备", "男","161~223年", "势力：蜀汉", "东汉末年幽州涿郡涿县（今河北省保定市涿州市）人", R.mipmap.liubei,"刘备（161年－223年6月10日），字玄德，东汉末年幽州涿郡涿县（今河北省保定市涿州市）人，三国时期蜀汉开国皇帝，谥号昭烈皇帝，史家又称为先主。根据《三国志·先主传》，刘备是汉朝的宗室，汉中山靖王刘胜的后代。他为人谦和、礼贤下士，宽以待人，志向远大，知人善用，素以仁德为世人称赞，是三国时期著名的政治家，公元221年在成都称帝，国号汉，年号章武，史称蜀或蜀汉，占有今四川、云南大部、贵州全部，陕西汉中和甘肃白龙江一部分。公元223病逝于白帝城。谥号昭烈帝，庙号烈祖，葬惠陵。后世有众多文艺作品以其为主角，在成都武侯祠有昭烈庙为纪念。");
        db.createPerson(t1);
        db.createPerson(t2);
        db.createPerson(t3);
        db.createPerson(t4);
        db.createPerson(t5);
        db.createPerson(t6);
        db.createPerson(t7);
        db.createPerson(t8);
        db.createPerson(t9);
        db.createPerson(t10);
        List<Present> d = db.queryAllPerson();
        data.addAll(d);

//        simpleAdapter = new SimpleAdapter(this, listItems2, R.layout.present_list_item, new String[]{"firstLetter", "name"}, new int[]{R.id.first, R.id.name});
//        peopleList.setAdapter(simpleAdapter);
        for (Present c : data) {
            Map<String, Object> listItem = new LinkedHashMap<>();
            listItem.put("firstLetter", c.getFirstLetter());
            listItem.put("name", c.getName());
            listItems.add(listItem);
        }
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);//这里用线性显示 类似于listview
        //mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
        commonAdapter = new CommonAdapter<Map<String, Object>>(this, R.layout.present_list_item, listItems) {
            @Override
            public void convert(ViewHolder holder, Map<String, Object> s) {
                TextView name = holder.getView(R.id.name);
                name.setText(s.get("name").toString());
                TextView first = holder.getView(R.id.first);
                first.setText(s.get("firstLetter").toString());
            }
        };
        commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(PresentList.this, PresentInfo.class);
                Bundle bundle = new Bundle();
                for (int i = 0; i < data.size(); i++){
                    if (listItems.get(position).get("name").toString() == (data.get(i).getName())){
                        bundle.putSerializable("Present", data.get(i));
                    }
                }
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
            @Override
            public void onLongClick(int position) {
                db.deletePerson((String) listItems.get(position).get("name"));
                commonAdapter.removeItem(position);
                mLayoutManager.scrollToPosition(position);
                Toast.makeText(getApplicationContext(),"已移除人物",Toast.LENGTH_SHORT).show();
            }
        });
        ScaleInAnimationAdapter animationAdapter=new ScaleInAnimationAdapter(commonAdapter);
        animationAdapter.setFirstOnly(true);
        animationAdapter.setDuration(1000);
        mRecyclerView.setAdapter(animationAdapter);
        mRecyclerView.setItemAnimator(new OvershootInLeftAnimator());
    }

    private void search(String s) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> temp = new LinkedHashMap<>();
        if (!TextUtils.isEmpty(s)) {
            Present present = db.queryPerson(s);
            if (present!=null) {
                temp.put("firstLetter", present.getFirstLetter());
                temp.put("name", present.getName());
                dataList.add(temp);
                simpleAdapter = new SimpleAdapter(this, dataList, R.layout.present_list_item, new String[]{"firstLetter", "name"}, new int[]{R.id.first, R.id.name});
                peopleList.setAdapter(simpleAdapter);
                peopleList.setVisibility(View.VISIBLE);
                peopleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(PresentList.this, PresentInfo.class);
                        Bundle bundle = new Bundle();
                        for (int j = 0; j < data.size(); j++){
                            if (listItems.get(i).get("name").toString() == (data.get(j).getName())){
                                bundle.putSerializable("Present", data.get(i));
                            }
                        }
                        intent.putExtras(bundle);
                        startActivityForResult(intent,1);
                    }
                });
            }
        }


//        List<Map<String, Object>> dataList = new ArrayList<>();
//        if (TextUtils.isEmpty(s)){
//            for (Present c : data) {
//                Map<String, Object> listItem = new LinkedHashMap<>();
//                listItem.put("firstLetter", c.getFirstLetter());
//                listItem.put("name", c.getName());
//                listItems.add(listItem);
//            }
//        } else {
//            dataList.clear();
//            for (Map<String, Object> listItem : listItems){
//                String name = listItem.get("name").toString();
//                if (name.indexOf(s.toString()) != -1) {
//                    dataList.add(listItem);
//                }
//            }
//            listItems.clear();
//            listItems.addAll(dataList);
//        }
//        commonAdapter.notifyDataSetChanged();
    }
}
