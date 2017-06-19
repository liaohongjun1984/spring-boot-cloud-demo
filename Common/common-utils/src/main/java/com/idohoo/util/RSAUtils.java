package com.idohoo.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/** 
 * RSA算法，实现数据的加密解密。 
 * @author  
 * 
 */  
public class RSAUtils {  
	/**
     * 算法名称
     */
    private final static String RSA = "RSA";
    public static Log log = LogFactory.getLog(RSAUtils.class);
    /**
     * 加密后的字节分隔长度
     */
    private final static int encryptSepLength = 256;
    
    /**
     * 明文字节分隔长度
     */
    private final static int plainSepLneght = 100; 

    private static byte[] encrypt(byte[] text, PublicKey pubRSA)
            throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(20L);
        cipher.init(Cipher.ENCRYPT_MODE, pubRSA,random);
        return cipher.doFinal(text);
    }

    public final static String encrypt(String text) {
    	 StringBuffer sbf = new StringBuffer(200);
    	if(!StringUtils.isEmpty(text)){
            try {
            	if(StringUtils.isBlank(text)){
            		return "";
            	}
                text = URLEncoder.encode(text, "UTF-8");//用这个的原因是为了支持汉字、汉字和英文混排,解密方法中同理
                byte[] plainByte = text.getBytes();
                ByteArrayInputStream bays = new ByteArrayInputStream(plainByte);
                byte[] readByte = new byte[plainSepLneght];
                int n = 0;

                //这个位置很恶心人的写了一堆，是为了支持超过117字节，我每次加密100字节。

                while ((n = bays.read(readByte)) > 0) {
                    if (n >= plainSepLneght) {
                        sbf.append(byte2hex(encrypt(readByte, getPublicKey())));
                    } else {
                        byte[] tt = new byte[n];
                        for (int i = 0; i < n; i++) {
                            tt[i] = readByte[i];
                        }
                        sbf.append(byte2hex(encrypt(tt, getPublicKey())));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
    	}
        return sbf.toString();
    }
    

    public final static String decrypt(String data) {
        String rrr = "";
        if(StringUtils.isBlank(data)){
    		return "";
    	}
        
        StringBuffer sb = new StringBuffer(100);
        ByteArrayInputStream bais = null;
        try {
        	bais = new ByteArrayInputStream(data.getBytes());
             //此处之所以是 256，而不是128的原因是因为有一个16进行的转换，所以由128变为了256
            byte[] readByte = new byte[256];
            int n = 0;
            while ((n = bais.read(readByte)) > 0) {
                if (n >= encryptSepLength) {
                    sb.append(new String(decrypt(hex2byte(readByte))));
                } else {

                }
            }
            rrr = URLDecoder.decode(sb.toString(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
				if(bais != null){
					bais.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        
        if (StringUtils.isEmpty(rrr)) {
        	rrr=data;
		}
        return rrr;
    }

    private static byte[] decrypt(byte[] src) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
        return cipher.doFinal(src);
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs += ("0" + stmp);
            else
                hs += stmp;
        }
        return hs.toUpperCase();
    }

    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");

        byte[] b2 = new byte[b.length / 2];

        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
    
    private static RSAPrivateKey prk;
    public static PrivateKey getPrivateKey() {
        if(prk == null){
        	String str = "/Skey_RSA_priv.dat";
            InputStream fis = RSAUtils.class.getResourceAsStream(str);
            ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(fis);
				prk = (RSAPrivateKey) ois.readObject();
			} catch (FileNotFoundException e) {
				log.error("打开密钥文件失败！" + str);
				e.printStackTrace();
			} catch (IOException e) {
				log.error("读取密钥文件中的对象失败！" + str);
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				log.error("读取密钥文件中的对象失败！" + str);
				e.printStackTrace();
			} finally {
				try {
					if(ois != null){
						ois.close();
					}
					
					if(fis != null){
						fis.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        }
        
        return prk;
    }
    
    private static RSAPublicKey publicKey;
    public static PublicKey getPublicKey() {
    	if(publicKey != null){
    		return publicKey;
    	}
    	
    	InputStream fis = null;
    	ObjectInputStream ois = null;
    	try {
    		fis = RSAUtils.class.getResourceAsStream("/Skey_RSA_pub.dat");
    		ois = new ObjectInputStream(fis);
    		publicKey = (RSAPublicKey) ois.readObject();
    		return publicKey;
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		try {
    			if(fis != null){
    				fis.close();
    			}
    			
    			if(ois != null){
    				ois.close();
    			}
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	
    	
    	return null;
    }

    public static void main(String args[]) {
        String phone = "430421199409015481";

        String encrPhone = RSAUtils.encrypt(phone);

        System.out.println("cipherText:" + encrPhone);
    }

    
    
    
    

    public static void main2(String args[]) throws Exception{
        String phone = "15211858710";

        String encrPhone = RSAUtils.encrypt(phone);

        System.out.println("cipherText:" + encrPhone);
    }
}  
