package com.idohoo.feign;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by henser on 17-5-31.
 */
public class FeignHeaderMap {

    public final static String key = "traceId-%s-url-%s";

    private FeignHeaderMap feignHeaderMap = new FeignHeaderMap();

    private static Map<String,String> map = new HashMap<String,String>();

    public static Map<String,String> initMap() {

        if(map == null) {

            synchronized(FeignHeaderMap.class){
                if(map==null){
                    map = new HashMap<String,String>();
                }
            }

        }

        return map;

    }


}
