# 微信支付服务器端SDK for Java / wx-pay sdk for JavaEE  
# 以及 微信公众平台SDK for Java / wx-mp sdk for JavaEE  

> yet another wheel for wx (╯‵д′)╯︵┻━┻

## Release

...can be found in https://github.com/cuter44/wxpay-sdk/releases

## License & Acknokledgement/授权 & 鸣谢

see [LICENSE.md](LICENSE.md)

## Disclaimer/免责声明

Though I made effort to not stealing your money or any secret about your wxpay account, or leak them to third-party. But I am still not responsable for any direct or indriect lost for your using of these (or part of) source code. It is your responsibility to check any risk before applying them.  

作者不对使用代码所带来的直接或间接损失负责. 由于源代码是公开的, 检查和确保代码的安全性是属于使用者的职责. 请时刻清醒地意识这一点.  

## Tutorial/使用说明

Frist of all, ensure you have `jdk` and `apache-ant`.  
`Oracle-HotSpot 1.7` and `apache-ant-1.9.2` are applied in development environment, all goes well.  

Besides, other runtime libraries are required, which are included in `lib` directory.

1. Generate jar file

   run `ant jar`
   then there will be the `wxpay-sdk-x.y.z.jar` in project root, where xyz is version code.

2. Config

   add these to your classpath:
   * the jar-file mentioned above  
   * jar-files in `lib/`, which are depended  
   * in the right above step, 'fastjson-1.1.35.jar' and `nyafx-servlet-2.3.0.jar` can be skip if you don't use provided servlet  
   * `wxpay.properties`, whose schema are provided in `doc/wxpay.properties.sample`  

Thanks to GFW, it is such a tough thing to upload binary release to Github. Sorry for that.  

首先, 你要有一个 `jdk` 和 `apache-ant`. 开发环境使用 Oracle家的jdk7 和 ant 1.9.2, 请依个人喜好酌量添加.  
依赖的函数库被放置于`lib`文件夹中.

1. 编译: 执行 `ant jar`, 然后你就有个jar了....  
2. 配置: 将那个jar以及lib里的jar加到你的classpath, 然后还需要一个配置文件, 怎么写参见 `doc/wxpay.properties.sample`  
3. 如果不使用SDK提供的servlet, 那么在上一步中的 `fastjson-1.1.35.jar` `nyafx-servlet-2.3.0.jar` 不是依赖项.

### Configurate/配置  

`doc/wxpay.properties.sample` provides a sample config file. Comments in that file instruct what you should do with that.

`doc/wxpay.properties.sample` 是一份样例操作文件. 关于如何使用该配置文件请参照其中的注释.

### Additional Configurate/额外配置

这里要额外提到微信支付独有的坑爹地方:

1. 如果是测试环境, 你需要登录 http://mp.weixin.qq.com/ > 微信支付 > 开发配置 > 测试目录 > 修改 , 写入用于调起支付的测试页面所在的目录, 并在测试白名单中加入你的微信号
2. 如果是生产环境, 你需要登录 http://mp.weixin.qq.com/ > 微信支付 > 开发配置 > 支付配置 > 修改 , 勾选 JSAPI, 填入同上的目录路径. Native原生支付 尚未被本SDK支持, 随便配置即可.
3. 使用JSAPI支付的页面, 必须处于所在目录(貌似还不能有子目录, 虽然我没有测试过), 否则会报错.
4. 使用JSAPI支付的页面, 必须从所使用的 APPID 对应的服务号打开(请谨慎断句...), 否则会报错. 从自己发给服务号的消息所形成的链接里点进去(请谨慎断句)也可以支付, 这对测试来说是个福音(因为不用登录到mp操纵公众号给自己发消息)
5. 支付金额的单位是人民币**分**, 而不是习惯的人民币元. 封装接口对于 `.setTotalFee()` 这类方法提供了参数为int(分)和double(元)的接口, 可以自行选择. 但如果使用 `.setProperty()` 请尤其注意单位
6. 未完待续(我坚信还有很多坑我还没踩到).

### Factory/工厂方法

This SDK provides factory object to easily create requests. This factory reads `wxpay.properties` mentioned above and use them as default config, code in next section demostrate how it works.  
This mechanism works profectly if you are using a unique wxpay enterprise account. Otherwise you may not use a config file but create and config factories/requests programtically (using `new`).

SDK 中提供了一个工厂方法用于快速地创建请求. 这个工厂方法读取 `wxpay.properties` 并将之传递给请求作为默认参数(可以在运行时被覆盖)  
工厂方法适用于单实例的时候. 如果你的系统需要集成多个商家帐号, 则你需要编程创建并自行维护工厂对象或单个请求(使用其构造器而不是 `getDefaultInstance()`)

### JSAPI

首先, 请打开 [微信公众号支付接口文档V3.3.7.pdf](src/【微信支付】微信公众号支付接口文档V3.3.7.pdf), 翻到章节 2.1.2 JSAPI 支付时序图.  

概括起来服务器端必需做两步操作(忽略获取openid):

1. 服务器需要先告知微信支付的标题金额等, 然后换取一个 id.
2. 服务器将上一步得到的 id 交给前端使用 JS 调起支付.

坑爹的地方在于一步就能完成的操作被分成了两步并且第二步不是靠页面跳转来完成的因此很难理解. 非要打个比方的话就是我们小时候的医院, 处方要先在一个窗口定价, 再到另一个窗口交费.  
出于不能过度隐藏细节的考虑 SDK 没有将两步封装成一步. 

