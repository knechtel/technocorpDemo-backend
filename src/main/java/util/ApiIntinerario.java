package util;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;



public  interface ApiIntinerario {

    @GET("http://www.poatransporte.com.br/php/facades/process.php")
    public Call<ResponseBody> getIntinerario(@Query("a") String a, @Query("p") Integer p);

    @GET("http://www.poatransporte.com.br/php/facades/process.php")
    public Call<ResponseBody> getAllLinhas(@Query("a") String a,@Query("t")String t);
}
