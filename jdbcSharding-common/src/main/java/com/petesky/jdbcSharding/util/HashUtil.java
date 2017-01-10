/**
 * Copyright (C) 2009-2011 Couchbase, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALING
 * IN THE SOFTWARE.
 */
package com.petesky.jdbcSharding.util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Desc:hash util 这个工具源自Spy Memcached client旧版的hash算法工具
 * Created by liegu.pengt on 2016/4/18 下午5:01.
 */
public class HashUtil {

	private static Log log = LogFactory.getLog(HashUtil.class);

	private static final long FNV_64_INIT = 0xcbf29ce484222325L;
	private static final long FNV_64_PRIME = 0x100000001b3L;
	private static final long FNV_32_INIT = 2166136261L;
	private static final long FNV_32_PRIME = 16777619;	
	
	private static MessageDigest MD5_DIGEST = null;

	static {
		try {
			MD5_DIGEST = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 not supported", e);
		}
	}

	
	public  static long FNV_64(String k,long num){
		long rv = FNV_64_INIT;
		int len = k.length();
		for (int i = 0; i < len; i++) {
			rv ^= k.charAt(i);
			rv *= FNV_64_PRIME;
		}
		return (rv & 0xffffffffL)%num;
	}

	
	public  static long KETAMA_HASH(final Integer k,final long num){
		byte[] bKey=computeMd5(k.toString());
		long rv = ((long) (bKey[3] & 0xFF) << 24)
				| ((long) (bKey[2] & 0xFF) << 16)
				| ((long) (bKey[1] & 0xFF) << 8)
				| (bKey[0] & 0xFF);
		long tmp=(rv & 0xffffffffL)%num;

		if(log.isDebugEnabled()){
			log.debug("Ketma_hash:"+tmp);
		}

		return tmp;
	}
	
	public  static long KETAMA_HASH(final String k,final long num){
		byte[] bKey=computeMd5(k.toString());
		long rv = ((long) (bKey[3] & 0xFF) << 24)
				| ((long) (bKey[2] & 0xFF) << 16)
				| ((long) (bKey[1] & 0xFF) << 8)
				| (bKey[0] & 0xFF);
		long tmp=(rv & 0xffffffffL)%num;

		if(log.isDebugEnabled()){
			log.debug("Ketma_hash:"+tmp);
		}

		return tmp;
	}
	
	public  static long FNV1A_32_HASH(String k,long num){
		long rv = FNV_32_INIT;
		int len = k.length();
		for (int i = 0; i < len; i++) {
			rv ^= k.charAt(i);
			rv *= FNV_32_PRIME;
		}
		return (rv & 0xffffffffL)%num;
	}
	
	private static byte[] getKeyBytes(String k) {
	    try
	    {
	      return k.getBytes("UTF-8");
	    } catch (UnsupportedEncodingException e) {
	      throw new RuntimeException(e);
	    }
	}

	private static byte[] computeMd5(String k) {
		MessageDigest md5;
		try {
			md5 = (MessageDigest)MD5_DIGEST.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("clone of MD5 not supported", e);
		}
		md5.update(getKeyBytes(k));
		return md5.digest();
	}
	private static Collection<byte[]> getKeyBytes(Collection<String> keys) {
	    Collection rv = new ArrayList(keys.size());
	    for (String s : keys) {
	      rv.add(getKeyBytes(s));
	    }
	    return rv;
	  }
	  
	  
}

