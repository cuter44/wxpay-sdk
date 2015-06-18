package com.github.cuter44.wxpay.constants;

/** TradeState
 * @since 0.4.1
 *
 * Notice that <code>code</code> is NOT officially defined by wx.
 * And since wx does not provide a status diagram, some state codes are tentative.
 * It may be changed in later version. Take consideration before using them.
 */
public enum TradeState
{
    /** code=0, NOTPAY—未支付
     */
    NOTPAY(0),
    /** code=1, NOPAY--未支付(输入密码或确认支付超时)
     */
    NOPAY(1),

    /** code=2, USERPAYING--用户支付中
     */
    USERPAYING(2),

    /** code=16, SUCCESS—支付成功
     */
    SUCCESS(16),

    /** code=32, REFUND—转入退款
     */
    REFUND(32),

    /** code=64, REVOKED—已撤销
     */
    REVOKED(64),

    /** code=-1, PAYERROR--支付失败(其他原因，如银行返回失败)
     */
    PAYERROR(-1),

    /** code=-128, CLOSED—已关闭
     */
    CLOSED(-128);


    public byte code;

    private TradeState(int code)
    {
        this.code = (byte)code;
    }
}
