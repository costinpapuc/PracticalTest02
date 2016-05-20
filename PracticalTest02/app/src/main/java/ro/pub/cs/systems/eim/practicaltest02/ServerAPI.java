package ro.pub.cs.systems.eim.practicaltest02;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by Jorj on 03-Apr-16.
 */
public interface ServerAPI {

    @GET("/utc/now?\\I.\\M")
    Call<ResponseBody> getTime();
}
