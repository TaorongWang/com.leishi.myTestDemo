package cn.joinhealth.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 本类实现项目中常用的公共方法 ，避免重复调用后期修改多处
 */
public class Common {

	// #region static fields
	/** 平台系统数据服务地址 */
	private static String WEBROOTAPI = null;

	// #endregion

	// #region 判断系统运行模式（是否为调试模式）
	/** 是否为调试模式 */
	private static volatile Boolean _isDebug = null;

	/** 是否为调试模式 */
	public static Boolean isDebug() {
		// 没有读取过配置
		if (_isDebug == null) {
			// 只读取一次
			synchronized (Common.class) {
				if (_isDebug == null) {
					ResourceBundle rb = ResourceBundle.getBundle("config");
					if (rb.containsKey("isdebug")) {
						if ("true".equals(rb.getString("isdebug").trim().toLowerCase())) {
							_isDebug = true;
						} else {
							_isDebug = false;
						}
					} else {
						_isDebug = false;
					}
				}
			}
		}
		return _isDebug;
	}
	// #endregion

	// #region 无人值守项目数据缓存时间（秒）10或20或30分钟
	/**
	 * 无人值守项目数据缓存时间（秒）10或20或30分钟
	 * 
	 * @return
	 */


	// #endregion

	// #region getDateTimeString 获取当前日期字符串（yyyy-MM-dd HH:mm:ss）
	/** 获取当前日期字符串（yyyy-MM-dd HH:mm:ss） */
	public static String getDateTimeString() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}

	public static String getDateTimeString(Date d) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(d);
	}

	// #endregion

	// #region getDateString 获取当前日期字符串（yyyy-MM-dd）
	public static String getDateString() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}

	public static String getDateString(String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}

	public static String getDateString(Date d) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(d);
	}

	public static String getDateString(Date d, String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(d);
	}

	// #endregion

	// #region 文件大小转成字符串显示
	public static String fileSizeToString(long size) {
		Double d = size * 1.0;
		BigDecimal bd;
		if (d.longValue() < 1024) {
			return d + "B";
		}
		d = d / 1024;
		bd = new BigDecimal(d);
		d = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
		if (d < 1024) {
			return d + "KB";
		}
		d = d / 1024;
		bd = new BigDecimal(d);
		d = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
		if (d < 1024) {
			return d + "MB";
		}
		d = d / 1024;
		bd = new BigDecimal(d);
		d = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
		if (d < 1024) {
			return d + "GB";
		}
		d = d / 1024;
		bd = new BigDecimal(d);
		d = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
		if (d < 1024) {
			return d + "TB";
		}
		d = d / 1024;
		bd = new BigDecimal(d);
		d = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
		return d + "PB";
	}

	// #endregion

	// #region 时间毫秒数转成中文显示格式（d天h小时m分钟m秒）
	public static String timeCountToCN(long l) {
		// l:毫秒数
		String strOnline = "";
		long tmp = 0;
		// 天数
		if (l > 1000 * 60 * 60 * 24) {
			tmp = l / (1000 * 60 * 60 * 24);
			strOnline = tmp + "天";
			l = l - tmp * (1000 * 60 * 60 * 24);
		}
		// 小时
		if (l > 1000 * 60 * 60) {
			tmp = l / (1000 * 60 * 60);
			strOnline += tmp + "小时";
			l = l - tmp * (1000 * 60 * 60);
		}
		// 分钟
		if (l > 1000 * 60) {
			tmp = l / (1000 * 60);
			strOnline += tmp + "分钟";
			l = l - tmp * (1000 * 60);
		}
		// 秒
		strOnline += (l / 1000 + 1) + "秒";
		return strOnline;
	}

	// #endregion

	// #region writeString 输出内容
	public static void writeString(HttpServletResponse response, String str) {
		try {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// #endregion

	// #region response.writeString 输出内容
	/**
	 * response.writeString 输出内容
	 * 
	 * @param response
	 * @param str
	 * @param request
	 *            支持ajax跨域调用，jsonpCallback参数名：callback
	 */
	public static void writeString(HttpServletResponse response, String str, HttpServletRequest request) {
		try {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			String callback = request.getParameter("callback");
			if (callback != null && callback.isEmpty() == false)
				out.print(callback + "(" + str + ")");
			else
				out.print(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// #endregion
	// #region writeJson 输出JSON内容
	public static void writeJson(HttpServletResponse response, Object object) {
		// String str = JSON.toJSONStringWithDateFormat(object,
		// "yyyy-MM-dd HH:mm:ss");
		String str = JSON.toJSONString(object);
		try {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// #endregion

	// #region writeJson 输出JSON内容
	public static void writeJsonWithDateFormat(HttpServletResponse response, Object object) {
		writeJsonWithDateFormat(response, object, "yyyy-MM-dd HH:mm:ss");
	}

	// #endregion

	// #region writeJson 输出JSON内容
	public static void writeJsonWithDateFormat(HttpServletResponse response, Object object, String dateFormat) {
		String str = JSON.toJSONStringWithDateFormat(object, dateFormat);
		try {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// #endregion

	// #region getWebRootAPI
	public static String getWebRootAPI() {
		if (WEBROOTAPI != null)
			return WEBROOTAPI;
		ResourceBundle rb = ResourceBundle.getBundle("config");
		if (rb != null && rb.getString("webRootAPI") != null)
			WEBROOTAPI = rb.getString("webRootAPI");
		return WEBROOTAPI;
	}

	// #endregion

	private static String _httpGet(String url, int readTimeout) {
		String result = "";
		try {
			URL _url = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
			// conn.setRequestProperty("Accept-Charset", "UTF-8");
			// conn.setRequestProperty("Content-Type","text/plain;charset=utf-8");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setReadTimeout(readTimeout); // 读取超时时间3秒
			conn.setConnectTimeout(30000);// 链接超时时间30秒
			conn.connect();
			if (200 == conn.getResponseCode()) {
				InputStream inputStream = conn.getInputStream();
				result = readInputStream(inputStream);
			}
			conn.disconnect();
		} catch (Exception e) {

		}
		return result;
	}
	// #endregion

	// #region http read inputStream
	public static String readInputStream(InputStream inputStream) throws Exception {
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String str = bufferedReader.readLine();
		StringBuffer sb = new StringBuffer();
		while (str != null) {
			sb.append(str);
			str = bufferedReader.readLine();
		}
		bufferedReader.close();
		inputStreamReader.close();
		inputStream.close();
		inputStream = null;
		return sb.toString();
	}

	// #endregion
	// #region httpPost
	public static String httpPost(String url, String data) throws Exception {
		String result = _httpPost(url, data, 6000);
		// if (IString.isNullOrEmpty(result))
		// result = _httpPost(url, data, 6000);
		return result;
	}

	public static String httpPost(String url, String data, Map<String, String> header) throws Exception {
		String result = _httpPost(url, data, 6000, header);
		// if (IString.isNullOrEmpty(result))
		// result = _httpPost(url, data, 6000);
		return result;
	}

	public static String httpPost(String url, String data, int readTimeout) throws Exception {
		return _httpPost(url, data, readTimeout);
	}

	public static String httpPost(String url, String data, int readTimeout, Map<String, String> header)
			throws Exception {
		return _httpPost(url, data, readTimeout, header);
	}

	private static String _httpPost(String url, String data, int readTimeout) {
		return _httpPost(url, data, readTimeout, null);
	}

	private static String _httpPost(String url, String data, int readTimeout, Map<String, String> header) {
		String result = "";
		try {
			// 创建url
			URL _url = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
			// conn.setRequestProperty("Content-Type","text/plain;charset=utf-8");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			if(null !=header && !header.isEmpty()){
				Iterator<String> keys = header.keySet().iterator();
				while(keys.hasNext()){
					String key = keys.next();
					conn.addRequestProperty(key, header.get(key));
				}
			}
			conn.setRequestMethod("POST");
			conn.setReadTimeout(readTimeout); // 读取超时时间3秒
			conn.setConnectTimeout(30000);// 链接超时时间30秒
			conn.connect();// 打开链接
			// 发送数据
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes("UTF-8"));
			out.flush();
			out.close();
			if (200 == conn.getResponseCode()) {
				InputStream inputStream = conn.getInputStream();
				result = readInputStream(inputStream);
			}
			conn.disconnect();
			// 字符串首尾都有个引号，这里先行处理掉
			if (result.indexOf("\"") == 0 && result.lastIndexOf("\"") == result.length() - 1)
				result = result.substring(1, result.length() - 1);
		} catch (Exception e) {

		}
		return result;
	}
	// #endregion

	// #region getRequestString
	public static String getRequestString(HttpServletRequest request) throws Exception {
		// 获取返回
		InputStream inputStream = request.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		StringBuffer sb = new StringBuffer();
		String str;
		while ((str = bufferedReader.readLine()) != null) {
			sb.append(str);
		}
		bufferedReader.close();
		inputStreamReader.close();
		inputStream.close();
		inputStream = null;
		String strResult = sb.toString();
		// 字符串首尾都有个引号，这里先行处理掉
		if (strResult.indexOf("\"") == 0 && strResult.lastIndexOf("\"") == strResult.length() - 1)
			strResult = strResult.substring(1, strResult.length() - 1);
		return strResult;
	}

	// #endregion

	// #region getRequestJSONObject
	public static JSONObject getRequestJSONObject(HttpServletRequest request) throws Exception {
		String str = getRequestString(request);
		JSONObject json = JSON.parseObject(str);
		return json;
	}

	// #endregion

	// #region getRequestString
	public synchronized static Map<String, String> getRequestParams(HttpServletRequest request) throws Exception {
		Map<String, String> params = new HashMap<String, String>();

		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		return params;
	}
	// #endregion
}
