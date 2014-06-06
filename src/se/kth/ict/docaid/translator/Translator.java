package se.kth.ict.docaid.translator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Translator parses a text input and uses MyMemory - Translation Memory api to translate it to swedish.
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 *
 */
public class Translator {

	/**
	 * 
	 * @param from - From language, e.g. "en"
	 * @param to - To language, e.g. "sv"
	 * @param text - the text to translate
	 * @return the translated text
	 */
	public static String translate(String from, String to, String text) {

		String url = "http://api.mymemory.translated.net/api/get?q=" + URLEncoder.encode(text) + "&langpair=" + URLEncoder.encode(from + "|" + to);
		// TODO: the following is not deprecated.
		// String url = "http://api.mymemory.translated.net/api/get?q="+URLEncoder.encode(text, "UTF-8")+"&langpair="+URLEncoder.encode(from+"|"+to, "UTF-8");

		System.out.println(url);
		try {
			return getJSONFromUrl(url).getJSONObject("responseData").get("translatedText").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static JSONObject getJSONFromUrl(String url) {
		InputStream is = null;
		JSONObject jObj = null;
		String json = "";
		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpPost = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
			System.out.println("error on parse data");
		}

		// return JSON String
		return jObj;

	}
}