样例如下:

````Java
/* file: src/com/github/cuter44/wxpay/servlet/JSAPISigner.java  (part)
 * 这是一个 servlet, 接受前端传入的商品名称,价格和客户端openid(如果服务器知道这个参数的话则不必要从前端获取)
 * 然后依次进行第一步和第二步的前半部分. 也就是说这个接口返回最后JS调起支付所需要的 JSON 对象.
 */
        try
        {
            req.setCharacterEncoding("utf-8");

            // 从工厂对象创建请求, 需要的参数请参见
            UnifiedOrder order = this.wxpayFactory.newUnifiedOrder()
                .setBody            (needString(req, BODY)                      )
                .setTotalFee        (needDouble(req, TOTAL_FEE)                 )
                .setOpenid          (needString(req, OPENID)                    )
                .setOutTradeNo      ("test"+Long.toString(new Date().getTime()) )
                .setSpbillCreateIp  (req.getRemoteAddr()                        )
                .setTradeType       (TradeType.JSAPI                            )
                .build()
                .sign();

            // 执行该请求以取得返回值(主要是prepay_id)
            UnifiedOrderResponse orderResp = order.execute();

            // 创建第二步使用的请求
            GetBrandWCPayRequest gbwxpr = this.wxpayFactory.newGetBrandWCPayRequest(orderResp.getProperties())
                .build()
                .sign();

            // 并将之构造成 JSON
            String jsonGbwxpr = gbwxpr.toJSON();

            // 交给前端
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().print(jsonGbwxpr);

            return;
        }
        catch (Exception ex)
        {
            resp.reset();
            resp.setStatus(500);
            ex.printStackTrace(resp.getWriter());
        }
````

````javascript
/* file: web/demo-pay.jsp
 * 包含调起支付的 JS 样例
 */
        // ajax.responseText 即上述 servlet 的响应
        var gbwcpr = JSON.parse(ajax.responseText);
        // 调起支付, 样例由微信支付文档章节 4.4 修改
        WeixinJSBridge.invoke(
          'getBrandWCPayRequest',
          gbwcpr,
          function(res){
            alert(res.err_msg);
            if(res.err_msg == "get_brand_wcpay_request:ok" ) {alert("喵喵喵")}
            // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg 将在用户支付成功后返回 ok，但幵丌保证它绝对可靠。
          }
        );
````

### Native

(还没有实现)

### 其他接口

(还没有实现)

### Notify/通知回调

`src/com/github/cuter44/wxpay/servlet/WxpayNotifyGatewayServlet.java` `src/com/github/cuter44/wxpay/WxpayNotifyPublisher.java` 提供了一套简单的事件回调方法.
要使用这个机制, 请参考 `doc/web.xml` 进行配置. 然后实现并注册监听器:  

````
            WxpayNotifyPublisher.getDefaultInstance().addListener(
                new WxpayNotifyListener(){
                    @Override
                    public boolean handle(Notify n)
                    {
                        // do something
                        
                        // 返回 true 表示已处理该通知, GatewayServlet 在遇到第一个 true 之前会顺次调用监听器.
                        // 在第一个返回 true 的调用之后停止调用并向微信服务器返回正确处理的响应.
                        // 如果没有任何监听器返回 true , 则向微信服务器返回 500.
                        return(true);
                    }
                }
            );
````

## Media Publicity / 公众平台

因为微信 API 和文档糅杂的缘故, 这个 SDK 从 Mar 2015 开始支持 MP 的接口, 目前支持的接口如下:

 * 网页授权获取用户基本信息 
   [spec↗](http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html)
   [doc↗](http://cuter44.github.io/wxpay-sdk/javadoc/0.2.0/com/github/cuter44/wxpay/servlet/SnsapiUserinfo.html)
   & [doc↗](http://cuter44.github.io/wxpay-sdk/javadoc/0.2.0/com/github/cuter44/wxpay/servlet/SnsapiBase.html)
 * 获取access token 
   [spec↗](http://mp.weixin.qq.com/wiki/11/0e4b294685f817b95cbed85ba5e82b8f.html)
   [doc↗](http://cuter44.github.io/wxpay-sdk/javadoc/0.2.1/com/github/cuter44/wxpay/reqs/TokenClientCredential.html)
 * 微信JSSDK支持
   [spec↗](http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html)
   [doc↗](http://cuter44.github.io/wxpay-sdk/javadoc/0.2.1/com/github/cuter44/wxpay/servlet/JSSDKConfig.html)
   


## Detailed docs/详细文档

Thank you for recognition of the early version of wxpay-sdk/utility. At the time I write down this line, only few basic API are covered.
If these cannot fulfill your desire, you are welcomed to participate in or send me a text to keep on. Usage demo are also welcomed.

For questions with weixin-pay, ask your search engineer. Or refer to [http://mp.weixin.qq.com/wiki/](http://mp.weixin.qq.com/wiki/) , mostly it helps you nothing.  
For javadoc, run `ant javadoc`(recommended), or visit [http://cuter44.github.io/wxpay-sdk/javadoc/](http://cuter44.github.io/wxpay-sdk/javadoc/0.2.0/) (recent version may be available, you can just take a try).  
For bugs/issues, thanks for submitting on [https://github.com/cuter44/wxpay-sdk/issues](https://github.com/cuter44/wxpay-sdk/issues)  
Gitter powered: [![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/cuter44/wxpay-sdk?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
