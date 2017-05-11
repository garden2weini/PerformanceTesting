package tmp.statemachine;
/**
 * Created by dora on 2017/5/11.
 */
public class StateEnum {
// 其中共有三个状态（待支付、待收货、结束）以及两个引起状态迁移的事件（支付、收货），
    // 其中支付事件PAY会触发状态从待支付UNPAID状态到待收货WAITING_FOR_RECEIVE状态的迁移，
    // 而收货事件RECEIVE会触发状态从待收货WAITING_FOR_RECEIVE状态到结束DONE状态的迁移。

    public enum States {
        UNPAID,                 // 待支付
        WAITING_FOR_RECEIVE,    // 待收货
        DONE                    // 结束
    }

    public enum Events {
        PAY,        // 支付
        RECEIVE     // 收货
    }


}
