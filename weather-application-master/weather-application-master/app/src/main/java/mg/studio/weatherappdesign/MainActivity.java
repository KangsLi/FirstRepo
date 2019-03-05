package mg.studio.weatherappdesign;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.lang.String;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ((TextView) findViewById(R.id.tv_date)).setText( sdf.format(new Date()));
        ((TextView) findViewById(R.id.topView)).setText(getWeek());
        new DownloadUpdate().execute();
    }

    public void btnClick(View view) {
        new DownloadUpdate().execute();
        Toast.makeText(this,"Weather updated",Toast.LENGTH_LONG).show();
    }
    public String getWeek(){
        Calendar cal = Calendar.getInstance();
        int i = cal.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
            default:
                return "";
        }
    }

    private class DownloadUpdate extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = "http://t.weather.sojson.com/api/weather/city/101043700";
            HttpURLConnection urlConnection = null;
            BufferedReader reader;

            try {
                URL url = new URL(stringUrl);

                // Create the request to get the information from the server, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Mainly needed for debugging
                   buffer.append(line);
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                //The temperature
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String forecast) {
            //Update the temperature displayed
            String temperature=null;
            String tmp1=null;
            String tmp2=null;
            try{
                JSONObject json=new JSONObject(forecast);
                JSONObject data=json.getJSONObject("data");
                temperature=data.optString("wendu");
                JSONArray weather=data.getJSONArray("forecast");
                JSONObject msg=weather.getJSONObject(0);
                String type=msg.optString("type");
                ((ImageView) findViewById(R.id.img_weather_condition)).setImageResource(selPicture(type));
                msg=weather.getJSONObject(1);
                String day1=msg.optString("week");
                String type1=msg.optString("type");
                tmp1=msg.optString("low");
                tmp2=msg.optString("high");
                ((TextView) findViewById(R.id.tmp1)).setText(tmp1.substring(tmp1.indexOf(" ")+1,tmp1.indexOf("℃"))+"-"+tmp2.substring(tmp2.indexOf(" "+1)));
                msg=weather.getJSONObject(2);
                String day2=msg.getString("week");
                String type2=msg.optString("type");
                tmp1=msg.optString("low");
                tmp2=msg.optString("high");
                ((TextView) findViewById(R.id.tmp2)).setText(tmp1.substring(tmp1.indexOf(" ")+1,tmp1.indexOf("℃"))+"-"+tmp2.substring(tmp2.indexOf(" "+1)));
                msg=weather.getJSONObject(3);
                String day3=msg.getString("week");
                String type3=msg.optString("type");
                tmp1=msg.optString("low");
                tmp2=msg.optString("high");
                ((TextView) findViewById(R.id.tmp3)).setText(tmp1.substring(tmp1.indexOf(" ")+1,tmp1.indexOf("℃"))+"-"+tmp2.substring(tmp2.indexOf(" "+1)));
                msg=weather.getJSONObject(4);
                String day4=msg.getString("week");
                String type4=msg.optString("type");
                tmp1=msg.optString("low");
                tmp2=msg.optString("high");
                ((TextView) findViewById(R.id.tmp4)).setText(tmp1.substring(tmp1.indexOf(" ")+1,tmp1.indexOf("℃"))+"-"+tmp2.substring(tmp2.indexOf(" "+1)));
                ((ImageView) findViewById(R.id.Picf)).setImageResource(selPicture(type1));
                ((ImageView) findViewById(R.id.Pics)).setImageResource(selPicture(type2));
                ((ImageView) findViewById(R.id.Pict)).setImageResource(selPicture(type3));
                ((ImageView) findViewById(R.id.Pic4)).setImageResource(selPicture(type4));
                ((TextView) findViewById(R.id.d1)).setText(selDay(day1));
                ((TextView) findViewById(R.id.d2)).setText(selDay(day2));
                ((TextView) findViewById(R.id.d3)).setText(selDay(day3));
                ((TextView) findViewById(R.id.d4)).setText(selDay(day4));
            }
            catch(JSONException e)
            {
                Log.i("nothing",e.toString());
            }


            ((TextView) findViewById(R.id.temperature_of_the_day)).setText(temperature);
        }
        public int selPicture(String type){
            int nID=0;
            switch(type) {
                case "晴":
                    nID=R.drawable.sunny_small;
                    break;
                case "小雨":
                    nID=R.drawable.rainy_small;
                    break;
                case "多云":
                    nID=R.drawable.partly_sunny_small;
                    break;
                case "阴":
                    nID=R.drawable.overcast;
                    break;
            }
            return nID;
        }
        public String selDay(String day){
            switch (day){
                case "星期一":
                    return "MON";
                case "星期二":
                    return "TUE";
                case "星期三":
                    return "WED";
                case "星期四":
                    return "THU";
                case "星期五":
                    return "FRI";
                case "星期六":
                    return "SAT";
                case "星期日":
                    return "SUN";
            }
            return "";
        }
    }
}
