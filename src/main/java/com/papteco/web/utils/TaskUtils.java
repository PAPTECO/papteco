package com.papteco.web.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskUtils {

	public static final String STUS_START = "START";
	public static final String STUS_SUCC = "SUCC";
	public static final String STUS_FAIL = "FAIL";

	private static Map<String, String[]> taskmap = new ConcurrentHashMap<String, String[]>();

	public TaskUtils() {
	}

	public static String genTaskId() {
		return java.util.UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String[] getTaskStatus(String taskid) {
		return taskmap.remove(taskid);
	}

	public static void setTaskStatus(String taskid, String status, String msg) {
		String[] value = { status, msg };
		taskmap.put(taskid, value);
	}
}
