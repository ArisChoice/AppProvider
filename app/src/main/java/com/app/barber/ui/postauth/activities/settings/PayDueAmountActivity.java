package com.app.barber.ui.postauth.activities.settings;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.request.BookPaymentRequestModel;
import com.app.barber.models.request.PayAmountRequest;
import com.app.barber.models.response.BaseResponse;
import com.app.barber.models.response.CardListResponse;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.postauth.activities.settings.setting_adapters.CardListAdapter;
import com.app.barber.ui.postauth.activities.settings.settingmvp.SettingMVPView;
import com.app.barber.ui.postauth.activities.settings.settingmvp.SettingPresenter;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;
import com.braintreepayments.cardform.view.CardForm;
import com.google.gson.Gson;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class PayDueAmountActivity extends BaseActivity implements SettingMVPView {
    @BindView(R.id.back_toolbar)
    ImageView backToolbar;
    @BindView(R.id.txt_title_toolbar)
    CustomTextView txtTitleToolbar;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.txt_btn)
    CustomTextView txtBtn;
    @BindView(R.id.option_type)
    CustomTextView optionType;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.saved_cards_recyclar)
    RecyclerView savedCardsRecyclar;
    @BindView(R.id.card_form)
    CardForm cardForm;
    @BindView(R.id.pay_btn)
    CustomTextView payBtn;
    private SettingPresenter presenter;
    private CardListAdapter cardsAdapter;
    private String dueAmount;

    /**
     * @return layout resource id
     */
    @Override
    public int getLayoutId() {
        return R.layout.layout_pay_due_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BarberApplication) getApplication()).getMyComponent(PayDueAmountActivity.this).inject(this);
        presenter = new SettingPresenter(PayDueAmountActivity.this);
        presenter.attachView(this);
        initRecyclar();
        getSavedCards();
        initCardForm();
        getIntentData();
        txtTitleToolbar.setText(R.string.str_pay);
    }

    private void getIntentData() {
        dueAmount = getIntent().getStringExtra(GlobalValues.KEYS.AMOUNT);
    }

    private void getSavedCards() {
        presenter.getSavedCard(NetworkConstatnts.RequestCode.API_GET_SAVED_CARDS, null, true);
    }

    private void initCardForm() {
        CardForm cardForm = (CardForm) findViewById(R.id.card_form);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(false)
                .mobileNumberRequired(false)
                .mobileNumberExplanation("SMS is required on this number")
                .actionLabel("Purchase")
                .setup(PayDueAmountActivity.this);
    }

    private void initRecyclar() {
        cardsAdapter = new CardListAdapter(PayDueAmountActivity.this, new ArrayList<CardListResponse.CardsBean.DataBean>(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int type, Object o) {
                switch (type) {
                    case GlobalValues.ClickOperations.DETAILS:
                        break;
                    case GlobalValues.ClickOperations.REMOVE:
                        CardListResponse.CardsBean.DataBean positiondata = (CardListResponse.CardsBean.DataBean) o;
                        Log.d(" onItemClick ", " remove " + positiondata.getLast4());
                        presenter.removeCard(NetworkConstatnts.RequestCode.API_REMOVE_SAVED_CARD, positiondata.getId(), true);
                        cardsAdapter.remove(position);
                        break;
                }
            }
        });
        LinearLayoutManager lManager = new LinearLayoutManager(PayDueAmountActivity.this, LinearLayoutManager.VERTICAL, false);
        savedCardsRecyclar.setLayoutManager(lManager);
        savedCardsRecyclar.setAdapter(cardsAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @OnClick({R.id.back_toolbar, R.id.pay_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.pay_btn:
                if (cardsAdapter.isSelected()) {
                    PayAmountRequest pRequest = new PayAmountRequest();
                    pRequest.setCardId(cardsAdapter.getselected().getId());
                    pRequest.setAmount(dueAmount);
                    presenter.pauDueAmount(NetworkConstatnts.RequestCode.API_PAY_DUE_AMOUNT, pRequest, true);
                } else {
                    if (cardForm.isValid()) {
                        generatePaymentToken();
                    } else
                        new CommonUtils().displayMessage(PayDueAmountActivity.this, getString(R.string.errr_str_required_details));
                }
                break;
        }
    }

    private void generatePaymentToken() {
        showProgres();
        Card card = new Card(cardForm.getCardNumber(),
                Integer.parseInt(cardForm.getExpirationMonth()),
                Integer.parseInt(cardForm.getExpirationYear()), cardForm.getCvv());
//        Remember to validate the card object before you use it to save time.
        if (card.validateCard()) {
            Stripe stripe = new Stripe(PayDueAmountActivity.this, NetworkConstatnts.STRIPE_TEST_KEY);
            stripe.createToken(
                    card,
                    new TokenCallback() {
                        public void onSuccess(Token token) {
                            hidePreogress();
                            // Send token to your server
                            Log.e("onSuccess", " Token: " + token.getId());
                            Log.e("onSuccess", " Token: " + token.getId());
                            BookPaymentRequestModel bRequest = new BookPaymentRequestModel();
                            bRequest.setStripeToken(token.getId());
                            presenter.saveCardDetail(NetworkConstatnts.RequestCode.API_SAVE_CARD, bRequest, false);

                        }

                        public void onError(Exception error) {
                            // Show localized error message
                            new CommonUtils().displayMessage(PayDueAmountActivity.this, error.getLocalizedMessage());
                        }
                    }
            );
        } else  // Do not continue token creation.
            new CommonUtils().displayMessage(PayDueAmountActivity.this, getString(R.string.srr_invalid_card));
    }

    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_GET_SAVED_CARDS:
//                new CommonUtils().displayMessage(PaymentActivity.this, ((PaymentResponseModel) o).getMessage());
                CardListResponse responseData = (CardListResponse) o;
                if (responseData != null && responseData.getCards() != null && responseData.getCards().getData() != null && responseData.getCards().getData().size() > 0) {
                    cardsAdapter.updateAll(responseData.getCards().getData());
                    Log.d("onSuccess", " saved cards " + responseData.getCards().getData().size());
                    savedCardsRecyclar.setVisibility(View.VISIBLE);
                } else {
                    savedCardsRecyclar.setVisibility(View.GONE);
                }
                break;
            case NetworkConstatnts.RequestCode.API_SAVE_CARD:
                BaseResponse baseRsponse = (BaseResponse) o;
                Log.e(" :: ", " " + baseRsponse.getCardId());
                if (baseRsponse.getCardId() != null && !baseRsponse.getCardId().equals("")) {
                    PayAmountRequest pRequest = new PayAmountRequest();
                    pRequest.setCardId(baseRsponse.getCardId());
                    pRequest.setAmount(dueAmount);
                    presenter.pauDueAmount(NetworkConstatnts.RequestCode.API_PAY_DUE_AMOUNT, pRequest, true);
                }
                break;
            case NetworkConstatnts.RequestCode.API_PAY_DUE_AMOUNT:
                new CommonUtils().ShowToast(((BaseResponse) o).getMessage());
                onBackPressed();
                break;
        }
    }

    @Override
    public void onfaliurResponse(int serviceMode, Object o) {
        new CommonUtils().ShowToast(((BaseResponse) o).getMessage());
    }

    @Override
    public void showProgres() {

    }

    @Override
    public void hidePreogress() {

    }

    @Override
    public void onSuccess(Object o, int type) {

    }

    @Override
    public void onError(String localizedMessage) {

    }

    @Override
    public void onException(Exception e) {

    }
}
