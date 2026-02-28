package cloud.palmbiz.domain.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 银行账户信息值对象
 */
@Getter
@AllArgsConstructor
public class BankAccount {
    private String bankName;
    private String bankCardName;
    private String bankCardNo;

    public static BankAccount empty() {
        return new BankAccount(null, null, null);
    }

    public static BankAccount of(String bankName, String bankCardName, String bankCardNo) {
        return new BankAccount(bankName, bankCardName, bankCardNo);
    }

    public boolean hasAccountInfo() {
        return bankName != null && bankCardName != null && bankCardNo != null
                && !bankName.isEmpty() && !bankCardName.isEmpty() && !bankCardNo.isEmpty();
    }
}
