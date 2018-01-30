package com.example.zhiqiang.vq.entity;

import org.json.JSONObject;

/**
 * Created by liteng on 16/9/6.
 */
public class Lives {


    /**
     * id : 2567959
     * level : 7
     * nick :     冷
     * portrait : MTQ3MjkwMjM3NDQwOCMzNzYjanBn.jpg
     */

    private Creator creator;
    /**
     * creator : {"id":2567959,"level":7,"nick":"    冷","portrait":"MTQ3MjkwMjM3NDQwOCMzNzYjanBn.jpg"}
     * id : 1473130152128754
     * name : 起床了
     * city : 北京市
     * share_addr : http://mlive6.inke.cn/share/live.html?uid=2567959&liveid=1473130152128754&ctime=1473130152
     * stream_addr : http://pull7.a8.com/live/1473130152128754.flv
     * version : 0
     * slot : 2
     * optimal : 0
     * group : 44
     * distance : 2.3km
     * link : 0
     * multi : 0
     * rotate : 0
     */

    private String id;
    private String name;
    private String city;
    private String share_addr;
    private String stream_addr;
    private String portrait;
    private String description;
    private int version;
    private int gender;
    private int slot;
    private int optimal;
    private int group;
    private int link;
    private int multi;
    private int rotate;

    public Lives(JSONObject live) {
        this.stream_addr = live.optString("stream_addr");
        this.name = live.optString("nick");
        this.gender = live.optInt("gender");
        this.city = live.optString("city");
        this.description = live.optString("desription");
        this.portrait = live.optString("portrait");
        this.creator = new Creator(live.optJSONObject("creator"));
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public String getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public int getGender() {
        return gender;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public String getPortrait() {
        return portrait;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getShare_addr() {
        return share_addr;
    }

    public void setShare_addr(String share_addr) {
        this.share_addr = share_addr;
    }

    public String getStream_addr() {
        return stream_addr;
    }

    public void setStream_addr(String stream_addr) {
        this.stream_addr = stream_addr;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getOptimal() {
        return optimal;
    }

    public void setOptimal(int optimal) {
        this.optimal = optimal;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }


    public int getLink() {
        return link;
    }

    public void setLink(int link) {
        this.link = link;
    }

    public int getMulti() {
        return multi;
    }

    public void setMulti(int multi) {
        this.multi = multi;
    }

    public int getRotate() {
        return rotate;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }

    public static class Creator {
        private int id;
        private int level;
        private int gender;
        private String city;
        private String name;
        private String description;
        private String portrait;

        public Creator(JSONObject creator) {
            this.name = creator.optString("nick");
            this.gender = creator.optInt("gender");
            this.portrait = creator.optString("portrait");
            this.city = creator.optString("city");
            this.description = creator.optString("description");
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getCity() {
            return city;
        }
        public String getDescription() {
            return description;
        }
        public int getGender() {
            return gender;
        }
        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }
    }
}
