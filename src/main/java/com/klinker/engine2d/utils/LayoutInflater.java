package com.klinker.engine2d.utils;


import com.klinker.engine2d.gui.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Stack;


public class LayoutInflater {

    public static final HashMap<String, Class<? extends View>> TAG_MAPPING = new HashMap<>();
    static {
        TAG_MAPPING.put("View", View.class);
        TAG_MAPPING.put("ViewGroup", ViewGroup.class);
        TAG_MAPPING.put("TextView", TextView.class);
        TAG_MAPPING.put("Button", Button.class);
    }

    public static ViewGroup inflate(final Scene scene, String layoutRes) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                Stack<View> tagStack = new Stack<>();

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    Log.d("START %s", qName);
                        try {
                        if (qName.equals("Scene")) {

                        } else if (qName.equals("background")) {
                            if (!(tagStack.peek() instanceof View)) throw new ClassCastException("");
                        } else {
                                Class<? extends View> klass = (Class<? extends View>) Class.forName(TAG_MAPPING.get(qName).getName());
                                Method method = klass.getMethod("newInstance", Scene.class, Attributes.class);
                                method.setAccessible(true);
                                View view = (View) method.invoke(null, scene, attributes);
                                tagStack.push(view);
                        }
                    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        Log.d("Problem creating class for " + qName);
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    Log.d("START %s", qName);
                }
            };

            saxParser.parse(new File(layoutRes), handler);

            return null;
        } catch (ParserConfigurationException | SAXException e) {
            Log.e("Error parsing layout " + layoutRes, e);
            return null;
        } catch (IOException e) {
            Log.e("Could not find layout " + layoutRes, e);
            return null;
        }
    }

}
