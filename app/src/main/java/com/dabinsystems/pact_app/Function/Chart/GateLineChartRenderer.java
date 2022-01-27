package com.dabinsystems.pact_app.Function.Chart;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.List;

public class GateLineChartRenderer extends LineChartRenderer {

    public GateLineChartRenderer(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    @Override
    public void drawValues(Canvas c) {

//        if (isDrawingValuesAllowed(mChart)) {

        List<ILineDataSet> dataSets = mChart.getLineData().getDataSets();

        for (int i = 0; i < dataSets.size(); i++) {

            LineDataSet dataSet = (LineDataSet) dataSets.get(i);

            if (!shouldDrawValues(dataSet) || dataSet.getEntryCount() < 1)
                continue;

            // apply the text-styling defined by the DataSet
            applyValueTextStyle(dataSet);

            Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

            // make sure the values do not interfear with the circles
            int valOffset = (int) (dataSet.getCircleRadius() * 1.75f);

            if (!dataSet.isDrawCirclesEnabled())
                valOffset = valOffset / 2;

            mXBounds.set(mChart, dataSet);

            float[] positions = trans.generateTransformedValuesLine(dataSet, mAnimator.getPhaseX(), mAnimator
                    .getPhaseY(), mXBounds.min, mXBounds.max);
            ValueFormatter formatter = dataSet.getValueFormatter();

            if (dataSet.isDrawGateSymbol()) {
                if (positions.length > 3) {
                    float x = positions[2];
                    float y = positions[3];

                    mValuePaint.setTextAlign(Paint.Align.LEFT);
                    drawValue(c, dataSet.getSymbolValue(), x + 2, y + valOffset + 10, dataSet.getValueTextColor(0));
                }
            }
            else if (dataSet.isDrawStartStop()) { // "start" "stop" 표시

                for (int j = 0; j < positions.length; j += 2) {

                    float x = positions[j];
                    float y = positions[j + 1];

                    if (!mViewPortHandler.isInBoundsRight(x))
                        break;

                    if (!mViewPortHandler.isInBoundsLeft(x) || !mViewPortHandler.isInBoundsY(y))
                        continue;

                    int idx = j / 2;
                    //Entry entry = dataSet.getEntryForIndex(idx + mXBounds.min);

                    if (idx == 1) {
                        mValuePaint.setTextAlign(Paint.Align.LEFT);
                        drawValue(c, "Delay", x + 2, y + valOffset + 10, dataSet.getValueTextColor(j / 2));
                    } else if (idx == 3) {
                        mValuePaint.setTextAlign(Paint.Align.LEFT);
                        drawValue(c, "Len", x + 2, y - valOffset, dataSet.getValueTextColor(j / 2));
                    }
                }

            } else {
                MPPointF iconsOffset = MPPointF.getInstance(dataSet.getIconsOffset());
                iconsOffset.x = Utils.convertDpToPixel(iconsOffset.x);
                iconsOffset.y = Utils.convertDpToPixel(iconsOffset.y);

                for (int j = 0; j < positions.length; j += 2) {

                    float x = positions[j];
                    float y = positions[j + 1];

                    if (!mViewPortHandler.isInBoundsRight(x))
                        break;

                    if (!mViewPortHandler.isInBoundsLeft(x) || !mViewPortHandler.isInBoundsY(y))
                        continue;

                    Entry entry = dataSet.getEntryForIndex(j / 2 + mXBounds.min);

                    if (dataSet.isDrawValuesEnabled()) {
                        drawValue(c, formatter.getPointLabel(entry), x, y - valOffset, dataSet.getValueTextColor(j / 2));
                    }
                    if (entry.getIcon() != null && dataSet.isDrawIconsEnabled()) {

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

        }
//        }
    }

    protected void drawCircles(Canvas c) {

        mRenderPaint.setStyle(Paint.Style.FILL);

        float phaseY = mAnimator.getPhaseY();

        mCirclesBuffer[0] = 0;
        mCirclesBuffer[1] = 0;

        List<ILineDataSet> dataSets = mChart.getLineData().getDataSets();

        for (int i = 0; i < dataSets.size(); i++) {

            LineDataSet dataSet = (LineDataSet) dataSets.get(i);

            if (!dataSet.isVisible() || !dataSet.isDrawCirclesEnabled() ||
                    dataSet.getEntryCount() == 0)
                continue;

            mCirclePaintInner.setColor(dataSet.getCircleHoleColor());

            Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

            mXBounds.set(mChart, dataSet);

            float circleRadius = dataSet.getCircleRadius();
            float circleHoleRadius = dataSet.getCircleHoleRadius();
            boolean drawCircleHole = dataSet.isDrawCircleHoleEnabled() &&
                    circleHoleRadius < circleRadius &&
                    circleHoleRadius > 0.f;
            boolean drawTransparentCircleHole = drawCircleHole &&
                    dataSet.getCircleHoleColor() == ColorTemplate.COLOR_NONE;

            DataSetImageCache imageCache;

            if (mImageCaches.containsKey(dataSet)) {
                imageCache = mImageCaches.get(dataSet);
            } else {
                imageCache = new DataSetImageCache();
                mImageCaches.put(dataSet, imageCache);
            }

            boolean changeRequired = imageCache.init(dataSet);

            // only fill the cache with new bitmaps if a change is required
            if (changeRequired) {
                imageCache.fill(dataSet, drawCircleHole, drawTransparentCircleHole);
            }

            int boundsRangeCount = mXBounds.range + mXBounds.min;

            for (int j = mXBounds.min; j <= boundsRangeCount; j++) {

                Entry e = dataSet.getEntryForIndex(j);

                if (e == null) break;

                if (dataSet.isDrawGateSymbol()) {
                    if (j == 1) {
                        // 원 그림
                    } else {
                        continue;
                    }
                }
                else if (dataSet.isDrawStartStop()) { // "start" "stop" 표시
                    if (j == 1 || j == 3) {
                        // 원 그림
                    } else {
                        continue;
                    }
                }

                mCirclesBuffer[0] = e.getX();
                mCirclesBuffer[1] = e.getY() * phaseY;

                trans.pointValuesToPixel(mCirclesBuffer);

                if (!mViewPortHandler.isInBoundsRight(mCirclesBuffer[0]))
                    break;

                if (!mViewPortHandler.isInBoundsLeft(mCirclesBuffer[0]) ||
                        !mViewPortHandler.isInBoundsY(mCirclesBuffer[1]))
                    continue;

                Bitmap circleBitmap = imageCache.getBitmap(j);

                if (circleBitmap != null) {
                    c.drawBitmap(circleBitmap, mCirclesBuffer[0] - circleRadius, mCirclesBuffer[1] - circleRadius, null);
                }
            }
        }
    }

}
