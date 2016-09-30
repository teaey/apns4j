/*
 *
 *  * Copyright 2015 The Apns4j Project
 *  *
 *  * The Netty Project licenses this file to you under the Apache License,
 *  * version 2.0 (the "License"); you may not use this file except in compliance
 *  * with the License. You may obtain a copy of the License at:
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  * License for the specific language governing permissions and limitations
 *  * under the License.
 *
 */

package cn.teaey.apns4j.keystore;

import cn.teaey.apns4j.ApnsException;
import cn.teaey.apns4j.ApnsHelper;

import javax.crypto.BadPaddingException;
import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

/**
 * @author teaey(xiaofei.wxf)
 * @since 1.0.3
 */
public class KeyStoreGetter {
    private final InputStream keyStoreInputStream;
    private final String keyStorePwd;
    private final KeyStoreType keyStoreType;

    public KeyStoreGetter(InputStream keyStoreInputStream, String keyStorePwd, KeyStoreType keyStoreType) {
        ApnsHelper.checkNullThrowException(keyStoreInputStream, "keyStoreInputStream");
        ApnsHelper.checkNullThrowException(keyStoreType, "keyStoreType");
        this.keyStoreInputStream = keyStoreInputStream;
        this.keyStorePwd = keyStorePwd;
        this.keyStoreType = keyStoreType;
    }

    public KeyStoreGetter(File keyStoreFile, String keyStorePwd, KeyStoreType keyStoreType) {
        ApnsHelper.checkNullThrowException(keyStoreFile, "keyStoreFile");
        ApnsHelper.checkNullThrowException(keyStoreType, "keyStoreType");
        try {
            this.keyStoreInputStream = new FileInputStream(keyStoreFile);
        } catch (FileNotFoundException e) {
            throw new ApnsException(e);
        }
        this.keyStorePwd = keyStorePwd;
        this.keyStoreType = keyStoreType;
    }

    public KeyStoreGetter(String keyStoreFilePath, String keyStorePwd, KeyStoreType keyStoreType) {
        ApnsHelper.checkNullThrowException(keyStoreFilePath, "keyStoreFilePath");
        ApnsHelper.checkNullThrowException(keyStoreType, "keyStoreType");
        try {
            this.keyStoreInputStream = new FileInputStream(keyStoreFilePath);
        } catch (FileNotFoundException e) {
            throw new ApnsException(e);
        }
        this.keyStorePwd = keyStorePwd;
        this.keyStoreType = keyStoreType;
    }

    public KeyStoreGetter(InputStream keyStoreInputStream, String keyStorePwd) {
        ApnsHelper.checkNullThrowException(keyStoreInputStream, "keyStoreInputStream");
        this.keyStoreInputStream = keyStoreInputStream;
        this.keyStorePwd = keyStorePwd;
        this.keyStoreType = KeyStoreType.PKCS12;
    }

    public KeyStoreGetter(File keyStoreFile, String keyStorePwd) {
        ApnsHelper.checkNullThrowException(keyStoreFile, "keyStoreFile");
        try {
            this.keyStoreInputStream = new FileInputStream(keyStoreFile);
        } catch (FileNotFoundException e) {
            throw new ApnsException(e);
        }
        this.keyStorePwd = keyStorePwd;
        this.keyStoreType = KeyStoreType.PKCS12;
    }

    public KeyStoreGetter(String keyStoreFilePath, String keyStorePwd) {
        ApnsHelper.checkNullThrowException(keyStoreFilePath, "keyStoreFilePath");
        try {
            this.keyStoreInputStream = new FileInputStream(keyStoreFilePath);
        } catch (FileNotFoundException e) {
            throw new ApnsException(e);
        }
        this.keyStorePwd = keyStorePwd;
        this.keyStoreType = KeyStoreType.PKCS12;
    }

    public KeyStore keyStore() {
        try {
            KeyStore keyStore = KeyStore.getInstance(this.keyStoreType.name());
            keyStore.load(this.keyStoreInputStream, null == keyStorePwd ? new char[0] : keyStorePwd.toCharArray());
            return keyStore;
        } catch (KeyStoreException e) {
            if (e.getCause() instanceof NoSuchAlgorithmException || e.getCause() instanceof NoSuchProviderException) {
                throw new ApnsException(new InvalidKeyStoreTypeException("(" + this.keyStoreType + ")", e));
            } else {
                throw new ApnsException(new InvalidKeyStoreTypeException(e));
            }
        } catch (CertificateException e) {
            throw new ApnsException(new InvalidKeyStoreFormatException("cant load keystore", e));
        } catch (NoSuchAlgorithmException e) {
            throw new ApnsException(new InvalidKeyStoreFormatException("cant load keystore", e));
        } catch (IOException e) {
            if (e.getCause() instanceof BadPaddingException) {
                throw new ApnsException(new InvalidKeyStorePasswordException("(" + this.keyStorePwd + ")", e));
            } else {
                throw new ApnsException(new InvalidKeyStoreFormatException(e));
            }
        }
    }


    public String keyStorePwd() {
        return this.keyStorePwd;
    }
}
