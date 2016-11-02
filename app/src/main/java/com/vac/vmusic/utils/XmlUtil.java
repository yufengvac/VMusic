package com.vac.vmusic.utils;

import com.vac.vmusic.beans.lyric.LyricXml;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

/**
 * Created by vac on 16/11/2.
 *
 */
public class XmlUtil {
    public static Object getXMLObject(String result,Class<?> clazz){
        if (clazz.equals(String.class)){
            int startResult = result.indexOf(">")+1;
            int endResult = result.indexOf("</");
            return result.substring(startResult,endResult);
        }
        Object o = null;
        Strategy strategy = new AnnotationStrategy();
        Serializer serializer = new Persister(strategy);
        try {
            o = serializer.read(clazz,result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return o;
    }
}
