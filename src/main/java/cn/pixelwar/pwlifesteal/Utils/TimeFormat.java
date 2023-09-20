package cn.pixelwar.pwlifesteal.Utils;

public class TimeFormat {

    public String getTimeFormat(int time) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0) {
            return "0秒";
        }
        if (time <= 60) {
            return time + "秒";
        } else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                return minute + "分" + second + "秒";
            } else {
                hour = minute / 60;
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                return hour + "小时" + minute + "分" + second + "秒";
            }
        }
    }

}
