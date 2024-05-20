package com.cyryl.kyu1.threepass;

public class BinOp implements Ast {

    private String operator;
    private Ast leftArg;
    private Ast rightArg;

    BinOp(String op, Ast left, Ast right) {
        this.operator = op;
        this.leftArg = left;
        this.rightArg = right;
    }

    BinOp(String op, Ast left) {
        this.operator = op;
        this.leftArg = left;
    }

    BinOp(Ast left) {
        this.leftArg = left;
    }

    public Ast a() {
        return leftArg;
    }

    public Ast b() {
        return rightArg;
    }

    public boolean isFull() {
        return rightArg != null && operator != null;
    }

    public void setRightArg(Ast ast) {
        if (isFull()) {
            throw new RuntimeException("Overriding argument: " + ast.op());
        }
        rightArg = ast;
    }

    public void setOperator(String operator) {
        if (this.operator != null) {
            throw new RuntimeException("Overriding operator: " + operator);
        }
        this.operator = operator;
    }

    @Override
    public String op() {
        return new StringBuilder("{'op':'")
                .append(operator)
                .append("','a':")
                .append(leftArg.op())
                .append(",'b':")
                .append(rightArg.op())
                .append("}")
                .toString();
    }
}
