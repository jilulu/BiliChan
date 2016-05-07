package com.jilulux.bilichan.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import com.jilulux.bilichan.Models.KonaTag;
/**
 * Created by jamesji on 28/4/2016.
 */
public interface KonaCharacter {
    @GET("tag.json?type=4&order=count")
    Call<List<KonaTag>> listChars(@Query("name") String character);
}
