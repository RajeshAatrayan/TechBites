package ibrickedlabs.com.techbites;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private static final String TAG = "QueryUtils";

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, "Problem building the URL ", e);
        }

        return url;
    }

    public static List<News> fetchNewsData(String stringurl) {
        //Create URL
        URL url = createUrl(stringurl);
        //perform http request to the URL obtained and get the json response back

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Problem making the HTTP request.", e);

        }

        //Extract the relevant json response from the string
        List<News> list = extractFromJsonResponse(jsonResponse);


        return list;
    }

    //For extracting the json response
    private static List<News> extractFromJsonResponse(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        List<News> list = new ArrayList<>();
        try {
            JSONObject baseObject = new JSONObject(jsonResponse);

            JSONArray articlesArray = baseObject.getJSONArray("articles");

            for (int i = 0; i < articlesArray.length(); i++) {
                JSONObject currentJsonObject = (JSONObject) articlesArray.get(i);
                JSONObject srcObject = currentJsonObject.getJSONObject("source");
                String srcName = srcObject.getString("name");
                String author = currentJsonObject.getString("author");
                String title = currentJsonObject.getString("title");
                String url = currentJsonObject.getString("url");
                String imageUrl = currentJsonObject.getString("urlToImage");
                String publishedAt = currentJsonObject.getString("publishedAt");
                String content = currentJsonObject.getString("content");
                int ind = content.indexOf("...");
                Log.e("found"," "+ind);
                //  content=content.substring(0,ind);


                list.add(new News(srcName, author, title, url, imageUrl, content, publishedAt));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;

        }
        HttpURLConnection mHttpURLConnection = null;
        InputStream inputStream = null;

        try {
            mHttpURLConnection = (HttpURLConnection) url.openConnection();
            mHttpURLConnection.setReadTimeout(10000);
            mHttpURLConnection.setConnectTimeout(15000);
            mHttpURLConnection.setRequestMethod("GET");
            mHttpURLConnection.connect();

            if (mHttpURLConnection.getResponseCode() == 200) {
                inputStream = mHttpURLConnection.getInputStream();
                jsonResponse = ReadFromInputStream(inputStream);
            } else {
                Log.e(TAG, "Error in response code: " + mHttpURLConnection.getResponseCode());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mHttpURLConnection != null) {
                mHttpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;

    }

    private static String ReadFromInputStream(InputStream inputStream) throws IOException {
        StringBuffer buffer = new StringBuffer();
        if (inputStream != null) {
            InputStreamReader mInputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader mBufferedReader = new BufferedReader(mInputStreamReader);
            try {
                String line = mBufferedReader.readLine();
                while (line != null) {
                    buffer.append(line);
                    line = mBufferedReader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                mInputStreamReader.close();
                mBufferedReader.close();
            }
        }

        return buffer.toString();
    }


}
