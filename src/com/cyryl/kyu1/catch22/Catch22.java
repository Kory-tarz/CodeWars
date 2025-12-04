package com.cyryl.kyu1.catch22;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Catch22 {
    public static Yossarian loophole() throws Throwable {
        return new Catch22().hack();
    }

    public Yossarian hack() throws Throwable {
        Yossarian yossarian = new Yossarian();
        CrazyYossarian crazyYossarian = new CrazyYossarian();
        Unsafe unsafe = getUnsafe();

        long classOffset = findKlassOffset(unsafe, yossarian, crazyYossarian);
        System.out.println("Class offset: " + classOffset);
        long crazyClass = unsafe.getLong(crazyYossarian, classOffset);
        unsafe.putLong(yossarian, classOffset, crazyClass);
        System.out.println(yossarian.isCrazy());
        return yossarian;
    }


    private static long findKlassOffset(Unsafe unsafe, Object obj1, Object obj2) {
        for (long i = 0; i < 64; i += 8) {
            long v1 = unsafe.getLong(obj1, i);
            long v2 = unsafe.getLong(obj2, i);
            if (v1 != v2) {
                return i;
            }
        }
        throw new RuntimeException("Could not find klass offset");
    }


    private class CrazyYossarian {
        public boolean isCrazy() {
            return true;
        }
    }

    private static Unsafe getUnsafe() throws Exception {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (Unsafe) f.get(null);
    }


}
