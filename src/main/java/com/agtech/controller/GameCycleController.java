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
    // GC状态：消除赛, 这个要进缓存-对应gcid
    static Integer[] gcState = {1, 0, 0, 0};

    @ResponseBody
    @RequestMapping(path = "/{gameId}/bet", method = {RequestMethod.GET, RequestMethod.POST})
    public String gameBet(@PathVariable Integer gameId) {
        long stakeID = -1;
        // 找到对应的Game Logic实现并处理bet
        if(gcState[0]==1) {
            GameLogicA gl = new GameLogicA();
            stakeID = gl.gameLogicBet(gameId, 10000, gcState);
        } else if(gcState[0]==2){
            GameLogicB gl = new GameLogicB();
            stakeID = gl.gameLogicBet(gameId, 10000, gcState);
        } else {
            return "GC over!";
        }
        ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
        String gcRule = "%d==1?(%d==1?0:(%d==8?2:1)):(%d==1?0:2);";
        String tmp = String.format(gcRule, gcState);
        System.out.println(tmp);

        try {
            // 通过规则获得下一关是什么
            Object tt = jse.eval(tmp);
            System.out.println("gcState[0]"+ tt);
            gcState[0] = Integer.valueOf(tt.toString());

        } catch (Exception t) {
            t.printStackTrace();
        }
        return stakeID + "+" + gcState[0].toString() + "+" + gcState[1].toString() + "+" + gcState[2].toString() + "+" + gcState[3].toString();
    }

    class GameLogicA {
        public long gameLogicBet(int gameid, int betPoint, Integer[] threadshold) {
            // RNG 解释
            // 处理积分
            // 处理奖池
            // 更新阈值 threadshold
            //threadshold[1] = threadshold[1] + 1;
            threadshold[2] = threadshold[2] + 1;

            // 返回stake id
            return System.currentTimeMillis();
        }
    }

    class GameLogicB {
        public long gameLogicBet(int gameid, int betPoint, Integer[] threadshold) {
            // RNG 解释
            // 处理积分
            // 处理奖池
            // 更新阈值 threadshold
            threadshold[3] = threadshold[3] + 1;

            // 返回stake id
            return System.currentTimeMillis();
        }
    }
}