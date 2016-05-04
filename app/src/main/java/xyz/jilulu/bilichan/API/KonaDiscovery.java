package xyz.jilulu.bilichan.API;

import retrofit2.Call;
import retrofit2.http.GET;
import xyz.jilulu.bilichan.Models.ExploreItems;

/**
 * Created by jamesji on 28/4/2016.
 */
public interface KonaDiscovery {
    @GET("/post.json?tags=holds:false%20%20limit:100&api_version=2&filter=1&include_tags=1&include_votes=1&include_pools=1")
    Call<ExploreItems> getExplorePosts();
}
