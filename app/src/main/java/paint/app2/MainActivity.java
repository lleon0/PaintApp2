package paint.app2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;
    Paint paint;
    View view;
    Path path2;
    Bitmap bitmap;
    Canvas canvas;
    Button tyhjenna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout1);

        tyhjenna = (Button)findViewById(R.id.tyhjenna);

        view = new SketchSheetView(MainActivity.this);

        paint = new Paint();

        path2 = new Path();

        relativeLayout.addView(view, new LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        paint.setDither(true);

        paint.setColor(Color.BLUE);

        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeJoin(Paint.Join.ROUND);

        paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setStrokeWidth(10);

        tyhjenna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                path2.reset();

            }
        });


        /*musta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                paint.setColor(Color.parseColor("#000000"));

            }
        });

        valkoinen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                paint.setColor(Color.parseColor("#FFFFFF"));

            }
        });*/
    }

    class SketchSheetView extends View {

        public SketchSheetView(Context context) {

            super(context);

            bitmap = Bitmap.createBitmap(800, 480, Bitmap.Config.ARGB_4444);

            canvas = new Canvas(bitmap);

            this.setBackgroundColor(Color.LTGRAY);
        }

        private ArrayList<DrawingClass> DrawingClassArrayList = new ArrayList<DrawingClass>();

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            DrawingClass pathWithPaint = new DrawingClass();

            canvas.drawPath(path2, paint);

            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                path2.moveTo(event.getX(), event.getY());

                path2.lineTo(event.getX(), event.getY());
            }
            else if (event.getAction() == MotionEvent.ACTION_MOVE) {

                path2.lineTo(event.getX(), event.getY());

                pathWithPaint.setPath(path2);

                pathWithPaint.setPaint(paint);

                DrawingClassArrayList.add(pathWithPaint);
            }

            invalidate();
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (DrawingClassArrayList.size() > 0) {

                canvas.drawPath(
                        DrawingClassArrayList.get(DrawingClassArrayList.size() - 1).getPath(),

                        DrawingClassArrayList.get(DrawingClassArrayList.size() - 1).getPaint());
            }
        }

    }


    public class DrawingClass {

        Path DrawingClassPath;
        Paint DrawingClassPaint;


        public Path getPath() {
            return DrawingClassPath;
        }

        public void setPath(Path path) {
            this.DrawingClassPath = path;
        }


        public Paint getPaint() {
            return DrawingClassPaint;
        }

        public void setPaint(Paint paint) {
            this.DrawingClassPaint = paint;
        }
    }

}