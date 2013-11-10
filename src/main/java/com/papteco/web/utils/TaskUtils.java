package com.papteco.web.utils;

import java.util.HashMap;
import java.util.Map;

public class TaskUtils {
	
	public static final String STUS_START = "START"; 
	public static final String STUS_SUCC = "SUCC"; 
	public static final String STUS_FAIL = "FAIL"; 
	
	private static Map<String, String[]> taskmap;
	
	public TaskUtils(){
		taskmap = new HashMap<String, String[]>();
	}

	public static String genTaskId(){
		return java.util.UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static String[] getTaskStatus(String taskid){
		return taskmap.get(taskid);
	}
	
	public static void setTaskStatus(String taskid, String status, String msg){
		String[] value = {status,msg};
		taskmap.put(taskid, value);
	}
}
