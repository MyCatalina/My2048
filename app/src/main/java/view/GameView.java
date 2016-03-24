package view;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hottest on 2016/3/21.
 */

public class GameView extends GridLayout {
    //此集合用来盛放所有的显示为0的textView
    private List<Point> blanklist;
    public int mrowNum = 4;
    private int mcolumnNum = 4;
    private NumberItem[][] numberItemsMatrix;
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    private List<Integer> caculorList;

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //用来初始化页面,在GridLayout中随机的显示两个NumberItem,其余的仍然为0
    public void init() {
        blanklist = new ArrayList<Point>();
        caculorList=new ArrayList<Integer>();
        numberItemsMatrix = new NumberItem[mrowNum][mcolumnNum];
        DisplayMetrics metrics = getDisplayMetrics();
        int widthPixels = metrics.widthPixels;
        //一开始的时候需要设置GridLayout的行和列
        setRowCount(mrowNum);
        setColumnCount(mcolumnNum);
        for (int i = 0; i < mrowNum; i++) {
            for (int j = 0; j < mcolumnNum; j++) {
                //得到当前layout的宽高，将位于其中的控件设置应有的size
                NumberItem mnumItem = new NumberItem(getContext(), 0);
                numberItemsMatrix[i][j] = mnumItem;
                int number = mnumItem.getNumber();
                int len = widthPixels / mcolumnNum;
                //将此mnumItem视图添加至GridLayout中，并将其添加至集合中
                addView(mnumItem, len, len);
                Point point = new Point();
                point.x = i;
                point.y = j;
                blanklist.add(point);

            }
        }
        //随机的在GridLayout上显示两个方块2或4
        addRandomNum();
        addRandomNum();
    }

    //即将原来的方块中的任意两个方块上的数字改为2或者4
    public void addRandomNum() {
        updateList();
        //随机生成2或者4，生成2的几率更大一些
        int setText = Math.random() > 0.3 ? 2 : 4;
        int size = blanklist.size();
        int location = (int) Math.floor(Math.random() * size);
        Point point = blanklist.get(location);
        numberItemsMatrix[point.x][point.y].setTextNum(setText);

    }

    //此方法是为了使第一次出现方块之后，以后随机出现的方块不会覆盖在前一个方块上
    public void updateList() {
        blanklist.clear();
        for (int i = 0; i < mrowNum; i++) {
            for (int j = 0; j < mcolumnNum; j++) {
                NumberItem item = numberItemsMatrix[i][j];
                if (0 == item.getNumber()) {
                    blanklist.add(new Point(i, j));
                }
            }
        }
    }

    private DisplayMetrics getDisplayMetrics() {
        //通过WindowManager获得关于应用屏幕的一些信息
        WindowManager wm = (WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE);
        Display defaultDisplay = wm.getDefaultDisplay();
        //获得当前窗口的一些信息比如分辨率，显示大小,存放在metrics中
        DisplayMetrics metrics = new DisplayMetrics();
        defaultDisplay.getMetrics(metrics);
        return metrics;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                startY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                endX = (int) event.getX();
                endY = (int) event.getY();
                juggeDirection(startX, startY, endX, endY);
                break;


        }
        return true;
    }

    //通过方块的移动来判断其是向哪个方向开始移动的
    public void juggeDirection(float startX, float startY, float endX, float endY) {
        boolean b = Math.abs(startX - endX) > Math.abs(startY - endY) ? true : false;
        if (b) {
            if (startX > endX) {
                slideLeft();
                Log.i("juggeDirection", "左移");
            } else {
                slideRight();
                Log.i("juggeDirection", "右移");
            }
        } else if (startY > endY) {
            slideUp();
            Log.i("juggeDirection", "上移");

        } else {
            slideDown();
            Log.i("juggeDirection", "下移");
        }
    }

    public void slideLeft() {


    }

    public void slideRight() {

    }

    public void slideUp() {

    }

    public void slideDown() {
        int prenumber = -1;

        for (int i = 0; i < mrowNum; i++) {
            //在每一列中，合并能够合并的，并将合并后的结果添加至caculorList中，并将其结果保存至numberItemMatrix[][]中
            for (int j = mcolumnNum - 1; j >= 0; j--) {
                int number = numberItemsMatrix[j][i].getNumber();
                //将其添加至caculorList中
                if (number != 0) {
                    if (number != prenumber && prenumber != -1) {
                        caculorList.add(prenumber);
                    }
                    if (number == prenumber && prenumber != -1) {
                        //当有数据的时候，其相等时，将其合并后的值添加至caculorList中，并将prenumber设置为-1，退出本次循坏
                        caculorList.add(number * 2);
                        prenumber=-1;
                        continue;
                    }
                    prenumber = number;
                }

            }
            if(prenumber!=-1&&prenumber!=0){
                caculorList.add(prenumber);
            }
            //将caculorList中的数据添加至numberItemMatrix[][]中，
            for(int p=mcolumnNum-1;p>=mcolumnNum-caculorList.size();p--){
                numberItemsMatrix[p][i].setTextNum(caculorList.get(mcolumnNum - p-1));
            }
            //将此列中剩余的部分用0填充
            for(int p=mcolumnNum-caculorList.size()-1;p>=0;p--){
                numberItemsMatrix[p][i].setTextNum(0);

            }
            caculorList.clear();
            prenumber=-1;

        }


    }

}
