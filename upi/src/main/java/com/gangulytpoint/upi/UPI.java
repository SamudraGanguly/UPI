package com.gangulytpoint.upi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;

public class UPI {
    public static final byte GOOGLE_PAY = 0;
    public static final byte PHONE_PE = 1;
    public static final byte PAYTM = 2;
    public static final byte AMAZON_PAY = 3;
    public static final byte WHATSAPP = 4;
    public static final byte ALL_APPS = 99;
    private static final String[] APPS = {"com.google.android.apps.nbu.paisa.user",
            "com.phonepe.app",
            "net.one97.paytm",
            "in.amazon.mShop.android.shopping",
            "com.whatsapp"};
    private static UPI upi;
    private UPI() {

    }
    public static UPI getInstance() {
        if (upi == null) {
            upi = new UPI();
        }
        return upi;
    }
    public Uri getPaymentUri(String UPI_ID, String name, String amount, String currency, String note, String transaction, String merchant_code) {
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", UPI_ID) //UPI ID
                .appendQueryParameter("pn", name) //Name
                .appendQueryParameter("am", amount) //Amount
                .appendQueryParameter("cu", currency) //Currency
                .appendQueryParameter("tn", note) //Note
                .appendQueryParameter("mc", merchant_code) //Merchant Code
                .appendQueryParameter("tr", transaction) //Transaction
                .build();
        return uri;
    }
    public Intent getPaymentIntent(String UPI_ID, String name, String amount, String currency, String note, String transaction, String merchant_code, byte app) {
        Uri uri = getPaymentUri(UPI_ID, name, amount, currency, note, transaction, merchant_code);
        Intent upiIntent = new Intent(Intent.ACTION_VIEW);
        upiIntent.setData(uri);
        if (app == ALL_APPS) {
            Intent chooser = Intent.createChooser(upiIntent, "Pay with");
            return chooser;
        }
        else {
            upiIntent.setPackage(APPS[app]);
            return upiIntent;
        }
    }
    public Bitmap getQR(String UPI_ID, String name, String amount, String currency, String note, String transaction, String merchant_code, int pixels) {
        Uri uri = getPaymentUri(UPI_ID, name, amount, currency, note, transaction, merchant_code);
        QRCodeWriter writer = new QRCodeWriter();
        Bitmap bitmap;
        HashMap<EncodeHintType, ErrorCorrectionLevel> hints;
        try {
            hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            BitMatrix bitMatrix = writer.encode(uri.toString(), BarcodeFormat.QR_CODE, pixels, pixels, hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
        }
        catch (Exception e) {
            return null;
        }
        return bitmap;
    }
}
