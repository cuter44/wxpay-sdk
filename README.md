# 微信支付服务器端SDK for Java / wx-pay seller-side sdk for Java  

## Release

...can be found in https://github.com/cuter44/wxpay-sdk/releases

## License & Acknokledgement/授权 & 鸣谢

see LICENSE.md

## Disclaimer/免责声明

Though I made effort to not stealing your money or any secret about your alipay account, or leak them to third-party. But I am still not responsable for any direct or indriect lost for your using of these (or part of) source code. It is your responsibility to check any risk before applying them.  

作者不对使用代码所带来的直接或间接损失负责. 由于源代码是公开的, 检查和确保代码的安全性是属于使用者的职责. 请时刻清醒地意识这一点.  

## Tutorial/教程

Frist of all, ensure you have `jdk` and `apache-ant`.  
`Oracle-HotSpot 1.7` and `apache-ant-1.9.2` are applied in development environment, all goes well.  

Besides, other runtime libraries are required, which are included in `lib` directory.

1. Generate jar file

run `ant jar`
then there will be the `wxpay-sdk-x.y.z.jar` in project root, where xyz is version code.

2. Config

add these to your classpath:
the jar-file mentioned above
`wxpay.properties`, whose schema are provided in `doc/wxpay.properties.sample`

3. Invoke

Demos are provided in `src/com/github/cuter44/test.java`. Which can be safely removed if you consider it insecure.

Thanks to GFW, it is such a tough thing to upload binary release to Github. Sorry for that.  

首先, 你要有一个 `jdk` 和 `apache-ant`. 开发环境使用 Oracle家的jdk7 和 ant 1.9.2, 请依个人喜好酌量添加.  
依赖的函数库被放置于`lib`文件夹中.

1. 编译: 执行 `ant jar`, 然后你就有个jar了....  
2. 配置: 将那个jar加到你的classpath, 然后还需要一个配置文件, 怎么写参见 `doc/alipay.properties.sample`  
3. 调用: 参见 `src/com/github/cuter44/test.java`. 如果需要安全性, 请排除这个类.

## Sample/样例

// TODO

### Initiative request/主动请求

// TODO

### Notify/接受回调

// TODO

## Covered API/已封装的接口

// TODO

## Detailed docs/详细文档

For questions with weixin-pay, ask your search engineer. Or refer to http://mp.weixin.qq.com/wiki/ , mostly it helps you nothing.  
For javadoc, run `ant javadoc`(recommended), or visit [http://cuter44.github.io/wxpay-sdk/javadoc/](http://cuter44.github.io/wxpay-sdk/javadoc/).  
For bugs/issues, thanks for visiting [https://github.com/cuter44/wxpay-sdk/issues](https://github.com/cuter44/wxpay-sdk/issues)  

