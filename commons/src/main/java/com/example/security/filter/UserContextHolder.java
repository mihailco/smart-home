package com.example.security.filter;

public class UserContextHolder {
    private static final ThreadLocal<Integer> threadLocalId = new ThreadLocal<>();

    public static void setId(Integer value) {
        threadLocalId.set(value);
    }

    public static Integer getId() {
        return threadLocalId.get();
    }

    public static void clearId() {
        threadLocalId.remove();
    }
}
