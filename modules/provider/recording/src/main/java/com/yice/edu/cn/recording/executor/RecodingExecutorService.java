package com.yice.edu.cn.recording.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecodingExecutorService {
	
	public final static ExecutorService executorService = Executors.newFixedThreadPool(30);
}
