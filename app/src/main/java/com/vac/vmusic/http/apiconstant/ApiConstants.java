package com.vac.vmusic.http.apiconstant;

/**
 * Created by vac on 16/10/15.
 *
 */
public class ApiConstants {

    /**搜索的BASE URL*/
    public static final String SEARCH_BASE= "http://search.dongting.com/";


    public static String getBaseUrl(int hostType){
        switch (hostType){
            case  HostType.HOST_TYPE_SEARCH:
                return SEARCH_BASE;
        }
        return "";
    }

}
