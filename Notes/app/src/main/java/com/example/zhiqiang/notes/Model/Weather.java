package com.example.zhiqiang.notes.Model;

/**
 * Created by ts13 on 2018/1/8.
 */

public class Weather {

    /**
     * resultcode : 200
     * reason : successed!
     * result : {"sk":{"temp":"16","wind_direction":"东风","wind_strength":"0级","humidity":"96%","time":"00:54"},"today":{"temperature":"6℃~12℃","weather":"中雨-大雨转小雨","weather_id":{"fa":"22","fb":"07"},"wind":"4-5 级","week":"星期一","city":"广州","date_y":"2018年01月08日","dressing_index":"较冷","dressing_advice":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。","uv_index":"最弱","comfort_index":"","wash_index":"不宜","travel_index":"较不宜","exercise_index":"较不宜","drying_index":""},"future":{"day_20180108":{"temperature":"6℃~12℃","weather":"中雨-大雨转小雨","weather_id":{"fa":"22","fb":"07"},"wind":"4-5 级","week":"星期一","date":"20180108"},"day_20180109":{"temperature":"6℃~9℃","weather":"小雨转阴","weather_id":{"fa":"07","fb":"02"},"wind":"3-4 级","week":"星期二","date":"20180109"},"day_20180110":{"temperature":"7℃~15℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"微风","week":"星期三","date":"20180110"},"day_20180111":{"temperature":"7℃~15℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"微风","week":"星期四","date":"20180111"},"day_20180112":{"temperature":"℃~℃","weather":"","weather_id":{"fa":"99","fb":"99"},"wind":"","week":"星期五","date":"20180112"},"day_20180113":{"temperature":"6℃~9℃","weather":"小雨转阴","weather_id":{"fa":"07","fb":"02"},"wind":"3-4 级","week":"星期六","date":"20180113"},"day_20180114":{"temperature":"7℃~15℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"微风","week":"星期日","date":"20180114"}}}
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    private ResultBean result;
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * sk : {"temp":"16","wind_direction":"东风","wind_strength":"0级","humidity":"96%","time":"00:54"}
         * today : {"temperature":"6℃~12℃","weather":"中雨-大雨转小雨","weather_id":{"fa":"22","fb":"07"},"wind":"4-5 级","week":"星期一","city":"广州","date_y":"2018年01月08日","dressing_index":"较冷","dressing_advice":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。","uv_index":"最弱","comfort_index":"","wash_index":"不宜","travel_index":"较不宜","exercise_index":"较不宜","drying_index":""}
         * future : {"day_20180108":{"temperature":"6℃~12℃","weather":"中雨-大雨转小雨","weather_id":{"fa":"22","fb":"07"},"wind":"4-5 级","week":"星期一","date":"20180108"},"day_20180109":{"temperature":"6℃~9℃","weather":"小雨转阴","weather_id":{"fa":"07","fb":"02"},"wind":"3-4 级","week":"星期二","date":"20180109"},"day_20180110":{"temperature":"7℃~15℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"微风","week":"星期三","date":"20180110"},"day_20180111":{"temperature":"7℃~15℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"微风","week":"星期四","date":"20180111"},"day_20180112":{"temperature":"℃~℃","weather":"","weather_id":{"fa":"99","fb":"99"},"wind":"","week":"星期五","date":"20180112"},"day_20180113":{"temperature":"6℃~9℃","weather":"小雨转阴","weather_id":{"fa":"07","fb":"02"},"wind":"3-4 级","week":"星期六","date":"20180113"},"day_20180114":{"temperature":"7℃~15℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"微风","week":"星期日","date":"20180114"}}
         */

        private SkBean sk;
        private TodayBean today;
        private FutureBean future;

        public SkBean getSk() {
            return sk;
        }

        public void setSk(SkBean sk) {
            this.sk = sk;
        }

        public TodayBean getToday() {
            return today;
        }

        public void setToday(TodayBean today) {
            this.today = today;
        }

        public FutureBean getFuture() {
            return future;
        }

        public void setFuture(FutureBean future) {
            this.future = future;
        }

        public static class SkBean {
            /**
             * temp : 16
             * wind_direction : 东风
             * wind_strength : 0级
             * humidity : 96%
             * time : 00:54
             */

