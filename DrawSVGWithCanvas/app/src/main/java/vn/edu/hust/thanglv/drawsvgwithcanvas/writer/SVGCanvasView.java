package vn.edu.hust.thanglv.drawsvgwithcanvas.writer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.larvalabs.svgandroid.SVGParser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import vn.edu.hust.thanglv.drawsvgwithcanvas.R;


public class SVGCanvasView extends View {
    private ArrayList<String> colorPath;
    Context context;
    private int currentHeight;
    private int currentWidth;
    private float distance;
    private boolean isRunning;
    private boolean isScalePath;
    private ArrayList<String> listPath;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mPaint;
    private Path mPath;
    private PathMeasure measure;
    int numStroke;
    private ArrayList<Path> pathArrayList;
    private Paint pencilPaint;
    private float[] pos;
    private ArrayList<Integer> ramdomNumber;
    private Matrix scaleMatrix;
    private int scaleNumber;
    private float scaleUnit;
    private float speed;
    long startTime;
    private boolean stop;
    private String svgData;
    private float[] tan;
    private Paint textPaint;
    private ArrayList<TextPoint> textPointArrayList;

    public SVGCanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        this.isRunning = true;
        this.numStroke = 0;
        this.stop = false;
        this.context = c;
        this.startTime = System.currentTimeMillis();
        postInvalidate();
        this.mPath = new Path();
        this.pathArrayList = new ArrayList();
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(Color.parseColor("#3367D6"));
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeJoin(Join.ROUND);
        this.mPaint.setStrokeWidth(8.0f);
        this.measure = new PathMeasure();
        this.distance = 0.0f;
        this.speed = 0.0f;
        this.pos = new float[2];
        this.tan = new float[2];
        this.scaleMatrix = new Matrix();
        this.pencilPaint = new Paint();
        this.pencilPaint.setAntiAlias(true);
        this.pencilPaint.setDither(true);
        this.pencilPaint.setColor(Color.parseColor("#8b7355"));
        this.pencilPaint.setStyle(Style.STROKE);
        this.pencilPaint.setStrokeJoin(Join.ROUND);
        this.pencilPaint.setStrokeWidth(10);
        this.textPaint = new TextPaint();
        this.textPaint.setAntiAlias(true);
        this.textPaint.setDither(true);
        this.textPaint.setColor(Color.parseColor("#D73F3F"));
        this.textPaint.setStyle(Style.FILL);
        this.textPaint.setStrokeJoin(Join.ROUND);
        this.textPaint.setStrokeWidth(1.0f);
        this.textPaint.setTextSize(24.0f);
        this.listPath = new ArrayList();
        this.pathArrayList = new ArrayList();
        this.textPointArrayList = new ArrayList();
        this.isScalePath = false;
        this.scaleUnit = -1.0f;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mBitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        this.mCanvas = new Canvas(this.mBitmap);
        this.currentWidth = w;
        this.currentHeight = h;
        this.scaleNumber = w ;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.isScalePath) {
            Iterator it = this.pathArrayList.iterator();
            while (it.hasNext()) {
                Path path = (Path) it.next();
                Matrix scaleMatrix1 = new Matrix();
                scaleMatrix1.setScale(this.scaleUnit, this.scaleUnit);
                path.transform(scaleMatrix1);
            }
            it = this.textPointArrayList.iterator();
            while (it.hasNext()) {
                TextPoint textPoint = (TextPoint) it.next();
                textPoint.setX(textPoint.getX() * this.scaleUnit);
                textPoint.setY(textPoint.getY() * this.scaleUnit);
            }
            this.textPaint.setTextSize(this.scaleUnit * 6.0f);
            this.textPaint.setTypeface(Typeface.SANS_SERIF);
            this.mPaint.setStrokeWidth(this.scaleUnit * 2.0f);
            this.isScalePath = true;
        }
        canvas.drawBitmap(this.mBitmap, 0.0f, 0.0f, new Paint(4));
        if (this.isRunning && !this.stop) {
            if (this.numStroke >= this.pathArrayList.size()) {
                this.mPath.reset();
                this.numStroke = 0;
                this.stop = true;
            }
            this.measure.setPath((Path) this.pathArrayList.get(this.numStroke), false);
            int i;
            if (this.distance < this.measure.getLength()) {
                this.measure.getPosTan(this.distance, this.pos, this.tan);
                this.speed = this.measure.getLength() / 10.0f;
                if (this.distance == 0.0f) {
                    canvas.drawText(Integer.toString(this.numStroke + 1), ((TextPoint) this.textPointArrayList.get(this.numStroke)).getX(), ((TextPoint) this.textPointArrayList.get(this.numStroke)).getY(), this.textPaint);
                    this.mCanvas.drawText(Integer.toString(this.numStroke + 1), ((TextPoint) this.textPointArrayList.get(this.numStroke)).getX(), ((TextPoint) this.textPointArrayList.get(this.numStroke)).getY(), this.textPaint);
                    this.mPath.moveTo(this.pos[0], this.pos[1]);
                }
                this.distance += 8.0f;
                this.mPath.lineTo(this.pos[0], this.pos[1]);
                canvas.drawPath(this.mPath, this.mPaint);
                for (i = 0; i <= this.numStroke; i++) {

                }
            } else {
                for (i = 0; i <= this.numStroke; i++) {

                }

                    this.mCanvas.drawPath(this.mPath, this.mPaint);
                    this.distance = 0.0f;
                    this.numStroke++;
                    this.mPath.reset();
                    this.mPaint.setColor(Color.BLACK);
                Random r = new Random();
                this.mPaint.setColor(context.getResources().getColor(Integer.parseInt(this.colorPath.get(this.ramdomNumber.get(r.nextInt(this.ramdomNumber.size() - 0) + 0)))));
                this.textPaint.setColor(context.getResources().getColor(Integer.parseInt(this.colorPath.get(this.ramdomNumber.get(r.nextInt(this.ramdomNumber.size() - 0) + 0)))));
            }
            invalidate();
        }
    }

    public ArrayList<String> getListPath() {
        return this.listPath;
    }

    public void setListPath(ArrayList<String> listPath, ArrayList<TextPoint> textPoints) {
        this.isRunning = true;
        this.listPath = listPath;
        this.textPointArrayList = textPoints;
        SvgPathParser pathParser = new SvgPathParser();
        for (int i = 0; i < listPath.size(); i++) {
            try {
                Path path = pathParser.parsePath((String) listPath.get(i));
                Matrix scaleMatrix = new Matrix();
                scaleMatrix.setScale(10.0f, 10.0f);
                path.transform(scaleMatrix);
                this.pathArrayList.add(path);
                Log.d("patharray", this.pathArrayList.get(0).toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void nodeTravel(Node node) {
        if (node.hasChildNodes()) {
            NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                nodeTravel(nodeList.item(i));
            }
            return;
        }
        this.listPath.add(((Element) node).getAttribute("d"));
    }

    public void textTravel(Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            try {
                this.textPointArrayList.add(stringToTextPoint(((Element) nodeList.item(i)).getAttribute("transform")));
            } catch (Exception e) {
            }
        }
    }

    public TextPoint stringToTextPoint(String string) {
        String sub2 = string.replace("matrix(1 0 0 1 ", "").replace(")", "");
        Log.d("sub2", sub2);
        TextPoint point = new TextPoint(Float.parseFloat(sub2.substring(0, sub2.lastIndexOf(" "))), Float.parseFloat(sub2.substring(sub2.lastIndexOf(" ") + 1, sub2.length())));
        Log.d("textpoint", point.getX() + "");
        Log.d("textpoint", point.getY() + "");
        return   point;

    }

    public void init(String data) {
        this.isRunning = true;
        invalidate();
        InputStream stream = null;
        try {
            stream = new ByteArrayInputStream(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            NodeList list = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream).getDocumentElement().getChildNodes();
            Node node = list.item(0);
            textTravel(list.item(1));
            nodeTravel(node);
            SVGParser pathParser = new SVGParser();
            for (int i = 0; i < this.listPath.size(); i++) {
                this.pathArrayList.add(pathParser.parsePath((String) this.listPath.get(i)));
            }
        } catch (ParserConfigurationException e2) {
            e2.printStackTrace();
        } catch (SAXException e3) {
            e3.printStackTrace();
        } catch (IOException e4) {
            e4.printStackTrace();
        }
        resolveColor();
    }

    public void init(String data, int numberScale) {
        this.isRunning = true;
        invalidate();
        try {
            NodeList list = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                list = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8))).getDocumentElement().getChildNodes();
            }
            Node node = list.item(0);
            textTravel(list.item(1));
            nodeTravel(node);
            SVGParser pathParser = new SVGParser();
            for (int i = 0; i < this.listPath.size(); i++) {
                Path path = SVGParser.parsePath((String) this.listPath.get(i));
                Matrix scaleMatrix = new Matrix();
                scaleMatrix.setScale(4.0f, 4.0f);
                path.transform(scaleMatrix);
                this.pathArrayList.add(path);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        resolveColor();
    }

    public void resolveColor() {
        this.colorPath = new ArrayList();
        this.colorPath.add(R.color.color1 + "");
        this.colorPath.add(R.color.color2 + "");
        this.colorPath.add(R.color.color3 + "");
        this.colorPath.add(R.color.color4 + "");
        this.colorPath.add(R.color.color5 + "");
        this.colorPath.add(R.color.color6 + "");
        this.colorPath.add(R.color.color7 + "");
        this.colorPath.add(R.color.color8 + "");
        this.colorPath.add(R.color.color9 + "");
        this.colorPath.add(R.color.color10 + "");
        this.colorPath.add(R.color.color11 + "");
        this.colorPath.add(R.color.color12 + "");
        this.colorPath.add(R.color.color13 + "");
        this.colorPath.add(R.color.color14 + "");
        this.colorPath.add(R.color.color15 + "");
        this.colorPath.add(R.color.color16 + "");
        this.colorPath.add(R.color.color17 + "");
        this.colorPath.add(R.color.color18 + "");
        this.colorPath.add(R.color.color19 + "");
        this.colorPath.add(R.color.color20 + "");
        Random r = new Random();

        this.ramdomNumber = new ArrayList();
        for (int i = 0; i < this.pathArrayList.size(); i++) {
            if (i <= this.colorPath.size() - 1) {
                this.ramdomNumber.add(Integer.valueOf(i));
            } else {
                this.ramdomNumber.add(Integer.valueOf(i % this.colorPath.size()));
            }
        }

//        Log.d("color", this.ramdomNumber.toString());
        this.mPaint.setColor(context.getResources().getColor(Integer.parseInt(this.colorPath.get(this.ramdomNumber.get(r.nextInt(this.ramdomNumber.size() - 0) + 0)))));
        this.textPaint.setColor(context.getResources().getColor(Integer.parseInt(this.colorPath.get(this.ramdomNumber.get(r.nextInt(this.ramdomNumber.size() - 0) + 0)))));
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec;
        if (getLayoutParams().height == -2) {
            heightSpec = MeasureSpec.makeMeasureSpec(536870911, LinearLayoutManager.INVALID_OFFSET);
        } else {
            heightSpec = heightMeasureSpec;
        }
        super.onMeasure(widthMeasureSpec, heightSpec);
        float f = this.context.getResources().getDisplayMetrics().density;
        int i = getMeasuredWidth();
        setMeasuredDimension(i, getMeasuredHeight());
        if (this.scaleUnit < 0.0f) {
            this.scaleUnit = ((float) i) / 109.0f;
            this.scaleMatrix.setScale(this.scaleUnit, this.scaleUnit);
        }
    }

    public void rePaint() {
        this.mCanvas.drawColor(0, Mode.CLEAR);
        this.distance = 0.0f;
        this.numStroke = 0;
        this.mPath.reset();
        this.stop = false;
        invalidate();
    }
}
