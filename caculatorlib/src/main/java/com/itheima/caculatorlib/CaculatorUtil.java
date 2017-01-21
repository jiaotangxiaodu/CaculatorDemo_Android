package com.itheima.caculatorlib;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Created by jiaotangxiaodu on 2017-1-19.
 */

public class CaculatorUtil {
    private static Stack<String> sTempStack = new Stack<>();
    private static List<String> sPostExp = new ArrayList<>();

    public synchronized static String getResult(List<String> inputs) throws MalformExpressionException {
        inputs = new ArrayList<>(inputs);
        parsePostExp(inputs);
        return calculatePostExp();
    }

    /**
     * 转化成后缀表达式并且储存在静态成员变量中
     * @param inputs
     * @throws MalformExpressionException
     */
    private static void parsePostExp(List<String> inputs) throws MalformExpressionException {
        sTempStack.clear();
        sPostExp.clear();

        handleSpecialNumber(inputs);

        StringBuffer sb = new StringBuffer();
        for (String input:inputs) {
            if(!isNumber(input)){
                input = " " + input + " ";
            }
            sb.append(input);
        }
        String[] split = sb.toString().split(" ");
        inputs = new ArrayList<>();

        for (String unit:split) {
            if(!TextUtils.isEmpty(unit) && !" ".equals(unit)){
                inputs.add(unit);
            }
        }

        for (int i = 0 ; i < inputs.size() ; i++){
            String str = inputs.get(i);
            if(isNumber(str)){
                sPostExp.add(str);
                continue;
            }
            if(str.equals("(")){
                sTempStack.push(str);
                continue;
            }
            if(str.equals(")")){
                popUtilLeft();
                continue;
            }
            //处理运算符号
            handleOpeation(str);
        }
        while(!sTempStack.isEmpty()){
            sPostExp.add(sTempStack.pop());
        }
    }

    private static void handleSpecialNumber(List<String> inputs) {
        for (int i = 0; i < inputs.size(); i++) {
            String input = inputs.get(i);
            if (input == "e"){
                inputs.set(i,Math.E + "");
            }
            if(input == "π"){
                inputs.set(i,Math.PI + "");
            }
        }
    }

    /**
     * 计算后缀表达式
     * @return
     */
    private static String calculatePostExp() throws MalformExpressionException{
        try {
            sTempStack.clear();
            for (String unit:sPostExp) {
                if(isNumber(unit)){
                    sTempStack.push(unit);
                }else{
                    handlePostExpOperation(unit);
                }
            }
            if(sTempStack.size() != 1){
                throw new MalformExpressionException();
            }
            String result = sTempStack.pop();
            return parseLongIfPrecise(result,0.000001);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new MalformExpressionException();
        } catch (EmptyStackException e){
            e.printStackTrace();
            throw new MalformExpressionException();
        }
    }

    private static String parseLongIfPrecise(String result, double precision) throws NumberFormatException{
        try {
            long l = Long.parseLong(result);
            return l + "";
        } catch (NumberFormatException e) {
            double d = Double.parseDouble(result);
            long l = (long) (d + 0.5);
            return Math.abs(d-l)<precision ? l+"" : d +"";
        }

    }

    private static void handlePostExpOperation(String unit) throws NumberFormatException,EmptyStackException{
        switch (unit){
            case "＋":
                handlePlus();
                break;
            case "－":
                handleMinus();
                break;
            case "×":
                handleMulti();
                break;
            case "÷":
                handleDivide();
                break;
            case "!":
                handleFac();
                break;
            case "^":
                handlePow();
                break;
            case "√":
                handleSqrt();
                break;
            case "sin":
                handleSin();
                break;
            case "cos":
                handleCos();
                break;
            case "ln":
                handleLn();
                break;
            case "lg":
                handleLg();
                break;
        }
    }

    private static void handleLg() {
        String p1 = sTempStack.pop();
        double p1Double = Double.parseDouble(p1);
        sTempStack.push(Math.log(p1Double) / Math.log(10) + "");
    }

    private static void handleLn() {
        String p1 = sTempStack.pop();
        sTempStack.push(Math.log(Double.parseDouble(p1)) + "");
    }

    private static void handleCos() {
        String p1 = sTempStack.pop();
        sTempStack.push(Math.cos(Double.parseDouble(p1)) + "");
    }

    private static void handleSin()  throws NumberFormatException,EmptyStackException {
        String p1 = sTempStack.pop();
        sTempStack.push(Math.sin(Double.parseDouble(p1)) + "");
    }

    private static void handleSqrt() throws NumberFormatException,EmptyStackException {
        String p1 = sTempStack.pop();
        sTempStack.push(Math.sqrt(Double.parseDouble(p1)) + "");
    }