            private String temp;
            private String wind_direction;
            private String wind_strength;
            private String humidity;
            private String time;

            public String getTemp() {
                return temp;
            }

            public void setTemp(String temp) {
                this.temp = temp;
            }

            public String getWind_direction() {
                return wind_direction;
            }

            public void setWind_direction(String wind_direction) {
                this.wind_direction = wind_direction;
            }

            public String getWind_strength() {
                return wind_strength;
            }

            public void setWind_strength(String wind_strength) {
                this.wind_strength = wind_strength;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class TodayBean {
            /**
             * temperature : 6℃~12℃
             * weather : 中雨-大雨转小雨
             * weather_id : {"fa":"22","fb":"07"}
             * wind : 4-5 级
             * week : 星期一
             * city : 广州
             * date_y : 2018年01月08日
             * dressing_index : 较冷
             * dressing_advice : 建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。
             * uv_index : 最弱
             * comfort_index :
             * wash_index : 不宜
             * travel_index : 较不宜
             * exercise_index : 较不宜
             * drying_index :
             */

            private String temperature;
            private String weather;
            private WeatherIdBean weather_id;
            private String wind;
            private String week;
            private String city;
            private String date_y;
            private String dressing_index;
            private String dressing_advice;
            private String uv_index;
            private String comfort_index;
            private String wash_index;
            private String travel_index;
            private String exercise_index;
            private String drying_index;

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public WeatherIdBean getWeather_id() {
                return weather_id;
            }

            public void setWeather_id(WeatherIdBean weather_id) {
                this.weather_id = weather_id;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDate_y() {
                return date_y;
            }

            public void setDate_y(String date_y) {
                this.date_y = date_y;
            }

            public String getDressing_index() {
                return dressing_index;
            }

            public void setDressing_index(String dressing_index) {
                this.dressing_index = dressing_index;
            }

            public String getDressing_advice() {
                return dressing_advice;
            }

            public void setDressing_advice(String dressing_advice) {
                this.dressing_advice = dressing_advice;
            }

            public String getUv_index() {
                return uv_index;
            }

            public void setUv_index(String uv_index) {
                this.uv_index = uv_index;
            }

            public String getComfort_index() {
                return comfort_index;
            }

            public void setComfort_index(String comfort_index) {
                this.comfort_index = comfort_index;
            }

            public String getWash_index() {
                return wash_index;
            }

            public void setWash_index(String wash_index) {
                this.wash_index = wash_index;
            }

            public String getTravel_index() {
                return travel_index;
            }

            public void setTravel_index(String travel_index) {
                this.travel_index = travel_index;
            }

            public String getExercise_index() {
                return exercise_index;
            }

            public void setExercise_index(String exercise_index) {
                this.exercise_index = exercise_index;
            }

            public String getDrying_index() {
                return drying_index;
            }

            public void setDrying_index(String drying_index) {
                this.drying_index = drying_index;
            }

            public static class WeatherIdBean {
                /**
                 * fa : 22
                 * fb : 07
                 */

                private String fa;
                private String fb;

                public String getFa() {
                    return fa;
                }

                public void setFa(String fa) {
                    this.fa = fa;
                }

                public String getFb() {
                    return fb;
                }

                public void setFb(String fb) {
                    this.fb = fb;
                }
            }
        }

        public static class FutureBean {
            /**
             * day_20180108 : {"temperature":"6℃~12℃","weather":"中雨-大雨转小雨","weather_id":{"fa":"22","fb":"07"},"wind":"4-5 级","week":"星期一","date":"20180108"}
             * day_20180109 : {"temperature":"6℃~9℃","weather":"小雨转阴","weather_id":{"fa":"07","fb":"02"},"wind":"3-4 级","week":"星期二","date":"20180109"}
             * day_20180110 : {"temperature":"7℃~15℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"微风","week":"星期三","date":"20180110"}
             * day_20180111 : {"temperature":"7℃~15℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"微风","week":"星期四","date":"20180111"}
             * day_20180112 : {"temperature":"℃~℃","weather":"","weather_id":{"fa":"99","fb":"99"},"wind":"","week":"星期五","date":"20180112"}
             * day_20180113 : {"temperature":"6℃~9℃","weather":"小雨转阴","weather_id":{"fa":"07","fb":"02"},"wind":"3-4 级","week":"星期六","date":"20180113"}
             * day_20180114 : {"temperature":"7℃~15℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"微风","week":"星期日","date":"20180114"}
             */

            private Day20180108Bean day_20180108;
            private Day20180109Bean day_20180109;
            private Day20180110Bean day_20180110;
            private Day20180111Bean day_20180111;
            private Day20180112Bean day_20180112;
            private Day20180113Bean day_20180113;
            private Day20180114Bean day_20180114;

            public Day20180108Bean getDay_20180108() {
                return day_20180108;
            }

            public void setDay_20180108(Day20180108Bean day_20180108) {
                this.day_20180108 = day_20180108;
            }

            public Day20180109Bean getDay_20180109() {
                return day_20180109;
            }

            public void setDay_20180109(Day20180109Bean day_20180109) {
                this.day_20180109 = day_20180109;
            }

            public Day20180110Bean getDay_20180110() {
                return day_20180110;
            }

            public void setDay_20180110(Day20180110Bean day_20180110) {
                this.day_20180110 = day_20180110;
            }

            public Day20180111Bean getDay_20180111() {
                return day_20180111;
            }

            public void setDay_20180111(Day20180111Bean day_20180111) {
                this.day_20180111 = day_20180111;
            }

            public Day20180112Bean getDay_20180112() {
                return day_20180112;
            }

            public void setDay_20180112(Day20180112Bean day_20180112) {
                this.day_20180112 = day_20180112;
            }

            public Day20180113Bean getDay_20180113() {
                return day_20180113;
            }

            public void setDay_20180113(Day20180113Bean day_20180113) {
                this.day_20180113 = day_20180113;
            }

            public Day20180114Bean getDay_20180114() {
                return day_20180114;
            }

            public void setDay_20180114(Day20180114Bean day_20180114) {
                this.day_20180114 = day_20180114;
            }

            public static class Day20180108Bean {
                /**
                 * temperature : 6℃~12℃
                 * weather : 中雨-大雨转小雨
                 * weather_id : {"fa":"22","fb":"07"}
                 * wind : 4-5 级
                 * week : 星期一
                 * date : 20180108
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanX {
                    /**
                     * fa : 22
                     * fb : 07
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20180109Bean {
                /**
                 * temperature : 6℃~9℃
                 * weather : 小雨转阴
                 * weather_id : {"fa":"07","fb":"02"}
                 * wind : 3-4 级
                 * week : 星期二
                 * date : 20180109
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXX {
                    /**
                     * fa : 07
                     * fb : 02
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20180110Bean {
                /**
                 * temperature : 7℃~15℃
                 * weather : 多云
                 * weather_id : {"fa":"01","fb":"01"}
                 * wind : 微风
                 * week : 星期三
                 * date : 20180110
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXXX {
                    /**
                     * fa : 01
                     * fb : 01
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20180111Bean {
                /**
                 * temperature : 7℃~15℃
                 * weather : 多云
                 * weather_id : {"fa":"01","fb":"01"}
                 * wind : 微风
                 * week : 星期四
                 * date : 20180111
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXXXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXXXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXXXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXXXX {
                    /**
                     * fa : 01
                     * fb : 01
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20180112Bean {
                /**
                 * temperature : ℃~℃
                 * weather :
                 * weather_id : {"fa":"99","fb":"99"}
                 * wind :
                 * week : 星期五
                 * date : 20180112
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXXXXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXXXXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXXXXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXXXXX {
                    /**
                     * fa : 99
                     * fb : 99
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20180113Bean {
                /**
                 * temperature : 6℃~9℃
                 * weather : 小雨转阴
                 * weather_id : {"fa":"07","fb":"02"}
                 * wind : 3-4 级
                 * week : 星期六
                 * date : 20180113
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXXXXXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXXXXXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXXXXXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXXXXXX {
                    /**
                     * fa : 07
                     * fb : 02
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20180114Bean {
                /**
                 * temperature : 7℃~15℃
                 * weather : 多云
                 * weather_id : {"fa":"01","fb":"01"}
                 * wind : 微风
                 * week : 星期日
                 * date : 20180114
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXXXXXXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXXXXXXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXXXXXXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXXXXXXX {
                    /**
                     * fa : 01
                     * fb : 01
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }
        }
    }
}
