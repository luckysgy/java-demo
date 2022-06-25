package com.demo.domain.mediaplay;

import com.concise.component.core.exception.BizException;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author shenguangyang
 * @date 2022-01-04 19:41
 */
public class HikVcrPullRtspUrl extends MediaPullUrl {
    private Integer channel;
    private String ip;
    private Integer port;
    private String username;
    private String password;


    private String pullRtspUrl;
    public HikVcrPullRtspUrl(Integer channel, String ip, Integer port, String username, String password) {
        this.ip = ip;
        this.channel = channel;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public void generatePullRtspUrl(String startTimeStr, String endTimeStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startTime = format.parse(startTimeStr).getTime();
        long endTime = format.parse(endTimeStr).getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(startTime));
        String vclStartTime = getVcrTime(calendar);

        calendar.setTime(new Date(endTime));
        String vclEndTime = getVcrTime(calendar);
        this.pullRtspUrl = "rtsp://" + username + ":" + password + "@" + ip + ":" + port + "/Streaming/tracks/" + channel + "01?"
                + "starttime=" + vclStartTime + "&endtime=" + vclEndTime;
    }

    private String getVcrTime(Calendar calendar) {
        return zeroPadding(calendar.get(Calendar.YEAR))
                + zeroPadding(calendar.get(Calendar.MONTH))
                + zeroPadding(calendar.get(Calendar.DAY_OF_MONTH)) + "T"
                + zeroPadding(calendar.get(Calendar.HOUR))
                + zeroPadding(calendar.get(Calendar.MINUTE))
                + zeroPadding(calendar.get(Calendar.SECOND)) + "Z";
    }

    private String zeroPadding(int time) {
        if (time < 10) {
            return "0" + time;
        }
        return time + "";
    }

    @Override
    String getUrl() {
        if (StringUtils.isEmpty(pullRtspUrl)) {
            throw new BizException("pullRtspUrl为空");
        }
        return this.pullRtspUrl;
    }

//    public static void main(String[] args) throws ParseException {
//        HikVcrPullRtspUrl mediaPullUrl = new HikVcrPullRtspUrl(1, "111", 12, "admin",
//                "123123");
//        mediaPullUrl.generatePullRtspUrl("2021-12-31 10:00:00", "2021-12-31 10:10:00");
//        System.out.println(mediaPullUrl.getUrl());
//    }
}
