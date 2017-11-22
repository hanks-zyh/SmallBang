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
 * Created by hanks
 */
public class CircleView extends View {
    public static final Property<CircleView, Float> OUTER_CIRCLE_RADIUS_PROGRESS =
            new Property<CircleView, Float>(Float.class, "progress") {
                @Override
                public Float get(CircleView object) {
                    return object.getProgress();
                }

                @Override
                public void set(CircleView object, Float value) {
                    object.setProgress(value);
                }
            };
    public static int START_COLOR = 0xFFDF4288;
    public static int END_COLOR = 0xFFCD8BF8;
    private int startColor = START_COLOR;
    private int endColor = START_COLOR;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint ringPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float progress = 0f;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(START_COLOR);
        ringPaint.setStyle(Paint.Style.STROKE);
        ringPaint.setColor(START_COLOR);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (progress == 0) {
            canvas.drawColor(Color.TRANSPARENT);
            return;
        }
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2;
        if (progress <= 0.5) {
            canvas.drawCircle(width / 2, height / 2, radius * 2 * progress, circlePaint);
            return;
        }
        float strokeWidth = 2f * radius * (1f - progress);
        if (strokeWidth <= 0) {
            canvas.drawColor(Color.TRANSPARENT);
            return;
        }
        ringPaint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(width / 2, height / 2, radius - strokeWidth / 2, ringPaint);
    }

    private void updateCircleColor() {
        if (progress <= 0.5) {
            Integer color = (Integer) argbEvaluator.evaluate(progress * 2f, startColor, endColor);
            this.circlePaint.setColor(color);
        } else {
            this.ringPaint.setColor(endColor);
        }
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        updateCircleColor();
        postInvalidate();
    }
}
