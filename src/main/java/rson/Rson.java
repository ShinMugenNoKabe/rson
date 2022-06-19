package rson;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Static class used for serializing objects into a simple JSON String.
 *
 * @author Rufino Serrano Cañas
 */
public class Rson {

    private static Integer staticNumberOfBlankSpaces = 0;

    /**
     * Serializes an object into a JSON String.
     *
     * @param obj Object to serialize.
     * @return Serialized object in a String with a JSON-like format.
     */
    public static String stringify(Object obj) {
        return buildJsonString(obj, false);
    }

    /**
     * Serializes an object into JSON with an easy-to-read format. It has a
     * default value of 4 blank spaces.
     *
     * @param obj Object to serialize.
     * @return Serialized object in a String with a JSON-like prettified format.
     */
    public static String stringifyPretty(Object obj) {
        return stringifyPretty(obj, 4);
    }

    /**
     * Serializes an object into JSON with an easy-to-read format. You can
     * specify how many blank spaces the JSON String will have.
     *
     * @param obj Object to serialize.
     * @param numberOfBlankSpaces Number of blank spaces the JSON String will
     * have.
     * @return Serialized object in a String with a JSON-like prettified format.
     */
    public static String stringifyPretty(Object obj, Integer numberOfBlankSpaces) {
        staticNumberOfBlankSpaces = numberOfBlankSpaces;
        return buildJsonString(obj, true);
    }

