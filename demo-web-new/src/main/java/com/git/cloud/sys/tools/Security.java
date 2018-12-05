// Source File Name:   Security.java

package com.git.cloud.sys.tools;

import java.io.*;
import java.security.*;
import javax.crypto.*;

import org.apache.commons.ssl.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加密工具
 * @ClassName:Security
 * @Description:TODO
 * @author dongjinquan
 * @date 2014-11-17 下午3:57:17
 */
public class Security
{
	private static Logger logger = LoggerFactory.getLogger(Security.class);
    public static final String MD5_ALGORITHM = "MD5";
    public static final String SHA1_ALGORITHM = "SHA-1";


    public Security()
    {
    }

    public void init()
    {
    }

    public byte[] digest(byte rawInfo[])
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance(MD5_ALGORITHM);
            md.update(rawInfo);
            byte cipher[] = md.digest();
            
            return cipher;
        }
        catch(NoSuchAlgorithmException nsae)
        {
            return null;
        }
    }

    public String digestToHex(byte rawInfo[])
    {
        return Util.encodeBase16(digest(rawInfo)).toUpperCase();
    }

    public String digestToBase64(byte rawInfo[])
    {
        return Util.encodeBase64(digest(rawInfo));
    }

    public char[] encryptography(char rawInfo[])
    {
        try
        {
            Cipher cipher = Cipher.getInstance(SHA1_ALGORITHM);
            KeyGenerator kg = KeyGenerator.getInstance(SHA1_ALGORITHM);
            Key encryptoKey = kg.generateKey();
            cipher.init(1, encryptoKey);
            return null;
        }
        catch(NoSuchPaddingException npe)
        {
            logger.error("异常exception",npe);
            return null;
        }
        catch(NoSuchAlgorithmException nsae)
        {
        	logger.error("异常exception",nsae);
            return null;
        }
        catch(InvalidKeyException ivke)
        {
        	logger.error("异常exception",ivke);
        }
        return null;
    }

    public char[] decryptography(char cryptographInfo[], Key deCryptoKey)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(SHA1_ALGORITHM);
            KeyGenerator kg = KeyGenerator.getInstance(SHA1_ALGORITHM);
            Key decryptoKey = kg.generateKey();
            cipher.init(1, decryptoKey);
            return null;
        }
        catch(NoSuchPaddingException npe)
        {
            return null;
        }
        catch(NoSuchAlgorithmException nsae)
        {
            return null;
        }
        catch(InvalidKeyException ivke0)
        {
            return null;
        }
    }

    public OutputStream encryptography(InputStream rawStream)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(SHA1_ALGORITHM);
            return null;
        }
        catch(NoSuchPaddingException npe)
        {
            return null;
        }
        catch(NoSuchAlgorithmException nsae)
        {
            return null;
        }
    }

    public OutputStream decryptography(InputStream cryptographStream)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(SHA1_ALGORITHM);
            return null;
        }
        catch(NoSuchPaddingException npe)
        {
            return null;
        }
        catch(NoSuchAlgorithmException nsae)
        {
            return null;
        }
    }

    public static String decodeBase64Str(String plainText){
        String decodeBase64Str=null;
        if (org.apache.commons.lang.StringUtils.isNotEmpty(plainText)){
             byte[] btyeArrayStr=plainText.getBytes();  
             Base64 base64=new Base64();  
             btyeArrayStr=base64.decode(btyeArrayStr);  
             try {
                decodeBase64Str =new String(btyeArrayStr,"UTF-8");
            } catch (Exception e) {
            	return null;
            } 
        }
        return decodeBase64Str;  
     }  
    public static void main(String args[])
    {
        Security security = new Security();
        String password = "shoms2013";
        String encyrptoString = security.digestToHex(password.getBytes());
        System.out.println("password=" + encyrptoString+"\t size="+encyrptoString.length() );
        encyrptoString = security.digestToBase64(password.getBytes());
        System.out.println("password=" + encyrptoString+"\t size="+encyrptoString.length());
        
    }

}
