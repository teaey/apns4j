package cn.teaey.apns4j;
import cn.teaey.apns4j.keystore.KeyStoreHelper;
import cn.teaey.apns4j.keystore.KeyStoreWraper;
import cn.teaey.apns4j.keystore.exception.InvalidKeyStoreException;
import cn.teaey.apns4j.network.*;
import cn.teaey.apns4j.protocol.NotifyPayload;
import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * User: Teaey
 * Date: 13-8-30
 */
public class TestSample
{
    //设置一个私钥密码
    private final String keyStorePasswd    = "37=int";
    //设置自己的需要推送的devicetoken（测试用）
    private final String deviceTokenString = "968b6a792359fdaef520f7089cb717a63a77961affe311d0aae6d0b1290b9d58";
    @Test
    public void performanceTest()
    {
        int times = 10000;
        {
            long start = System.nanoTime();
            for (int i = 0; i < times; i++)
            {
                try
                {
                    NotifyPayload notifyPayload = NotifyPayload.newNotifyPayload();
                    //notifyPayload.alert("TEST1");
                    //notifyPayload.badge(2);
                    //notifyPayload.sound("default");
                    notifyPayload.alertBody("TEST");
                    notifyPayload.alertActionLocKey("TEST");
                    byte[] payloadAsBytes = notifyPayload.toJsonBytes();
                } catch (Exception e)
                {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            long end = System.nanoTime();
            System.out.println("APNS4j use " + TimeUnit.NANOSECONDS.toMillis(end - start) + "ms");
        }
    }
    /**
     * How to get a KeyStore
     */
    @Test
    public void loadKeyStoreWithClassPath()
    {
        try
        {
            KeyStoreWraper keyStore = KeyStoreHelper.getKeyStoreWraper("iphone_dev.p12", keyStorePasswd);
            Assert.assertNotNull(keyStore);
        } catch (InvalidKeyStoreException e)
        {
            e.printStackTrace();
        }
    }
    @Test
    public void loadKeyStoreWithSystemPath()
    {
        try
        {
            KeyStoreWraper keyStore = KeyStoreHelper.getKeyStoreWraper("D:/iphone_dev.p12", keyStorePasswd);
            Assert.assertNotNull(keyStore);
        } catch (InvalidKeyStoreException e)
        {
            e.printStackTrace();
        }
    }
    @Test
    public void loadKeyStoreByFile()
    {
        try
        {
            File f = new File("D:/iphone_dev.p12");
            KeyStoreWraper keyStore = KeyStoreHelper.getKeyStoreWraper(f, keyStorePasswd);
            Assert.assertNotNull(keyStore);
        } catch (InvalidKeyStoreException e)
        {
            e.printStackTrace();
        }
    }
    @Test
    public void loadKeyStoreByInputStream()
    {
        try
        {
            InputStream in = getClass().getClassLoader().getResourceAsStream("iphone_dev.p12");
            KeyStoreWraper keyStore = KeyStoreHelper.getKeyStoreWraper(in, keyStorePasswd);
            Assert.assertNotNull(keyStore);
        } catch (InvalidKeyStoreException e)
        {
            e.printStackTrace();
        }
    }
    @Test
    public void loadKeyStoreByByteArray() throws IOException, InvalidKeyStoreException
    {
        InputStream in = getClass().getClassLoader().getResourceAsStream("iphone_dev.p12");
        byte[] bytes = new byte[in.available()];
        in.read(bytes);
        in.close();
        KeyStoreWraper keyStore = KeyStoreHelper.getKeyStoreWraper(bytes, keyStorePasswd);
        Assert.assertNotNull(keyStore);
    }
    /**
     * How to get a Connection
     * @throws cn.teaey.apns4j.keystore.exception.InvalidKeyStoreException
     * @throws cn.teaey.apns4j.network.ConnectionException
     * @throws java.io.IOException
     */
    @Test
    public void getConnection() throws InvalidKeyStoreException, ConnectionException, IOException
    {
        KeyStoreWraper keyStoreWrapper = KeyStoreHelper.getKeyStoreWraper("iphone_dev.p12", keyStorePasswd);
        AppleNotificationServer appleNotificationServer = AppleNotificationServer.get(AppleGateway.ENV_DEVELOPMENT);
        SecuritySocketFactory connectionFactory = SecuritySocketFactory.Builder.newBuilder().appleServer(appleNotificationServer).keyStoreWrapper(keyStoreWrapper).build();
        SecurityConnection connection = SecurityConnection.newSecurityConnection(connectionFactory);
        Assert.assertNotNull(connection);
        connection.close();
    }
    /**
     * Very Simple to use APNS4j
     * @throws cn.teaey.apns4j.keystore.exception.InvalidKeyStoreException
     * @throws cn.teaey.apns4j.network.ConnectionException
     * @throws java.io.IOException
     */
    @Test
    public void simpleUseApns4j() throws InvalidKeyStoreException, ConnectionException, IOException
    {
        //get a keystore
        KeyStoreWraper keyStoreWrapper = KeyStoreHelper.getKeyStoreWraper("iphone_dev.p12", keyStorePasswd);
        //get apple server with env
        AppleNotificationServer appleNotificationServer = AppleNotificationServer.get(AppleGateway.ENV_DEVELOPMENT);
        //init ssl socket factory
        SecuritySocketFactory socketFactory = SecuritySocketFactory.Builder.newBuilder().appleServer(appleNotificationServer).keyStoreWrapper(keyStoreWrapper).build();
        //create a ssl socket
        SecurityConnection connection = SecurityConnection.newSecurityConnection(socketFactory);
        Assert.assertNotNull(connection);
        //create & init a notify payload
        NotifyPayload notifyPayload = NotifyPayload.newNotifyPayload();
        notifyPayload.alert("" + System.currentTimeMillis());
        notifyPayload.badge(2);
        //notifyPayload.sound("default");
        //notifyPayload.alertBody("Pushed By \\\" apns4j");
        //notifyPayload.alertActionLocKey("Button Text");
        connection.sendAndFlush(deviceTokenString, notifyPayload);
        connection.close();
    }
    @Test
    public void asynServiceTest() throws InvalidKeyStoreException, ConnectionException, IOException, InterruptedException
    {
        KeyStoreWraper keyStoreWrapper = KeyStoreHelper.getKeyStoreWraper("iphone_dev.p12", keyStorePasswd);
        AppleNotificationServer appleNotificationServer = AppleNotificationServer.get(AppleGateway.ENV_DEVELOPMENT);
        SecuritySocketFactory connectionFactory = SecuritySocketFactory.Builder.newBuilder().appleServer(appleNotificationServer).keyStoreWrapper(keyStoreWrapper).build();
        final APNSAsynService ser = APNSAsynService.newAPNSAsynService(4, connectionFactory);
        List<Thread> tList = new ArrayList<Thread>();
        for (int i = 0; i < 10; i++)
        {
            Thread t = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    for (int i = 0; i < 1; i++)
                    {
                        NotifyPayload notifyPayload = NotifyPayload.newNotifyPayload();
                        notifyPayload.alert("" + System.currentTimeMillis());
                        notifyPayload.badge((int) System.currentTimeMillis() % 100);
                        ser.sendAndFlush(deviceTokenString, notifyPayload);
                        try
                        {
                            Thread.sleep(100);
                        } catch (Exception e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            tList.add(t);
        }
        for (Thread each : tList)
        {
            each.start();
        }
        for (Thread each : tList)
        {
            each.join();
        }
        ser.shutdown();
    }
}
