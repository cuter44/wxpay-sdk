package com.github.cuter44.wxpay.constants;

/** TradeState
 * @since 0.4.1
 *
 * Notice that <code>code</code> is NOT officially defined by wx.
 * And since wx does not provide a status diagram, this distribution is a temporary solution.
 * It may be changed in later version. Take consideration before using them.
 */
public enum TradeState
{
    /** =0, NOTPAY—未支付
     */
    NOTPAY(0),
    /** NOPAY--未支付(输入密码或确认支付超时)
     */
    NOPAY(1),

    /** =2, USERPAYING--用户支付中
     */
    USERPAYING(2),

    /** =16, SUCCESS—支付成功
     */
    SUCCESS(16),

    /** =32, REFUND—转入退款
     */
    REFUND(32),

    /** =64, REVOKED—已撤销
     * This is an unclear state.
     */
    REVOKED(64),

    /** =-1, PAYERROR--支付失败(其他原因，如银行返回失败)
     */
    PAYERROR(-1),

    /** =-128, CLOSED—已关闭
     */
    CLOSED(-128);


    public byte code;

    private TradeState(int code)
    {
        this.code = (byte)code;
    }
}
