package com.git.cloud.rest.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestMethod;



public class HttpRestClient extends RestClient {

	@Override
	protected RestResult restService(RestModel restModel) throws Exception {
		URL restServiceURL = new URL(restModel.getTargetURL());
		HttpURLConnection connection = (HttpURLConnection) restServiceURL.openConnection();
		connection.setRequestMethod(restModel.getRequestMethod());
		this.setRequestProperty(connection, restModel.getHeader());
		if(!restModel.getRequestMethod().equals(RequestMethod.GET.name())) {
			connection.setDoOutput(true);
			if (restModel.getRequestJosnData() != null) {
				OutputStream outStream = connection.getOutputStream(); // 返回写入到此连接的输出流
				outStream.write(restModel.getRequestJosnData().getBytes());
				if (outStream != null) {
					outStream.close(); // 关闭流
				}
			}
		}
		boolean flag = true;
		if("false".equals(restModel.getSych()) && restModel.getSych() != null){
			flag = false;
		}
		StringBuffer resultData = new StringBuffer();
		if(flag){
			// HTTP服务端返回的编码是UTF-8,故必须设置为UTF-8,保持编码统一,否则会出现中文乱码
			BufferedReader in = new BufferedReader(new InputStreamReader((InputStream) connection.getInputStream(), "UTF-8"));
			if(in != null) {
				String output;
				while ((output = in.readLine()) != null) {
					resultData.append(output);
				}
				in.close();
			}
		}
		/*BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((connection.getInputStream())));
		StringBuffer resultData = new StringBuffer();
		String output;
		while ((output = responseBuffer.readLine()) != null) {
			resultData.append(output);
		}*/
//		System.out.println("restService result:" + resultData.toString());
		if(connection != null) {
			connection.disconnect();
		}
		RestResult restResult = new RestResult();
		restResult.setResponseCode(connection.getResponseCode());
		restResult.setMessage(connection.getResponseMessage());
		restResult.setHeaderMap(connection.getHeaderFields());
		restResult.setJsonData(resultData.toString());
		return restResult;
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	private void setRequestProperty(HttpURLConnection connection, Map<String, String> headerMap) {
		if (headerMap == null || headerMap.size() == 0) {
			return;
		}
		Set<String> set = headerMap.keySet();
		Iterator it = set.iterator();
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			connection.setRequestProperty(key, headerMap.get(key));
		}
	}
}
