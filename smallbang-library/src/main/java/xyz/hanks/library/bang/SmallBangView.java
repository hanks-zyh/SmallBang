package xyz.hanks.library.bang;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.Property;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import xyz.hanks.library.R;


/**
 * Created by hanks.
 */
public class SmallBangView extends FrameLayout {

    public static final Property<View, Float> SCALE = new FloatProperty<View>("scale") {
        @Override
        public void setValue(View object, float value) {
            object.setScaleX(value);
            object.setScaleY(value);
        }

        @Override
        public Float get(View object) {
            return object.getScaleY();
        }
    };

    private static AccelerateDecelerateInterpolator ACCELERATE_DECELERATE_INTERPOLATOR;
    private static OvershootInterpolator OVERSHOOT_INTERPOLATOR;
    private int circleStartColor;
    private int circleEndColor;
    private int dotPrimaryColor;
    private int dotSecondaryColor;
    private int animScaleFactor;
    private CircleView vCircle;
    private DotsView vDotsView;
    private View scaleView;
    private boolean isChecked;
    private AnimatorSet animatorSet;
    private boolean init;

    public SmallBangView(Context context) {
        this(context, null);
    }

    public SmallBangView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmallBangView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SmallBangView, defStyleAttr, 0);

        circleStartColor = array.getColor(R.styleable.SmallBangView_circle_start_color, CircleView.START_COLOR);
        circleEndColor = array.getColor(R.styleable.SmallBangView_circle_end_color, CircleView.END_COLOR);

        dotPrimaryColor = array.getColor(R.styleable.SmallBangView_dots_primary_color, DotsView.COLOR_1);
        dotSecondaryColor = array.getColor(R.styleable.SmallBangView_dots_secondary_color, DotsView.COLOR_2);

        animScaleFactor = array.getColor(R.styleable.SmallBangView_anim_scale_factor, 4);

        Boolean status = array.getBoolean(R.styleable.SmallBangView_liked, false);
        setSelected(status);

        array.recycle();

        OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(animScaleFactor);
        ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    }

    public void setCircleEndColor(int circleEndColor) {
        this.circleEndColor = circleEndColor;
    }

    public void setCircleStartColor(int circleStartColor) {
        this.circleStartColor = circleStartColor;
    }

    public void setDotPrimaryColor(int dotPrimaryColor) {
        this.dotPrimaryColor = dotPrimaryColor;
    }

    public void setDotSecondaryColor(int dotSecondaryColor) {
        this.dotSecondaryColor = dotSecondaryColor;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initChildren();
    }

    private void initChildren() {
        if (init) {
            return;
        }
        init = true;
        int childCount = getChildCount();
        if (childCount != 1) {
            throw new RuntimeException("must have one child view");
        }
        scaleView = getChildAt(0);

        int iconSize = Math.min(scaleView.getMeasuredHeight(), scaleView.getMeasuredWidth());

        LayoutParams params = new FrameLayout.LayoutParams(iconSize, iconSize);
        params.gravity = Gravity.CENTER;

        vDotsView = new DotsView(getContext());
        LayoutParams dotParams = new LayoutParams((int) (iconSize * 2.5f), (int) (iconSize * 2.5f));
        dotParams.gravity = Gravity.CENTER;
        vDotsView.setLayoutParams(dotParams);
        vDotsView.setColors(new int[]{dotPrimaryColor, dotSecondaryColor, dotPrimaryColor, dotSecondaryColor});

        LayoutParams circleParam = new FrameLayout.LayoutParams((int) (iconSize * 1.3f), (int) (iconSize * 1.3f));
        circleParam.gravity = Gravity.CENTER;
        vCircle = new CircleView(getContext());
        vCircle.setLayoutParams(circleParam);
        vCircle.setStartColor(circleStartColor);
        vCircle.setEndColor(circleEndColor);
        addView(vCircle, 0);
        addView(vDotsView, 0);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        isChecked = selected;
    }

    public void likeAnimation() {
        likeAnimation(null);
    }

    public void likeAnimation(Animator.AnimatorListener listener) {

        if (animatorSet != null) {
            animatorSet.cancel();
        }

        if (isChecked) {
            scaleView.animate().cancel();
            scaleView.setScaleX(0);
            scaleView.setScaleY(0);
            vCircle.setProgress(0);
            vDotsView.setCurrentProgress(0);

            animatorSet = new AnimatorSet();

            ObjectAnimator outerCircleAnimator = ObjectAnimator.ofFloat(vCircle, CircleView.OUTER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
            outerCircleAnimator.setDuration(250);

            ObjectAnimator starScaleAnimator = ObjectAnimator.ofFloat(scaleView, SCALE, 0.2f, 1f);
            starScaleAnimator.setDuration(250);
            starScaleAnimator.setStartDelay(250);
            starScaleAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

            ObjectAnimator dotsAnimator = ObjectAnimator.ofFloat(vDotsView, DotsView.DOTS_PROGRESS, 0f, 1f);
            dotsAnimator.setDuration(900);
            dotsAnimator.setStartDelay(50);
            dotsAnimator.setInterpolator(ACCELERATE_DECELERATE_INTERPOLATOR);

            animatorSet.playTogether(
                    outerCircleAnimator,
                    starScaleAnimator,
                    dotsAnimator
            );

            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    vCircle.setProgress(0);
                    vDotsView.setCurrentProgress(0);
                    scaleView.setScaleX(1);
                    scaleView.setScaleY(1);
                }
            });

            animatorSet.start();

            if (listener != null) {
                animatorSet.addListener(listener);
            }
        } else {
            vCircle.setProgress(0);
            vDotsView.setCurrentProgress(0);
        }
    }

}
