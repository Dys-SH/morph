package com.bob.android.morph.translator;

import android.support.annotation.StringDef;

import com.bob.android.morph.bean.Condition;
import com.bob.android.morph.util.TextUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * created by cly on 2019-09-17
 */
public class ConditionTranslator {

    public static Object trans(List<Condition> conditions, String jsonData) {
        if (conditions == null || conditions.size() == 0) return null;
        for (Condition condition : conditions) {
            Object result = trans(condition, jsonData);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private static Object trans(Condition condition, String jsonData) {
        Condition.LeftOrRight left = condition.getLeft();
        Condition.LeftOrRight right = condition.getRight();
        if (left == null || right == null) {
            if (TextUtil.isEmpty(condition.getOperation())) {//无操作符
                return condition.getResult();
            }
            //无左右边区，有操作符直接运算
            boolean b = OperationValueTranslator.transValueOp(condition.getValue(), condition.getOperation(), jsonData);
            return b ? condition.getResult() : null;
        }
        boolean judge = judge(left, right, condition.getOperation(), jsonData);
        return judge ? condition.getResult() : null;
    }

    private static boolean judge(Condition.LeftOrRight left, Condition.LeftOrRight right, String operation, String jsonData) {
        boolean result;
        boolean count = true;

        boolean leftB = false;
        if (left.getLeft() != null) {//有值 值+操作符判断
            leftB = judge(left.getLeft(), left.getRight(), left.getOperation(), jsonData);
        } else if (TextUtil.isNotEmpty(left.getOperation())) {//有操作符 操作符判断
            leftB = OperationValueTranslator.transValueOp(left.getValue(), left.getOperation(), jsonData);
        } else {
            count = false;
        }
        boolean rightB = false;
        if (right.getLeft() != null) {
            rightB = judge(right.getLeft(), right.getRight(), right.getOperation(), jsonData);
        } else if (TextUtil.isNotEmpty(right.getOperation())) {
            rightB = OperationValueTranslator.transValueOp(right.getValue(), right.getOperation(), jsonData);
        } else {
            count = false;
        }
        if (!count) {//没有经过操作符运算 直接值+操作符输出结果
            result = OperationValueTranslator.transValue(left.getValue(), right.getValue(), operation, jsonData);
        } else {//经过操作符运算 2Boolean值+操作符计算结果
            result = OperationValueTranslator.transBoolean(leftB, rightB, operation);
        }
        return result;
    }

    private static class OperationValueTranslator {

        static boolean transValueOp(String value, String operation, String jsonData) {
            String trueValue = JsonDataTranslator.transJsonData(jsonData, value);
            if (operation.equals(OperationValue.NOT_EMPTY)) {
                return TextUtil.isNotEmpty(trueValue);
            } else if (operation.equals(OperationValue.EMPTY)) {
                return TextUtil.isEmpty(trueValue);
            }
            return false;
        }

        static boolean transValue(String left, String right, String operation, String jsonData) {
            String leftTrue = JsonDataTranslator.transJsonData(jsonData, left);
            String rightTrue = JsonDataTranslator.transJsonData(jsonData, right);
            if (operation.equals(OperationValue.EQ)) {
                return leftTrue.equals(rightTrue);
            } else if (operation.equals(OperationValue.NE)) {
                return !leftTrue.equals(rightTrue);
            }
            if (TextUtil.isNumeric(leftTrue) || TextUtil.isNumeric(rightTrue)) {
                return false;
            }
            int leftInt = Integer.parseInt(leftTrue);
            int rightInt = Integer.parseInt(rightTrue);
            switch (operation) {
                case OperationValue.GT:
                    return leftInt > rightInt;
                case OperationValue.GE:
                    return leftInt >= rightInt;
                case OperationValue.LT:
                    return leftInt < rightInt;
                case OperationValue.LE:
                    return leftInt <= rightInt;
            }
            return false;
        }

        static boolean transBoolean(boolean left, boolean right, String operation) {
            if (operation.equals(OperationValue.AND)) {
                return left && right;
            } else if (operation.equals(OperationValue.OR)) {
                return left || right;
            }
            return false;
        }
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    private @interface OperationValue {
        String NOT_EMPTY = "notEmpty";
        String EMPTY = "empty";
        String AND = "&&";
        String OR = "||";
        String EQ = "==";// =
        String NE = "!=";// !=
        String GT = ">";// >
        String GE = ">=";// >=
        String LT = "<";// <
        String LE = "<=";// <=
    }
}
