package com.example.ddd.intra;

import java.util.List;

//인프라스트럭처
public class DroolsRuleEngine {
    private KieContainer kContainer;

    public DroolsRuleEngine() {
        KieServices ks = KieServices.Factory.get();
        kContainer = ks.getKieClasspathContainer();
    }

    public void evalute(String sessionName, List<?> facts){
        KieSession kSession = kContainer.newKieSession(sessionName);
        try{
            facts.forEach(x -> kSession.insert(x));
            kSession.fireAllRules();
        }finally{
            kSession.dispose();
        }
    }
}
