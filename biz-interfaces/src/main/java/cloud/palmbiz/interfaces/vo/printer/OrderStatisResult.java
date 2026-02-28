package cloud.palmbiz.interfaces.vo.printer;

/**
 * 订单统计结果
 */
public class OrderStatisResult {

    /**
     * 已打印订单数
     */
    private int printed;
    /**
     * 等待打印订单数
     */
    private int waiting;

    public int getPrinted() {
        return printed;
    }

    public void setPrinted(int printed) {
        this.printed = printed;
    }

    public int getWaiting() {
        return waiting;
    }

    public void setWaiting(int waiting) {
        this.waiting = waiting;
    }
}