    /**
     * Builds a String with a number of blank spaces passed as parameter.
     *
     * @param numberOfBlankSpaces Number of blank spaces the JSON String will
     * have.
     * @return String with blank spaces.
     */
    public static String buildBlankSpaces(Integer numberOfBlankSpaces) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numberOfBlankSpaces; i++) {
            sb.append(" ");
        }

        return sb.toString();
    }

    /**
     * Constructs de object into a JSON-like String.
     *
     * @param obj Object to serialize.
     * @param prettify If set to true, the String will have an easy-to-read
     * format.
     * @param numberOfBlankSpaces Number of blank spaces the JSON String will
     * have.
     */
    private static String buildJsonString(Object obj, boolean prettify) {
        return compareObject(obj, prettify, staticNumberOfBlankSpaces);
    }

    /**
     * Compares the type of every object passed as the first parameter and its
     * childs to serialize them.
     *
     * @param obj Object to serialize.
     * @param prettify If set to true, the String will have an easy-to-read
     * format.
     * @param accNumberOfBlankSpaces Accumilative number of blank spaces the
     * JSON String will have.
     * @return Serialized type of sub-object.
     */
    private static String compareObject(Object obj, boolean prettify, Integer accNumberOfBlankSpaces) {
        String serializedJsonString = "";

        if (obj == null) {
            return "null";
        } else if (obj instanceof String) {
            serializedJsonString += serializeString((String) obj);
        } else if (obj instanceof Collection) {
            serializedJsonString += serializeCollection((Collection) obj, prettify, accNumberOfBlankSpaces);
        } else if (obj instanceof Number || obj instanceof Boolean) {
            serializedJsonString += obj;
        } else if (obj instanceof Map) {
            serializedJsonString += serializeMap((Map) obj, prettify, accNumberOfBlankSpaces);
        } else if (obj instanceof Object) {
            serializedJsonString += serializeObject(obj, prettify, accNumberOfBlankSpaces);
        }

        // TODO: Comparar Map
        return serializedJsonString;
    }

    /**
     * Stringifies a single Java object.
     *
     * @param obj Object to serialize.
     * @param prettify If set to true, the String will have an easy-to-read
     * format.
     * @param accNumberOfBlankSpaces Accumilative number of blank spaces the
     * JSON String will have.
     * @return Serialized Java Object.
     */
    private static String serializeObject(Object obj, boolean prettify, Integer accNumberOfBlankSpaces) {
        String serializedJsonString = "{";

        String blankSpaces = buildBlankSpaces(accNumberOfBlankSpaces);
        String previousBlankSpaces = blankSpaces.substring(0, blankSpaces.length() - staticNumberOfBlankSpaces);

        if (prettify) {
            serializedJsonString += "\n" + blankSpaces;
        }

        Class<?> cl = obj.getClass();

        TreeMap<String, Object> mapObjectValueByObjectName = new TreeMap<>();

        for (Field field : cl.getDeclaredFields()) {
            field.setAccessible(true);

            try {
                mapObjectValueByObjectName.put(field.getName(), field.get(obj));
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        for (Entry<String, Object> object : mapObjectValueByObjectName.entrySet()) {

            serializedJsonString += "\"" + object.getKey() + "\": "
                    + compareObject(object.getValue(), prettify, (accNumberOfBlankSpaces + staticNumberOfBlankSpaces))
                    + (!object.getKey().equals(mapObjectValueByObjectName.lastKey()) ? ", " + (prettify ? ("\n" + blankSpaces) : "")
                    : (prettify ? ("\n" + previousBlankSpaces) : ""));
        }

        serializedJsonString += "}";

        return serializedJsonString;
    }

    /**
     * Stringifies a single String.
     *
     * @param obj String to serialize.
     * @return Serialized String.
     */
    private static String serializeString(String obj) {
        return "\"" + obj + "\"";
    }

    /**
     * Stringifies a single Collection.
     *
     * @param collection Collection to serialize.
     * @param prettify If set to true, the String will have an easy-to-read
     * format.
     * @param accNumberOfBlankSpaces Accumilative number of blank spaces the
     * JSON String will have.
     * @return Serialized Collection.
     */
    private static String serializeCollection(Collection collection, boolean prettify, Integer accNumberOfBlankSpaces) {
        String serializedCollectionString = "[";

        String blankSpaces = buildBlankSpaces(accNumberOfBlankSpaces);
        String previousBlankSpaces = buildBlankSpaces(accNumberOfBlankSpaces - staticNumberOfBlankSpaces);

        if (prettify) {
            serializedCollectionString += "\n" + blankSpaces;
        }

        Iterator<Object> iterator = collection.iterator();

        while (iterator.hasNext()) {
            Object element = iterator.next();

            serializedCollectionString += compareObject(element, prettify, (accNumberOfBlankSpaces + staticNumberOfBlankSpaces));

            if (iterator.hasNext()) {
                serializedCollectionString += ", ";

                if (prettify) {
                    serializedCollectionString += "\n" + blankSpaces;
                }
            } else if (prettify) {
                serializedCollectionString += "\n" + previousBlankSpaces;
            }
        }

        serializedCollectionString += "]";

        return serializedCollectionString;
    }

    /**
     * Stringifies a single Map.
     *
     * @param map Map to serialize.
     * @param prettify If set to true, the String will have an easy-to-read
     * format.
     * @param accNumberOfBlankSpaces Accumilative number of blank spaces the
     * JSON String will have.
     * @return Serialized Map.
     */
    private static String serializeMap(Map map, boolean prettify, Integer accNumberOfBlankSpaces) {
        String serializedJsonString = "{";

        String blankSpaces = buildBlankSpaces(accNumberOfBlankSpaces);
        String previousBlankSpaces = blankSpaces.substring(0, blankSpaces.length() - staticNumberOfBlankSpaces);

        if (prettify) {
            serializedJsonString += "\n" + blankSpaces;
        }

        String lastElementKey = getLastElementKey(map);

        for (Object element : map.entrySet()) {

            Entry<Object, Object> entry = (Entry<Object, Object>) element;

            serializedJsonString += "\"" + entry.getKey().toString() + "\": "
                    + compareObject(entry.getValue(), prettify, (accNumberOfBlankSpaces + staticNumberOfBlankSpaces))
                    + (!entry.getKey().toString().equals(lastElementKey.toString()) ? ", " + (prettify ? ("\n" + blankSpaces) : "")
                    : (prettify ? ("\n" + previousBlankSpaces) : ""));
        }

        serializedJsonString += "}";

        return serializedJsonString;
    }

    /**
     * Returns the key of the last element of a map.
     *
     * @param map Map to get the last key.
     * @return Key of the key of the last element of the map.
     */
    private static String getLastElementKey(Map map) {
        int count = 1;

        Entry<Object, Object> lastElement = null;

        for (Object object : map.entrySet()) {
            Entry<Object, Object> entry = (Entry<Object, Object>) object;

            if (count == map.size()) {
                lastElement = entry;
            }

            count++;
        }

        return lastElement.getKey().toString();
    }

}
