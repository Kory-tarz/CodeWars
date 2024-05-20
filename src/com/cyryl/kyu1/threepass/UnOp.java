package com.cyryl.kyu1.threepass;

public class UnOp implements Ast {

    private String valueType;
    private int n;

    UnOp(String valueType, int n) {
        this.valueType = valueType;
        this.n = n;
    }

    public int n() {
        return n;
    }

    @Override
    public String op() {
        return new StringBuilder("{'op':'")
                .append(valueType)
                .append("','n':")
                .append(n)
                .append("}")
                .toString();
    }
}
