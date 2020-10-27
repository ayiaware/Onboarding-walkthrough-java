package commitware.ayia.onboardingwalkthrough;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import commitware.ayia.onboardingwalkthrough.adapter.SliderAdapter;
import commitware.ayia.onboardingwalkthrough.interfaces.OnSliderInteractionListener;
import commitware.ayia.onboardingwalkthrough.model.Slide;

public class OnBoardingActivity extends AppCompatActivity implements OnSliderInteractionListener {

    private ViewPager sViewPager;

    private LinearLayout dotsLayout;

    SliderAdapter sliderAdapter;

    public int slideCount;

    private Button btnNext;

    private Button btnPrevious;

    public String[] button ={
            "setup",
            "user",
            "sign up"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);


        sViewPager = findViewById(R.id.sViewPager);
        dotsLayout =  findViewById(R.id.layoutDots);
        btnPrevious =  findViewById(R.id.btnCancel);
        btnNext =  findViewById(R.id.btnNext);

        List<Slide> slideList = new ArrayList<>();

        slideList.add(new Slide( "Setup Device",
                getResources().getString(R.string.dummy_text)
                ,R.drawable.mobile, button[0]));

        slideList.add(new Slide("Create new User",
                getResources().getString(R.string.dummy_text)
                ,R.drawable.mobile, button[1]));

        slideList.add(new Slide("Sign up",
                getResources().getString(R.string.dummy_text)
                ,R.drawable.mobile, button[2]));

        sliderAdapter = new SliderAdapter(this, slideList);

        slideCount = sliderAdapter.getCount();

        // set adapter in ViewPager
        sViewPager.setAdapter(sliderAdapter);

        // set PageChangeListener
        sViewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        addBottomDots(0);

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnPreviousClick(v);

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnNextClick(v);

            }
        });


    }

    private int getItem(int i) {
        return sViewPager.getCurrentItem() + i;
    }

    //btnNextClick
    public  void btnNextClick(View v) {
        // checking for last page
        // if last page home screen will be launched
        int current = getItem(1);

        if (current < sliderAdapter.getCount()) {
            // move to next screen
            sViewPager.setCurrentItem(current);
        }
        else if(current==sliderAdapter.getCount())
        {
            Intent intent = new Intent(OnBoardingActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        }
        else {
            onNavigateUp();
        }
    }

    //btnPreviousClick
    public  void btnPreviousClick(View v) {

        // checking for first page

        // if first page finish activity

        int current = getItem(1);

        if (current < sliderAdapter.getCount()) {
            // move to previous screen
            sViewPager.setCurrentItem(current-1);
        }
        else if(current==0)
        {
            finish();
        }

    }


    // viewPagerPage ChangeListener according to Dots-Points
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

            addBottomDots(position);

            if (position == slideCount-1)
            {
                btnNext.setText(getResources().getString(R.string.button_text_finish));
            }
            else {
                btnNext.setText(getResources().getString(R.string.button_text_next));
            }

            if (position > 0)
            {
                btnPrevious.setText(getResources().getString(R.string.button_text_previous));
            }
            else if (position == 0){
                btnPrevious.setText(getResources().getString(R.string.button_cancel));
            }


        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

    };



    // set of Dots points
    private void addBottomDots(int currentPage) {
        TextView[] dots = new TextView[slideCount];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorAccent));  // dot_inactive
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.colorAccent)); // dot_active
    }

    @Override
    public void buttonClick() {
        Toast.makeText(this,"button clicked "+ button[ getItem(1)-1],Toast.LENGTH_SHORT).show();
    }


}
