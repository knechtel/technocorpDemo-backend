package util;


import com.google.gson.JsonElement;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public  interface ApiIntinerario {


    @GET("http://www.poatransporte.com.br/php/facades/process.php")
    public Call<ResponseBody> doBoleto(@Query("a") String a, @Query("p") Integer p);
}
