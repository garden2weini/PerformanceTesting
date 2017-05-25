package tmp;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * Created by dora on 2017/5/24.
 * http://www.journaldev.com/2389/java-8-features-with-examples#java8-time
 * http://www.jianshu.com/p/5b800057f2d8#
 */
public class HelloJDK8 {
    public static void main(String[] args) throws Exception {
        HelloJDK8 hello = new HelloJDK8();

        hello.helloNashorn();
        hello.helloLambda();
        hello.helloLambda2();
        hello.helloBase64();
    }
    /**
     * JDK8's new JS Engine.
     * 1. jjs ***.js, jjs -scripting ***.js. jjs是一个基于标准Nashorn引擎的命令行工具，可以接受js源码并执行。
     * 2. 内嵌Nashorn
     */
    public void helloNashorn() throws Exception {
        ScriptEngineManager engineManager =
            new ScriptEngineManager();
        ScriptEngine engine =
            engineManager.getEngineByName("nashorn");
        engine.eval("function sum(a, b) { return a + b; }");
        System.out.println(engine.eval("sum(1, 2);"));

        Invocable invocable = (Invocable) engine;
        System.out.println(invocable.invokeFunction("sum", 10, 2));

        NashornAdder adder = invocable.getInterface(NashornAdder.class);
        System.out.println(adder.sum(2, 3));
    }

    /**
     * Lambda表达式和函数式接口
     * note: 最简单的Lambda表达式可由逗号分隔的参数列表、->符号 和 语句块组成
     */
    public void helloLambda() {
        Arrays.asList( "a", "b", "d" ).forEach(e -> System.out.println(e) );
        // 函数接口指的是只有一个函数的接口，这样的接口可以隐式转换为Lambda表达式。
        // java.lang.Runnable和java.util.concurrent.Callable是函数式接口的最佳例子.
        // @FunctionalInterface:显式说明某个接口是函数式接口

        // Lambda表达式有返回值，返回值的类型也由编译器推理得出。如果Lambda表达式中的语句块只有一行，则可以不用使用return语句
        Arrays.asList( "a", "b", "d" ).sort( ( e1, e2 ) -> e1.compareTo( e2 ) );
    }

    /**
     * 不需要使用第三方库就可以进行Base64编码
     */
    public void helloBase64() {
        String text = "hello base64";
        final String encoded = Base64
            .getEncoder()
            .encodeToString( text.getBytes( StandardCharsets.UTF_8 ) );
        System.out.println( encoded );
    }

    interface IntegerMath {
        int operation(int a, int b);
    }

    public int operateBinary(int a, int b, IntegerMath op) {
        return op.operation(a, b);
    }

    public void helloLambda2() {
        HelloJDK8 myApp = new HelloJDK8();
        IntegerMath addition = (a, b) -> a + b;
        IntegerMath subtraction = (a, b) -> a - b;
        System.out.println("40 + 2 = " +
            myApp.operateBinary(40, 2, addition));
        System.out.println("20 - 10 = " +
            myApp.operateBinary(20, 10, subtraction));
    }



}

