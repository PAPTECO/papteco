package com.papteco.web.utils;

import java.lang.reflect.Method;

public class BeanUtil {
	public static void bean2Bean(Object frm, Object dest) {
		try {
			// 取得frm对象中的方法的集合
			Method[] argsFrmMtd = frm.getClass().getDeclaredMethods();
			// 取得dest对象中方法的集合
			Method[] argsDestMtd = dest.getClass().getDeclaredMethods();

			// frm中方法名称
			String frmMtdName;
			// 方法描述 包括修饰符、返回值、方法名、参数
			String frmGeneric;
			// 返回值类型
			String returnType;

			// dest中方法名称
			String destMtdName;
			// 方法对应的对象名称
			String destFieldName;
			// 方法描述 包括修饰符、返回值、方法名、参数
			String destGeneric;
			// 参数类型
			String paramType;

			// 便利dest对象中的方法
			for (Method destMtd : argsDestMtd) {
				destMtdName = destMtd.getName(); // 获得方法名称

				if (destMtdName.startsWith("set")) { // 判断方法是否为setter方法
					destFieldName = destMtdName.replace("set", ""); // 取得变量名称（即去掉setter方法中的“set”）
					destGeneric = destMtd.toGenericString(); // 取得方法描述
																// 包括修饰符、返回值、方法名、参数
					paramType = destGeneric.split(destMtdName)[1]; // 取得方法名称之后的字符串
					paramType = paramType.substring(1, paramType.length() - 1); // 去掉左右括号
																				// 得到参数类型

					// 便利frm对象中的方法
					for (Method frmMtd : argsFrmMtd) {
						frmMtdName = frmMtd.getName(); // 获得方法名称

						// 判断frmMtdName方法是否为getter方法并且变量名称(第一个字母)等于frm中的对象名称
						if (frmMtdName.startsWith("get")
								&& frmMtdName.replace("get", "").equals(
										destFieldName)) {
							frmGeneric = frmMtd.toGenericString(); // 获得getter方法的返回值
							// 取得以空格split后数组倒数第一个字符串，即方法的返回值
							returnType = frmGeneric.split(" ")[frmGeneric
									.split(" ").length - 2];

							// 判断frm中getter方法的返回值类型是否等于dest对象中变量的setter方法的参数类型
							if (paramType != null
									&& paramType.equals(returnType)) {
								// 将frm对象中getter方法的返回值为参数调用dest对象中同名变量的setter方法
								destMtd.invoke(dest,
										frmMtd.invoke(frm, (Object[]) null));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
