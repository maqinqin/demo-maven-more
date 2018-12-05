// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Util.java

package com.git.cloud.sys.tools;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.util.Random;

/**
 * 加密工具辅助类
 * @ClassName:Util
 * @Description:TODO
 * @author dongjinquan
 * @date 2014-11-17 下午3:53:17
 */
public class Util
{

    private static final int HASH_LEN = 20;
    private static final char base64Table[] = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz./".toCharArray();
    public static final String BASE64_ENCODING = "BASE64";
    public static final String BASE16_ENCODING = "HEX";
    private static SecureRandom psuedoRng;
    private static MessageDigest sha1Digest;
    private static boolean initialized;

    public Util()
    {
    }

    public static void init()
        throws NoSuchAlgorithmException
    {
        if(initialized)
        {
            return;
        } else
        {
            init(null);
            return;
        }
    }

    public static void init(byte prngSeed[])
        throws NoSuchAlgorithmException
    {
        sha1Digest = MessageDigest.getInstance("SHA");
        psuedoRng = SecureRandom.getInstance("SHA1PRNG");
        if(prngSeed != null)
            psuedoRng.setSeed(prngSeed);
        initialized = true;
    }

    public static MessageDigest newDigest()
    {
        MessageDigest md = null;
        try
        {
            md = (MessageDigest)sha1Digest.clone();
        }
        catch(CloneNotSupportedException clonenotsupportedexception) { }
        return md;
    }

    public static Random getPRNG()
    {
        return psuedoRng;
    }

    public static double nextDouble()
    {
        return psuedoRng.nextDouble();
    }

    public static long nextLong()
    {
        return psuedoRng.nextLong();
    }

    public static void nextBytes(byte bytes[])
    {
        psuedoRng.nextBytes(bytes);
    }

    public static byte[] generateSeed(int numBytes)
    {
        return psuedoRng.generateSeed(numBytes);
    }

    public static byte[] calculatePasswordHash(String username, char password[], byte salt[])
    {
        MessageDigest xd = newDigest();
        byte user[] = (byte[])null;
        byte colon[] = new byte[0];
        try
        {
            user = username.getBytes("UTF-8");
            colon = ":".getBytes("UTF-8");
        }
        catch(UnsupportedEncodingException e)
        {
            user = username.getBytes();
            colon = ":".getBytes();
        }
        byte passBytes[] = new byte[2 * password.length];
        int passBytesLength = 0;
        for(int p = 0; p < password.length; p++)
        {
            int c = password[p] & 0xffff;
            byte b0 = (byte)(c & 0xff);
            byte b1 = (byte)((c & 0xff00) >> 8);
            passBytes[passBytesLength++] = b0;
            if(c > 255)
                passBytes[passBytesLength++] = b1;
        }

        xd.update(user);
        xd.update(colon);
        xd.update(passBytes, 0, passBytesLength);
        byte h[] = xd.digest();
        xd.reset();
        xd.update(salt);
        xd.update(h);
        byte xb[] = xd.digest();
        return xb;
    }

    public static byte[] calculateVerifier(String username, char password[], byte salt[], byte Nb[], byte gb[])
    {
        BigInteger g = new BigInteger(1, gb);
        BigInteger N = new BigInteger(1, Nb);
        return calculateVerifier(username, password, salt, N, g);
    }

    public static byte[] calculateVerifier(String username, char password[], byte salt[], BigInteger N, BigInteger g)
    {
        byte xb[] = calculatePasswordHash(username, password, salt);
        BigInteger x = new BigInteger(1, xb);
        BigInteger v = g.modPow(x, N);
        return v.toByteArray();
    }

    public static byte[] sessionKeyHash(byte number[])
    {
        int offset;
        for(offset = 0; offset < number.length && number[offset] == 0; offset++);
        byte key[] = new byte[40];
        int klen = (number.length - offset) / 2;
        byte hbuf[] = new byte[klen];
        for(int i = 0; i < klen; i++)
            hbuf[i] = number[number.length - 2 * i - 1];

        byte hout[] = newDigest().digest(hbuf);
        for(int i = 0; i < 20; i++)
            key[2 * i] = hout[i];

        for(int i = 0; i < klen; i++)
            hbuf[i] = number[number.length - 2 * i - 2];

        hout = newDigest().digest(hbuf);
        for(int i = 0; i < 20; i++)
            key[2 * i + 1] = hout[i];

        return key;
    }

    public static byte[] trim(byte in[])
    {
        if(in.length == 0 || in[0] != 0)
            return in;
        int len = in.length;
        int i;
        for(i = 1; in[i] == 0 && i < len; i++);
        byte ret[] = new byte[len - i];
        System.arraycopy(in, i, ret, 0, len - i);
        return ret;
    }

