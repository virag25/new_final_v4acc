package amigoinn.example.v4sales;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Virag kuvadia on 24-04-2016.
 */
public class FontAwsam1 extends TextView {
    private static Typeface sFontello;
    Context myContext;

    public FontAwsam1(Context context) {
        super(context);
        myContext = context;
        if (isInEditMode()) return; //Won't work in Eclipse graphical layout
        setTypeface();
    }

    public FontAwsam1(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) return;
        setTypeface();
    }

    public FontAwsam1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode()) return;
        setTypeface();
    }

    private void setTypeface() {

        if (sFontello == null) {
            sFontello = Typeface.createFromAsset(getContext().getAssets(), "fonts/fontello1.ttf");
        }
        setTypeface(sFontello);
    }
}