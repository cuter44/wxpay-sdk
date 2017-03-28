# 微信支付/公众平台/消息响应/企业号服务器端SDK for Java

> yet another wheel for wx (╯‵д′)╯︵┻━┻

## Tutorial

A third-party encapsulation of Weixin(Wechat) Media Publicity / Service / Enterprise Account APIs. Gradually growing from only one simple pay feature to a large crowd of APIs. ("ㅍ‿ㅍ)

Usage is like:

    WxpayFactorySingl factory = WxpayFactorySingl.getInstance();

    UnifiedOrder order = ((UnifiedOrder)factory.instantiate(UnifiedOrder.class))
      .setBody          ("买买买!"                                            )
      .setTotalFee      (10970                                                )
      .setOpenid        ("oKKrIt0yPJ2OdoDZrrfORxWEnr0s"                       )
      .setOutTradeNo    ("wxpay-demopay-"+Long.toString(new Date().getTime()) )
      .setSpbillCreateIp(httpServletRequest.getRemoteAddr()                   )
      .setTradeType     (TradeType.JSAPI                                      )
      .build()
      .sign();

    UnifiedOrderResponse orderResp = order.execute();

As this example shows, this SDK is simple, stale, and not easy to use. Think before loving it.

For futher info / demos / detailed Chinese tutorials, please refer to project's [wiki](https://github.com/cuter44/wxpay-sdk/wiki)

## Release

Official release can be found in https://github.com/cuter44/wxpay-sdk/releases

## Sponser

This project is brought to you by:

* ![](https://avatars3.githubusercontent.com/u/9026603?v=3&s=40) [SCAU-SIDC](https://github.com/scau-sidc), a college student organization belongs to South China Agricultural University.

with love and humorousness.

## License & Acknowledgement

See [LICENSE.md](LICENSE.md).  

This project is NOT officially affiliated with Weixin(Wechat) or Tencent.

## Maven

Using Github self-hosted repo, refer to https://github.com/cuter44/wxpay-sdk/issues/17 for details.
