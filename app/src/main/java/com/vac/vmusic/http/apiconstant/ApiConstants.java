package com.vac.vmusic.http.apiconstant;

/**
 * Created by vac on 16/10/15.
 *
 */
public class ApiConstants {

    /**搜索的BASE URL*/
    public static final String SEARCH_BASE= "http://search.dongting.com/";

    /**其他API接口*/
    public static final String API_BASE = "http://api.dongting.com/";

    /**天天POD**/
    public static final String API_TTPOD = "http://api.songlist.ttpod.com/";

    public static String getBaseUrl(int hostType){
        switch (hostType){
            case  HostType.HOST_TYPE_SEARCH:
                return SEARCH_BASE;
            case HostType.HOST_TYPE_WALLPAPER:
                return API_BASE;
            case HostType.HOST_TYPE_TTPOD:
                return API_TTPOD;
        }
        return "";
    }

}
