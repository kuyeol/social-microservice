package org.acme;

import java.lang.reflect.*;
import java.security.KeyFactory;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;

import org.acme.ext.terran.model.TerranModel;
import org.python.core.exceptions;

public class Main
{

    Vector attribs;

    static Hashtable props;

    static Hashtable ignore;

    static String[] initProps = {"name"};

    static String[] intitIgnore = {"password"};

    static String unknown = "unknown";

    private int idx;

    Vector results;

    private Object attrib[];

    private Map<String, Class<?>> map;

    long start;

    private int currentIndex;

    private boolean lastValueWasNull;
    static {
        props = new Hashtable();
        for (int i = 0; i < initProps.length; i++) {
            props.put(initProps[i], unknown);
        }
        ignore = new Hashtable();
        for (int i = 0; i < intitIgnore.length; i++) {
            ignore.put(intitIgnore[i], unknown);
        }
    }
    private Object[] attributes;


    public Main()
    {
        Properties sysprops = System.getProperties();
        props = (Hashtable) props.clone();
        Enumeration enum_ = sysprops.keys();
        while (enum_.hasMoreElements()) {
            Object key = enum_.nextElement();
            if (!ignore.containsKey(key)) {
                props.put(key, sysprops.get(key));
            }
        }
        results = new Vector();
        start   = System.currentTimeMillis();
    }


    private Object getNextAttribute()
    {
        if (currentIndex < attrib.length) {
            return attrib[currentIndex++];
        }
        return null; // 모든 데이터를 읽은 경우
    }


    public List<byte[]> readBytes()
    {
        return attribs; // attribs 반환 (읽기 전용으로 제공 가능)
    }


    public Main(Vector<?> attributes)
    {
        this.attribs = attributes;
    }


    public void writeByte(byte x)
    {
        attribs.add(Byte.valueOf(x));
    }


    public byte readByte()
    {
        Byte attrib = (Byte) getNextAttribute();
        return (attrib == null) ? 0 : attrib.byteValue();
    }


    public void writeBytes(byte[] x)
    {
        if (x == null) {
            throw new IllegalArgumentException("Input byte array cannot be null");
        }
        attribs.add(x); // 바이트 배열 추가
    }


    public static void main(String[] args)
    {
        String text      = "Hello, World!";
        byte[] byteArray = text.getBytes();

        Vector<String> att = new Vector<>();
        //String         s   = "new String()";
        //att.add(s);

        Main m = new Main(att);
        m.writeBytes(byteArray);
        List<byte[]> dd = m.readBytes();

        byte[] by1 =  dd.get(0);
        System.out.println(new String(by1)+"  !!!!!");
        System.out.println("Read Byte : " + dd);

        for (byte[] bytes : m.readBytes()) {
            System.out.println(new String(bytes));
        }

        System.out.println(m);
    }


    public static void typeTest(String s)
    {

        TerranModel ty = TerranModel.toStr(s);
        System.out.println(ty);
    }


    @Override
    public String toString()
    {
        return "Main{" + "attribs=" + attribs + '}';
    }





    static void getServiceLoader( final Class<?>[] interfaces,  InvocationHandler h) throws IllegalArgumentException,
                                                                                            NoSuchMethodException,
                                                                                            InvocationTargetException,
                                                                                            InstantiationException,
                                                                                            IllegalAccessException
    {

        Class<?>[] cl = interfaces.clone();

        Class<?>       clElement   = cl.getClass();
        Constructor<?> constructor = clElement.getConstructor(InvocationHandler.class);

        constructor.newInstance(new Object[]{h});

    }

    private static final Comparator<Method> ORDER_BY_SIGNATURE_AND_SUBTYPE = new Comparator<Method>() {
        @Override public int compare(Method a, Method b) {
            int comparison =ORDER_BY_SIGNATURE.compare(a, b);
            if (comparison != 0) {
                return comparison;
            }
            Class<?> aClass = a.getDeclaringClass();
            Class<?> bClass = b.getDeclaringClass();
            if (aClass == bClass) {
                return 0;
            } else if (aClass.isAssignableFrom(bClass)) {
                return 1;
            } else if (bClass.isAssignableFrom(aClass)) {
                return -1;
            } else {
                return 0;
            }
        }
    };


      public static final Comparator<Method> ORDER_BY_SIGNATURE = new Comparator<Method>() {
            @Override public int compare(Method a, Method b) {
                if (a == b) {
                    return 0;
                }
                int comparison = a.getName().compareTo(b.getName());
                if (comparison == 0) {

                    if (comparison == 0) {
                        // This is necessary for methods that have covariant return types.
                        Class<?> aReturnType = a.getReturnType();
                        Class<?> bReturnType = b.getReturnType();
                        if (aReturnType == bReturnType) {
                            comparison = 0;
                        } else {
                            comparison = aReturnType.getName().compareTo(bReturnType.getName());
                        }
                    }
                }
                return comparison;
            }
        };



    private static native Class<?> generateProxy(String name, Class<?>[] interfaces,
                                                 ClassLoader loader, Method[] methods,
                                                 Class<?>[][]exceptions);




    private static List<Method> getMethods(Class<?>[] interfaces) {
        List<Method> result = new ArrayList<Method>();
        try {
            result.add(Object.class.getMethod("equals", Object.class));
            result.add(Object.class.getMethod("hashCode", Object.class));
            result.add(Object.class.getMethod("toString",  Object.class));
        } catch (NoSuchMethodException e) {
            throw new AssertionError();
        }

        getMethodsRecursive(interfaces, result);
        return result;
    }

    /**
     * Fills {@code proxiedMethods} with the methods of {@code interfaces} and
     * the interfaces they extend. May contain duplicates.
     */
    private static void getMethodsRecursive(Class<?>[] interfaces, List<Method> methods) {
        for (Class<?> i : interfaces) {
            getMethodsRecursive(i.getInterfaces(), methods);
            Collections.addAll(methods, i.getDeclaredMethods());
        }
    }

}
