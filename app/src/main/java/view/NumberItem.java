package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by hottest on 2016/3/21.
 */
public class NumberItem extends FrameLayout {
    private int number;
    private TextView mtv;

    public NumberItem(Context context) {
        super(context);
        init(0);
    }

    public NumberItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(0);
    }
    public NumberItem(Context context,int number){
        super(context);
        init(number);

    }

    public int getNumber() {
        return number;
    }

    public void init(int number){
        this.number=number;
        //绘制每一个小方块，
       mtv= new TextView(getContext());
        //绘制小方块的宽高,通过LayoutParam与直接通过mtv的方法设置的属性有哪些不同呢？
        LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.setMargins(5, 5, 5, 5);
        mtv.setGravity(Gravity.CENTER);
        setTextNum(number);
        addView(mtv,params);


    }
    public void setTextNum(int num){
        //当对其中的number做更改的时候，应该将其更改值进行保存
        number=num;
        //绘制方块中应该要显示的数字
        if(number==0){
            mtv.setText("");
        }else {
            mtv.setText(number + "");
        }
        //根据不同的数字，绘制不同的背景颜色,此时的背景颜色设置的有问题（没有设置alpha值）
        switch (number) {
            case 0:
                mtv.setBackgroundColor(0x00000000);
                break;
            case 2:
                mtv.setBackgroundColor(0xFFFFFFCC);
                break;
            case 4:
                mtv.setBackgroundColor(0xFFFFFF99);
                break;
            case 8:
                mtv.setBackgroundColor(0xFFCCCCFF);
                break;
            case 16:
                mtv.setBackgroundColor(0xFFFF9966);
                break;
            case 32:
                mtv.setBackgroundColor(0xFFFF6666);
                break;
            case 64:
                mtv.setBackgroundColor(0xFFCC99);
                break;
            case 128:
                mtv.setBackgroundColor(0xFFCCFF99);
                break;
            case 256:
                mtv.setBackgroundColor(0xFFCCCCCC);
                break;
            case 512:
                mtv.setBackgroundColor(0xFFCCFFCC);
                break;
            case 1024:
                mtv.setBackgroundColor(0xFF66FF66);
                break;
            case 2048:
                break;


        }

    }
}
