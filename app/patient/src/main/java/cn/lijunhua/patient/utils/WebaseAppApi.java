package cn.lijunhua.patient.utils;

import cn.lijunhua.patient.config.WebaseAppConfig;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Map;

public class WebaseAppApi {
    private static String md5Encrypt(long timestamp, String appKey, String appSecret) {
        try {
            String dataStr = timestamp + appKey + appSecret;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte s[] = m.digest();
            String result = "";
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            return result.toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 利用java原生的摘要实现SHA256加密
     * @param str 加密后的报文
     * @return
     */
    private static String getSHA256StrJava(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    public static Object newUser(WebaseAppConfig config, int groupId, String phone, String email) throws Exception {
        Map<String, Object> headerMap = Maps.newHashMap();
        headerMap.put("Content-Type","application/json");
        Map<String, Object> accountAddParams= Maps.newHashMap();
        accountAddParams.put("account",phone);
        accountAddParams.put("accountPwd",getSHA256StrJava("Simmed2022"));
        accountAddParams.put("roleId","100002");
        accountAddParams.put("email",email);
        Timestamp scurrtest = new Timestamp(System.currentTimeMillis());
        System.out.println("scurrtest = "+scurrtest);
        long now = scurrtest.getTime();// 直接转换成long
        String md5Encrypt=md5Encrypt(now,config.getAppKey(),config.getAppSecret());
        String url=config.getNodeManagerUrl()+"/WeBASE-Node-Manager/api/accountAdd?timestamp="+now+"&appKey="+config.getAppKey()+"&signature="+md5Encrypt;
        String result=OkHttpRequestUtils.doPost(url,headerMap,accountAddParams);
        JSONObject accountInfo = JSONObject.parseObject(result);
        if(accountInfo.getIntValue("code")==0){
            Map<String, Object> newUserParams= Maps.newHashMap();
            newUserParams.put("groupId",groupId);
            newUserParams.put("userName",phone);
            newUserParams.put("description","");
            newUserParams.put("account",phone);
            scurrtest = new Timestamp(System.currentTimeMillis());
            System.out.println("scurrtest = "+scurrtest);
            now = scurrtest.getTime();// 直接转换成long
            md5Encrypt=md5Encrypt(now,config.getAppKey(),config.getAppSecret());
            url=config.getNodeManagerUrl()+"/WeBASE-Node-Manager/api/newUser?timestamp="+now+"&appKey="+config.getAppKey()+"&signature="+md5Encrypt;
            JSONObject newUserParamsInfo=JSONObject.parseObject(OkHttpRequestUtils.doPost(url,headerMap,newUserParams));
            if(newUserParamsInfo.getIntValue("code")==0){
                return newUserParamsInfo.getJSONObject("data");
            }
        }
        return null;
    }
}
