package com.dabinsystems.pact_app.Function.Chart;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.apache.poi.ss.formula.functions.Index;

import java.util.List;

public class CustomLineChartRenderer extends LineChartRenderer {

    private Float XOffset = null;
    private Float YOffset = null;

    public CustomLineChartRenderer(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    @Override
    public void drawValues(Canvas c) {

        try {

            List<ILineDataSet> dataSets = mChart.getLineData().getDataSets();

            for (int i = 0; i < dataSets.size(); i++) {

                ILineDataSet dataSet = dataSets.get(i);

                if (!shouldDrawValues(dataSet) || dataSet.getEntryCount() < 1)
                    continue;

                // apply the text-styling defined by the DataSet
                applyValueTextStyle(dataSet);

                Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

                // make sure the values do not interfear with the circles

                if (YOffset == null)
                    YOffset = (dataSet.getCircleRadius() * 1.75f);

                if (XOffset == null) XOffset = 0f;

                if (!dataSet.isDrawCirclesEnabled())
                    YOffset = YOffset / 2;

                mXBounds.set(mChart, dataSet);

                float[] positions = trans.generateTransformedValuesLine(dataSet, mAnimator.getPhaseX(), mAnimator
                        .getPhaseY(), mXBounds.min, mXBounds.max);


                ValueFormatter formatter = dataSet.getValueFormatter();

                MPPointF iconsOffset = MPPointF.getInstance(dataSet.getIconsOffset());
                iconsOffset.x = Utils.convertDpToPixel(iconsOffset.x);
                iconsOffset.y = Utils.convertDpToPixel(iconsOffset.y);

                for (int j = 0; j < positions.length - 6; j += 2) {

                    float x = positions[j];
                    float y = positions[j + 1];

                    float x2 = positions[j + 2];
//                    float y2 = positions[j + 3];

//                    float x3 = positions[j + 4];
                    float y3 = positions[j + 5];

//                if (!mViewPortHandler.isInBoundsRight(x))
//                    break;

//                if (!mViewPortHandler.isInBoundsLeft(x) || !mViewPortHandler.isInBoundsY(y))
//                    continue;

                    Entry entry = dataSet.getEntryForIndex(j / 2 + mXBounds.min);

                    if(entry == null) return;

                    XOffset = (x2 - x) / 2;
                    YOffset = 60f;

                    if (dataSet.isDrawValuesEnabled() && entry.getBoxHeight() != null && dataSet.getValueTextColor(j / 2) != -1) {
                        drawValue(c, formatter.getPointLabel(entry), x + XOffset, y + YOffset, dataSet.getValueTextColor(j / 2));
//                    InitActivity.logMsg("CustomLineChartRenderer", XOffset + " " + YOffset + " " + y2 + " " + y);
                    }
                    if (entry != null && entry.getIcon() != null && dataSet.isDrawIconsEnabled()) {

                        Drawable icon = entry.getIcon();

                        Utils.drawImage(
                                c,
                                icon,
                                (int) (x + iconsOffset.x),
                                (int) (y + iconsOffset.y),
                                icon.getIntrinsicWidth(),
                                icon.getIntrinsicHeight());

                    }
                }

                MPPointF.recycleInstance(iconsOffset);
            }

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public Float getXOffset() {
        return XOffset;
    }

    public void setXOffset(Float XOffset) {
        this.XOffset = XOffset;
    }

//    public Float getYOffset() {
//        return YOffset;
//    }
//
//    public void setYOffset(Float YOffset) {
//        this.YOffset = YOffset;
//    }
}
