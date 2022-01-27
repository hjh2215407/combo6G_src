
package com.dabinsystems.pact_app.Marker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import me.grantland.widget.AutofitTextView;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
@SuppressLint("ViewConstructor")
public class CustomMarkerView extends MarkerView {

    private Context Context;
    private AutofitTextView tvContent;
    private FrameLayout flParent;
    private LinearLayout linMarkerParent;
    private LayoutParams parentParams;

    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = (AutofitTextView) findViewById(R.id.tvContent);
        flParent = findViewById(R.id.flParent);

        LayoutParams params = new LayoutParams(10, 20);
        setLayoutParams(params);

    }

    public CustomMarkerView(Context context, int normal, int continuous) {
        super(context, normal, continuous);

        tvContent = findViewById(R.id.tvContent);
        flParent = findViewById(R.id.flParent);
    }


    public CustomMarkerView(Context context, int normalResource, int selectRes, int fixedRes, int semMark) {
        super(context, normalResource, selectRes, fixedRes, semMark);

        tvContent = findViewById(R.id.tvContent);
        flParent = findViewById(R.id.flParent);

        setupLayoutResource(normalResource, "");
    }

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {



//        if (e instanceof CandleEntry) {
//
//            CandleEntry ce = (CandleEntry) e;
//
//            tvContent.setText(Utils.formatNumber(ce.getHigh(), 2, true));
//        } else {
//
//            tvContent.setText(Utils.formatNumber(e.getY(), 2, true));
//        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

    @Override
    public void drawNormalMark(Canvas canvas, float posX, float posY, String msg) {

        flParent = findViewById(R.id.flParent);
        tvContent = findViewById(R.id.tvContent);

        super.drawNormalMark(canvas, posX, posY, msg);

//        InitActivity.logMsg("DrawMarker", "PosX : " + posX + " PosY : " + posY + " offsetX : " + getOffsetForDrawingAtPoint(posX, posY).x + " offsetY : " + getOffsetForDrawingAtPoint(posX, posY).y);
    }

    @Override
    public void drawSelectMark(Canvas canvas, float posX, float posY, String msg) {
        super.drawSelectMark(canvas, posX, posY, msg);
    }

    @Override
    public void drawForContinuous(Canvas canvas, float posX, float posY, String msg) {
        super.drawForContinuous(canvas, posX, posY, msg);
    }

    @Override
    public void drawSemMark(Canvas canvas, float posX, float posY, String msg) {
        super.drawSemMark(canvas, posX, posY, msg);
    }

    @Override
    public void drawRightArrow(Canvas canvas, float posX, float posY, String msg) {
        super.drawRightArrow(canvas, posX, posY, msg);
    }

    @Override
    public void setupLayoutResource(int layoutResource, String msg) {
        super.setupLayoutResource(layoutResource, msg);

        tvContent = findViewById(R.id.tvContent);
        tvContent.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(1, mContext));
        tvContent.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, mContext));
        tvContent.setText(msg);

    }

}
