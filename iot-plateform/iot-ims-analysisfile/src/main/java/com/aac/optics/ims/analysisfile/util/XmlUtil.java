package com.aac.optics.ims.analysisfile.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlUtil {

    public static Map<String, Object> xmlToMap(String xmlPath) throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(xmlPath));
        Element root = document.getRootElement();
        Map<String, Object> map = new HashMap<String, Object>();
        convert(root, map);
        return map;
    }

    private static void convert(Element element, Map<String, Object> map) {
        if (element == null || map == null) {
            return;
        }
        List subElements = element.elements();
        if (subElements == null || subElements.size() <= 0) {
            return;
        }
        for (Object subElement : subElements) {
            if (!(subElement instanceof Element)) {
                continue;
            }
            String name = ((Element) subElement).getName();
            Object data = null;
            if (((Element) subElement).isTextOnly()) {
                data = ((Element) subElement).getData();
            } else {
                data = new HashMap<String, Object>();
                convert((Element) subElement, (Map<String, Object>) data);
            }
            Object preData = map.remove(name);
            if (preData != null) {
                if (preData instanceof List) {
                    ((List) preData).add(data);
                    map.put(name, preData);
                } else {
                    List list = new ArrayList();
                    list.add(preData);
                    list.add(data);
                    map.put(name, list);
                }
            } else if (data != null) {
                map.put(name, data);
            }
        }
    }
}