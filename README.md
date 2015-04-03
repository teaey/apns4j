apns4j
======

Apple Push Notification Service Implement with Java


How to use?
====================================================

1.
-----

```

        <dependency>
            <groupId>com.github.teaey</groupId>
            <artifactId>apns4j</artifactId>
            <version>1.0.1</version>
        </dependency>

```

2.
-----


@see [TestSample](src/test/java/cn/teaey/apns4j/TestSample.java)



```


KeyStoreWraper keyStore = KeyStoreHelper.getKeyStoreWraper("XXXXXXXX.p12", keyStorePasswd);

AppleNotificationServer appleNotificationServer = new AppleNotificationServer(AppleGateway.ENV_DEVELOPMENT, keyStore);

SecurityConnectionFactory connectionFactory = new SecurityConnectionFactory(appleNotificationServer);

SecurityConnection connection = connectionFactory.getSecurityConnection();

NotifyPayload notifyPayload = new NotifyPayload();

//notifyPayload.setAlert("TEST1");

notifyPayload.setBadge(2);

notifyPayload.setSound("default");

notifyPayload.setAlertBody("Pushed By apns4j");

notifyPayload.setAlertActionLocKey("Button Text");

connection.writeAndFlush(deviceTokenString, notifyPayload);

connection.close();


```