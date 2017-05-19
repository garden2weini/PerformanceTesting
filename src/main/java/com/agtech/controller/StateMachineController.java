package com.agtech.controller;

import com.agtech.controller.statemachine.EventConfig;
import com.agtech.controller.statemachine.StateEnum.Events;
import com.agtech.controller.statemachine.StateEnum.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statemachine")
public class StateMachineController {
    @Autowired
    private StateMachine<States, Events> stateMachine;

    @RequestMapping(path = "/demo", method = {RequestMethod.GET, RequestMethod.POST})
    public String run() {
        stateMachine.start();
        stateMachine.sendEvent(Events.PAY);
        stateMachine.sendEvent(Events.RECEIVE);
        return "State Machine OK!";
    }

    @RequestMapping(path = "/demo1", method = {RequestMethod.GET, RequestMethod.POST})
    public String run1() {
        EventConfig event = new EventConfig();
        event.create();
        event.pay();
        event.receive();
        return "State Machine(Event) OK!";
    }
}