    public static byte[] xor(byte b1[], byte b2[], int length)
    {
        byte result[] = new byte[length];
        for(int i = 0; i < length; i++)
            result[i] = (byte)(b1[i] ^ b2[i]);

        return result;
    }

    public static String encodeBase16(byte bytes[])
    {
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for(int i = 0; i < bytes.length; i++)
        {
            byte b = bytes[i];
            char c = (char)(b >> 4 & 0xf);
            if(c > '\t')
                c = (char)((c - 10) + 97);
            else
                c += '0';
            sb.append(c);
            c = (char)(b & 0xf);
            if(c > '\t')
                c = (char)((c - 10) + 97);
            else
                c += '0';
            sb.append(c);
        }

        return sb.toString();
    }

    public static String encodeBase64(byte bytes[])
    {
        String base64 = null;
        try
        {
            base64 = Base64Encoder.encode(bytes);
        }
        catch(Exception exception) { }
        return base64;
    }

    public static String createPasswordHash(String hashAlgorithm, String hashEncoding, String hashCharset, String username, String password)
    {
        String passwordHash = null;
        byte passBytes[];
        try
        {
            if(hashCharset == null)
                passBytes = password.getBytes();
            else
                passBytes = password.getBytes(hashCharset);
        }
        catch(UnsupportedEncodingException uee)
        {
            passBytes = password.getBytes();
        }
        try
        {
            byte hash[] = MessageDigest.getInstance(hashAlgorithm).digest(passBytes);
            if(hashEncoding.equalsIgnoreCase("BASE64"))
                passwordHash = encodeBase64(hash);
            else
            if(hashEncoding.equalsIgnoreCase("HEX"))
                passwordHash = encodeBase16(hash);
        }
        catch(Exception exception) { }
        return passwordHash;
    }

    public static String tob64(byte buffer[])
    {
        boolean notleading = false;
        int len = buffer.length;
        int pos = len % 3;
        byte b0 = 0;
        byte b1 = 0;
        byte b2 = 0;
        StringBuffer sb = new StringBuffer();
        switch(pos)
        {
        case 1: // '\001'
            b2 = buffer[0];
            break;

        case 2: // '\002'
            b1 = buffer[0];
            b2 = buffer[1];
            break;
        }
        do
        {
            int c = (b0 & 0xfc) >>> 2;
            if(notleading || c != 0)
            {
                sb.append(base64Table[c]);
                notleading = true;
            }
            c = (b0 & 0x3) << 4 | (b1 & 0xf0) >>> 4;
            if(notleading || c != 0)
            {
                sb.append(base64Table[c]);
                notleading = true;
            }
            c = (b1 & 0xf) << 2 | (b2 & 0xc0) >>> 6;
            if(notleading || c != 0)
            {
                sb.append(base64Table[c]);
                notleading = true;
            }
            c = b2 & 0x3f;
            if(notleading || c != 0)
            {
                sb.append(base64Table[c]);
                notleading = true;
            }
            if(pos >= len)
                break;
            try
            {
                b0 = buffer[pos++];
                b1 = buffer[pos++];
                b2 = buffer[pos++];
                continue;
            }
            catch(ArrayIndexOutOfBoundsException e) { }
            break;
        } while(true);
        if(notleading)
            return sb.toString();
        else
            return "0";
    }

    public static byte[] fromb64(String str)
        throws NumberFormatException
    {
        int len = str.length();
        if(len == 0)
            throw new NumberFormatException("Empty Base64 string");
        byte a[] = new byte[len + 1];
        int i;
        int j;
        for(i = 0; i < len; i++)
        {
            char c = str.charAt(i);
            try
            {
                for(j = 0; c != base64Table[j]; j++);
            }
            catch(Exception e)
            {
                throw new NumberFormatException("Illegal Base64 character");
            }
            a[i] = (byte)j;
        }

        i = len - 1;
        j = len;
        try
        {
            do
            {
                a[j] = a[i];
                if(--i < 0)
                    break;
                a[j] |= (a[i] & 0x3) << 6;
                j--;
                a[j] = (byte)((a[i] & 0x3c) >>> 2);
                if(--i < 0)
                    break;
                a[j] |= (a[i] & 0xf) << 4;
                j--;
                a[j] = (byte)((a[i] & 0x30) >>> 4);
                if(--i < 0)
                    break;
                a[j] |= a[i] << 2;
                a[j - 1] = 0;
                j--;
            } while(--i >= 0);
        }
        catch(Exception exception) { }
        try
        {
            for(; a[j] == 0; j++);
        }
        catch(Exception e)
        {
            return new byte[1];
        }
        byte result[] = new byte[(len - j) + 1];
        System.arraycopy(a, j, result, 0, (len - j) + 1);
        return result;
    }

    static 
    {
        try
        {
            init();
        }
        catch(NoSuchAlgorithmException nosuchalgorithmexception) { }
    }
}
