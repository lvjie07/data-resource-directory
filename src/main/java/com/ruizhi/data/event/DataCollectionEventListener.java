package com.ruizhi.data.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * 数据采集事件监听
 * Created by lvjie on 2020/8/9
 */
@Slf4j
@Component
@EnableAsync
public class DataCollectionEventListener {

    //@Async
    @EventListener
    public void DataCollectionEventListener(DataCollectionEvent event) {
        log.info("DataCollectionEvent flwInfoId:{}",event.getFlwInfoId());
        event.insertDataHandle();
    }
}
