package wk.statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

/**
 * just for test
 */
@StateMachineParameters(stateType = String.class, eventType = String.class, contextType = TradeContext.class)
class CfetsQuoteStateMachine extends AbstractUntypedStateMachine {

    @Autowired
    private OtherBean otherBean;


    public void submitTradeAuth(String from, String to, String event, TradeContext context) {
        System.out.println("from:" + from + " to: " + to + " on: " + event + " context: " + context);
        System.out.println("submit trade auth");

        otherBean.invoke();
    }


}