package com.dabinsystems.pact_app.View.SA.SaMarker;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class SAMarkerSetup extends LayoutBase {

    private DynamicView dynamicView;
    private LinearLayout linSelect, linFreq, linMarkerType, linMarker, linMarkerTableButton, linMarkerPreset;
    private LinearLayout linMarkerTable, linMarkerList1, linMarkerList2, linMarkerList3, linMarkerList4, linMarkerList5;
    private AutofitTextView tvSelectedMarker, tvFreqValue, tvMarkerTypeRef, tvMarkerTypeDelta;
    private AutofitTextView tvMarkerOn, tvMarkerOff, tvMarkerTableOn, tvMarkerTableOff;
    private ArrayList<View> mSelectList, mFreqList, mMarkerTypeList, mMarkerList, mMarkerTableList, mMarkerPresetList;

    private AutofitTextView tvMarkerFreq;
    private AutofitTextView tvMarker1, tvMarker2, tvMarker3, tvMarker4, tvMarker5;
    private AutofitTextView tvMarkerXValue1, tvMarkerXValue2, tvMarkerXValue3, tvMarkerXValue4, tvMarkerXValue5;
    private AutofitTextView tvMarkerYValue1, tvMarkerYValue2, tvMarkerYValue3, tvMarkerYValue4, tvMarkerYValue5;

    private float mCurrentMarkerPos;

    private Boolean isRef = true;
    //    private Boolean isMarker = false;
    private Boolean[] isMarker = new Boolean[5];
    private Boolean isMarkerTable = false;

    private SelectMarkerOldVer mSelectMarkerOldVer;

    private String mCurrentMarkerName = "";

    public SAMarkerSetup(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }


    @Override
    public void initView() {
        super.initView();

        if(dynamicView != null) return;
        dynamicView = new DynamicView(mActivity.getApplicationContext());

        mSelectMarkerOldVer = new SelectMarkerOldVer(mActivity, binding);
        mSelectList = dynamicView.addRightMenuButton("Select", "");

        mCurrentMarkerPos = FunctionHandler.getInstance().getMainLineChart().getCenterFreq();
        mFreqList = dynamicView.addRightMenuButton("Frequency", mCurrentMarkerPos + " MHz");

        mMarkerTypeList = dynamicView.addRightMenuButton("Marker Type", "Ref", "Delta");
        mMarkerList = dynamicView.addRightMenuButton("Marker", "On", "Off");
        mMarkerTableList = dynamicView.addRightMenuButton("Marker Table", "On", "Off");
        mMarkerPresetList = dynamicView.addRightMenuButton("Marker Preset", Gravity.RIGHT | Gravity.CENTER_VERTICAL);

        linSelect = (LinearLayout) mSelectList.get(0);
        tvSelectedMarker = (AutofitTextView) mSelectList.get(2);

        linFreq = (LinearLayout) mFreqList.get(0);
        tvMarkerFreq = (AutofitTextView) mFreqList.get(1);
        tvFreqValue = (AutofitTextView) mFreqList.get(2);

        linMarkerType = (LinearLayout) mMarkerTypeList.get(0);
        tvMarkerTypeRef = (AutofitTextView) mMarkerTypeList.get(2);
        tvMarkerTypeDelta = (AutofitTextView) mMarkerTypeList.get(3);

        linMarker = (LinearLayout) mMarkerList.get(0);
        tvMarkerOn = (AutofitTextView) mMarkerList.get(2);
        tvMarkerOff = (AutofitTextView) mMarkerList.get(3);

        linMarkerTableButton = (LinearLayout) mMarkerTableList.get(0);
        tvMarkerTableOn = (AutofitTextView) mMarkerTableList.get(2);
        tvMarkerTableOff = (AutofitTextView) mMarkerTableList.get(3);

        linMarkerPreset = (LinearLayout) mMarkerPresetList.get(0);

        for (int i = 0; i < isMarker.length; i++) {
            isMarker[i] = false;
        }

        mActivity.runOnUiThread(()->{
            setMarkerRefType(isRef);
//        selectMarkerOnOff(isMarker);
            setMarkerTable(isMarkerTable);

            initMarkerTable();

            setUpEvents();
        });

    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            try {

                initView();

                mActivity.runOnUiThread(() -> {


                    binding.linRightMenu.removeAllViews();
                    binding.linRightMenu.addView(linSelect);
//                    if (SaView.getCurrentSelectedMenu() == SaView.Menu.VswrView ||
//                            SaView.getCurrentSelectedMenu() == SaView.Menu.CL) {
//                        tvMarkerFreq.setText(mActivity.getResources().getText(R.string.frequency_name));
//                        binding.linRightMenu.addView(linFreq);
//                    } else if (SaView.getCurrentSelectedMenu() == SaView.Menu.DtfView) {
//                        tvMarkerFreq.setText(mActivity.getResources().getText(R.string.distance_name));
//                        binding.linRightMenu.addView(linFreq);
//                    }

                    binding.linRightMenu.addView(linMarkerType);
                    binding.linRightMenu.addView(linMarker);
                    binding.linRightMenu.addView(linMarkerTableButton);
                    binding.linRightMenu.addView(linMarkerPreset);

                    binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.marker_name));

                });


            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }).start();

    }

    @Override
    public void setUpEvents() throws NullPointerException {
        super.setUpEvents();

        linSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectMarkerOldVer.addMenu();
            }
        });

        linFreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Freq 설정 다이얼로그 생성
//                if (SaView.getCurrentSelectedMenu() == SaView.Menu.VswrView)
//                    new SaMarkerFreqKeypad(mActivity, binding).show();

            }
        });

        linMarkerType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.lineChartLayout.mainLineChart.getOnTouchListener().getSelectedHighlightIndex() == -1 ||
                        binding.lineChartLayout.mainLineChart.getHighlighted().get(
                                binding.lineChartLayout.mainLineChart.getSelectedHighlightIndex()) == null ||
                        binding.lineChartLayout.mainLineChart.getHighlighted().get(0) == null) return;

                if (binding.lineChartLayout.mainLineChart.getOnTouchListener().getSelectedHighlightIndex() == 0) {
                    Toast.makeText(mActivity, "Marker # 1 can not be changed to delta.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isRef) {

                    isRef = false;
                    setMarkerRefType(isRef);

                    binding.lineChartLayout.mainLineChart.getHighlighted().get(
                            binding.lineChartLayout.mainLineChart.getSelectedHighlightIndex()
                    ).setMarkerTypeToDelta();

//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().syncMarkerTableData();

                } else {

                    isRef = true;
                    setMarkerRefType(isRef);

                    binding.lineChartLayout.mainLineChart.getHighlighted().get(
                            binding.lineChartLayout.mainLineChart.getOnTouchListener().getSelectedHighlightIndex()
                    ).setMarkerTypeToRef();

//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().syncMarkerTableData();

                }

            }
        });

        linMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int idx = binding.lineChartLayout.mainLineChart.getOnTouchListener().getSelectedHighlightIndex();
                if (idx == -1) idx = 0;

                if (isMarker[idx]) {

                    selectMarker(idx, false);
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().getSaSelectMarkerView().removeAllMarkerSelect();
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().addMenu();

                } else {

                    setMarkerPos((binding.lineChartLayout.mainLineChart.getXAxis().getAxisMinimum() + binding.lineChartLayout.mainLineChart.getXAxis().getAxisMaximum()) / 2f);
                    selectMarker(idx, true);
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().getSaSelectMarkerView().selectMarker1();
//                    ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().addMenu();

                }
            }
        });

        linMarkerTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isMarkerTable) {

                    isMarkerTable = false;
                    removeMarkerTable();

                } else {

                    isMarkerTable = true;
                    addMarkerTable();

                }
            }
        });

        linMarkerPreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeAllMarkers();

                if (binding.lineChartLayout.mainLineChart.getData().getDataSetByIndex(FunctionHandler.getInstance().getMainLineChart().getChartDataSetIndex()) == null ||
                        binding.lineChartLayout.mainLineChart.getData().getDataSetByIndex(FunctionHandler.getInstance().getMainLineChart().getChartDataSetIndex()).getEntryForIndex(
                                binding.lineChartLayout.mainLineChart.getData().getDataSetByIndex(FunctionHandler.getInstance().getMainLineChart().getChartDataSetIndex()).getEntryCount() / 2) == null) {

                    for (int i = 0; i < 5; i++) {

                        selectMarkerOnOff(i, false);

                    }

                    return;
                }