    private static void handlePow() throws NumberFormatException,EmptyStackException {
        String p1 = sTempStack.pop();
        String p2 = sTempStack.pop();
        String result;
        if(p1.contains(".") || p2.contains(".")){
            result = Math.pow(Double.parseDouble(p2),Double.parseDouble(p1)) + "";
        }else{
            long p1Long = Long.parseLong(p1);
            if(p1Long < 0){
                result = Math.pow(Double.parseDouble(p2),p1Long) + "";
            }else{
                result = pow(Long.parseLong(p2), p1Long)+"";
            }
        }
        sTempStack.push(result);
    }




    private static void handleFac()  throws NumberFormatException,EmptyStackException{
        String p1 = sTempStack.pop();
        long result =  fac(Integer.parseInt(p1));
        sTempStack.push(result+"");

    }


    private static void handlePlus() throws NumberFormatException,EmptyStackException{
        String p1 = sTempStack.pop();
        String p2 = sTempStack.pop();
        String result;
        if(p1.contains(".") || p2.contains(".")){
            result = Double.parseDouble(p2) + Double.parseDouble(p1)+"";
        }else{
            result = Long.parseLong(p2) + Long.parseLong(p1) + "";
        }
        sTempStack.push(result);
    }
    private static void handleMinus()  throws NumberFormatException,EmptyStackException{
        String p1 = sTempStack.pop();
        String p2 = sTempStack.pop();
        String result;
        if(p1.contains(".") || p2.contains(".")){
            result = Double.parseDouble(p2) - Double.parseDouble(p1)+"";
        }else{
            result = Long.parseLong(p2) - Long.parseLong(p1) + "";
        }
        sTempStack.push(result);
    }
    private static void handleMulti() throws NumberFormatException,EmptyStackException {
        String p1 = sTempStack.pop();
        String p2 = sTempStack.pop();
        String result;
        if(p1.contains(".") || p2.contains(".")){
            result = Double.parseDouble(p2) * Double.parseDouble(p1)+"";
        }else{
            result = Long.parseLong(p2) * Long.parseLong(p1) + "";
        }
        sTempStack.push(result);
    }
    private static void handleDivide() throws NumberFormatException,EmptyStackException {
        String p1 = sTempStack.pop();
        String p2 = sTempStack.pop();
        String result;
        if(p1.contains(".") || p2.contains(".")){
            result = Double.parseDouble(p2) / Double.parseDouble(p1)+"";
            sTempStack.push(result);
            return;
        }
        long i1 = Long.parseLong(p1);
        long i2 = Long.parseLong(p2);
        if(i2 % i1 == 0){
            result = i2/i1+"";
        }else{
            result = (i2+.0d)/i1+"";
        }
        sTempStack.push(result);
    }


    private synchronized static void handleOpeation(String str) throws MalformExpressionException {
        int priority = getPriority(str);
        while (!sTempStack.isEmpty()){
            String optionOnStack = sTempStack.peek();
            int priorityOnStack = getPriority(optionOnStack);
            if(priorityOnStack >= priority){
                break;
            }
            if(optionOnStack.equals("(")){
                break;
            }
            sPostExp.add(sTempStack.pop());
        }
        sTempStack.push(str);

    }

    private static int getPriority(String str) throws MalformExpressionException {
        switch (str){
            case "(":
            case ")":
                return 0;
            case "sin":
            case "cos":
            case "tan":
            case "ln":
            case "lg":
                return 1;
            case "^":
            case "!":
            case "√":
                return 2;
            case "×":
            case "÷":
                return 3;
            case "＋":
            case "－":
                return 4;
            default:
                throw new MalformExpressionException();
        }
    }

    private static synchronized void popUtilLeft() throws MalformExpressionException {
        for (;;){
            if(sTempStack.isEmpty()){
                throw new MalformExpressionException();
            }
            String pop = sTempStack.pop();
            if(pop.equals("(")){
                break;
            }else{
                sPostExp.add(pop);
            }
        }
    }


    public static boolean isNumber(String s) {
        char[] chars = s.trim().toCharArray();
        for (char c:chars) {
            if(!Character.isDigit(c) && c != '.'){
                return false;
            }
        }
        return true;
    }

    public static long fac(int i) throws NumberFormatException {
        if(i < 0){
            throw new NumberFormatException();
        }
        if(i == 0){
            return 1;
        }
        return i * fac(i-1);

    }

    public static long pow(long i1, long i2)  throws NumberFormatException {
        if(i2 == 0){
            return 1;
        }
        return i1*pow(i1 , i2-1);
    }
    public static class MalformExpressionException  extends Exception{

    }
}
