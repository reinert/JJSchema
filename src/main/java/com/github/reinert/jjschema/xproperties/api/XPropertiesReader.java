package com.github.reinert.jjschema.xproperties.api;

import java.lang.reflect.AccessibleObject;
import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.xproperties.impl.DefaultXPropertiesReader;

/**
 *
 *
 * X Properties Reader
 *
 *
 * @author WhileTrueEndWhile
 *
 *
 */
public interface XPropertiesReader {

    /**
     * Fly Weight Instance
     */
    static XPropertiesReader instance = DefaultXPropertiesReader.instance;

    /**
     *
     *
     * Reads X Properties from a class.
     *
     * The result list writes the X Properties into a JSON schema.
     *
     *
     * <br />
     * Example:
     * <br />
     * <code>
     * &#64;XProperty({ "X-Greeting = \"Hello World\"" })
     * <br />
     * class MyClass {
     * <br />
     * }
     * </code>
     *
     *
     * @param type
     *             A class to read X Properties from.
     *
     * @return A list of X Properties to be inserted into the class schema.
     *
     *
     */
    List<XProperty> readFromClass(Class<?> type);

    /**
     *
     *
     * Reads X Properties from a field.
     *
     * The result list writes the X Properties into a JSON schema.
     *
     *
     * <br />
     * Example:
     * <br />
     * <code>
     * class MyClass {
     * <br />
     * &nbsp;&nbsp;&nbsp;&nbsp;&#64;XProperties({"X-Greeting = \"Hello World\""})
     * <br />
     * &nbsp;&nbsp;&nbsp;&nbsp;MyType myAttribute;
     * <br />
     * }
     * </code>
     *
     *
     * @param accessibleObj
     *                      A field to read X Properties from.
     *
     * @return A list of X Properties to be inserted into the field schema.
     *
     *
     */
    List<XProperty> readFromField(AccessibleObject accessibleObj);

    /**
     *
     * Reads X Properties from JsonProperty annotation instances.
     * 
     * The result list writes the JsonProperty attributes into a JSON schema.
     *
     *
     * <br />
     * Example:
     * <br />
     * <code>
     * class MyClass {
     * <br />
     * &nbsp;&nbsp;&nbsp;&nbsp;&#64;JsonProperty("MyAttribute")
     * <br />
     * &nbsp;&nbsp;&nbsp;&nbsp;MyType myAttribute;
     * <br />
     * }
     * </code>
     *
     *
     * @param type
     *               The class containing the fields to read from.
     *
     * @param schema
     *               Schema of the class containing the fields to read from.
     *
     * @return A list of X Properties to be inserted into the class schema.
     *
     */
    List<XProperty> readFromJsonProperty(Class<?> type, ObjectNode schema);

    /**
     *
     *
     * Reads the presence the oneOf annotation from a field.
     * 
     * The result list writes a schema that is supported by default.
     * Each class attribute of the field's type
     * gets an item of the oneOf list.
     *
     *
     * <br />
     * Example:
     * <br />
     * <code>
     * class MyClass {
     * <br />
     * &nbsp;&nbsp;&nbsp;&nbsp;&#64;OneOf()
     * <br />
     * &nbsp;&nbsp;&nbsp;&nbsp;MyType myAttribute;
     * <br />
     * }
     * </code>
     *
     *
     * @param type
     *               The class containing the fields to read from.
     *
     * @param schema
     *               Schema of the class containing the fields to read from.
     *
     * @return A list of X Properties to be inserted into the class schema.
     *
     *
     */
    List<XProperty> readOneOf(Class<?> type, ObjectNode schema);

}
