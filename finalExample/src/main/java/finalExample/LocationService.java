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

public final class LocationService {
  public static final String API_URL = "http://ip-api.com";

  public static class LocationResponse {
	    public final String country;
	    public final String city;
    
	public LocationResponse(
			String country, 
			String city) {
		this.country = country;
		this.city = city;

	}
  }

  public interface IpApi {
    @GET("/json")
    Call<LocationResponse> byIp();
  }

  public static String[] myLocation() throws IOException {
    // Create a very simple REST adapter which points the GitHub API.
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    IpApi location = retrofit.create(IpApi.class);
    Call<LocationResponse> call = location.byIp();
    LocationResponse response = call.execute().body();
    
    String []loc = new String[2];
    loc[0]= response.city;
    loc[1]= response.country;
    return loc;
 
  }
}
