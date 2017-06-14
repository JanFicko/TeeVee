package xyz.janficko.teevee.ui.onboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import xyz.janficko.teevee.R;
import xyz.janficko.teevee.TeeVee;
import xyz.janficko.teevee.commons.Constants;
import xyz.janficko.teevee.util.SharedPreferenceUtil;

public class OnboardingFragment extends android.support.v17.leanback.app.OnboardingFragment {

    private static final String TAG = OnboardingFragment.class.getSimpleName();
    private SharedPreferenceUtil mSharedPreferenceUtil = TeeVee.getInstance().getSharedPreferenceUtil();
    private Animator mContentAnimator;
    private ImageView mContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setLogoResourceId(R.mipmap.teevee_banner);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void onFinishFragment() {
        super.onFinishFragment();
        mSharedPreferenceUtil.saveFirstStart(false);

        getActivity().finish();
    }

    @Override
    protected int getPageCount() {
        return Constants.ONBOARDING_PAGE_TITLE.length;
    }

    @Override
    protected String getPageTitle(int pageIndex) {
        return getString(Constants.ONBOARDING_PAGE_TITLE[pageIndex]);
    }

    @Override
    protected String getPageDescription(int pageIndex) {
        return getString(Constants.ONBOARDING_PAGE_DESCRIPTION[pageIndex]);
    }

    @Nullable
    @Override
    protected View onCreateBackgroundView(LayoutInflater inflater, ViewGroup container) {
        View bgView = new View(getActivity());
        bgView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.onboarding_background));
        return bgView;
    }

    @Nullable
    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        mContentView = new ImageView(getActivity());
        mContentView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mContentView.setPadding(0, 32, 0, 32);
        return mContentView;
    }

    @Nullable
    @Override
    protected View onCreateForegroundView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    protected void onPageChanged(final int newPage, int previousPage) {
        if (mContentAnimator != null) {
            mContentAnimator.end();
        }
        ArrayList<Animator> animators = new ArrayList<>();
        Animator fadeOut = createFadeOutAnimator(mContentView);

        fadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mContentView.setImageDrawable(ContextCompat.getDrawable(getActivity(), Constants.ONBOARDING_PAGE_IMAGES[newPage]));
                ((AnimationDrawable) mContentView.getDrawable()).start();
            }
        });
        animators.add(fadeOut);
        animators.add(createFadeInAnimator(mContentView));
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animators);
        set.start();
        mContentAnimator = set;
    }
    @Override
    protected Animator onCreateEnterAnimation() {
        mContentView.setImageDrawable(ContextCompat.getDrawable(getActivity(), Constants.ONBOARDING_PAGE_IMAGES[0]));
        ((AnimationDrawable) mContentView.getDrawable()).start();
        mContentAnimator = createFadeInAnimator(mContentView);
        return mContentAnimator;
    }
    private Animator createFadeInAnimator(View view) {
        return ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f).setDuration(Constants.ONBOARDING_ANIMATION_DURATION);
    }

    private Animator createFadeOutAnimator(View view) {
        return ObjectAnimator.ofFloat(view, View.ALPHA, 1.0f, 0.0f).setDuration(Constants.ONBOARDING_ANIMATION_DURATION);
    }
}
