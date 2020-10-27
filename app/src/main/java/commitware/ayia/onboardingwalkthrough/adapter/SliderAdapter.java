package commitware.ayia.onboardingwalkthrough.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import commitware.ayia.onboardingwalkthrough.R;
import commitware.ayia.onboardingwalkthrough.interfaces.OnSliderInteractionListener;
import commitware.ayia.onboardingwalkthrough.model.Slide;

public class SliderAdapter extends PagerAdapter {
    private OnSliderInteractionListener mListener;


    private Context context;

    private List<Slide> slideItems;



    public SliderAdapter(Context context, List<Slide> slideItems ) {

        this.context = context;
        this.slideItems = slideItems;

    }
    @Override
    public int getCount() {

        return slideItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.slide_layout, container,false);
        container.addView(view);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView slideHeading = view.findViewById(R.id.tvHeading);
        TextView slideDescription = view.findViewById(R.id.tvSubHeading);
        Button btnAction = view.findViewById(R.id.btnAction);

        btnAction.setText(slideItems.get(position).getButtonText());

        onAttach(context);


        imageView.setImageResource(slideItems.get(position).getImage());

        slideHeading.setText(slideItems.get(position).getHeading());
        slideDescription.setText(slideItems.get(position).getSubHeading());

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onButtonClicked();
            }
        });

        return view;

    }



    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {

        container.removeView((ConstraintLayout)object);
    }

    private void onAttach(Context context)
    {
        if (context instanceof OnSliderInteractionListener) {
            mListener = (OnSliderInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private void onButtonClicked() {
        if (mListener != null) {
            mListener.buttonClick();
        }
    }

}


