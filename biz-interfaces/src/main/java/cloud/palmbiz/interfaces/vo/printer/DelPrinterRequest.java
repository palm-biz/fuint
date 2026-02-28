package cloud.palmbiz.interfaces.vo.printer;

/**
 * 删除打印机请求参数
 */
public class DelPrinterRequest extends RestRequest {

    /**
     * 打印机编号集合
     */
    private String[] snlist;

    public String[] getSnlist() {
        return snlist;
    }

    public void setSnlist(String[] snlist) {
        this.snlist = snlist;
    }
}
