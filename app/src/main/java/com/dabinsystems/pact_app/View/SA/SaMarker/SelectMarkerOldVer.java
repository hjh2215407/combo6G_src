package com.dabinsystems.pact_app.View.SA.SaMarker;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class SelectMarkerOldVer extends LayoutBase {

    private LinearLayout linMarker1, linMarker2, linMarker3, linMarker4, linMarker5;
    private AutofitTextView tvMarker1, tvMarker2, tvMarker3, tvMarker4, tvMarker5;
    private ArrayList<View> mMarkerList1, mMarkerList2, mMarkerList3, mMarkerList4, mMarkerList5;
    private DynamicView mDynamicView;

    public SelectMarkerOldVer(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            try {

                initView();

                mActivity.runOnUiThread(() -> {

                    binding.linRightMenu.removeAllViews();

                    binding.linRightMenu.addView(linMarker1);
                    binding.linRightMenu.addView(linMarker2);
                    binding.linRightMenu.addView(linMarker3);
                    binding.linRightMenu.addView(linMarker4);
                    binding.linRightMenu.addView(linMarker5);

                    binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.marker_name));

                });

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity.getApplicationContext());

        mMarkerList1 = mDynamicView.addRightMenuButton("Marker1");
        mMarkerList2 = mDynamicView.addRightMenuButton("Marker2");
        mMarkerList3 = mDynamicView.addRightMenuButton("Marker3");
        mMarkerList4 = mDynamicView.addRightMenuButton("Marker4");
        mMarkerList5 = mDynamicView.addRightMenuButton("Marker5");

        linMarker1 = (LinearLayout) mMarkerList1.get(0);
        linMarker2 = (LinearLayout) mMarkerList2.get(0);
        linMarker3 = (LinearLayout) mMarkerList3.get(0);
        linMarker4 = (LinearLayout) mMarkerList4.get(0);
        linMarker5 = (LinearLayout) mMarkerList5.get(0);

        tvMarker1 = (AutofitTextView) mMarkerList1.get(1);
        tvMarker2 = (AutofitTextView) mMarkerList2.get(1);
        tvMarker3 = (AutofitTextView) mMarkerList3.get(1);
        tvMarker4 = (AutofitTextView) mMarkerList4.get(1);
        tvMarker5 = (AutofitTextView) mMarkerList5.get(1);

        tvMarker1.setTag("unselect");
        tvMarker2.setTag("unselect");
        tvMarker3.setTag("unselect");
        tvMarker4.setTag("unselect");
        tvMarker5.setTag("unselect");

        setUpEvents();

    }

    @Override
    public void setUpEvents() throws NullPointerException {

        mActivity.runOnUiThread(() -> {

            linMarker1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                if (!ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().isMarker(0)) {
//
//                    selectMarker(linMarker1, tvMarker1);
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().selectMarker(0, true);
//
//                } else {
//                    binding.chart.getOnTouchListener().setSelectedHighlightIndex(0);
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().syncMarkerTableData();
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().syncSelectedMarkerFreq();
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().addMenu();
//
//                }
                }
            });

            linMarker2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                if (!ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().isMarker(1)) {
//                    selectMarker(linMarker2, tvMarker2);
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().selectMarker(1, true);
//                } else {
//                    binding.chart.getOnTouchListener().setSelectedHighlightIndex(1);
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().syncMarkerTableData();
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().syncSelectedMarkerFreq();
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().addMenu();
//
//                }
                }
            });

            linMarker3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                if (!ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().isMarker(2)) {
//
//                    selectMarker(linMarker3, tvMarker3);
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().selectMarker(2, true);
//
//                } else {
//                    binding.chart.getOnTouchListener().setSelectedHighlightIndex(2);
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().syncMarkerTableData();
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().syncSelectedMarkerFreq();
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().addMenu();
//
//                }
                }
            });

            linMarker4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                if (!ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().isMarker(3)) {
//                    selectMarker(linMarker4, tvMarker4);
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().selectMarker(3, true);
//                } else {
//                    binding.chart.getOnTouchListener().setSelectedHighlightIndex(3);
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().syncMarkerTableData();
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().syncSelectedMarkerFreq();
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().addMenu();
//
//                }
                }
            });

            linMarker5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                if (!ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().isMarker(4)) {
//                    selectMarker(linMarker5, tvMarker5);
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().selectMarker(4, true);
//                } else {
//                    binding.chart.getOnTouchListener().setSelectedHighlightIndex(4);
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().syncMarkerTableData();
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().syncSelectedMarkerFreq();
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().addMenu();
//
//                }
                }
            });

            binding.tvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().addMenu();

                }
            });


        });

    }

    private void setSelectMarker(LinearLayout parent, AutofitTextView textView, Boolean isSelect) throws NullPointerException {

        parent.setSelected(isSelect);

    }

    public void removeAllMarkerSelect() throws NullPointerException {

        new Thread(() -> {

            try {

                mActivity.runOnUiThread(() -> {


                    setSelectMarker(linMarker1, tvMarker1, false);
                    setSelectMarker(linMarker2, tvMarker2, false);
                    setSelectMarker(linMarker3, tvMarker3, false);
                    setSelectMarker(linMarker4, tvMarker4, false);
                    setSelectMarker(linMarker5, tvMarker5, false);

//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().getTvMarker1().setText("");
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().getTvMarkerXValue1().setText("");
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().getTvMarkerYValue1().setText("");
//
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().getTvMarker2().setText("");
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().getTvMarkerXValue2().setText("");
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().getTvMarkerYValue2().setText("");
//
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().getTvMarker3().setText("");
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().getTvMarkerXValue3().setText("");
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().getTvMarkerYValue3().setText("");
//
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().getTvMarker4().setText("");
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().getTvMarkerXValue4().setText("");
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().getTvMarkerYValue4().setText("");
//
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().getTvMarker5().setText("");
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().getTvMarkerXValue5().setText("");
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().getTvMarkerYValue5().setText("");

//                                    binding.tvMarkerName.setText("");
//                                    binding.tvMarkerValue.setText("");
//                                    binding.tvMarkerYVal.setText("");

                });

                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }).start();

    }

    public void selectMarker(int idx, boolean isSelect) throws NullPointerException {
        switch (idx) {
            case 0:
                if (isSelect) {
                    tvMarker1.setTag("select");
                    linMarker1.setSelected(true);
                } else {
                    tvMarker1.setTag("unselect");
                    linMarker1.setSelected(false);
                }
                break;

            case 1:
                if (isSelect) {
                    tvMarker2.setTag("select");
                    linMarker2.setSelected(true);
                } else {
                    tvMarker2.setTag("unselect");
                    linMarker2.setSelected(false);
                }
                break;

            case 2:
                if (isSelect) {
                    tvMarker3.setTag("select");
                    linMarker3.setSelected(true);
                } else {
                    tvMarker3.setTag("unselect");
                    linMarker3.setSelected(false);
                }
                break;

            case 3:
                if (isSelect) {
                    tvMarker4.setTag("select");
                    linMarker4.setSelected(true);
                } else {
                    tvMarker4.setTag("unselect");
                    linMarker4.setSelected(false);
                }
                break;

            case 4:
                if (isSelect) {
                    tvMarker5.setTag("select");
                    linMarker5.setSelected(true);
                } else {
                    tvMarker5.setTag("unselect");
                    linMarker5.setSelected(false);
                }
                break;
        }
    }

//    public void selectMarker1() throws NullPointerException {
//
//        try {
//
//            ViewHandler.getInstance().getSaMarkerView().getMarkerSetup()
//                    .selectMarker(0, !ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().isMarker(0));
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void selectMarker2() throws NullPointerException {
//
//        try {
//            ViewHandler.getInstance().getSaMarkerView().getMarkerSetup()
//                    .selectMarker(1, !ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().isMarker(1));
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void selectMarker3() throws NullPointerException {
//
//        try {
//
//            ViewHandler.getInstance().getSaMarkerView().getMarkerSetup()
//                    .selectMarker(2, !ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().isMarker(2));
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void selectMarker4() throws NullPointerException {
//
//        try {
//
//            ViewHandler.getInstance().getSaMarkerView().getMarkerSetup()
//                    .selectMarker(3, !ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().isMarker(3));
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void selectMarker5() {
//
//        try {
//            ViewHandler.getInstance().getSaMarkerView().getMarkerSetup()
//                    .selectMarker(4, !ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().isMarker(4));
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//
//    }

}
