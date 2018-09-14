package majunbao.bwie.com.jingdong_base_marster.utils;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import majunbao.bwie.com.jingdong_base_marster.base.BaseApp;

public class SharedPreferencesUtils {
    private static final String FILE_NAME = "share_date";
    public static void setParam( String key, Object object){
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = BaseApp.mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if("String".equals(type)){
            editor.putString(key, (String)object);
        }
        else if("Integer".equals(type)){
            editor.putInt(key, (Integer)object);
        }
        else if("Boolean".equals(type)){
            editor.putBoolean(key, (Boolean)object);
        }
        else if("Float".equals(type)){
            editor.putFloat(key, (Float)object);
        }
        else if("Long".equals(type)){
            editor.putLong(key, (Long)object);
        }
        editor.commit();
    }
    public static Object getParam(String key, Object defaultObject){
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = BaseApp.mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if("String".equals(type)){
            return sp.getString(key, (String)defaultObject);
        }
        else if("Integer".equals(type)){
            return sp.getInt(key, (Integer)defaultObject);
        }
        else if("Boolean".equals(type)){
            return sp.getBoolean(key, (Boolean)defaultObject);
        }
        else if("Float".equals(type)){
            return sp.getFloat(key, (Float)defaultObject);
        }
        else if("Long".equals(type)){
            return sp.getLong(key, (Long)defaultObject);
        }
        return null;
    }

    public static String getValue(String key,String defaultObject){
        SharedPreferences sp = BaseApp.mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key,defaultObject);
    }

    public static void putValue(String key,String defaultObject){
        SharedPreferences sp = BaseApp.mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    sp.edit().putString(key,defaultObject).commit();
    }
}
