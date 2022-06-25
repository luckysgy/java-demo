package com.demo.domain.mediaplay;

import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegLogCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 媒体转换者
 * @author shenguangyang
 *
 */
public abstract class MediaTransfer {
	private static final Logger log = LoggerFactory.getLogger(MediaTransfer.class);
	static {
		avutil.av_log_set_level(avutil.AV_LOG_ERROR);
		FFmpegLogCallback.set();
		log.info("完成加载MediaTransfer");
	}

}
