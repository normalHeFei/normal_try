package wk.statemachine;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;

/**
 * @author: fei.he
 */
@Configuration
@ComponentScan(basePackages = "wk.statemachine")
public class SmMain implements CommandLineRunner {

    public static void main(String[] args) {
        new SpringApplicationBuilder().web(WebApplicationType.NONE)
                .sources(SmMain.class)
                .build()
                .run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        UntypedStateMachine sm = buildTransitions();
        TradeContext context = new TradeContext();
        context.setQuoteId("q0001");
        context.setTradeOrdId("t001");
        sm.fire("submitTradeAuth", context);

    }

    public static UntypedStateMachine buildTransitions() {
        UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(CfetsQuoteStateMachine.class);
        // 提交交易审批单
        builder.externalTransition().from("orderApprove").to("tradeApproveSubmit").on("submitTradeAuth").callMethod("submitTradeAuth");
        // 收到交易审批单审核通过后,更新本地审批状态
        builder.externalTransition().from("tradeApproveSubmit").to("tradeApproveApply").on("recvTradeApproveApply").callMethod("updateLocalAuthStatus");
        // 收到交易审批单审核拒绝后,撤销交易审批单
        builder.externalTransition().from("tradeApproveSubmit").to("tradeApproveReject").on("recvTradeApproveReject").callMethod("writeOffTradeApprove");
        // 发送报价
        builder.externalTransition().from("tradeApproveApply").to("quotePadding").on("sendQuote").callMethod("sendQuote");

        return builder.newUntypedStateMachine("orderApprove");

    }
}
