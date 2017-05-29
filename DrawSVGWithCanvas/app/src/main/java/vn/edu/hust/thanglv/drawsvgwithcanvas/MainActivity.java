package vn.edu.hust.thanglv.drawsvgwithcanvas;

import android.graphics.drawable.Animatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import vn.edu.hust.thanglv.drawsvgwithcanvas.writer.SVGCanvasView;
import vn.edu.hust.thanglv.drawsvgwithcanvas.writer.TextPoint;

public class MainActivity extends AppCompatActivity {
    private SVGCanvasView svg_kanji;
    Button btn;
    ArrayList<TextPoint> textPoints;
    ArrayList<String> listPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textPoints = new ArrayList<>();
        listPath = new ArrayList<>();
        btn = (Button) findViewById(R.id.btn);

        svg_kanji = (SVGCanvasView) findViewById(R.id.svg_kanji);

        svg_kanji.init("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"109\" height=\"109\" viewBox=\"0 0 109 109\"><g id=\"kvg:StrokePaths_05eb5\" style=\"fill:none;stroke:#000000;stroke-width:3;stroke-linecap:round;stroke-linejoin:round;\"><g id=\"kvg:05eb5\" kvg:element=\"庵\"><g id=\"kvg:05eb5-g1\" kvg:element=\"广\" kvg:position=\"tare\" kvg:radical=\"general\"><path id=\"kvg:05eb5-s1\" kvg:type=\"㇑a\" d=\"M54.06,11.63c1.15,1.15,1.26,2.49,1.26,3.9c0,2.59-0.07,5.59-0.07,7.96\"/><g id=\"kvg:05eb5-g2\" kvg:element=\"厂\"><path id=\"kvg:05eb5-s2\" kvg:type=\"㇐\" d=\"M23.63,27.23c3,0.65,6.64,0.65,9.61,0.27c15.64-2,30.89-4.12,46.26-5.52c2.86-0.26,5.56-0.26,8.38,0.35\"/><path id=\"kvg:05eb5-s3\" kvg:type=\"㇒\" d=\"M27.49,28.25c1.07,1.07,1.05,2.74,0.9,4.96c-1.26,18.54-2.76,42.16-17.33,60\"/></g></g><g id=\"kvg:05eb5-g3\" kvg:element=\"奄\"><g id=\"kvg:05eb5-g4\" kvg:element=\"大\" kvg:position=\"top\"><path id=\"kvg:05eb5-s4\" kvg:type=\"㇐\" d=\"M34.94,41.82c2.69,0.68,4.96,0.36,6.93,0.12c11.29-1.39,24.56-3.5,34.88-4.31c2.37-0.18,4.83-0.46,7.17,0.07\"/><path id=\"kvg:05eb5-s5\" kvg:type=\"㇒\" d=\"M56.91,30.25c0.21,1.38-0.2,2.66-0.67,3.71C49.88,48,42.38,57.62,28.14,68.35\"/><path id=\"kvg:05eb5-s6\" kvg:type=\"㇏\" d=\"M58.38,39.96c5.84,4.02,18.22,13.27,26.11,18c2.25,1.35,4.6,2.67,7.12,3.46\"/></g><g id=\"kvg:05eb5-g5\" kvg:position=\"bottom\"><g id=\"kvg:05eb5-g6\" kvg:element=\"日\"><path id=\"kvg:05eb5-s7\" kvg:type=\"㇑\" d=\"M38.83,61.57c0.95,1.03,0.92,2.52,1.07,3.82c0.34,2.93,1.12,9.26,1.71,13.85c0.37,2.89,0.66,5.08,0.71,5.31\"/><path id=\"kvg:05eb5-s8\" kvg:type=\"㇕a\" d=\"M40.07,61.94c2.84-0.31,22.86-3.01,31.05-3.65c3.37-0.27,5.26,0.46,4.73,4.09c-0.46,3.12-1.35,8.46-2.31,14.12c-0.26,1.53-0.52,3.09-0.78,4.63\"/><path id=\"kvg:05eb5-s9\" kvg:type=\"㇐a\" d=\"M41.88,72.03c4.14-0.33,27.06-3.08,31.73-3.46\"/><path id=\"kvg:05eb5-s10\" kvg:type=\"㇐a\" d=\"M43.6,82.69c6.37-0.69,23.55-2.95,28.56-3.35\"/></g><path id=\"kvg:05eb5-s11\" kvg:type=\"㇟\" d=\"M55.32,50.37c0.97,0.97,1.14,2.09,1.14,3.57c0,4.06-0.09,28.31-0.09,32.81c0,9.12,1.12,10.25,17.9,10.03c12.67-0.17,14.44-1.64,14.44-8.98\"/></g></g></g></g><g id=\"kvg:StrokeNumbers_05eb5\" style=\"font-size:8;fill:#808080\"><text transform=\"matrix(1 0 0 1 45.25 11.50)\">1</text><text transform=\"matrix(1 0 0 1 25.50 24.50)\">2</text><text transform=\"matrix(1 0 0 1 19.50 36.50)\">3</text><text transform=\"matrix(1 0 0 1 33.50 38.40)\">4</text><text transform=\"matrix(1 0 0 1 48.50 33.50)\">5</text><text transform=\"matrix(1 0 0 1 72.50 47.50)\">6</text><text transform=\"matrix(1 0 0 1 33.25 73.18)\">7</text><text transform=\"matrix(1 0 0 1 46.25 59.10)\">8</text><text transform=\"matrix(1 0 0 1 45.25 69.03)\">9</text><text transform=\"matrix(1 0 0 1 45.25 80.03)\">10</text><text transform=\"matrix(1 0 0 1 58.50 53.50)\">11</text></g></svg>" +
                "");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                svg_kanji.rePaint();
            }
        });

        Log.d("list", String.valueOf(svg_kanji.getListPath().get(0)));
    }
}
