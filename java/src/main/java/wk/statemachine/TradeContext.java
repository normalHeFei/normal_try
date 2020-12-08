package wk.statemachine;

/**
 * @author: fei.he
 */
public class TradeContext {
    private String quoteId;
    private String tradeOrdId;

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getTradeOrdId() {
        return tradeOrdId;
    }

    public void setTradeOrdId(String tradeOrdId) {
        this.tradeOrdId = tradeOrdId;
    }

    @Override
    public String toString() {
        return "TradeContext{" +
                "quoteId='" + quoteId + '\'' +
                ", tradeOrdId='" + tradeOrdId + '\'' +
                '}';
    }
}
