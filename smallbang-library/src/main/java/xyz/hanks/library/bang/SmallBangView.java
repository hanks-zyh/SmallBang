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
    private int animScaleFactor;
    private CircleView vCircle;
    private DotsView vDotsView;
    private View scaleView;
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
        setWillNotDraw(false);
        final TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SmallBangView, defStyleAttr, 0);

        circleStartColor = array.getColor(R.styleable.SmallBangView_circle_start_color, CircleView.START_COLOR);
        circleEndColor = array.getColor(R.styleable.SmallBangView_circle_end_color, CircleView.END_COLOR);

        int dotPrimaryColor = array.getColor(R.styleable.SmallBangView_dots_primary_color, DotsView.COLOR_1);
        int dotSecondaryColor = array.getColor(R.styleable.SmallBangView_dots_secondary_color, DotsView.COLOR_2);

        animScaleFactor = array.getColor(R.styleable.SmallBangView_anim_scale_factor, 3);

        Boolean status = array.getBoolean(R.styleable.SmallBangView_liked, false);
        setSelected(status);

        array.recycle();

        OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(animScaleFactor);
        ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();


        FrameLayout.LayoutParams dotParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        dotParams.gravity = Gravity.CENTER;
        vDotsView = new DotsView(getContext());
        vDotsView.setLayoutParams(dotParams);
        vDotsView.setColors(new int[]{dotPrimaryColor, dotSecondaryColor, dotPrimaryColor, dotSecondaryColor});

        FrameLayout.LayoutParams circleParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        circleParams.gravity = Gravity.CENTER;
        vCircle = new CircleView(getContext());
        vCircle.setStartColor(circleStartColor);
        vCircle.setEndColor(circleEndColor);
        vCircle.setLayoutParams(circleParams);

        addView(vDotsView);
        addView(vCircle);
    }

    public void setCircleEndColor(int circleEndColor) {
        this.circleEndColor = circleEndColor;
        vCircle.setEndColor(circleEndColor);
    }

    public void setCircleStartColor(int circleStartColor) {
        this.circleStartColor = circleStartColor;
        vCircle.setStartColor(circleStartColor);
    }

    public void setDotColors(int[] colors) {
        vDotsView.setColors(colors);
    }

    public void setAnimScaleFactor(int animScaleFactor) {
        this.animScaleFactor = animScaleFactor;
        OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(animScaleFactor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        if (childCount != 3) {
            throw new RuntimeException("must have one child view");
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        if (scaleView == null) {
            scaleView = findScaleView();
        }
        int iconSize = Math.min(scaleView.getMeasuredWidth(), scaleView.getMeasuredHeight());

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child instanceof CircleView) {
                measureChild(child,
                        MeasureSpec.makeMeasureSpec((int) (iconSize * 1.5f), MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec((int) (iconSize * 1.5f), MeasureSpec.EXACTLY));
            } else if (child instanceof DotsView) {
                measureChild(child,
                        MeasureSpec.makeMeasureSpec((int) (iconSize * 2.5f), MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec((int) (iconSize * 2.5f), MeasureSpec.EXACTLY));
            }
        }
        int width = sizeWidth;
        int height = sizeHeight;
        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
            width = (int) (iconSize * 2.5f);
        }
        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            height = (int) (iconSize * 2.5f);
        }
        setMeasuredDimension(width, height);
    }

    private View findScaleView() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (isScaleView(child)) {
                return child;
            }
        }
        throw new RuntimeException("must have one child in SmallBangView");
    }

    private boolean isScaleView(View child) {
        return child != null && !(child instanceof DotsView) && !(child instanceof CircleView);
    }

    public void stopAnimation() {
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        scaleView.setScaleX(1);
        scaleView.setScaleY(1);
        vCircle.setProgress(0);
        vDotsView.setCurrentProgress(0);
    }

    public void likeAnimation() {
        likeAnimation(null);
    }

    public void likeAnimation(Animator.AnimatorListener listener) {

        if (animatorSet != null) {
            animatorSet.cancel();
        }

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
        dotsAnimator.setDuration(800);
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
    }
}
