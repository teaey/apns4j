# Apns4j

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/teaey/apns4j.svg?branch=master)](https://travis-ci.org/teaey/apns4j)

### Quick start

```
<dependency>
    <groupId>cn.teaey.apns4j</groupId>
    <artifactId>apns4j</artifactId>
    <version>1.1.1</version>
</dependency>
```

```
//Step 1
ApnsChannelFactory apnsChannelFactory = Apns4j.newChannelFactoryBuilder()
.keyStoreMeta("${path to your keystore}")
.keyStorePwd("${keystore password}")
.build();

//Setp 2
ApnsChannel apnsChannel = apnsChannelFactory.newChannel();

//Step 3 create & init notify payload
ApnsPayload apnsPayload = Apns4j.newPayload()
        .alertTitle("Title")
        .alertBody("Pushed by apns4j")
        .sound("default");

//Step 4 send via channel
apnsChannel.send("${target device token}", apnsPayload);

//Step 5 in the end, apnsChannel can be Recycle and Reuse 
apnsChannel.close();
```


#### Version Control

```
This project uses Semantic Versioning. Version format is X.Y.Z:

X: New program version.

Y: New feature or huge bug fix patch.

Z: Minor fix or patch.
```



