package com.versionpb.bodmascalculator;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.versionpb.bodmascalculator.databinding.ActivityMainBinding;

import java.util.Collections;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private final CalculatorState calculatorState = new CalculatorState();

    private int theme = 0;
    private String title;
    private boolean doubleBackToExitPressedOnce = false;
    private InterstitialAd interstitialAd;
    private RewardedAd rewardedAd;
    private AdView bannerAdView;

    private void log(String msg) {
        if (BuildConfig.DEBUG) {
            android.util.Log.d("BODMASCalculator", msg);
        }
    }

    private void applyInputResult(CalculatorState.InputResult result) {
        switch (result.uiUpdate) {
            case EDIT_ONLY:
                binding.edt1.setText(calculatorState.setLineEditView);
                break;
            case TEXT_ONLY:
                binding.infoTextView.setText(calculatorState.setLineTextView);
                break;
            case BOTH:
                binding.edt1.setText(calculatorState.setLineEditView);
                binding.infoTextView.setText(calculatorState.setLineTextView);
                break;
            case NONE:
            default:
                break;
        }
    }

    public void CalculatorInputLogic(View v, String input) {
        CalculatorState.InputResult result = calculatorState.onInput(input);
        applyInputResult(result);
    }

    public void AddText(View v, String input) {
        CalculatorInputLogic(v, input);
    }

    public void setTheme(View v, int c, int actionBarColor, int titleColor, int editTextColor, int nextColor) {
        Button[] buttons = {
                binding.buttonLBracket, binding.buttonRBracket, binding.buttonC, binding.buttondel,
                binding.button7, binding.button8, binding.button9, binding.buttondiv,
                binding.button4, binding.button5, binding.button6, binding.button1, binding.button2,
                binding.button3, binding.button10, binding.button0, binding.buttoneql, binding.buttonadd,
                binding.buttonof, binding.buttoninterad, binding.buttonvedioad, binding.buttonskin,
                binding.buttonmul, binding.buttonsub
        };
        int btnColor = ContextCompat.getColor(this, c);
        for (Button btn : buttons) {
            btn.setBackgroundColor(btnColor);
        }
        binding.buttonskin.setTextColor(ContextCompat.getColor(this, nextColor));
        binding.textViewLink.setBackgroundColor(ContextCompat.getColor(this, actionBarColor));

        binding.infoTextView.setBackgroundColor(ContextCompat.getColor(this, editTextColor));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            getSupportActionBar().setTitle(Html.fromHtml("<font color=" + getString(titleColor) + ">" + title + "</font>", Html.FROM_HTML_MODE_LEGACY));
        } else {
            getSupportActionBar().setTitle(Html.fromHtml("<font color=" + getString(titleColor) + ">" + title + "</font>"));
        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, actionBarColor)));
    }

    public void ChangeSkin(View v) {
        if (theme == 0) {
            setTheme(v, R.color.pink, R.color.red, R.string.black, R.color.lavenderblush, R.color.colorPrimaryDark);
            theme = 1;
        } else if (theme == 1) {
            setTheme(v, R.color.ButtonColor, R.color.colorPrimaryDark, R.string.white, R.color.TextViewBg,
                    R.color.pink);
            theme = 0;
        }
    }

    private String bannerAdUnitId() {
        return BuildConfig.DEBUG
                ? getString(R.string.ad_id_banner_test)
                : getString(R.string.ad_id_banner);
    }

    private String interstitialAdUnitId() {
        return BuildConfig.DEBUG
                ? getString(R.string.interstitial_ad_unit_id_test)
                : getString(R.string.interstitial_ad_unit_id);
    }

    private String rewardedAdUnitId() {
        return BuildConfig.DEBUG
                ? getString(R.string.rewarded_ad_unit_id_test)
                : getString(R.string.rewarded_ad_unit_id);
    }

    private void initializeAds() {
        if (BuildConfig.DEBUG) {
            MobileAds.setRequestConfiguration(
                    new RequestConfiguration.Builder()
                            .setTestDeviceIds(Collections.singletonList(AdRequest.DEVICE_ID_EMULATOR))
                            .build());
        }

        MobileAds.initialize(this, status -> {
            loadBannerAd();
            loadRewardedAd();
            loadInterstitialAd();
        });
    }

    private void loadBannerAd() {
        if (bannerAdView != null) {
            return;
        }

        bannerAdView = new AdView(this);
        bannerAdView.setAdSize(AdSize.BANNER);
        bannerAdView.setAdUnitId(bannerAdUnitId());

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        binding.bannerAdContainer.addView(bannerAdView, params);

        bannerAdView.loadAd(new AdRequest.Builder().build());
    }

    private void loadInterstitialAd() {
        InterstitialAd.load(this, interstitialAdUnitId(), new AdRequest.Builder().build(),
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd ad) {
                        interstitialAd = ad;
                        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                interstitialAd = null;
                                loadInterstitialAd();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                interstitialAd = null;
                                loadInterstitialAd();
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        interstitialAd = null;
                        log("Interstitial ad failed to load: " + loadAdError.getMessage());
                    }
                });
    }

    private void loadRewardedAd() {
        RewardedAd.load(this, rewardedAdUnitId(), new AdRequest.Builder().build(),
                new RewardedAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        rewardedAd = ad;
                        rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                rewardedAd = null;
                                loadRewardedAd();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                rewardedAd = null;
                                loadRewardedAd();
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        rewardedAd = null;
                        log("Rewarded ad failed to load: " + loadAdError.getMessage());
                    }
                });
    }

    private void showInterstitialAd() {
        if (interstitialAd != null) {
            interstitialAd.show(this);
        } else {
            Toast.makeText(this, R.string.ad_not_ready_interstitial, Toast.LENGTH_SHORT).show();
            loadInterstitialAd();
        }
    }

    private void showRewardedAd() {
        if (rewardedAd != null) {
            rewardedAd.show(this, rewardItem ->
                    log("Rewarded ad completed: " + rewardItem.getAmount() + " " + rewardItem.getType()));
        } else {
            Toast.makeText(this, R.string.ad_not_ready_rewarded, Toast.LENGTH_SHORT).show();
            loadRewardedAd();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        title = "\t" + getString(R.string.app_name) + "\t\t\t\t\t\t\t" + getString(R.string.Version);
        setTitle(title);

        String linkText = "<a href='https://www.versionpb.co.in'>www.versionpb.co.in</a>";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            binding.textViewLink.setText(Html.fromHtml(linkText, Html.FROM_HTML_MODE_LEGACY));
        } else {
            binding.textViewLink.setText(Html.fromHtml(linkText));
        }
        binding.textViewLink.setMovementMethod(LinkMovementMethod.getInstance());

        binding.edt1.setText(calculatorState.setLineEditView);
        binding.infoTextView.setText(calculatorState.setLineTextView);

        View.OnClickListener calcClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = "";
                int id = v.getId();
                if (id == R.id.button1) input = "1";
                else if (id == R.id.button2) input = "2";
                else if (id == R.id.button3) input = "3";
                else if (id == R.id.button4) input = "4";
                else if (id == R.id.button5) input = "5";
                else if (id == R.id.button6) input = "6";
                else if (id == R.id.button7) input = "7";
                else if (id == R.id.button8) input = "8";
                else if (id == R.id.button9) input = "9";
                else if (id == R.id.button0) input = "0";
                else if (id == R.id.buttonadd) input = "+";
                else if (id == R.id.buttonsub) input = "-";
                else if (id == R.id.buttonmul) input = "X";
                else if (id == R.id.buttondiv) input = "\u00F7";
                else if (id == R.id.buttoneql) input = "=";
                else if (id == R.id.buttonC) input = "C";
                else if (id == R.id.button10) input = ".";
                else if (id == R.id.buttonLBracket) input = "(";
                else if (id == R.id.buttonRBracket) input = ")";
                else if (id == R.id.buttonof) input = "^";
                else if (id == R.id.buttondel) input = "D";

                if (!input.isEmpty()) {
                    AddText(v, input);
                }
            }
        };

        View[] calcButtons = {
                binding.button0, binding.button1, binding.button2, binding.button3, binding.button4,
                binding.button5, binding.button6, binding.button7, binding.button8, binding.button9,
                binding.buttonadd, binding.buttonsub, binding.buttonmul, binding.buttondiv,
                binding.buttoneql, binding.buttonC, binding.button10, binding.buttonLBracket,
                binding.buttonRBracket, binding.buttonof, binding.buttondel
        };
        for (View button : calcButtons) {
            button.setOnClickListener(calcClickListener);
        }

        binding.buttonskin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeSkin(v);
            }
        });

        binding.buttoninterad.setOnClickListener(v -> showRewardedAd());
        binding.buttonvedioad.setOnClickListener(v -> showInterstitialAd());

        initializeAds();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                    return;
                }

                doubleBackToExitPressedOnce = true;
                Toast.makeText(MainActivity.this, getString(R.string.exit_msg), Toast.LENGTH_SHORT).show();

                new Handler(Looper.getMainLooper()).postDelayed(
                        () -> doubleBackToExitPressedOnce = false, 2000);
            }
        });
    }

    @Override
    protected void onPause() {
        if (bannerAdView != null) {
            bannerAdView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bannerAdView != null) {
            bannerAdView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (bannerAdView != null) {
            bannerAdView.destroy();
        }
        super.onDestroy();
    }
}
