package com.agtech.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

@RestController
@RequestMapping("/game")
public class GameCycleController {
    // GC状态：当前阶段，（阶段内）关数，是否中累计奖，王炸数, 这个要进缓存-对应gcid
    static Integer[] gcState = {0, 0, 0, 0};

    @ResponseBody
    @RequestMapping(path = "/{gameId}/bet", method = {RequestMethod.GET, RequestMethod.POST})
    public Long gameBet(@PathVariable Integer gameId) {
        // 找到对应的Game Logic实现并处理bet
        GameLogicA glA = new GameLogicA();
        long stakeID = glA.gameLogicBet(gameId, 10000, gcState);

        ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
        String gcRule = "%d>%d?%d:%d;";
        String tmp = String.format(gcRule, gcState);
        System.out.println(tmp);

        try {
            // 通过规则获得下一关是什么
            System.out.println(jse.eval(tmp));
        } catch (Exception t) {
        }
        return stakeID;
    }

    class GameLogicA {
        public long gameLogicBet(int gameid, int betPoint, Integer[] threadshold) {
            // RNG 解释
            // 处理积分
            // 处理奖池
            // 更新阈值 threadshold
            threadshold[3] = threadshold[3] + 5;
            threadshold[1] = threadshold[1] + 6;
            // 返回stake id
            return System.currentTimeMillis();
        }
    }
}