package com.github.cuter44.wxmp.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Enumeration;
import java.security.KeyStore;
import java.security.cert.*;
import javax.net.ssl.*;
import java.io.*;
import java.util.MissingResourceException;

/** Transient tool-object to load certificate and generate ssl-context
 * Code adapted from nyafx/crypto.
 * Thread-unsafe.
 * This toolkit provided an ease way to load certificate meterial from classpath
 */
public class CertificateLoader
{
  // CONSTANTS
    public static final String KEY_LOAD_TRUSTS = "LOAD_TRUSTS";
    public static final String KEY_LOAD_IDENTIFICATION = "LOAD_IDENTIFICATION";
    public static final String KEY_MCH_ID = "mch_id";
  // CONSTRUCT
    public static final String ALGORITHM = "TLSv1";

    /** Trusts
     * To store server certificates
     */
    public KeyStore trusts;
    /** Iddentifiers
     * To store our own certificates
     */
    public KeyStore identification;

    public String passphrase;

    public CertificateLoader()
    {
        return;
    }

    public CertificateLoader(KeyStore trusts, KeyStore identification)
    {
        this();

        this.trusts = trusts;
        this.identification = identification;

        return;
    }

  // EXPORT

    ///** Export loaded certificates as SSLContext
     //*/
    public SSLContext asSSLContext()
    {
        try
        {
            TrustManager[] tm = null;
            KeyManager[] km = null;

            if (this.trusts != null)
            {
                TrustManagerFactory tmFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmFactory.init(this.trusts);
                tm = tmFactory.getTrustManagers();
            }

            if (this.identification != null)
            {
                KeyManagerFactory kmFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                kmFactory.init(this.identification, this.passphrase.toCharArray());
                km = kmFactory.getKeyManagers();
            }

            SSLContext sslCtx = SSLContext.getInstance(ALGORITHM);
            sslCtx.init(
                km,
                tm,
                null
            );

            return(sslCtx);
        }
        catch (Exception ex)
        {
            throw(new RuntimeException(ex));
        }
    }

  // IMPORT
    /** Load trusts from .crt file.
     * Existing <code>trusts</code> will be dropped.
     */
    public CertificateLoader loadTrusts(String ... resourcePaths)
    {
        try
        {
            this.trusts = KeyStore.getInstance(KeyStore.getDefaultType());
            this.trusts.load(null, null);

            for (String resourcePath:resourcePaths)
            {
                InputStream stream = this.getClass()
                    .getResourceAsStream(resourcePath);

                CertificateFactory crtFactory = CertificateFactory.getInstance("X.509");
                BufferedInputStream buffer = new BufferedInputStream(stream);

                while (buffer.available() > 0)
                {
                    Certificate cert = crtFactory.generateCertificate(buffer);
                    this.trusts.setCertificateEntry("cert-"+System.currentTimeMillis(), cert);

                    //System.out.println("Trust:");
                    //System.out.println(cert);
                }

                buffer.close();
            }
        }
        catch (Exception ex)
        {
            throw(new RuntimeException(ex));
        }

        return(this);
    }

    /** Load identification from .p12 file.
     * Existing <code>identification</code> will be dropped.
     * @param passphrase Passphrase to decrypt the cert, mch_id if certificate is acquired from pay.weixin.qq.com .
     */
    public CertificateLoader loadIdentification(String resourcePath, String passphrase)
    {
        try
        {
            InputStream stream = this.getClass()
                .getResourceAsStream(resourcePath);

            if (stream == null)
                throw(new MissingResourceException("Load identification failed.", CertificateLoader.class.getName(), resourcePath));

            this.identification = KeyStore.getInstance("PKCS12");
            this.identification.load(stream, passphrase.toCharArray());

            this.passphrase = passphrase;

            //System.out.println("Identification:");
            //Enumeration<String> aliases = this.identification.aliases();
            //while (aliases.hasMoreElements())
            //{
                //String alias = aliases.nextElement();
                //System.out.println(alias);
                //System.out.println(this.identification.getCertificate(alias));
            //}
        }
        catch (Exception ex)
        {
            //ex.printStackTrace();
            throw(new RuntimeException(ex));
        }

        return(this);
    }

    public CertificateLoader config(Properties conf)
    {
        String confLoadTrusts = conf.getProperty(KEY_LOAD_TRUSTS);
        if (confLoadTrusts != null)
            this.loadTrusts(
                confLoadTrusts.split("\\:|;")
            );

        String confLoadIdentification = conf.getProperty(KEY_LOAD_IDENTIFICATION);
        if (confLoadIdentification != null)
        {
            String mchId = conf.getProperty(KEY_MCH_ID);
            this.loadIdentification(confLoadIdentification, mchId);
        }

        return(this);
    }


  // TEST
    public static void main(String[] args)
    {
        try
        {
            InputStream stream = CertificateLoader.class
                .getResourceAsStream("/wxpay.properties");

            Properties conf = new Properties();
            conf.load(stream);

            CertificateLoader cl = new CertificateLoader().config(conf);

            Enumeration<String> et = cl.identification.aliases();
            while (et.hasMoreElements())
            {
                System.out.println(cl.identification.getCertificate(et.nextElement()));
            }

            Enumeration<String> ei = cl.trusts.aliases();
            while (ei.hasMoreElements())
            {
                System.out.println(cl.trusts.getCertificate(ei.nextElement()));
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return;

    }




}
