package com.liferay.ide.intellij.swingspy;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.keymap.impl.ModifierKeyDoubleClickHandler;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.util.ui.UIUtil;

import java.awt.Component;
import java.awt.Container;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;

/**
 * @author Andy Wu
 */
public class SpyAction extends AnAction {

    public SpyAction()
    {
        super("SwingSpy");
    }

    public void actionPerformed(AnActionEvent event)
    {
        Window window = UIUtil.getActiveWindow();

        //System.out.println("windows num:"+Window.getWindows().length);

        Point point = MouseInfo.getPointerInfo().getLocation();

        stringBuffer = new StringBuffer();

        findComponents(window,point);

        String result = stringBuffer.toString();

        if( result.trim().length() < 1 )
        {
            result = "can't find components at current mouse position";
        }

        SwingSpyInfoPopup popup = new SwingSpyInfoPopup();

        popup.setContent(result);

        JBPopupFactory.getInstance().createComponentPopupBuilder(popup,null).createPopup().showInFocusCenter();


        stringBuffer.delete(0, stringBuffer.length());
    }

    private void findComponents(Container container, Point point)
    {
        Component[] children = container.getComponents();

        for (Component child : children) {

            int cX = child.getX();
            int cY = child.getY();
            int width = child.getWidth();
            int height = child.getHeight();

            if(child.isShowing())
            {
                Rectangle bounds = child.getBounds();

                bounds.setLocation(child.getLocationOnScreen());

                if(bounds.contains(point))
                {
                    stringBuffer.append(child.getClass().getName()+ " [" + cX + "," + cY + "," + width + "," + height+"]\n");
                }
            }

            if (child instanceof Container) {
                Container con = (Container) child;

                findComponents(con,point);
            }
        }
    }

    private StringBuffer stringBuffer;

    static
    {
        //double click Ctrl to trigger this action
        ModifierKeyDoubleClickHandler.getInstance().registerAction("swingspy", 17, -1, false);
    }
}