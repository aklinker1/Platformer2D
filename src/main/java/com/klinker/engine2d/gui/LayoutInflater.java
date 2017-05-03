package com.klinker.engine2d.gui;


import com.klinker.engine2d.utils.Log;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Stack;


public class LayoutInflater {

    public static boolean DEBUG = false;

    public static final HashMap<String, Class<? extends View>> TAG_MAPPING = new HashMap<>();
    static {
        TAG_MAPPING.put("View", View.class);
        TAG_MAPPING.put("ViewGroup", ViewGroup.class);
        TAG_MAPPING.put("TextView", TextView.class);
        TAG_MAPPING.put("Button", Button.class);
    }

    private static ViewGroup root;

    public static ViewGroup inflate(final Scene scene, String layoutRes) {
        if (layoutRes == null) return null;
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                Stack<View> viewStack = new Stack<>();
                Stack<Attributes> backgroundStacks = new Stack<>();

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (DEBUG) Log.d("START %s", qName);
                    try {
                        if (qName.equals("Scene")) {
                            scene.initializeCamera(attributes);
                            scene.init();
                            if (DEBUG) Log.d("Camera: " + scene.getCamera().toString());
                            ViewGroup root = new ViewGroup(scene);
                            viewStack.push(root);
                        } else if (qName.equals("background")) {
                            if (!(viewStack.peek() instanceof View)) throw new ClassCastException("");
                            backgroundStacks.push(attributes);
                        } else if (qName.equals("selected")) {
                            // do stuff in the endElement
                        } else {
                            Class<? extends View> klass = (Class<? extends View>) Class.forName(TAG_MAPPING.get(qName).getName());
                            Constructor constructor = klass.getConstructor(Scene.class, Attributes.class);//klass.getMethod("newInstance", Scene.class, Attributes.class);
                            //constructor.setAccessible(true);
                            View view = (View) constructor.newInstance(scene, attributes);
                            viewStack.push(view);
                        }
                    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                        Log.e("Problem creating class for " + qName, e);
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (DEBUG) Log.d("END %s", qName);
                    if (qName.equals("Scene")) {
                        LayoutInflater.root = (ViewGroup) viewStack.pop();
                    } else if (qName.equals("background")) {
                        Attributes bgAttrs = backgroundStacks.pop();
                        View view = viewStack.peek();
                        View.StateObject.setBackground(bgAttrs, view);
                    } else if (qName.equals("selected")) {
                        viewStack.peek().setState(View.State.SELECTED);
                    } else {
                        View view = viewStack.pop();
                        ((ViewGroup) viewStack.peek()).addView(view);
                        if (DEBUG) Log.d("View: " + view.getPosition() + ", " + view.getSize());
                        if (view.getBackground() != null && DEBUG)  Log.d("Background:" + view.getBackground().position + ", " + view.getSize());
                    }
                }
            };

            saxParser.parse(new File(layoutRes), handler);

            return root;
        } catch (ParserConfigurationException | SAXException e) {
            Log.e("Error parsing layout " + layoutRes, e);
            return null;
        } catch (IOException e) {
            Log.e("Could not find layout " + layoutRes, e);
            return null;
        }
    }

}
