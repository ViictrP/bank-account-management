package rest.bankaccount;

import java.math.BigDecimal;

public class WithdrawDTO {

    private BigDecimal value;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
