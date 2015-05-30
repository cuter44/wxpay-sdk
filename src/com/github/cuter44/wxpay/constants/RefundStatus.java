package com.github.cuter44.wxpay.constants;

/** RefundState
 * @since 0.4.1
 *
 */
public enum RefundStatus
{
    /** code=0, NOTSURE—未确定，需要商户原退款单号重新发起
     */
    NOTSURE(0),
    /** code=1, PROCESSING—退款处理中
     */
    PROCESSING(1),

    /** code=16, SUCCES—退款成功
     */
    SUCCESS(16),

    /** code=-1, FAIL—退款失败
     */
    FAIL(-1),

    /** code=-2, CHANGE—转入代发，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者财付通转账的方式进行退款。
     */
    CHANGE(-2);

    public byte code;

    private RefundStatus(int code)
    {
        this.code = (byte)code;
    }
}
