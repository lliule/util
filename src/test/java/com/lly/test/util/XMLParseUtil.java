package com.lly.test.util;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;

public class XMLParseUtil {

    /**
     * 读取 指定配置文件中的 类信息，然后实例化对象
     * @param configPath configPath
     * @return
     */
    public static Object getBean(String configPath){
        DocumentBuilderFactoryImpl factory = new DocumentBuilderFactoryImpl();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document document = builder.parse(configPath);
            NodeList className = document.getElementsByTagName("className");
            Node child = className.item(0).getFirstChild();
            String value = child.getNodeValue();
            Class<?> name = Class.forName(value);
            return name.newInstance();
        } catch (Exception e) {
            return null;
        }

    }

}
