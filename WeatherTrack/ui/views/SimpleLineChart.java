// SOLUTION 4: Custom Simple Chart Implementation
// Create this file: app/src/main/java/com/example/weathertrack/ui/views/SimpleLineChart.java

package com.example.weathertrack.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class SimpleLineChart extends View {
    private List<Float> dataPoints = new ArrayList<>();
    private List<String> labels = new ArrayList<>();
    private Paint linePaint;
    private Paint pointPaint;
    private Paint textPaint;
    private Paint gridPaint;

    public SimpleLineChart(Context context) {
        super(context);
        init();
    }

    public SimpleLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        linePaint = new Paint();
        linePaint.setColor(Color.BLUE);
        linePaint.setStrokeWidth(4f);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);

        pointPaint = new Paint();
        pointPaint.setColor(Color.RED);
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(24f);
        textPaint.setAntiAlias(true);

        gridPaint = new Paint();
        gridPaint.setColor(Color.LTGRAY);
        gridPaint.setStrokeWidth(1f);
        gridPaint.setStyle(Paint.Style.STROKE);
    }

    public void setData(List<Float> data, List<String> labels) {
        this.dataPoints = new ArrayList<>(data);
        this.labels = new ArrayList<>(labels);
        invalidate(); // Trigger redraw
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (dataPoints.isEmpty()) return;

        float width = getWidth() - 100; // Leave margin
        float height = getHeight() - 100;
        float startX = 50;
        float startY = 50;

        // Find min and max values
        float minVal = Float.MAX_VALUE;
        float maxVal = Float.MIN_VALUE;
        for (float val : dataPoints) {
            minVal = Math.min(minVal, val);
            maxVal = Math.max(maxVal, val);
        }

        // Draw grid lines
        for (int i = 0; i <= 5; i++) {
            float y = startY + (height / 5) * i;
            canvas.drawLine(startX, y, startX + width, y, gridPaint);
        }

        // Draw data line
        Path path = new Path();
        for (int i = 0; i < dataPoints.size(); i++) {
            float x = startX + (width / (dataPoints.size() - 1)) * i;
            float y = startY + height - ((dataPoints.get(i) - minVal) / (maxVal - minVal)) * height;

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }

            // Draw points
            canvas.drawCircle(x, y, 6f, pointPaint);

            // Draw temperature values
            canvas.drawText(String.format("%.1f°", dataPoints.get(i)), x - 15, y - 15, textPaint);
        }

        canvas.drawPath(path, linePaint);

        // Draw title
        canvas.drawText("Temperature Trend", startX, 30, textPaint);
    }
}


