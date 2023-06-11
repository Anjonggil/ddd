package com.example.ddd.service;

import com.example.ddd.domain.entity.Money;
import com.example.ddd.domain.entity.OrderLine;
import com.example.ddd.intra.DroolsRuleEngine;

import java.util.Arrays;
import java.util.List;

public class CalculateDiscountService {
    private DroolsRuleEngine ruleEngine;

    public CalculateDiscountService() {
        ruleEngine = new DroolsRuleEngine();
    }

    public Money calculateDiscount(List<OrderLine> orderLines, String customerId){
        Customer customer = findCustomer(customerId);
        MotableMoney money = new MotableMoney(0);
        List<?> facts = Arrays.asList(customer, money);
        facts.addAll(orderLines);
        ruleEngine.evalute("discountCalculation", facts); // discountCalculation라는 세션명을 알아야함
        return money.toImmutableMoney();
    }

    //해당 구조를 사용할 경우 인프라스트럭처 영역에 대한 의존성을 가지고 있어서 이부분을 해서해야한다.
}
