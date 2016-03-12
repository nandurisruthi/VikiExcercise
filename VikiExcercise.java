import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class VikiExcercise {

	public static void main(String[] args) throws Exception {

		String baseUrl = "http://api.viki.io/v4/videos.json?app=100250a&per_page=10&page=";
		int pageCount = 0;
		int trueCount = 0;
		int falseCount = 0;
		boolean isMore = true;
		HttpClient httpClient;
		
		do {
			pageCount++;
			String url = baseUrl + pageCount;
			System.out.println("processing url :" + url);
			HttpGet httpget = new HttpGet(url);
		
			httpClient = HttpClientBuilder.create().build();
			HttpResponse httpResponse = httpClient.execute(httpget);
	
			String jsonResponse = IOUtils.toString(httpResponse.getEntity().getContent());
			
			JSONObject obj = new JSONObject(jsonResponse);
			
		    isMore = obj.getBoolean("more");

			JSONArray j = obj.getJSONArray("response");
			for (int i = 0; i < j.length(); i++) {
				boolean b = j.getJSONObject(i).getJSONObject("flags").getBoolean("hd");
				if (b)
					trueCount++;
				else
					falseCount++;
			}	
			System.out.println("done processing url : " + url);

		} while (isMore);
		
		System.out.println("trueCount is  " + trueCount);
		System.out.println("falseCount is  " + falseCount);
	}

}
