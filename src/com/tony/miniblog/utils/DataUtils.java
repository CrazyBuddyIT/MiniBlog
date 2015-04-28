package com.tony.miniblog.utils;

import com.tony.miniblog.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataUtils {

	public static List<Map<String, Object>> getData(int newsType) {
		List<Map<String, Object>> datas = null;
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (null == datas) {
			datas = produceDatas(0, new ArrayList<Map<String,Object>>());
		}
		return datas;
	}

	static List<Map<String, Object>> produceDatas(int type,
			List<Map<String, Object>> datasList) {
		switch (type) {
		case 0:
			for (int i = 0; i < 15; i++) {
				Map<String, Object> mapDatas = new HashMap<String, Object>();
				//mapDatas.put("blogIcon", convertToDrawableInt(0));
				mapDatas.put("blogIcon", R.drawable.pic1);
				mapDatas.put("blogTitle", "blogTitle[" + i + "]");
				mapDatas.put("blogContent", "这是第<" + i + "> 条热门微博!");
				datasList.add(mapDatas);
			}
			break;
		case 1:
			for (int i = 0; i <15; i++) {
				Map<String, Object> mapDatas = new HashMap<String, Object>();
				//mapDatas.put("blogIcon", convertToDrawableInt(i));
				mapDatas.put("blogIcon", R.drawable.pic2);
				mapDatas.put("blogTitle", "blogTitle[" + i + "]");
				mapDatas.put("blogContent", "这是第<" + i + ">条好友动态!");
				datasList.add(mapDatas);
			}
			break;

			case 2:
			for (int i = 0; i < 15; i++) {
				Map<String, Object> mapDatas = new HashMap<String, Object>();
				mapDatas.put("blogIcon", R.drawable.pic3);
				//mapDatas.put("blogIcon", convertToDrawableInt(i));
				mapDatas.put("blogTitle", "blogTitle[" + i + "]");
				mapDatas.put("blogContent", "与我相关,第<" + i + ">条动态!");
				datasList.add(mapDatas);
			}
			break;

//			case 3:
//			for (int i = 0; i < 15; i++) {
//				Map<String, Object> mapDatas = new HashMap<String, Object>();
//				mapDatas.put("blogIcon", convertToDrawableInt(i));
//				mapDatas.put("blogTitle", "blogTitle[" + i + "]");
//				mapDatas.put("blogContent", ",第<" + i + ">条动态!");
//				datasList.add(mapDatas);
//			}
//			break;

//			case 4:
//			for (int i = 0; i < 15; i++) {
//				Map<String, Object> mapDatas = new HashMap<String, Object>();
//				mapDatas.put("blogIcon", convertToDrawableInt(i));
//				mapDatas.put("blogTitle", "blogTitle[" + i + "]");
//				mapDatas.put("blogContent", "与我相关,第<" + i + ">条动态!");
//				datasList.add(mapDatas);
//			}
//			break;
		}
		return datasList;
	}

	private static Integer convertToDrawableInt(int i) {
		int picInt = 0;
		switch (i) {
		case 0:
			picInt = R.drawable.pic1;
			break;
		case 1:
			picInt = R.drawable.pic2;
			break;
		case 2:
			picInt = R.drawable.pic3;
			break;
		case 3:
			//picInt = R.drawable.pic4;
			break;
		case 4:
			//picInt = R.drawable.pic5;
			break;
		}

		return picInt;
	}

}
