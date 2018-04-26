package com.chaycao.webmvc.util;

import com.chaycao.webmvc.expection.WebMvcException;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;

/**
 * Title:用于类型转换
 * 支持：
 * 8种基本类型：int, boolean, char, short, long, float, double, byte
 * 引用类型：String
 * @author chaycao
 * @description:
 * @date 2018-04-26 15:41.
 */
public class TypeUtil {

    public static <T> T cast(Object obj, Class<T> clazz) {
        if(obj == null){
            if(clazz == int.class){
                return (T) Integer.valueOf(0);
            } else if(clazz == long.class){
                return (T) Long.valueOf(0);
            } else if(clazz == short.class){
                return (T) Short.valueOf((short) 0);
            } else if(clazz == byte.class){
                return (T) Byte.valueOf((byte) 0);
            } else if(clazz == float.class){
                return (T) Float.valueOf(0);
            } else if(clazz == double.class){
                return (T) Double.valueOf(0);
            } else if(clazz == boolean.class){
                return (T) Boolean.FALSE;
            }
            return null;
        }
        if(clazz == null){
            throw new IllegalArgumentException("clazz is null");
        }
        if(clazz == obj.getClass()){
            return (T)obj;
        }
        if(clazz == boolean.class || clazz == Boolean.class){
            return (T) castToBoolean(obj);
        }
        if(clazz == byte.class || clazz == Byte.class){
            return (T) castToByte(obj);
        }
        if(clazz == char.class || clazz == Character.class){
            return (T) castToChar(obj);
        }
        if(clazz == short.class || clazz == Short.class){
            return (T) castToShort(obj);
        }
        if(clazz == int.class || clazz == Integer.class){
            return (T) castToInt(obj);
        }
        if(clazz == long.class || clazz == Long.class){
            return (T) castToLong(obj);
        }
        if(clazz == float.class || clazz == Float.class){
            return (T) castToFloat(obj);
        }
        if(clazz == double.class || clazz == Double.class){
            return (T) castToDouble(obj);
        }
        if(clazz == String.class){
            return (T) castToString(obj);
        }
        throw new WebMvcException("can not cast to : " + clazz.getName());
    }

    public static String castToString(Object value){
        if(value == null){
            return null;
        }
        return value.toString();
    }

    public static Integer castToInt(Object value) {
        if(value == null){
            return null;
        }
        if(value instanceof Integer){
            return (Integer) value;
        }
        if(value instanceof Number){
            return ((Number) value).intValue();
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if(strVal.length() == 0 //
                    || "null".equals(strVal) //
                    || "NULL".equals(strVal)){
                return null;
            }
            return Integer.parseInt(strVal);
        }
        throw new WebMvcException("can not cast to int, value : " + value);
    }

    public static Boolean castToBoolean(Object value) {
        if(value == null){
            return null;
        }
        if(value instanceof Boolean){
            return (Boolean) value;
        }
        if(value instanceof Number){
            return ((Number) value).intValue() == 1;
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if(strVal.length() == 0 //
                    || "null".equals(strVal) //
                    || "NULL".equals(strVal)){
                return null;
            }
            if("true".equalsIgnoreCase(strVal) //
                    || "1".equals(strVal)){
                return Boolean.TRUE;
            }
            if("false".equalsIgnoreCase(strVal) //
                    || "0".equals(strVal)){
                return Boolean.FALSE;
            }
            if("Y".equalsIgnoreCase(strVal) //
                    || "T".equals(strVal)){
                return Boolean.TRUE;
            }
            if("F".equalsIgnoreCase(strVal) //
                    || "N".equals(strVal)){
                return Boolean.FALSE;
            }
        }
        throw new WebMvcException("can not cast to boolean, value : " + value);
    }

    public static Character castToChar(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof Character){
            return (Character) value;
        }
        if(value instanceof String){
            String strVal = (String) value;
            if(strVal.length() == 0){
                return null;
            }
            if(strVal.length() != 1){
                throw new WebMvcException("can not cast to char, value : " + value);
            }
            return strVal.charAt(0);
        }
        throw new WebMvcException("can not cast to char, value : " + value);
    }

    public static Short castToShort(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof Number){
            return ((Number) value).shortValue();
        }
        if(value instanceof String){
            String strVal = (String) value;
            if(strVal.length() == 0 //
                    || "null".equals(strVal) //
                    || "NULL".equals(strVal)){
                return null;
            }
            return Short.parseShort(strVal);
        }
        throw new WebMvcException("can not cast to short, value : " + value);
    }


    public static Long castToLong(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof Number){
            return ((Number) value).longValue();
        }
        if(value instanceof String){
            String strVal = (String) value;
            if(strVal.length() == 0 //
                    || "null".equals(strVal) //
                    || "NULL".equals(strVal)){
                return null;
            }
            try{
                return Long.parseLong(strVal);
            } catch(NumberFormatException ex){
                //
            }
        }
        throw new WebMvcException("can not cast to long, value : " + value);
    }

    public static Float castToFloat(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof Number){
            return ((Number) value).floatValue();
        }
        if(value instanceof String){
            String strVal = value.toString();
            if(strVal.length() == 0 //
                    || "null".equals(strVal) //
                    || "NULL".equals(strVal)){
                return null;
            }
            return Float.parseFloat(strVal);
        }
        throw new WebMvcException("can not cast to float, value : " + value);
    }

    public static Double castToDouble(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof Number){
            return ((Number) value).doubleValue();
        }
        if(value instanceof String){
            String strVal = value.toString();
            if(strVal.length() == 0 //
                    || "null".equals(strVal) //
                    || "NULL".equals(strVal)){
                return null;
            }
            return Double.parseDouble(strVal);
        }
        throw new WebMvcException("can not cast to double, value : " + value);
    }

    public static Byte castToByte(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof Number){
            return ((Number) value).byteValue();
        }
        if(value instanceof String){
            String strVal = (String) value;
            if(strVal.length() == 0 //
                    || "null".equals(strVal) //
                    || "NULL".equals(strVal)){
                return null;
            }
            return Byte.parseByte(strVal);
        }
        throw new WebMvcException("can not cast to byte, value : " + value);
    }


}
