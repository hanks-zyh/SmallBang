package xyz.hanks.library.bang;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;

/**
 * Created by Miroslaw Stanek on 20.12.2015.
 */
public class DotsView extends View {
    public static final Property<DotsView, Float> DOTS_PROGRESS = new Property<DotsView, Float>(Float.class, "dotsProgress") {
        @Override
        public Float get(DotsView object) {
            return object.getCurrentProgress();
        }

        @Override
        public void set(DotsView object, Float value) {
            object.setCurrentProgress(value);
        }
    };
    public static final int COLOR_1 = 0xFFDF4288;
    public static final int COLOR_2 = 0xFFCD8BF8;
    public static final int COLOR_3 = 0XFF2B9DF2;
    public static final int COLOR_4 = 0XFFA4EEB4;
    private static final int DOTS_COUNT = 7;
    private static final int OUTER_DOTS_POSITION_ANGLE = 360 / DOTS_COUNT;
    private final Paint[] circlePaints = new Paint[4];
    private int[] colors = {COLOR_1, COLOR_2, COLOR_3, COLOR_4};
    private int centerX;
    private int centerY;
    private float maxOuterDotsRadius;
    private float maxInnerDotsRadius;
    private float maxDotSize;
    private float currentProgress = 0;
    private float currentRadius1 = 0;
    private float currentDotSize1 = 0;
    private float currentDotSize2 = 0;
    private float currentRadius2 = 0;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    public DotsView(Context context) {
        super(context);
        init();
    }

    public DotsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public DotsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        for (int i = 0; i < circlePaints.length; i++) {
            circlePaints[i] = new Paint(Paint.ANTI_ALIAS_FLAG);
            circlePaints[i].setStyle(Paint.Style.FILL);
        }
    }

    public void setColors(int[] colors) {
        if (colors.length < 4) {
            throw new RuntimeException("the count of dot colors must not be less 4");
        }
        this.colors = colors;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        maxDotSize = Utils.dp2px(getContext(), 1.1f);
        maxOuterDotsRadius = w / 2 - maxDotSize * 2;
        maxInnerDotsRadius = 0.8f * maxOuterDotsRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentProgress == 0) {
            canvas.drawColor(Color.TRANSPARENT);
            return;
        }
        drawOuterDotsFrame(canvas);
        drawInnerDotsFrame(canvas);
    }

    private void drawOuterDotsFrame(Canvas canvas) {
        for (int i = 0; i < DOTS_COUNT; i++) {
            int cX = (int) (centerX + currentRadius1 * Math.cos(i * OUTER_DOTS_POSITION_ANGLE * Math.PI / 180));
            int cY = (int) (centerY + currentRadius1 * Math.sin(i * OUTER_DOTS_POSITION_ANGLE * Math.PI / 180));
            canvas.drawCircle(cX, cY, currentDotSize1, circlePaints[i % circlePaints.length]);
        }
    }

    private void drawInnerDotsFrame(Canvas canvas) {
        for (int i = 0; i < DOTS_COUNT; i++) {
            int cX = (int) (centerX + currentRadius2 * Math.cos((i * OUTER_DOTS_POSITION_ANGLE - 10) * Math.PI / 180));
            int cY = (int) (centerY + currentRadius2 * Math.sin((i * OUTER_DOTS_POSITION_ANGLE - 10) * Math.PI / 180));
            canvas.drawCircle(cX, cY, currentDotSize2, circlePaints[(i + 1) % circlePaints.length]);
        }
    }

    public float getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(float currentProgress) {
        this.currentProgress = currentProgress;

        updateInnerDotsPosition();
        updateOuterDotsPosition();
        updateDotsPaints();
        updateDotsAlpha();

        postInvalidate();
    }

    private void updateInnerDotsPosition() {
        if (currentProgress < 0.3f) {
            this.currentRadius2 = (float) Utils.mapValueFromRangeToRange(currentProgress, 0, 0.3f, 0.f, maxInnerDotsRadius);
        } else {
            this.currentRadius2 = maxInnerDotsRadius;
        }

        if (currentProgress < 0.2) {
            this.currentDotSize2 = maxDotSize;
        } else if (currentProgress < 0.5) {
            this.currentDotSize2 = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.2f, 0.5f, maxDotSize, 0.5 * maxDotSize);
        } else {
            this.currentDotSize2 = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.5f, 1f, maxDotSize * 0.5f, 0);
        }

    }

    private void updateOuterDotsPosition() {
        if (currentProgress < 0.3f) {
            this.currentRadius1 = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.0f, 0.3f, 0, maxOuterDotsRadius * 0.8f);
        } else {
            this.currentRadius1 = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.3f, 1f, 0.8f * maxOuterDotsRadius, maxOuterDotsRadius);
        }

        if (currentProgress < 0.7) {
            this.currentDotSize1 = maxDotSize;
        } else {
            this.currentDotSize1 = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.7f, 1f, maxDotSize, 0);
        }
    }

    private void updateDotsPaints() {
        if (currentProgress < 0.5f) {
            float progress = (float) Utils.mapValueFromRangeToRange(currentProgress, 0f, 0.5f, 0, 1f);
            circlePaints[0].setColor((Integer) argbEvaluator.evaluate(progress, colors[0], colors[1]));
            circlePaints[1].setColor((Integer) argbEvaluator.evaluate(progress, colors[1], colors[2]));
            circlePaints[2].setColor((Integer) argbEvaluator.evaluate(progress, colors[2], colors[3]));
            circlePaints[3].setColor((Integer) argbEvaluator.evaluate(progress, colors[3], colors[0]));
        } else {
            float progress = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.5f, 1f, 0, 1f);
            circlePaints[0].setColor((Integer) argbEvaluator.evaluate(progress, colors[1], colors[2]));
            circlePaints[1].setColor((Integer) argbEvaluator.evaluate(progress, colors[2], colors[3]));
            circlePaints[2].setColor((Integer) argbEvaluator.evaluate(progress, colors[3], colors[0]));
            circlePaints[3].setColor((Integer) argbEvaluator.evaluate(progress, colors[0], colors[1]));
        }
    }

    private void updateDotsAlpha() {
        float progress = (float) Utils.clamp(currentProgress, 0.6f, 1f);
        int alpha = (int) Utils.mapValueFromRangeToRange(progress, 0.6f, 1f, 255, 0);
        circlePaints[0].setAlpha(alpha);
        circlePaints[1].setAlpha(alpha);
        circlePaints[2].setAlpha(alpha);
        circlePaints[3].setAlpha(alpha);
    }
}
