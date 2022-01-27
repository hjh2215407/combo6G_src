package com.dabinsystems.pact_app.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.dabinsystems.pact_app.List.Adapter.CustomSpinnerAdapter;
import com.dabinsystems.pact_app.R;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class DynamicView {

    private Context mContext;
    private float mBottomWeight = 1 / 11f;
    private float mRightWeight = 0.125f;

    private float mBottomTextSize = 10f;
    private float mBottomTextMaxSize = 12f;

    private float mRightTextSize = 12f;
    private float mRightTextMaxSize = 14f;

    private float mSubTextSize = 10f;
    private float mSubTextMaxSize = 11f;

    public DynamicView(Context context) {
        super();
        mContext = context;
    }

    public DynamicView(Context context, float botWeight) {
        super();
        mContext = context;
        mBottomWeight = botWeight;
    }

    public DynamicView(Context context, float botWeight, float rightWeight) {
        super();
        mContext = context;
        mBottomWeight = botWeight;
        mRightWeight = rightWeight;
    }

    /*
    0 is parent
    1 is tvTitle
     */
    public ArrayList<View> addRightMenuButton(String title) {

        try {

            LinearLayout parent = new LinearLayout(mContext);

            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    mRightWeight
            );

            parentParams.gravity = Gravity.CENTER;
            parentParams.setMargins(1, 1, 1, 1);
            parent.setLayoutParams(parentParams);

            parent.setBackground(mContext.getResources().getDrawable(R.drawable.right_menu_layout_selector));
            parent.setOrientation(LinearLayout.VERTICAL);
            parent.setWeightSum(1);

            AutofitTextView tvTitle = new AutofitTextView(mContext);

            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            titleParams.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;

            tvTitle.setLayoutParams(titleParams);
            tvTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            tvTitle.setTextColor(mContext.getResources().getColorStateList(R.color.s_text_top));
            tvTitle.setSingleLine(true);
            tvTitle.setPadding((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvTitle.setPaddingRelative((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
            tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mRightTextMaxSize, mContext));
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mRightTextSize, mContext));
            tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
            tvTitle.setText(title);
            tvTitle.setDuplicateParentStateEnabled(true); // 부모 상태(enable, ...)와 동기화

            parent.addView(tvTitle);

            ArrayList<View> views = new ArrayList<View>();
            views.add(parent);
            views.add(tvTitle);

            return views;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;

    }

    /*
    Different Alignment
     */
    public ArrayList<View> addRightMenuButton(String title, int gravity) {

        try {

            LinearLayout parent = new LinearLayout(mContext);

            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    mRightWeight
            );
            parentParams.gravity = Gravity.CENTER;
            parentParams.setMargins(1, 1, 1, 1);
            parent.setLayoutParams(parentParams);

            parent.setBackground(mContext.getResources().getDrawable(R.drawable.right_menu_layout_selector));
            parent.setOrientation(LinearLayout.VERTICAL);
            parent.setWeightSum(1);

            AutofitTextView tvTitle = new AutofitTextView(mContext);

            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );

            tvTitle.setLayoutParams(titleParams);
            tvTitle.setGravity(gravity);
            tvTitle.setTextColor(mContext.getResources().getColorStateList(R.color.s_text_top));
            tvTitle.setPadding((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvTitle.setPaddingRelative((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvTitle.setSingleLine(false); // [jigum] 2021-07-16 2줄 이상 표출 되도록 수정.
            tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
            tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mRightTextMaxSize, mContext));
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mRightTextSize, mContext));
            tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
            tvTitle.setText(title);
            tvTitle.setDuplicateParentStateEnabled(true); // 부모 상태(enable, ...)와 동기화

            parent.addView(tvTitle);

            ArrayList<View> views = new ArrayList<View>();
            views.add(parent);
            views.add(tvTitle);

            return views;

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;

    }

    /*
    0 is parent (LinearLayout)
    1 is tvTitle (TextView)
    2 is tvRightSubText (TextView)
    */
    public ArrayList<View> addRightMenuButton(String title, String sub) {


        try {

        /*
        Parent
         */
            LinearLayout parent = new LinearLayout(mContext);

            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    mRightWeight
            );

            parentParams.gravity = Gravity.CENTER;
            parentParams.setMargins(1, 1, 1, 1);
            parent.setLayoutParams(parentParams);

            parent.setBackground(mContext.getResources().getDrawable(R.drawable.right_menu_layout_selector));
            parent.setOrientation(LinearLayout.VERTICAL);
            parent.setWeightSum(1);

        /*
        TextView for TitleText
         */
            AutofitTextView tvTitle = new AutofitTextView(mContext);

            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    0.5f
            );
            titleParams.gravity = Gravity.RIGHT;

            tvTitle.setLayoutParams(titleParams);
            tvTitle.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            tvTitle.setTextColor(mContext.getResources().getColorStateList(R.color.s_text_top));
            tvTitle.setPadding((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvTitle.setPaddingRelative((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvTitle.setSingleLine(true);
            tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
            tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mRightTextMaxSize, mContext));
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mRightTextSize, mContext));
            tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
            tvTitle.setText(title);
            tvTitle.setDuplicateParentStateEnabled(true); // 부모 상태(enable, ...)와 동기화
            tvTitle.setTag("title");

        /*
        LinearLayout for subText
         */

            LinearLayout linSubText = new LinearLayout(mContext);

            LinearLayout.LayoutParams subParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    0.5f
            );
            subParams.gravity = Gravity.RIGHT;
            linSubText.setLayoutParams(subParams);
            linSubText.setOrientation(LinearLayout.HORIZONTAL);
            linSubText.setWeightSum(1);
            linSubText.setGravity(Gravity.RIGHT);

        /*
        textView for right subtext
         */
            AutofitTextView tvRightSubText = new AutofitTextView(mContext);

            LinearLayout.LayoutParams rightTextParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1f
            );
            rightTextParams.gravity = Gravity.RIGHT;

            tvRightSubText.setLayoutParams(rightTextParams);
            tvRightSubText.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            tvRightSubText.setPadding((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvRightSubText.setPaddingRelative((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvRightSubText.setTextColor(mContext.getResources().getColorStateList(R.color.s_text_top));
//        tvRightSubText.setTextSize(convertDpToPixel(5, mContext));
            tvRightSubText.setText(sub);
            tvRightSubText.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
            tvRightSubText.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextMaxSize, mContext));
            tvRightSubText.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextSize, mContext));
            tvRightSubText.setMaxLines(1);
            tvRightSubText.setTag("rightSubText");

            parent.addView(tvTitle);
            linSubText.addView(tvRightSubText);
            parent.addView(linSubText);

            ArrayList<View> views = new ArrayList<View>();
            views.add(parent);
            views.add(tvTitle);
            views.add(tvRightSubText);

            return views;

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;

    }

    /*
   0 is parent (LinearLayout)
   1 is tvTitle (TextView)
   2 is tvRightSubText (TextView)
   */
    public ArrayList<View> addRightMenuButton(String title, String[] items) {

        try {

        /*
        Parent
         */
            LinearLayout parent = new LinearLayout(mContext);

            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    mRightWeight
            );

            parentParams.gravity = Gravity.CENTER;
            parentParams.setMargins(1, 1, 1, 1);
            parent.setLayoutParams(parentParams);

            parent.setBackground(mContext.getResources().getDrawable(R.drawable.right_menu_layout_selector));
            parent.setOrientation(LinearLayout.VERTICAL);
            parent.setWeightSum(1);

        /*
        TextView for TitleText
         */
            AutofitTextView tvTitle = new AutofitTextView(mContext);

            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    0.5f
            );
            titleParams.gravity = Gravity.RIGHT;

            tvTitle.setLayoutParams(titleParams);
            tvTitle.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            tvTitle.setTextColor(mContext.getResources().getColorStateList(R.color.s_text_top));//(mContext.getResources().getColor(R.color.colorBlack));
            tvTitle.setPadding((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvTitle.setPaddingRelative((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvTitle.setSingleLine(true);
            tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
            tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mRightTextMaxSize, mContext));
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mRightTextSize, mContext));
            tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
            tvTitle.setText(title);
            tvTitle.setDuplicateParentStateEnabled(true); // 부모 상태(enable, ...)와 동기화
            tvTitle.setTag("title");

        /*
        LinearLayout for subText
         */

            LinearLayout linSubText = new LinearLayout(mContext);

            LinearLayout.LayoutParams subParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    0.5f
            );
            subParams.gravity = Gravity.RIGHT;
            linSubText.setLayoutParams(subParams);
            linSubText.setOrientation(LinearLayout.HORIZONTAL);
            linSubText.setWeightSum(1);
            linSubText.setGravity(Gravity.RIGHT);

        /*
        textView for right subtext
         */
            Spinner spRightSub = new Spinner(mContext, Spinner.MODE_DROPDOWN);

            LinearLayout.LayoutParams rightTextParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0.5f
            );
            rightTextParams.gravity = Gravity.RIGHT;
            rightTextParams.rightMargin = (int) convertDpToPixel(5, mContext);
            rightTextParams.leftMargin = (int) convertDpToPixel(10, mContext);


            spRightSub.setLayoutParams(rightTextParams);
            spRightSub.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            spRightSub.setPadding((int) convertDpToPixel(0, mContext), 0, (int) convertDpToPixel(0, mContext), 0);
            spRightSub.setPaddingRelative((int) convertDpToPixel(0, mContext), 0, (int) convertDpToPixel(0, mContext), 0);
            spRightSub.setBackground(null);
