package com.bwie.majunbao.common;

public class Api {
    //判断网址是否是正式服
    public static String Product_url=Constants.isRelease==true?Constants.BASE_URL:Constants.BASE_DEBUG_URL;
}
