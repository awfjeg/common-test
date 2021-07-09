/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.husher.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class CText extends Canvas implements Listener {
    private Text text;

    private Color outerColor = getParent().getBackground();
    private Color borderColor  = new Color(Display.getDefault(),new RGB(255,0,0));
    // private Color borderColor  = new Color(Display.getDefault(),new RGB(202,202,204));

    public CText(Composite parent, int style) {
        super(parent,/*SWT.BORDER|*/SWT.DOUBLE_BUFFERED);
        GridLayout gl = new GridLayout();
        gl.marginWidth= gl.marginHeight = 0;
        gl.marginTop =  gl.marginBottom = 1;
        gl.marginLeft =   gl.marginRight = 1;
        this.setLayout(gl);  // 网格布局，充满
        text = new Text(this, SWT.NONE/*|SWT.BORDER*/);
        text.setLayoutData(new GridData(GridData.FILL_BOTH));
        this.addListener(SWT.Paint, this);
        this.addListener(SWT.Dispose,this);
    }

    //handle event
    @Override public void handleEvent(Event event) {
        switch(event.type){
        case SWT.Paint:onPaint(event);break;
        case SWT.Dispose:onDisposed();
        }
    }


    //paint
    private void onPaint(Event e) {
        Rectangle recttmp = text.getBounds();
        Point size = new Point(recttmp.width,recttmp.height);
        final int[] pointArray = new int[]{0,2,2,0,size.x-2,0,size.x,2,size.x,size.y-2,size.x-2,size.y,2,size.y,0,size.y-2,0,2};
        Region region = new Region();
        region.add(pointArray);
        text.setRegion(region);
        e.gc.setBackground(borderColor);
        e.gc.setForeground(borderColor);

        final int[] pointArray2 = new int[]{0,2,2,0,size.x-2+1,0,size.x+1,2,size.x+1,size.y-2+1,size.x-2+1,size.y+1,2,size.y+1,0,size.y-2+1,0,2};

        //  e.gc.fillPolygon(pointArray2);
        e.gc.drawPolyline(pointArray2);

        //  GraphicUtils.drawRoundRectangle(gc, rect.x,rect.y , rect.width-1, rect.height,outerColor,borderColor, true, true);
    }

    //disposed
    private void onDisposed() {
        if(outerColor!=null && !outerColor.isDisposed()){
            outerColor.dispose();
            outerColor = null;
        }

        if(borderColor!=null && !borderColor.isDisposed()){
            borderColor.dispose();
            borderColor = null;
        }
    }

    public void setText(String textValue){
        text.setText(textValue==null?"":textValue);
    }

    public String getText(){
        if(this.isDisposed()){
            return "";
        }
        return text.getText();
    }
}