//            spRightSubText.setTextColor(mContext.getResources().getColorStateList(R.color.right_menu_text_selector));
//            spRightSubText.setText(sub);

//            spRightSubText.setMaxLines(1);
//            spRightSubText.setTag("rightSubText");

            CustomSpinnerAdapter spAdapter = new CustomSpinnerAdapter(mContext, R.layout.spinner_item, items);
            spRightSub.setAdapter(spAdapter);

            parent.addView(tvTitle);
            linSubText.addView(spRightSub);
            parent.addView(linSubText);

            ArrayList<View> views = new ArrayList<View>();
            views.add(parent);
            views.add(tvTitle);
            views.add(spRightSub);

            return views;

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;

    }

    /*
    0 is parent (LinearLayout)
    1 is tvTitle (TextView)
    2 is tvLeftSubText (TextView)
    3 is tvRightSubText (TextView)
    */
    public ArrayList<View> addRightMenuButton(String title, String leftSub, String rightSub) {

        try {

            /* Parent */
            LinearLayout parent = new LinearLayout(mContext);

            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    mRightWeight
            );
            parentParams.gravity = Gravity.CENTER;
            parentParams.setMargins(1, 1, 1, 1);
            parent.setLayoutParams(parentParams);

            parent.setBackground(mContext.getResources().getDrawable(R.drawable.right_menu_layout_selector));
            parent.setOrientation(LinearLayout.VERTICAL);
            parent.setWeightSum(1);

            /*
            TextView for TitleText
             */
            AutofitTextView tvTitle = new AutofitTextView(mContext);

            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    0.5f
            );
            titleParams.gravity = Gravity.RIGHT;

            tvTitle.setLayoutParams(titleParams);
            tvTitle.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            tvTitle.setTextColor(mContext.getResources().getColorStateList(R.color.s_text_top));//(mContext.getResources().getColor(R.color.colorBlack));
            tvTitle.setPadding((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvTitle.setPaddingRelative((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvTitle.setSingleLine(true);
            tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
            tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mRightTextMaxSize, mContext));
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mRightTextSize, mContext));
            tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
            tvTitle.setText(title);
            tvTitle.setDuplicateParentStateEnabled(true); // 부모 상태(enable, ...)와 동기화
            tvTitle.setTag("title");

            /*
            LinearLayout for subText
             */
            LinearLayout linSubText = new LinearLayout(mContext);

            LinearLayout.LayoutParams subParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    0.5f
            );
            linSubText.setLayoutParams(subParams);
            linSubText.setOrientation(LinearLayout.HORIZONTAL);
            linSubText.setWeightSum(1);

            LinearLayout linSubLeftText = new LinearLayout(mContext);

            LinearLayout.LayoutParams subLeftParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0.5f
            );

            linSubLeftText.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            linSubLeftText.setLayoutParams(subLeftParams);
            linSubLeftText.setOrientation(LinearLayout.HORIZONTAL);
            linSubLeftText.setWeightSum(1);


            /*
            textView for left subtext
             */
            AutofitTextView tvLeftSubText = new AutofitTextView(mContext);

            LinearLayout.LayoutParams leftTextParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            leftTextParams.setMargins((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvLeftSubText.setLayoutParams(leftTextParams);
            tvLeftSubText.setGravity(Gravity.CENTER);
            tvLeftSubText.setSingleLine(true);
            tvLeftSubText.setTextColor(mContext.getResources().getColorStateList(R.color.s_text_yellow));//(mContext.getResources().getColor(R.color.colorBlack));
            tvLeftSubText.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
            tvLeftSubText.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextMaxSize, mContext));
            tvLeftSubText.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextSize, mContext));
            tvLeftSubText.setText(leftSub);
            tvLeftSubText.setTag("leftSubText");


            LinearLayout linSubRightText = new LinearLayout(mContext);

            LinearLayout.LayoutParams subRightParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0.5f
            );
            linSubRightText.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            linSubRightText.setLayoutParams(subRightParams);
            linSubRightText.setOrientation(LinearLayout.HORIZONTAL);
            linSubRightText.setWeightSum(1);

            /*
            textView for right subtext
             */
            AutofitTextView tvRightSubText = new AutofitTextView(mContext);

            LinearLayout.LayoutParams rightTextParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            rightTextParams.setMargins((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvRightSubText.setLayoutParams(rightTextParams);
            tvRightSubText.setGravity(Gravity.CENTER);
            tvRightSubText.setSingleLine(true);
            tvRightSubText.setTextColor(mContext.getResources().getColorStateList(R.color.s_text_yellow));//(mContext.getResources().getColor(R.color.colorBlack));
            tvRightSubText.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
            tvRightSubText.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextMaxSize, mContext));
            tvRightSubText.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextSize, mContext));
            tvRightSubText.setText(rightSub);
            tvRightSubText.setTag("rightSubText");

            parent.addView(tvTitle);

            linSubLeftText.addView(tvLeftSubText);
            linSubRightText.addView(tvRightSubText);

            linSubText.addView(linSubLeftText);
            linSubText.addView(linSubRightText);

            parent.addView(linSubText);

            ArrayList<View> views = new ArrayList<View>();

            views.add(parent);
            views.add(tvTitle);
            views.add(tvLeftSubText);
            views.add(tvRightSubText);

            return views;

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
0 is parent (LinearLayout)
1 is tvTitle (TextView)
2 is tvLeftSubText (TextView)
3 is tvRightSubText (TextView)
*/
    public ArrayList<View> addRightMenuButtonForTopSub(String title, String topSub, String leftSub, String rightSub) {

        try {

        /*
        Parent
         */
            LinearLayout parent = new LinearLayout(mContext);

            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    mRightWeight
            );
            parentParams.gravity = Gravity.CENTER;
            parentParams.setMargins(1, 1, 1, 1);
            parent.setLayoutParams(parentParams);

            parent.setBackground(mContext.getResources().getDrawable(R.drawable.right_menu_layout_selector));
            parent.setOrientation(LinearLayout.VERTICAL);
            parent.setWeightSum(1);

        /*
        TextView for TitleText
         */
            AutofitTextView tvTitle = new AutofitTextView(mContext);

            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    0.4f
            );
            titleParams.gravity = Gravity.RIGHT;

            tvTitle.setLayoutParams(titleParams);
            tvTitle.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            tvTitle.setTextColor(mContext.getResources().getColorStateList(R.color.s_text_top));//(mContext.getResources().getColor(R.color.colorBlack));
            tvTitle.setPadding((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvTitle.setPaddingRelative((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvTitle.setSingleLine(true);
            tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
            tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mRightTextMaxSize, mContext));
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mRightTextSize, mContext));
            tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
            tvTitle.setText(title);
            tvTitle.setDuplicateParentStateEnabled(true); // 부모 상태(enable, ...)와 동기화
            tvTitle.setTag("title");

        /*
        LinearLayout for topsubText
         */

            LinearLayout linTopSubText = new LinearLayout(mContext);

            LinearLayout.LayoutParams topSubParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    0.3f
            );
            linTopSubText.setLayoutParams(topSubParams);
            linTopSubText.setOrientation(LinearLayout.HORIZONTAL);
            linTopSubText.setWeightSum(1);

            LinearLayout linSubTopLeftText = new LinearLayout(mContext);

            LinearLayout.LayoutParams subLeftParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0.5f
            );

            linSubTopLeftText.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            linSubTopLeftText.setLayoutParams(subLeftParams);
            linSubTopLeftText.setOrientation(LinearLayout.HORIZONTAL);
            linSubTopLeftText.setWeightSum(1);


            LinearLayout linSubTopRightText = new LinearLayout(mContext);

            LinearLayout.LayoutParams subTopRightParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0.5f
            );
            linSubTopRightText.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            linSubTopRightText.setLayoutParams(subTopRightParams);
            linSubTopRightText.setOrientation(LinearLayout.HORIZONTAL);
            linSubTopRightText.setWeightSum(1);

        /*
        textView for top right subtext
         */
            AutofitTextView tvTopRightSubText = new AutofitTextView(mContext);

            LinearLayout.LayoutParams topRightTextParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            topRightTextParams.setMargins((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvTopRightSubText.setLayoutParams(topRightTextParams);
            tvTopRightSubText.setGravity(Gravity.CENTER);
            tvTopRightSubText.setSingleLine(true);
            tvTopRightSubText.setTextColor(mContext.getResources().getColorStateList(R.color.s_text_yellow));
            tvTopRightSubText.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
            tvTopRightSubText.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextMaxSize, mContext));
            tvTopRightSubText.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextSize, mContext));
            tvTopRightSubText.setText(topSub);
            tvTopRightSubText.setTag("topSubText");


            /*
        LinearLayout for bottom subText
         */

            LinearLayout linBottomSubText = new LinearLayout(mContext);

            LinearLayout.LayoutParams bottomSubParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    0.3f
            );
            linBottomSubText.setLayoutParams(bottomSubParams);
            linBottomSubText.setOrientation(LinearLayout.HORIZONTAL);
            linBottomSubText.setWeightSum(1);

            LinearLayout linSubLeftText = new LinearLayout(mContext);

            LinearLayout.LayoutParams subTopLeftParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0.5f
            );

            linSubLeftText.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            linSubLeftText.setLayoutParams(subTopLeftParams);
            linSubLeftText.setOrientation(LinearLayout.HORIZONTAL);
            linSubLeftText.setWeightSum(1);


        /*
        textView for left subtext
         */
            AutofitTextView tvLeftSubText = new AutofitTextView(mContext);

            LinearLayout.LayoutParams leftTextParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            leftTextParams.setMargins((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvLeftSubText.setLayoutParams(leftTextParams);
            tvLeftSubText.setGravity(Gravity.CENTER);
            tvLeftSubText.setSingleLine(true);
            tvLeftSubText.setTextColor(mContext.getResources().getColorStateList(R.color.s_text_yellow));
            tvLeftSubText.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
            tvLeftSubText.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextMaxSize, mContext));
            tvLeftSubText.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextSize, mContext));
            tvLeftSubText.setText(leftSub);
            tvLeftSubText.setTag("leftSubText");


            LinearLayout linSubRightText = new LinearLayout(mContext);

            LinearLayout.LayoutParams subRightParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0.5f
            );
            linSubRightText.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            linSubRightText.setLayoutParams(subRightParams);
            linSubRightText.setOrientation(LinearLayout.HORIZONTAL);
            linSubRightText.setWeightSum(1);

        /*
        textView for right subtext
         */
            AutofitTextView tvRightSubText = new AutofitTextView(mContext);

            LinearLayout.LayoutParams rightTextParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            rightTextParams.setMargins((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvRightSubText.setLayoutParams(rightTextParams);
            tvRightSubText.setGravity(Gravity.CENTER);
            tvRightSubText.setSingleLine(true);
            tvRightSubText.setTextColor(mContext.getResources().getColorStateList(R.color.s_text_yellow));
            tvRightSubText.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
            tvRightSubText.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextMaxSize, mContext));
            tvRightSubText.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextSize, mContext));
            tvRightSubText.setText(rightSub);
            tvRightSubText.setTag("rightSubText");

            parent.addView(tvTitle);


            linSubTopRightText.addView(tvTopRightSubText);

            linTopSubText.addView(linSubTopLeftText);
            linTopSubText.addView(linSubTopRightText);

            linSubLeftText.addView(tvLeftSubText);
            linSubRightText.addView(tvRightSubText);

            linBottomSubText.addView(linSubLeftText);
            linBottomSubText.addView(linSubRightText);

            parent.addView(linTopSubText);
            parent.addView(linBottomSubText);

            ArrayList<View> views = new ArrayList<View>();

            views.add(parent);
            views.add(tvTitle);
            views.add(tvLeftSubText);
            views.add(tvRightSubText);
            views.add(tvTopRightSubText);

            return views;

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
  0 is parent (LinearLayout)
  1 is tvTitle (TextView)
  2 is tvLeftSubText (TextView)
  3 is tvMiddleSubText (TextView)
  4 is tvRightSubText
  */
    public ArrayList<View> addRightMenuButton(String title, String leftSub, String middleSub, String rightSub) {

        try {

        /*
        Parent
         */
            LinearLayout parent = new LinearLayout(mContext);

            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    mRightWeight
            );
            parentParams.gravity = Gravity.CENTER;
            parentParams.setMargins(1, 1, 1, 1);
            parent.setLayoutParams(parentParams);

            parent.setBackground(mContext.getResources().getDrawable(R.drawable.right_menu_layout_selector));
            parent.setOrientation(LinearLayout.VERTICAL);
            parent.setWeightSum(1);

        /*
        TextView for TitleText
         */
            AutofitTextView tvTitle = new AutofitTextView(mContext);

            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    0.5f
            );
            titleParams.gravity = Gravity.RIGHT;

            tvTitle.setLayoutParams(titleParams);
            tvTitle.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            tvTitle.setTextColor(mContext.getResources().getColorStateList(R.color.s_text_top));
            tvTitle.setPadding((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvTitle.setPaddingRelative((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvTitle.setSingleLine(true);
            tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
            tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mRightTextMaxSize, mContext));
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mRightTextSize, mContext));
            tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
            tvTitle.setText(title);
            tvTitle.setDuplicateParentStateEnabled(true); // 부모 상태(enable, ...)와 동기화
            tvTitle.setTag("title");

        /*
        LinearLayout for subText
         */

            LinearLayout linSubText = new LinearLayout(mContext);

            LinearLayout.LayoutParams subParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    0.5f
            );
            linSubText.setLayoutParams(subParams);
            linSubText.setOrientation(LinearLayout.HORIZONTAL);
            linSubText.setWeightSum(1);

        /*
        textView for left subtext
         */
            LinearLayout linSubLeftText = new LinearLayout(mContext);

            LinearLayout.LayoutParams subLeftParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1 / 3f
            );

            linSubLeftText.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            linSubLeftText.setLayoutParams(subLeftParams);
            linSubLeftText.setOrientation(LinearLayout.HORIZONTAL);
            linSubLeftText.setWeightSum(1);


            AutofitTextView tvLeftSubText = new AutofitTextView(mContext);

            LinearLayout.LayoutParams leftTextParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            leftTextParams.setMargins((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvLeftSubText.setLayoutParams(leftTextParams);
            tvLeftSubText.setGravity(Gravity.CENTER);
            tvLeftSubText.setSingleLine(true);
            tvLeftSubText.setTextColor(mContext.getResources().getColorStateList(R.color.s_text_yellow));
            tvLeftSubText.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
            tvLeftSubText.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextMaxSize, mContext));
            tvLeftSubText.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextSize, mContext));
            tvLeftSubText.setText(leftSub);
            tvLeftSubText.setTag("leftSubText");

            /*
            textView for middle subtext
            */

            LinearLayout linSubMiddleText = new LinearLayout(mContext);

            LinearLayout.LayoutParams subMiddleParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1 / 3f
            );
            linSubMiddleText.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            linSubMiddleText.setLayoutParams(subMiddleParams);
            linSubMiddleText.setOrientation(LinearLayout.HORIZONTAL);
            linSubMiddleText.setWeightSum(1);

            AutofitTextView tvMiddleSubText = new AutofitTextView(mContext);

            LinearLayout.LayoutParams middleTextParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            middleTextParams.setMargins((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvMiddleSubText.setLayoutParams(leftTextParams);
            tvMiddleSubText.setGravity(Gravity.CENTER);
            tvMiddleSubText.setSingleLine(true);
            tvMiddleSubText.setTextColor(mContext.getResources().getColorStateList(R.color.s_text_yellow));
            tvMiddleSubText.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
            tvMiddleSubText.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextMaxSize, mContext));
            tvMiddleSubText.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextSize, mContext));
            tvMiddleSubText.setText(middleSub);
            tvMiddleSubText.setTag("middleSubText");


            LinearLayout linSubRightText = new LinearLayout(mContext);

            LinearLayout.LayoutParams subRightParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1 / 3f
            );
            linSubRightText.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            linSubRightText.setLayoutParams(subRightParams);
            linSubRightText.setOrientation(LinearLayout.HORIZONTAL);
            linSubRightText.setWeightSum(1);

        /*
        textView for right subtext
         */
            AutofitTextView tvRightSubText = new AutofitTextView(mContext);

            LinearLayout.LayoutParams rightTextParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            rightTextParams.setMargins((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvRightSubText.setLayoutParams(rightTextParams);
            tvRightSubText.setGravity(Gravity.CENTER);
            tvRightSubText.setSingleLine(true);
            tvRightSubText.setTextColor(mContext.getResources().getColorStateList(R.color.s_text_yellow));
            tvRightSubText.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
            tvRightSubText.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextMaxSize, mContext));
            tvRightSubText.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextSize, mContext));
            tvRightSubText.setText(rightSub);
            tvRightSubText.setTag("rightSubText");

            parent.addView(tvTitle);

            linSubLeftText.addView(tvLeftSubText);
            linSubMiddleText.addView(tvMiddleSubText);
            linSubRightText.addView(tvRightSubText);

            linSubText.addView(linSubLeftText);
            linSubText.addView(linSubMiddleText);
            linSubText.addView(linSubRightText);

            parent.addView(linSubText);

            ArrayList<View> views = new ArrayList<View>();

            views.add(parent);
            views.add(tvTitle);
            views.add(tvLeftSubText);
            views.add(tvMiddleSubText);
            views.add(tvRightSubText);

            return views;

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
0 is parent (LinearLayout)
1 is tvTitle (TextView)
2 is tvLeftSubText (TextView)
3 is tvMiddleSubText (TextView)
4 is tvRightSubText
*/
    public ArrayList<View> addRightMenuButton(String title, ArrayList<String> subMenuList) {

        try {

        /*
        Parent
         */
            LinearLayout parent = new LinearLayout(mContext);

            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    mRightWeight
            );
            parentParams.gravity = Gravity.CENTER;
            parentParams.setMargins(1, 1, 1, 1);
            parent.setLayoutParams(parentParams);

            parent.setBackground(mContext.getResources().getDrawable(R.drawable.right_menu_layout_selector));
            parent.setOrientation(LinearLayout.VERTICAL);
            parent.setWeightSum(1);

        /*
        TextView for TitleText
         */
            AutofitTextView tvTitle = new AutofitTextView(mContext);

            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    0.5f
            );
            titleParams.gravity = Gravity.RIGHT;

            tvTitle.setLayoutParams(titleParams);
            tvTitle.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            tvTitle.setTextColor(mContext.getResources().getColorStateList(R.color.s_text_top));
            tvTitle.setPadding((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvTitle.setPaddingRelative((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvTitle.setSingleLine(true);
            tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
            tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mRightTextMaxSize, mContext));
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mRightTextSize, mContext));
            tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
            tvTitle.setText(title);
            tvTitle.setDuplicateParentStateEnabled(true); // 부모 상태(enable, ...)와 동기화
            tvTitle.setTag("title");

        /*
        LinearLayout for subText
         */

            LinearLayout linSubParent = new LinearLayout(mContext);

            LinearLayout.LayoutParams subParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    0.5f
            );
            linSubParent.setLayoutParams(subParams);
            linSubParent.setOrientation(LinearLayout.HORIZONTAL);
            linSubParent.setWeightSum(1);

            parent.addView(tvTitle);

            ArrayList<View> views = new ArrayList<View>();

            views.add(tvTitle);

        /*
        textView for left subtext
         */


            for (int i = 0; i < subMenuList.size(); i++) {
                LinearLayout linSubText = new LinearLayout(mContext);

                LinearLayout.LayoutParams subLeftParams = new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        1 / subMenuList.size()
                );

                linSubText.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                linSubText.setLayoutParams(subLeftParams);
                linSubText.setOrientation(LinearLayout.HORIZONTAL);
                linSubText.setWeightSum(1);


                AutofitTextView tvSubText = new AutofitTextView(mContext);

                LinearLayout.LayoutParams leftTextParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

                leftTextParams.setMargins((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
                tvSubText.setLayoutParams(leftTextParams);
                tvSubText.setGravity(Gravity.CENTER);
                tvSubText.setSingleLine(true);
                tvSubText.setTextColor(mContext.getResources().getColorStateList(R.color.s_text_top));
                tvSubText.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
                tvSubText.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextMaxSize, mContext));
                tvSubText.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextSize, mContext));
                tvSubText.setText(subMenuList.get(i));

                linSubText.addView(tvSubText);
                linSubParent.addView(linSubText);

                views.add(tvSubText);

            }
            views.add(parent);
            parent.addView(linSubParent);

            return views;

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////



    /*Dynamic LayoutBase View
    *
    *                     <me.grantland.widget.AutofitTextView
                        android:id="@+id/tvMeasurement"
                        android:layout_width="0dp"
                        android:layout_height="match_parent"
                        android:layout_weight="0.1428"
                        android:text="@string/measurement_name"
                        android:background="@drawable/bottom_menu_unselected_button"
                        android:textColor="@color/colorBlack"
                        android:gravity="center"
                        android:singleLine="true"
                        autofit:minTextSize="8sp"/>
    *
    *
    * */

    /*
    0 is parent
    1 is tvTitle
     */
    public ArrayList<View> addBottomMenuButton(String title) {

        try {

            LinearLayout parent = new LinearLayout(mContext);

            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    mBottomWeight
            );
            parentParams.gravity = Gravity.CENTER;
            parent.setLayoutParams(parentParams);

            parent.setBackground(mContext.getResources().getDrawable(R.drawable.s_btn_bottom));
            parent.setOrientation(LinearLayout.VERTICAL);
            parent.setWeightSum(1);

            AutofitTextView tvTitle = new AutofitTextView(mContext);

            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            titleParams.gravity = Gravity.CENTER;

            tvTitle.setLayoutParams(titleParams);
            tvTitle.setGravity(Gravity.CENTER);
            tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
            tvTitle.setTextColor(mContext.getResources().getColorStateList(R.color.s_text_top));
            tvTitle.setSingleLine(true);
            tvTitle.setPadding((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
            tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mBottomTextMaxSize, mContext));
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mBottomTextSize, mContext));
            tvTitle.setText(title);
            tvTitle.setDuplicateParentStateEnabled(true); // 부모 상태(enable, ...)와 동기화

            parent.addView(tvTitle);

            ArrayList<View> views = new ArrayList<View>();
            views.add(parent);
            views.add(tvTitle);

            return views;

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;

    }

    public ArrayList<View> addBottomMenuButton(String title, float weight) {

        try {

            LinearLayout parent = new LinearLayout(mContext);

            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    weight
            );
            parentParams.gravity = Gravity.CENTER;
            parent.setLayoutParams(parentParams);

            parent.setBackground(mContext.getResources().getDrawable(R.drawable.s_btn_bottom));
            parent.setOrientation(LinearLayout.VERTICAL);
            parent.setWeightSum(1);

            AutofitTextView tvTitle = new AutofitTextView(mContext);

            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            titleParams.gravity = Gravity.CENTER;

            tvTitle.setLayoutParams(titleParams);
            tvTitle.setGravity(Gravity.CENTER);
            tvTitle.setTextColor(mContext.getResources().getColorStateList(R.color.s_text_top));
            tvTitle.setSingleLine(true);
            tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
            tvTitle.setPadding((int) convertDpToPixel(5, mContext), 0, (int) convertDpToPixel(5, mContext), 0);
            tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
            tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mBottomTextMaxSize, mContext));
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mBottomTextSize, mContext));
            tvTitle.setText(title);
            tvTitle.setDuplicateParentStateEnabled(true); // 부모 상태(enable, ...)와 동기화

            parent.addView(tvTitle);

            ArrayList<View> views = new ArrayList<View>();
            views.add(parent);
            views.add(tvTitle);

            return views;

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;

    }

    public ArrayList<ArrayList<View>> getMultipleLayout(String[] title) {

        ArrayList<ArrayList<View>> viewList = new ArrayList<>();

        for (String name : title) {

            viewList.add(addRightMenuButton(name, Gravity.RIGHT | Gravity.CENTER_VERTICAL));

        }

        return viewList;

    }

    public static float convertPixelsToDp(float px, Context context) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertDpToPixel(float dp, Context context) {

        try {

            float dpi = context.getResources().getDisplayMetrics().densityDpi;
            float density = context.getResources().getDisplayMetrics().density;
            float defaultDensity = DisplayMetrics.DENSITY_DEFAULT;
            float resolutionHeight = context.getResources().getDisplayMetrics().heightPixels;

//        InitActivity.logMsg("dpi/density", dpi  + " " + density);

            float resolutionPerDpi = resolutionHeight / dpi;
//        InitActivity.logMsg("ResolutionPerDpi", resolutionPerDpi + "");

            if (resolutionPerDpi > 3.9f) {

                dpi *= 1.5f;

            } else if (resolutionPerDpi < 2.6f) {

                dpi /= 1.5f;

            }

            float px = dp * (dpi / defaultDensity);

            return px;

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