//                getSaSelectMarkerView().selectMarker1();
                selectMarkerList1();

            }
        });

        binding.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewHandler.getInstance().getSAView().selectMarker();

            }
        });

    }

    public SelectMarkerOldVer getSelectMarker() {

//        addMenu();

        return mSelectMarkerOldVer;
    }

    public void setMarkerRefType(Boolean isRef) throws NullPointerException {

        this.isRef = isRef;

        if (isRef) {

            tvMarkerTypeRef.setBackgroundColor(mActivity.getResources().getColor(R.color.colorRightSmallMenuSelectBack));
            tvMarkerTypeRef.setTextColor(mActivity.getResources().getColor(R.color.colorRightSmallMenuSelectText));

            tvMarkerTypeDelta.setBackgroundColor(mActivity.getResources().getColor(R.color.colorRightUnselectedButton));
            tvMarkerTypeDelta.setTextColor(mActivity.getResources().getColor(R.color.colorRightSmallMenuUnSelectText));


        } else {

            tvMarkerTypeDelta.setBackgroundColor(mActivity.getResources().getColor(R.color.colorRightSmallMenuSelectBack));
            tvMarkerTypeDelta.setTextColor(mActivity.getResources().getColor(R.color.colorRightSmallMenuSelectText));

            tvMarkerTypeRef.setBackgroundColor(mActivity.getResources().getColor(R.color.colorRightUnselectedButton));
            tvMarkerTypeRef.setTextColor(mActivity.getResources().getColor(R.color.colorRightSmallMenuUnSelectText));

        }

    }

    private void selectMarkerOnOff(int idx, Boolean isMarker) throws NullPointerException {

        this.isMarker[idx] = isMarker;

        if (isMarker) {

            tvMarkerOn.setBackgroundColor(mActivity.getResources().getColor(R.color.colorRightSmallMenuSelectBack));
            tvMarkerOn.setTextColor(mActivity.getResources().getColor(R.color.colorRightSmallMenuSelectText));

            tvMarkerOff.setBackgroundColor(mActivity.getResources().getColor(R.color.colorRightUnselectedButton));
            tvMarkerOff.setTextColor(mActivity.getResources().getColor(R.color.colorRightSmallMenuUnSelectText));


        } else {

            tvMarkerOff.setBackgroundColor(mActivity.getResources().getColor(R.color.colorRightSmallMenuSelectBack));
            tvMarkerOff.setTextColor(mActivity.getResources().getColor(R.color.colorRightSmallMenuSelectText));

            tvMarkerOn.setBackgroundColor(mActivity.getResources().getColor(R.color.colorRightUnselectedButton));
            tvMarkerOn.setTextColor(mActivity.getResources().getColor(R.color.colorRightSmallMenuUnSelectText));

        }

    }

    public void setMarkerTable(Boolean isMarkerTable) throws NullPointerException {

        this.isMarkerTable = isMarkerTable;

        if (isMarkerTable) {

            tvMarkerTableOn.setBackgroundColor(mActivity.getResources().getColor(R.color.colorRightSmallMenuSelectBack));
            tvMarkerTableOn.setTextColor(mActivity.getResources().getColor(R.color.colorRightSmallMenuSelectText));

            tvMarkerTableOff.setBackgroundColor(mActivity.getResources().getColor(R.color.colorRightUnselectedButton));
            tvMarkerTableOff.setTextColor(mActivity.getResources().getColor(R.color.colorRightSmallMenuUnSelectText));

        } else {

            tvMarkerTableOff.setBackgroundColor(mActivity.getResources().getColor(R.color.colorRightSmallMenuSelectBack));
            tvMarkerTableOff.setTextColor(mActivity.getResources().getColor(R.color.colorRightSmallMenuSelectText));

            tvMarkerTableOn.setBackgroundColor(mActivity.getResources().getColor(R.color.colorRightUnselectedButton));
            tvMarkerTableOn.setTextColor(mActivity.getResources().getColor(R.color.colorRightSmallMenuUnSelectText));

        }

    }

    public void initMarkerTable() throws NullPointerException {

        if (linMarkerTable != null) return;

        new Thread(() -> {


            linMarkerTable = new LinearLayout(mActivity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    0.25f);

            params.setMargins(40, 20, 40, 20);
            linMarkerTable.setLayoutParams(params);
            linMarkerTable.setWeightSum(1);
            linMarkerTable.setOrientation(LinearLayout.VERTICAL);

            linMarkerList1 = new LinearLayout(mActivity);
            linMarkerList2 = new LinearLayout(mActivity);
            linMarkerList3 = new LinearLayout(mActivity);
            linMarkerList4 = new LinearLayout(mActivity);
            linMarkerList5 = new LinearLayout(mActivity);

            LinearLayout.LayoutParams listParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    0.2f
            );

            linMarkerList1.setLayoutParams(listParams);
            linMarkerList2.setLayoutParams(listParams);
            linMarkerList3.setLayoutParams(listParams);
            linMarkerList4.setLayoutParams(listParams);
            linMarkerList5.setLayoutParams(listParams);

            linMarkerList1.setOrientation(LinearLayout.HORIZONTAL);
            linMarkerList2.setOrientation(LinearLayout.HORIZONTAL);
            linMarkerList3.setOrientation(LinearLayout.HORIZONTAL);
            linMarkerList4.setOrientation(LinearLayout.HORIZONTAL);
            linMarkerList5.setOrientation(LinearLayout.HORIZONTAL);

            linMarkerList1.setWeightSum(1);
            linMarkerList2.setWeightSum(1);
            linMarkerList3.setWeightSum(1);
            linMarkerList4.setWeightSum(1);
            linMarkerList5.setWeightSum(1);

            linMarkerList1.setBackground(mActivity.getResources().getDrawable(R.drawable.marker_table_unselected));
            linMarkerList2.setBackground(mActivity.getResources().getDrawable(R.drawable.marker_table_unselected));
            linMarkerList3.setBackground(mActivity.getResources().getDrawable(R.drawable.marker_table_unselected));
            linMarkerList4.setBackground(mActivity.getResources().getDrawable(R.drawable.marker_table_unselected));
            linMarkerList5.setBackground(mActivity.getResources().getDrawable(R.drawable.marker_table_unselected));

            tvMarker1 = new AutofitTextView(mActivity);
            tvMarker2 = new AutofitTextView(mActivity);
            tvMarker3 = new AutofitTextView(mActivity);
            tvMarker4 = new AutofitTextView(mActivity);
            tvMarker5 = new AutofitTextView(mActivity);

            LinearLayout.LayoutParams markerNameParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0.15f
            );

            tvMarker1.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tvMarker2.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tvMarker3.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tvMarker4.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tvMarker5.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

            tvMarker1.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getContext()));
            tvMarker2.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getContext()));
            tvMarker3.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getContext()));
            tvMarker4.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getContext()));
            tvMarker5.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getContext()));

            tvMarker1.setPadding(20, 0, 0, 0);
            tvMarker2.setPadding(20, 0, 0, 0);
            tvMarker3.setPadding(20, 0, 0, 0);
            tvMarker4.setPadding(20, 0, 0, 0);
            tvMarker5.setPadding(20, 0, 0, 0);

            tvMarker1.setLayoutParams(markerNameParams);
            tvMarker2.setLayoutParams(markerNameParams);
            tvMarker3.setLayoutParams(markerNameParams);
            tvMarker4.setLayoutParams(markerNameParams);
            tvMarker5.setLayoutParams(markerNameParams);

            tvMarkerXValue1 = new AutofitTextView(mActivity);
            tvMarkerXValue2 = new AutofitTextView(mActivity);
            tvMarkerXValue3 = new AutofitTextView(mActivity);
            tvMarkerXValue4 = new AutofitTextView(mActivity);
            tvMarkerXValue5 = new AutofitTextView(mActivity);

            LinearLayout.LayoutParams markerXValueParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0.35f
            );

            tvMarkerXValue1.setLayoutParams(markerXValueParams);
            tvMarkerXValue2.setLayoutParams(markerXValueParams);
            tvMarkerXValue3.setLayoutParams(markerXValueParams);
            tvMarkerXValue4.setLayoutParams(markerXValueParams);
            tvMarkerXValue5.setLayoutParams(markerXValueParams);

            tvMarkerXValue1.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tvMarkerXValue2.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tvMarkerXValue3.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tvMarkerXValue4.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tvMarkerXValue5.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

            tvMarkerXValue1.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getContext()));
            tvMarkerXValue2.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getContext()));
            tvMarkerXValue3.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getContext()));
            tvMarkerXValue4.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getContext()));
            tvMarkerXValue5.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getContext()));

            tvMarkerYValue1 = new AutofitTextView(mActivity);
            tvMarkerYValue2 = new AutofitTextView(mActivity);
            tvMarkerYValue3 = new AutofitTextView(mActivity);
            tvMarkerYValue4 = new AutofitTextView(mActivity);
            tvMarkerYValue5 = new AutofitTextView(mActivity);

            LinearLayout.LayoutParams markerYValueParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0.5f
            );

            tvMarkerYValue1.setLayoutParams(markerYValueParams);
            tvMarkerYValue2.setLayoutParams(markerYValueParams);
            tvMarkerYValue3.setLayoutParams(markerYValueParams);
            tvMarkerYValue4.setLayoutParams(markerYValueParams);
            tvMarkerYValue5.setLayoutParams(markerYValueParams);

            tvMarkerYValue1.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tvMarkerYValue2.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tvMarkerYValue3.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tvMarkerYValue4.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tvMarkerYValue5.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

            tvMarkerYValue1.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getContext()));
            tvMarkerYValue2.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getContext()));
            tvMarkerYValue3.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getContext()));
            tvMarkerYValue4.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getContext()));
            tvMarkerYValue5.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getContext()));

            mActivity.runOnUiThread(() -> {


                try {

                    linMarkerList1.addView(tvMarker1);
                    linMarkerList2.addView(tvMarker2);
                    linMarkerList3.addView(tvMarker3);
                    linMarkerList4.addView(tvMarker4);
                    linMarkerList5.addView(tvMarker5);

                    linMarkerList1.addView(tvMarkerXValue1);
                    linMarkerList2.addView(tvMarkerXValue2);
                    linMarkerList3.addView(tvMarkerXValue3);
                    linMarkerList4.addView(tvMarkerXValue4);
                    linMarkerList5.addView(tvMarkerXValue5);


                    linMarkerList1.addView(tvMarkerYValue1);
                    linMarkerList2.addView(tvMarkerYValue2);
                    linMarkerList3.addView(tvMarkerYValue3);
                    linMarkerList4.addView(tvMarkerYValue4);
                    linMarkerList5.addView(tvMarkerYValue5);

                    linMarkerTable.addView(linMarkerList1);
                    linMarkerTable.addView(linMarkerList2);
                    linMarkerTable.addView(linMarkerList3);
                    linMarkerTable.addView(linMarkerList4);
                    linMarkerTable.addView(linMarkerList5);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }


            });

        }).start();

    }

    public void selectMarKerListByIndex(int idx) {

        initMarkerTable();
        resetMarkerListColor();

        switch (idx) {

            case 0:
                selectMarkerList1();
                break;
            case 1:
                selectMarkerList2();
                break;
            case 2:
                selectMarkerList3();
                break;
            case 3:
                selectMarkerList4();
                break;
            case 4:
                selectMarkerList5();
                break;

        }

    }

    public void resetMarkerListColor() {

        if (linMarkerTable != null) {
            linMarkerList1.setBackground(mActivity.getResources().getDrawable(R.drawable.marker_table_unselected));
            linMarkerList2.setBackground(mActivity.getResources().getDrawable(R.drawable.marker_table_unselected));
            linMarkerList3.setBackground(mActivity.getResources().getDrawable(R.drawable.marker_table_unselected));
            linMarkerList4.setBackground(mActivity.getResources().getDrawable(R.drawable.marker_table_unselected));
            linMarkerList5.setBackground(mActivity.getResources().getDrawable(R.drawable.marker_table_unselected));
        }

    }

    public void selectMarkerList1() throws NullPointerException {

        initMarkerTable();
        resetMarkerListColor();

//        tvSelectedMarker.setText("Mkr1");
//        mCurrentMarkerName = "M1";

        if (binding.lineChartLayout.mainLineChart.getOnTouchListener()
                .getSelectedHighlightIndex() != -1 &&

                binding.lineChartLayout.mainLineChart.getHighlighted()
                        .get(binding.lineChartLayout.mainLineChart
                                .getOnTouchListener()
                                .getSelectedHighlightIndex()) != null)

            tvMarker1.setText("M1(" + binding.lineChartLayout.mainLineChart.getHighlighted().get(
                    binding.lineChartLayout.mainLineChart.getOnTouchListener().getSelectedHighlightIndex()).getMarkerType() + ")"
            );
        linMarkerList1.setBackground(mActivity.getResources().getDrawable(R.drawable.marker_table_selected));

    }


    public void selectMarkerList2() throws NullPointerException {

        initMarkerTable();
        resetMarkerListColor();
//        tvSelectedMarker.setText("Mkr2");
//        mCurrentMarkerName = "M2";

        tvMarker2.setText("M2(" + binding.lineChartLayout.mainLineChart.getHighlighted().get(
                binding.lineChartLayout.mainLineChart.getOnTouchListener().getSelectedHighlightIndex()).getMarkerType() + ")"
        );

        linMarkerList2.setBackground(mActivity.getResources().getDrawable(R.drawable.marker_table_selected));

    }

    public void selectMarkerList3() throws NullPointerException {

        initMarkerTable();
        resetMarkerListColor();
//        tvSelectedMarker.setText("Mkr3");
//        mCurrentMarkerName = "M3";

        if (binding.lineChartLayout.mainLineChart.getOnTouchListener().getSelectedHighlightIndex() != -1) {
            tvMarker3.setText("M3(" + binding.lineChartLayout.mainLineChart.getHighlighted().get(
                    binding.lineChartLayout.mainLineChart.getOnTouchListener().getSelectedHighlightIndex()).getMarkerType() + ")"
            );
        }

        linMarkerList3.setBackground(mActivity.getResources().getDrawable(R.drawable.marker_table_selected));

    }

    public void selectMarkerList4() throws NullPointerException {

        initMarkerTable();
        resetMarkerListColor();
//        tvSelectedMarker.setText("Mkr4");
//        mCurrentMarkerName = "M4";

        tvMarker4.setText("M4(" + binding.lineChartLayout.mainLineChart.getHighlighted().get(
                binding.lineChartLayout.mainLineChart.getOnTouchListener().getSelectedHighlightIndex()).getMarkerType() + ")"
        );

        linMarkerList4.setBackground(mActivity.getResources().getDrawable(R.drawable.marker_table_selected));

    }

    private void selectMarkerList(int idx, boolean isSelect) throws NullPointerException {

        switch (idx) {

            case 0:

                if (isSelect) {
                    selectMarkerList1();
                } else {
                    unselectMarkerList1();
                }

                break;

            case 1:

                if (isSelect) {
                    selectMarkerList2();
                } else {
                    unselectMarkerList2();
                }


                break;

            case 2:

                if (isSelect) {
                    selectMarkerList3();
                } else {
                    unselectMarkerList3();
                }


                break;

            case 3:

                if (isSelect) {
                    selectMarkerList4();
                } else {
                    unselectMarkerList4();
                }

                break;

            case 4:

                if (isSelect) {
                    selectMarkerList5();
                } else {
                    unselectMarkerList5();
                }


                break;

        }

    }

    public void selectMarkerList5() throws NullPointerException {

        initMarkerTable();
        resetMarkerListColor();
//        tvSelectedMarker.setText("Mkr5");
//        mCurrentMarkerName = "M5";

        if (binding.lineChartLayout.mainLineChart.getHighlighted().get(binding.lineChartLayout.mainLineChart.getOnTouchListener().getSelectedHighlightIndex()) != null)
            tvMarker5.setText("M5(" + binding.lineChartLayout.mainLineChart.getHighlighted().get(
                    binding.lineChartLayout.mainLineChart.getOnTouchListener().getSelectedHighlightIndex()).getMarkerType() + ")"
            );

        linMarkerList5.setBackground(mActivity.getResources().getDrawable(R.drawable.marker_table_selected));

    }

    public void unselectMarkerList1() throws NullPointerException {

        initMarkerTable();
        linMarkerList1.setBackground(mActivity.getResources().getDrawable(R.drawable.marker_table_unselected));
        tvMarker1.setText("");
        tvMarkerXValue1.setText("");
        tvMarkerYValue1.setText("");

    }


    public void unselectMarkerList2() throws NullPointerException {

        initMarkerTable();
        linMarkerList2.setBackground(mActivity.getResources().getDrawable(R.drawable.marker_table_unselected));
        tvMarker2.setText("");
        tvMarkerXValue2.setText("");
        tvMarkerYValue2.setText("");

    }

    public void unselectMarkerList3() throws NullPointerException {

        initMarkerTable();
        linMarkerList3.setBackground(mActivity.getResources().getDrawable(R.drawable.marker_table_unselected));
        tvMarker3.setText("");
        tvMarkerXValue3.setText("");
        tvMarkerYValue3.setText("");

    }

    public void unselectMarkerList4() throws NullPointerException {

        initMarkerTable();
        linMarkerList4.setBackground(mActivity.getResources().getDrawable(R.drawable.marker_table_unselected));
        tvMarker4.setText("");
        tvMarkerXValue4.setText("");
        tvMarkerYValue4.setText("");

    }

    public void unselectMarkerList5() throws NullPointerException {

        initMarkerTable();
        linMarkerList5.setBackground(mActivity.getResources().getDrawable(R.drawable.marker_table_unselected));
        tvMarker5.setText("");
        tvMarkerXValue5.setText("");
        tvMarkerYValue5.setText("");

    }

    public void resetMarkerType() throws NullPointerException {

        setMarkerRefType(true);

        for (int i = 1; i < binding.lineChartLayout.mainLineChart.getHighlighted().size(); i++) {

            if (binding.lineChartLayout.mainLineChart.getHighlighted().get(i) != null) {

                binding.lineChartLayout.mainLineChart.getHighlighted().get(i).setMarkerTypeToRef();

            }

        }

//        ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().syncMarkerTableData();

    }

    public void addMarkerTable() throws NullPointerException {

        setMarkerTable(true);

        LinearLayout.LayoutParams mainChartParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                0.65f
        );

        LinearLayout.LayoutParams tvChartInfoParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                0.1f
        );

        LinearLayout.LayoutParams PassFailBoxParams = new LinearLayout.LayoutParams(

                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                0.175f
        );

        binding.lineChartLayout.mainLineChart.setLayoutParams(mainChartParams);
        binding.lineChartLayout.tvChartInfo.setLayoutParams(tvChartInfoParams);
        binding.lineChartLayout.linChartParent.addView(linMarkerTable);

