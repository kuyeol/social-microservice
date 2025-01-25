package org.acme;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import org.acme.ext.terran.model.TerranModel;

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


}
