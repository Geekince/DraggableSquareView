package com.widget.draggablesquareview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.View;

import androidx.annotation.Nullable;

public class StepProgressView extends View {

    private float space;
    private int count;
    private int targetStep;
    private int defaultStep;
    private long currentTime;
    private StepEndAnimationListener listener;

    private Choreographer choreographer;
    private int backgroundColor = 0;
    private int progressColor = 0;
    private int duration = 350;
    private final Paint progressPaint = new Paint();
    private final Paint backgroundPaint = new Paint();
    private final Choreographer.FrameCallback frameCallback = new Choreographer.FrameCallback() {
        @Override
        public void doFrame(long j) {
            if (defaultStep < targetStep) {
                invalidate();
            }
        }
    };

    public interface StepEndAnimationListener {
        void end(int i);
    }

    public StepProgressView(Context context) {
        super(context);
    }

    public StepProgressView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public StepProgressView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public void setTargetStep(int step) {
        if (step <= this.targetStep) {
            return;
        }
        this.targetStep = step;
        this.currentTime = System.currentTimeMillis();
        this.choreographer.postFrameCallback(this.frameCallback);
    }

    public void setNextStep() {
        this.targetStep++;
        this.currentTime = System.currentTimeMillis();
        this.choreographer.postFrameCallback(this.frameCallback);
    }

    public void setPreviousStep() {
        this.targetStep--;
        this.defaultStep--;
        this.currentTime = System.currentTimeMillis();
        invalidate();
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public void setDefaultStep(int step) {
        if (step <= this.targetStep) {
            return;
        }
        this.targetStep = step;
        this.defaultStep = step;
    }

    public void setStepAnimationDuration(int duration) {
        this.duration = duration;
    }

    public void setStepCount(int count) {
        this.count = count;
    }

    public void setStepEndAnimationListener(StepEndAnimationListener listener) {
        this.listener = listener;
    }

    private void init() {
        this.backgroundColor = getResources().getColor(R.color.purple_200);
        this.progressColor = getResources().getColor(R.color.purple_500);

        this.progressPaint.setAntiAlias(true);
        this.progressPaint.setDither(true);
        this.progressPaint.setStyle(Paint.Style.STROKE);
        this.progressPaint.setStrokeCap(Paint.Cap.ROUND);
        this.progressPaint.setColor(this.progressColor);

        this.backgroundPaint.setAntiAlias(true);
        this.backgroundPaint.setDither(true);
        this.backgroundPaint.setStyle(Paint.Style.STROKE);
        this.backgroundPaint.setStrokeCap(Paint.Cap.ROUND);
        this.backgroundPaint.setColor(this.backgroundColor);

        this.choreographer = Choreographer.getInstance();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getHeight() <= 0 || getWidth() <= 0) {
            return;
        }
        float width = (getWidth() - space * (count - 1)) / (float) this.count;
        int height = getHeight() / 2;
        this.progressPaint.setStrokeWidth(getHeight());
        this.backgroundPaint.setStrokeWidth(getHeight());

        for (int i = 0; i < this.count; i++) {
            if (this.targetStep > this.defaultStep && this.defaultStep == i) {
                float currentTimeMillis = (((float) (System.currentTimeMillis() - this.currentTime))) / this.duration;
                float startX1 = (i * width) + (i * this.space);
                if (currentTimeMillis >= 1.0f) {
                    canvas.drawLine(startX1 + height, height, (startX1 + width) - height, height, this.progressPaint);
                    this.defaultStep = this.targetStep;
                    if (listener != null) {
                        this.listener.end(this.defaultStep);
                    }
                } else {
                    float startX2 = startX1 + height;
                    canvas.drawLine(startX2, height, (startX1 + width) - height, height, this.backgroundPaint);
                    canvas.drawLine(startX2, height, startX2 + ((width - getHeight()) * currentTimeMillis), height, this.progressPaint);
                    this.choreographer.postFrameCallback(this.frameCallback);
                }
            } else if (i < this.targetStep) {
                float startX1 = (i * width) + (i * this.space);
                canvas.drawLine(startX1 + height, height, (startX1 + width) - height, height, this.progressPaint);
            } else {
                float startX2 = (i * width) + (i * this.space);
                canvas.drawLine(startX2 + height, height, (startX2 + width) - height, height, this.backgroundPaint);
            }
        }
    }

}