//        ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().syncMarkerTableData();
    }

    public void removeMarkerTable() throws NullPointerException {

        mActivity.runOnUiThread(() -> {


            try {

                setMarkerTable(false);
                resetMarkerListColor();

                binding.lineChartLayout.linChartParent.removeView(linMarkerTable);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        0,
                        0.9f);

                LinearLayout.LayoutParams PassFailBoxParams = new LinearLayout.LayoutParams(

                        ViewGroup.LayoutParams.MATCH_PARENT,
                        0,
                        0.1f
                );

                binding.lineChartLayout.mainLineChart.setLayoutParams(params);
                binding.lineChartLayout.tvChartInfo.setLayoutParams(PassFailBoxParams);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        });

    }

//    public void initMarkerPos() throws NullPointerException {
//
//        try {
//
//            float yValue;
//
//            if (binding.chart.getSelectedHighlightIndex() == -1 ||
//                    binding.chart.getData().getEntryForHighlight(binding.chart.getHighlighted()
//                            .get(binding.chart.getSelectedHighlightIndex())) == null) {
//
//                yValue = 0f;
//
//            } else {
//
//                yValue = binding.chart.getData().getEntryForHighlight(binding.chart.getHighlighted()
//                        .get(binding.chart.getSelectedHighlightIndex())).getY();
//
//            }
//
//            InitActivity.logMsg("initMarker", SaView.getCurrentSelectedMenu() + "");
//
//            mCurrentMarkerPos = ViewHandler.getInstance().getSaFrequencyView().getCenterFreq();
//            tvMarkerFreq.setText(mActivity.getResources().getText(R.string.frequency_name));
//            tvFreqValue.setText(mCurrentMarkerPos + "MHz");
//
//            if (yValue != 0f) {
//                binding.linMarkerInfo.setVisibility(View.VISIBLE);
//                binding.tvMarkerName.setText("M" + (binding.chart.getSelectedHighlightIndex() + 1) + " : ");
//                binding.tvMarkerValue.setText(Utils.formatNumber(mCurrentMarkerPos, 2, false) + "MHz");
//                binding.tvMarkerYVal.setText(Utils.formatNumber(yValue, 2, false));
//            }
//
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//
//    }

    public void setMarkerPos(float pos) throws NullPointerException {

        mCurrentMarkerPos = pos;

        if (binding.lineChartLayout.mainLineChart.getSelectedHighlightIndex() == -1 ||
                binding.lineChartLayout.mainLineChart.getData().getEntryForHighlight(binding.lineChartLayout.mainLineChart.getHighlighted()
                        .get(binding.lineChartLayout.mainLineChart.getSelectedHighlightIndex())) == null) return;

        float yValue = binding.lineChartLayout.mainLineChart.getData().getEntryForHighlight(binding.lineChartLayout.mainLineChart.getHighlighted()
                .get(binding.lineChartLayout.mainLineChart.getSelectedHighlightIndex())).getY();

//        if (SaView.getCurrentSelectedMenu().equals(SaView.Menu.VswrView)) {
//
//            tvFreqValue.setText(Utils.formatNumber(mCurrentMarkerPos, 2, false) + "MHz");
//
//            binding.linMarkerInfo.setVisibility(View.VISIBLE);
//            binding.tvMarkerName.setText("M" + (binding.chart.getSelectedHighlightIndex() + 1) + " : ");
//            binding.tvMarkerValue.setText(Utils.formatNumber(mCurrentMarkerPos, 2, false) + "MHz");
//            binding.tvMarkerYVal.setText(Utils.formatNumber(yValue, 2, false));
////            binding.tvMarkerYVal.setText(binding.chart.getData().getDataSetByIndex(0).getEn)
////            InitActivity.logMsg("VswrView", "MHz");
//
//        } else if (SaView.getCurrentSelectedMenu().equals(SaView.Menu.DtfView)) {
//
//            tvFreqValue.setText(Utils.formatNumber(mCurrentMarkerPos, 2, false) + "m");
//
//            binding.linMarkerInfo.setVisibility(View.VISIBLE);
//            binding.tvMarkerName.setText("M" + (binding.chart.getSelectedHighlightIndex() + 1) + " : ");
//            binding.tvMarkerValue.setText(Utils.formatNumber(mCurrentMarkerPos, 2, false) + "m");
//            binding.tvMarkerYVal.setText(Utils.formatNumber(yValue, 2, false));
//            InitActivity.logMsg("DtfView", "M");
//
//        } else if (SaView.getCurrentSelectedMenu().equals(SaView.Menu.CL)) {
//
//            tvFreqValue.setText(Utils.formatNumber(mCurrentMarkerPos, 2, false) + "MHz");
//
//            binding.linMarkerInfo.setVisibility(View.VISIBLE);
//            binding.tvMarkerName.setText("M" + (binding.chart.getSelectedHighlightIndex() + 1) + " : ");
//            binding.tvMarkerValue.setText(Utils.formatNumber(mCurrentMarkerPos, 2, false) + "MHz");
//            binding.tvMarkerYVal.setText(Utils.formatNumber(yValue, 2, false));
//            InitActivity.logMsg("CL", "MHz");
//
//        } else {
//
//            tvMarkerFreq.setText(mActivity.getResources().getText(R.string.frequency_name));
//            tvFreqValue.setText(Utils.formatNumber(mCurrentMarkerPos, 2, false) + "MHz");
//
//            binding.linMarkerInfo.setVisibility(View.VISIBLE);
//            binding.tvMarkerName.setText("M" + (binding.chart.getSelectedHighlightIndex() + 1) + " : ");
//            binding.tvMarkerValue.setText(Utils.formatNumber(mCurrentMarkerPos, 2, false) + "MHz");
//            binding.tvMarkerYVal.setText(Utils.formatNumber(yValue, 2, false));
//            InitActivity.logMsg("else", "MHz");
//
//        }

        syncMarkerTableData();
        syncSelectedMarkerFreq();

    }

    public float getMarkerPos() {
        return mCurrentMarkerPos;
    }

    public Boolean isMarkerTable() {
        return isMarkerTable;
    }

    public Boolean isMarker() {

        if (isMarker == null) return false;

        for (int i = 0; i < 5; i++) {
            if (isMarker != null && isMarker[i] != null && isMarker[i]) {
                return isMarker[i];
            }
        }

        return false;
    }

    public Boolean[] getIsMarker() {
        return isMarker;
    }

    public boolean isMarker(int idx) {

        if (idx >= 0 && idx < 5) {

            return isMarker[idx];

        } else return false;
    }

    public void selectMarker(int idx, boolean isEnabled) throws NullPointerException {

        if (binding.lineChartLayout.mainLineChart.getData().getDataSetByIndex(FunctionHandler.getInstance().getMainLineChart().getChartDataSetIndex()).getEntryCount() == 0 ||
                binding.lineChartLayout.mainLineChart.getData().getDataSetByIndex(FunctionHandler.getInstance().getMainLineChart().getChartDataSetIndex()).getEntryCount() == 1)
            return;

        if (isEnabled) {

            InitActivity.logMsg("tvFreq", getMarkerPos() + "");
            binding.lineChartLayout.mainLineChart.addHighlightValue(idx, new Highlight(
                    (binding.lineChartLayout.mainLineChart.getXAxis().mAxisMaximum + binding.lineChartLayout.mainLineChart.getXAxis().mAxisMinimum) / 2f,
                    (binding.lineChartLayout.mainLineChart.getAxisLeft().mAxisMaximum + binding.lineChartLayout.mainLineChart.getAxisLeft().mAxisMinimum) / 2f,
                    FunctionHandler.getInstance().getMainLineChart().getChartDataSetIndex()));

            binding.lineChartLayout.mainLineChart.getOnTouchListener().setSelectedHighlightIndex(idx);

            if (binding.lineChartLayout.mainLineChart.getHighlighted().get(idx).getMarkerType().equals("R")) {
                setMarkerRefType(true);
            } else {
                setMarkerRefType(false);
            }

            selectMarkerOnOff(idx, true);
            selectContentMarker(idx, true);
            selectMarkerList(idx, true);
            getSelectMarker().selectMarker(idx, true);

            ViewHandler.getInstance().getSAView().selectMarkerLayout();
            setMarkerPos(binding.lineChartLayout.mainLineChart.getHighlighted().get(idx).getX());
            addMenu();

        } else {

            selectMarkerOnOff(idx, false);
            binding.lineChartLayout.mainLineChart.removeHighlightValue(idx);

            tvSelectedMarker.setText("");
            resetMarkerType();
            selectMarkerOnOff(idx, false);
            selectMarkerList(idx, false);
            selectContentMarker(idx, false);
            getSelectMarker().selectMarker(idx, false);
            eraseMarkerInfo();

            for (int i = 0; i < binding.lineChartLayout.mainLineChart.getHighlighted().size(); i++) {

                if (binding.lineChartLayout.mainLineChart.getHighlighted().get(i) != null) {

                    binding.lineChartLayout.mainLineChart.getOnTouchListener().setSelectedHighlightIndex(i);

                    if (binding.lineChartLayout.mainLineChart.getHighlighted().get(i).getMarkerType().equals("R")) {
                        setMarkerRefType(true);
                    } else {
                        setMarkerRefType(false);
                    }

                    selectMarkerOnOff(i, true);
                    selectContentMarker(i, true);
                    selectMarkerList(i, true);
                    setMarkerPos(binding.lineChartLayout.mainLineChart.getHighlighted().get(i).getX());

                    break;

                }

            }

        }

        syncSelectedMarkerFreq();
        syncMarkerTableData();

    }

    public void selectMarker(int idx, boolean isEnabled, float xValue) throws NullPointerException {

        if (binding.lineChartLayout.mainLineChart.getData().getDataSetByIndex(FunctionHandler.getInstance().getMainLineChart().getChartDataSetIndex()).getEntryCount() == 0 ||
                binding.lineChartLayout.mainLineChart.getData().getDataSetByIndex(FunctionHandler.getInstance().getMainLineChart().getChartDataSetIndex()).getEntryCount() == 1)
            return;

        if (isEnabled) {

            InitActivity.logMsg("tvFreq", getMarkerPos() + "");
            binding.lineChartLayout.mainLineChart.addHighlightValue(idx, new Highlight(
                    xValue,
                    (binding.lineChartLayout.mainLineChart.getAxisLeft().mAxisMaximum + binding.lineChartLayout.mainLineChart.getAxisLeft().mAxisMinimum) / 2f,
                    FunctionHandler.getInstance().getMainLineChart().getChartDataSetIndex()));

            binding.lineChartLayout.mainLineChart.getOnTouchListener().setSelectedHighlightIndex(idx);

            if (binding.lineChartLayout.mainLineChart.getHighlighted().get(idx).getMarkerType().equals("R")) {
                setMarkerRefType(true);
            } else {
                setMarkerRefType(false);
            }

            selectMarkerOnOff(idx, true);
            selectContentMarker(idx, true);
            selectMarkerList(idx, true);
            getSelectMarker().selectMarker(idx, true);

            ViewHandler.getInstance().getSAView().selectMarkerLayout();
            setMarkerPos(binding.lineChartLayout.mainLineChart.getHighlighted().get(idx).getX());
            addMenu();

        } else {

            selectMarkerOnOff(idx, false);
            binding.lineChartLayout.mainLineChart.removeHighlightValue(idx);

            tvSelectedMarker.setText("");
            resetMarkerType();
            selectMarkerOnOff(idx, false);
            selectMarkerList(idx, false);
            selectContentMarker(idx, false);
            getSelectMarker().selectMarker(idx, false);
            eraseMarkerInfo();

            for (int i = 0; i < binding.lineChartLayout.mainLineChart.getHighlighted().size(); i++) {

                if (binding.lineChartLayout.mainLineChart.getHighlighted().get(i) != null) {

                    binding.lineChartLayout.mainLineChart.getOnTouchListener().setSelectedHighlightIndex(i);

                    if (binding.lineChartLayout.mainLineChart.getHighlighted().get(i).getMarkerType().equals("R")) {
                        setMarkerRefType(true);
                    } else {
                        setMarkerRefType(false);
                    }

                    selectMarkerOnOff(i, true);
                    selectContentMarker(i, true);
                    selectMarkerList(i, true);
                    setMarkerPos(binding.lineChartLayout.mainLineChart.getHighlighted().get(i).getX());

                    break;

                }

            }

        }

        syncSelectedMarkerFreq();
        syncMarkerTableData();

    }


    public void syncSelectedMarkerFreq() throws NullPointerException {

        if (binding.lineChartLayout.mainLineChart.getSelectedHighlightIndex() != -1) {

            String xFormat = "";
            String yFormat = "";

//            if (SaView.getCurrentSelectedMenu().getFullName().contains(SaView.Menu.VswrView.getFullName()) ||
//                    SaView.getCurrentSelectedMenu().getFullName().contains(SaView.Menu.CL.getFullName())) {
//
//                xFormat = " MHz";
//
//                if (SaView.getCurrentSelectedMode().getFullName().contains(SaView.SaModeView.RL.getFullName()) ||
//                        SaView.getCurrentSelectedMode().getFullName().contains(SaView.SaModeView.CL.getFullName()))
//                    yFormat = " dB";
//
//
//            } else if (SaView.getCurrentSelectedMenu().getFullName().contains(SaView.Menu.DtfView.getFullName())) {
//
//                xFormat = " m";
//                yFormat = " dB";
//
//            }

            if (binding.lineChartLayout.mainLineChart.getData().getEntryForHighlight(binding.lineChartLayout.mainLineChart.getHighlighted().get(binding.lineChartLayout.mainLineChart.getSelectedHighlightIndex())) != null) {

//                binding.tvMarkerName.setText("M" + (binding.lineChartLayout.lineChart.getSelectedHighlightIndex() + 1) + " : ");
//                getTvSelectedMarker()
//                        .setText("Mkr" + (binding.chart.getSelectedHighlightIndex() + 1));
//
//                float xValue = binding.chart.getHighlighted().get(binding.chart.getSelectedHighlightIndex()).getX();
//
//                if (SaView.getCurrentSelectedMenu().equals(SaView.Menu.VswrView)) {
//                    getMarkerFreq().setText(
//                            Utils.formatNumber(xValue, 2, false) + xFormat
//                    );
//
//                    binding.tvMarkerValue.setText(Utils.formatNumber(xValue, 2, false) + xFormat);
//
//                } else if (SaView.getCurrentSelectedMenu().equals(SaView.Menu.DtfView)) {
//
//                    getMarkerFreq().setText(
//                            Utils.formatNumber(xValue, 2, false) + xFormat
//                    );
//
//                    binding.tvMarkerValue.setText(
//                            Utils.formatNumber(xValue, 2, false) + xFormat);
//
//                } else {
//
//                    getMarkerFreq().setText(
//                            Utils.formatNumber(xValue, 2, false) + xFormat
//                    );
//
//                    binding.tvMarkerValue.setText(Utils.formatNumber(xValue, 2, false) + xFormat);
//
//                }
//
//                if (xValue < binding.chart.getXAxis().mAxisMinimum || xValue > binding.chart.getXAxis().mAxisMaximum)
//                    binding.tvMarkerYVal.setText("--.--");
//                else {
//                    float yValue = binding.chart.getData().getEntryForHighlight(binding.chart.getHighlighted()
//                            .get(binding.chart.getSelectedHighlightIndex())).getY();
//
//                    binding.tvMarkerYVal.setText(Utils.formatNumber(yValue, 2, false) + yFormat);
//                }
            }
        } else {

            eraseMarkerInfo();

        }

    }

    public void eraseMarkerInfo() {

        try {

//            binding.linMarkerInfo.setVisibility(View.INVISIBLE);
//            binding.tvMarkerName.setText("");
//            binding.tvMarkerValue.setText("");
//            binding.tvMarkerYVal.setText("");
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

    }

    private void selectContentMarker(int idx, Boolean isSelect) {

        switch (idx) {

            case 0:
                binding.tvMarker1.setSelected(isSelect);
//                if (isSelect) {
//                    binding.tvMarker1.setBackground(mActivity.getResources().getDrawable(R.color.select_content_mark));
//                    binding.tvMarker1.setTag(R.color.select_content_mark);
//                } else {
//                    binding.tvMarker1.setBackground(mActivity.getResources().getDrawable(R.color.unselect_content_mark));
//                    binding.tvMarker1.setTag(R.color.unselect_content_mark);
//                }
                break;

            case 1:
                binding.tvMarker2.setSelected(isSelect);
//                if (isSelect) {
//                    binding.tvMarker2.setBackground(mActivity.getResources().getDrawable(R.color.select_content_mark));
//                    binding.tvMarker2.setTag(R.color.select_content_mark);
//                } else {
//                    binding.tvMarker2.setBackground(mActivity.getResources().getDrawable(R.color.unselect_content_mark));
//                    binding.tvMarker2.setTag(R.color.unselect_content_mark);
//                }
                break;

            case 2:
                binding.tvMarker3.setSelected(isSelect);
//                if (isSelect) {
//                    binding.tvMarker3.setBackground(mActivity.getResources().getDrawable(R.color.select_content_mark));
//                    binding.tvMarker3.setTag(R.color.select_content_mark);
//                } else {
//                    binding.tvMarker3.setBackground(mActivity.getResources().getDrawable(R.color.unselect_content_mark));
//                    binding.tvMarker3.setTag(R.color.unselect_content_mark);
//                }
                break;

            case 3:
                binding.tvMarker4.setSelected(isSelect);
//                if (isSelect) {
//                    binding.tvMarker4.setBackground(mActivity.getResources().getDrawable(R.color.select_content_mark));
//                    binding.tvMarker4.setTag(R.color.select_content_mark);
//                } else {
//                    binding.tvMarker4.setBackground(mActivity.getResources().getDrawable(R.color.unselect_content_mark));
//                    binding.tvMarker4.setTag(R.color.unselect_content_mark);
//                }
                break;

            case 4:
                binding.tvMarker5.setSelected(isSelect);
//                if (isSelect) {
//                    binding.tvMarker5.setBackground(mActivity.getResources().getDrawable(R.color.select_content_mark));
//                    binding.tvMarker5.setTag(R.color.select_content_mark);
//                } else {
//                    binding.tvMarker5.setBackground(mActivity.getResources().getDrawable(R.color.unselect_content_mark));
//                    binding.tvMarker5.setTag(R.color.unselect_content_mark);
//                }
                break;

        }

    }

    public void syncMarkerTableData() {

        if (isMarker() && isMarkerTable()) {

            selectMarkerList(binding.lineChartLayout.mainLineChart.getSelectedHighlightIndex(), true);

            String xFormat = "";
            String yFormat = "";

//            if (SaView.getCurrentSelectedMenu().getFullName().contains(SaView.Menu.VswrView.getFullName()) ||
//                    SaView.getCurrentSelectedMenu().getFullName().contains(SaView.Menu.CL.getFullName())) {
//
//                xFormat = " MHz";
//
//                if (SaView.getCurrentSelectedMode().getFullName().contains(SaView.SaModeView.RL.getFullName()) ||
//                        SaView.getCurrentSelectedMode().getFullName().contains(SaView.SaModeView.CL.getFullName()))
//                    yFormat = " dB";
//
//
//            } else if (SaView.getCurrentSelectedMenu().getFullName().contains(SaView.Menu.DtfView.getFullName())) {
//
//                xFormat = " m";
//                yFormat = " dB";
//
//            }

            for (int i = 0; i < binding.lineChartLayout.mainLineChart.getHighlighted().size(); i++) {

                if (binding.lineChartLayout.mainLineChart.getHighlighted().get(i) == null) continue;

                float valueY = 0f;
                float valueX = 0f;
                float deltaX = 0f;
                float deltaY = 0f;

                if (binding.lineChartLayout.mainLineChart.getHighlighted() != null && binding.lineChartLayout.mainLineChart.getHighlighted().get(i) != null &&
                        binding.lineChartLayout.mainLineChart.getData().getEntryForHighlight(binding.lineChartLayout.mainLineChart.getHighlighted().get(i)) != null) {

                    valueX = binding.lineChartLayout.mainLineChart.getHighlighted().get(i).getX();
                    valueY = binding.lineChartLayout.mainLineChart.getData().getEntryForHighlight(binding.lineChartLayout.mainLineChart.getHighlighted()
                            .get(i)).getY();

                    if (binding.lineChartLayout.mainLineChart.getHighlighted().get(0) != null) {
                        deltaX = binding.lineChartLayout.mainLineChart.getData().getEntryForHighlight(binding.lineChartLayout.mainLineChart.getHighlighted().get(0)).getX() - valueX;
                        deltaY = binding.lineChartLayout.mainLineChart.getData().getEntryForHighlight(binding.lineChartLayout.mainLineChart.getHighlighted().get(0)).getY() - valueY;
                    }

                } else {
                    continue;
                }

                switch (i) {

                    case 0:

                        getTvMarker1().setText(
                                "M1(" + binding.lineChartLayout.mainLineChart.getHighlighted().get(0).getMarkerType() + ")");
                        getTvMarkerXValue1().
                                setText(Utils.formatNumber(valueX, 2, false) + xFormat);

                        if (valueX < binding.lineChartLayout.mainLineChart.getXAxis().mAxisMinimum || valueX > binding.lineChartLayout.mainLineChart.getXAxis().mAxisMaximum)
                            getTvMarkerYValue1().
                                    setText("--.--");
                        else
                            getTvMarkerYValue1().
                                    setText(Utils.formatNumber(valueY, 2, false) + yFormat);

                        break;

                    case 1:

                        getTvMarker2().setText(
                                "M2(" + binding.lineChartLayout.mainLineChart.getHighlighted().get(1).getMarkerType() + ")");

                        if (binding.lineChartLayout.mainLineChart.getHighlighted().get(1).getMarkerType().equals("R")) {

                            getTvMarkerXValue2().
                                    setText(Utils.formatNumber(valueX, 2, false) + xFormat);

                            if (valueX < binding.lineChartLayout.mainLineChart.getXAxis().mAxisMinimum || valueX > binding.lineChartLayout.mainLineChart.getXAxis().mAxisMaximum)
                                getTvMarkerYValue2().setText("--.--");
                            else
                                getTvMarkerYValue2().setText(Utils.formatNumber(valueY, 2, false) + yFormat);

                        } else {

                            if (binding.lineChartLayout.mainLineChart.getHighlighted().get(0) != null) {

                                getTvMarkerXValue2().setText(Utils.formatNumber(deltaX, 2, false) + xFormat);
                                getTvMarkerYValue2().setText(Utils.formatNumber(deltaY, 2, false) + yFormat);

                            }

                        }

                        break;

                    case 2:

                        getTvMarker3().setText("M3(" + binding.lineChartLayout.mainLineChart.getHighlighted().get(2).getMarkerType() + ")");

                        if (binding.lineChartLayout.mainLineChart.getHighlighted().get(2).getMarkerType().equals("R")) {

                            getTvMarkerXValue3().
                                    setText(Utils.formatNumber(valueX, 2, false) + xFormat);

                            if (valueX < binding.lineChartLayout.mainLineChart.getXAxis().mAxisMinimum || valueX > binding.lineChartLayout.mainLineChart.getXAxis().mAxisMaximum)
                                getTvMarkerYValue3().
                                        setText("--.--");
                            else
                                getTvMarkerYValue3().
                                        setText(Utils.formatNumber(valueY, 2, false) + yFormat);

                        } else {

                            if (binding.lineChartLayout.mainLineChart.getHighlighted().get(0) != null) {

                                valueY = binding.lineChartLayout.mainLineChart.getData().getEntryForHighlight(binding.lineChartLayout.mainLineChart.getHighlighted()
                                        .get(i)).getY();

                                getTvMarkerXValue3().setText(Utils.formatNumber(deltaX, 2, false) + xFormat);

                                getTvMarkerYValue3().setText(Utils.formatNumber(deltaY, 2, false) + yFormat);


                            }

                        }

                        break;

                    case 3:

                        getTvMarker4().setText("M4(" + binding.lineChartLayout.mainLineChart.getHighlighted().get(3).getMarkerType() + ")");

                        if (binding.lineChartLayout.mainLineChart.getHighlighted().get(3).getMarkerType().equals("R")) {

                            getTvMarkerXValue4().setText(Utils.formatNumber(valueX, 2, false) + xFormat);

                            if (valueX < binding.lineChartLayout.mainLineChart.getXAxis().mAxisMinimum || valueX > binding.lineChartLayout.mainLineChart.getXAxis().mAxisMaximum)
                                getTvMarkerYValue4().setText("--.--");
                            else
                                getTvMarkerYValue4().setText(Utils.formatNumber(valueY, 2, false) + yFormat);

                        } else {

                            if (binding.lineChartLayout.mainLineChart.getHighlighted().get(0) != null) {

                                valueY = binding.lineChartLayout.mainLineChart.getData().getEntryForHighlight(binding.lineChartLayout.mainLineChart.getHighlighted()
                                        .get(i)).getY();

                                getTvMarkerXValue4().setText(Utils.formatNumber(deltaX, 2, false) + xFormat);
                                getTvMarkerYValue4().setText(Utils.formatNumber(deltaY, 2, false) + yFormat);


                            }

                        }

                        break;

                    case 4:

                        getTvMarker5().setText("M5(" + binding.lineChartLayout.mainLineChart.getHighlighted().get(4).getMarkerType() + ")");

                        if (binding.lineChartLayout.mainLineChart.getHighlighted().get(4).getMarkerType().equals("R")) {

                            getTvMarkerXValue5().
                                    setText(Utils.formatNumber(valueX, 2, false) + xFormat);

                            if (valueX < binding.lineChartLayout.mainLineChart.getXAxis().mAxisMinimum || valueX > binding.lineChartLayout.mainLineChart.getXAxis().mAxisMaximum)
                                getTvMarkerYValue5().
                                        setText("--.--");
                            else
                                getTvMarkerYValue5().
                                        setText(Utils.formatNumber(valueY, 2, false) + yFormat);

                        } else {

                            if (binding.lineChartLayout.mainLineChart.getHighlighted().get(0) != null) {

                                valueY = binding.lineChartLayout.mainLineChart.getData().getEntryForHighlight(binding.lineChartLayout.mainLineChart.getHighlighted()
                                        .get(i)).getY();

                                getTvMarkerXValue5().setText(Utils.formatNumber(deltaX, 2, false) + xFormat);

                                getTvMarkerYValue5().setText(Utils.formatNumber(deltaY, 2, false) + yFormat);

                            }
                        }
                        break;
                }
            }
        }
    }

    /*
    @param idx
     */

    public void removeAllMarkers() {

        try {

            for (int i = 0; i < 5; i++) {

                binding.lineChartLayout.mainLineChart.removeHighlightValue(i);
//                ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().selectMarkerOnOff(i, false);

            }

            selectContentMarker(0, false);
            selectContentMarker(1, false);
            selectContentMarker(2, false);
            selectContentMarker(3, false);
            selectContentMarker(4, false);


//            ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().getSaSelectMarkerView().removeAllMarkerSelect();
//            ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().resetMarkerListColor();
//        ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().addMenu();
//            ViewHandler.getInstance().getSaMarkerView().getMarkerSetup().getTvSelectedMarker().setText("");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public AutofitTextView getTvSelectedMarker() {
        return tvSelectedMarker;
    }

    public AutofitTextView getTvMarker1() {
        return tvMarker1;
    }

    public AutofitTextView getTvMarker2() {
        return tvMarker2;
    }

    public AutofitTextView getTvMarker3() {
        return tvMarker3;
    }

    public AutofitTextView getTvMarker4() {
        return tvMarker4;
    }

    public AutofitTextView getTvMarker5() {
        return tvMarker5;
    }

    public AutofitTextView getTvMarkerXValue1() {
        return tvMarkerXValue1;
    }

    public AutofitTextView getTvMarkerXValue2() {
        return tvMarkerXValue2;
    }

    public AutofitTextView getTvMarkerXValue3() {
        return tvMarkerXValue3;
    }

    public AutofitTextView getTvMarkerXValue4() {
        return tvMarkerXValue4;
    }

    public AutofitTextView getTvMarkerXValue5() {
        return tvMarkerXValue5;
    }

    public AutofitTextView getTvMarkerYValue1() {
        return tvMarkerYValue1;
    }

    public AutofitTextView getTvMarkerYValue2() {
        return tvMarkerYValue2;
    }

    public AutofitTextView getTvMarkerYValue3() {
        return tvMarkerYValue3;
    }

    public AutofitTextView getTvMarkerYValue4() {
        return tvMarkerYValue4;
    }

    public AutofitTextView getTvMarkerYValue5() {
        return tvMarkerYValue5;
    }

    public AutofitTextView getMarkerFreq() {

        return tvFreqValue;

    }

}
