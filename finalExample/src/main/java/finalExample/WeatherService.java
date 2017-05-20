/*
 * Copyright (C) 2012 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package finalExample;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public final class WeatherService {
  public static final String API_URL = "http://api.openweathermap.org";

  public static class WeatherResponse {
    public final Map<String,String> main;

    
	public WeatherResponse(
			Map<String, String> main) {
		this.main = main;


	}


  }

  public interface OpenWeatherMap {
    @GET("/data/2.5/weather")
    Call<WeatherResponse> byCity(
        @Query("q") String cityName,
        @Query("APPID") String apiKey);
  }

  public static String getWeather(String city) throws IOException {
    // Create a very simple REST adapter which points the GitHub API.
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    // Create an instance of our GitHub API interface.
    OpenWeatherMap weather = retrofit.create(OpenWeatherMap.class);

    // Create a call instance for looking up Retrofit contributors.
    Call<WeatherResponse> call = weather.byCity(city, "f2fd16c6b8824df5fcbe2102ac786544");

    // Fetch and print a list of the contributors to the library.
    WeatherResponse response = call.execute().body();
    
    return response.main.get("temp");
  }
}
