package com.demo;

import com.demo.application.Vcr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author shenguangyang
 * @date 2022-01-01 9:16
 */
@Component
public class InitServer implements ApplicationRunner {
    @Resource
    private Vcr vcr;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        for (int i = 1; i <= 6; i++) {
//            int finalI = i;
//            new Thread(() -> {
//                try {
//                    VcrLogin vcrLogin = VcrLogin.builder()
//                            .ip("xxx").password("xx").username("xx").port(8000)
//                            .build();
//                    vcr.login(vcrLogin);
//                    VcrPlayStart vcrPlayStart = VcrPlayStart.create(finalI + "", new VcrChannel(22), "2021-12-31 10:00:00", "2021-12-31 10:10:00");
//                    vcr.playStart(vcrPlayStart, new PlayCallBack(vcrPlayStart));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//        }
    }
}
