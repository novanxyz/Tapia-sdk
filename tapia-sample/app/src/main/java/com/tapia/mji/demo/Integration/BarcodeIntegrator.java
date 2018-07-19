package com.tapia.mji.demo.Integration;

import android.app.AlertDialog;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;


public class BarcodeIntegrator {

        public static final int REQUEST_CODE = 0x0ba7c0de; // get it?

        public static final String DEFAULT_TITLE = "Install Barcode Scanner?";
        public static final String DEFAULT_MESSAGE ="This application requires Barcode Scanner. Would you like to install it?";
        public static final String DEFAULT_YES = "Yes";
        public static final String DEFAULT_NO = "No";

        // supported barcode formats
        public static final String PRODUCT_CODE_TYPES = "UPC_A,UPC_E,EAN_8,EAN_13";
        public static final String ONE_D_CODE_TYPES = PRODUCT_CODE_TYPES + ",CODE_39,CODE_128";
        public static final String QR_CODE_TYPES = "QR_CODE";
        public static final String ALL_CODE_TYPES = null;

        public IntentIntegrator() {
        }


        public static void initiateScan(Activity activity) {
            initiateScan(activity, DEFAULT_TITLE, DEFAULT_MESSAGE, DEFAULT_YES, DEFAULT_NO);
        }


        public static void initiateScan(Activity activity,
                                        int stringTitle,
                                        int stringMessage,
                                        int stringButtonYes,
                                        int stringButtonNo) {
            initiateScan(activity,
                    activity.getString(stringTitle),
                    activity.getString(stringMessage),
                    activity.getString(stringButtonYes),
                    activity.getString(stringButtonNo));
        }


        public static void initiateScan(Activity activity,
                                        String stringTitle,
                                        String stringMessage,
                                        String stringButtonYes,
                                        String stringButtonNo) {

            initiateScan(activity,
                    stringTitle,
                    stringMessage,
                    stringButtonYes,
                    stringButtonNo,
                    ALL_CODE_TYPES);
        }


        public static void initiateScan(Activity activity,
                                        String stringTitle,
                                        String stringMessage,
                                        String stringButtonYes,
                                        String stringButtonNo,
                                        String stringDesiredBarcodeFormats) {
            Intent intentScan = new Intent("com.google.zxing.client.android.SCAN");
            intentScan.addCategory(Intent.CATEGORY_DEFAULT);

            // check which types of codes to scan for
            if (stringDesiredBarcodeFormats != null) {
                // set the desired barcode types
                intentScan.putExtra("SCAN_FORMATS", stringDesiredBarcodeFormats);
            }

            try {
                activity.startActivityForResult(intentScan, REQUEST_CODE);
            } catch (ActivityNotFoundException e) {
                return false;
                //showDownloadDialog(activity, stringTitle, stringMessage, stringButtonYes, stringButtonNo);
            }
        }


        public static IntentResult parseActivityResult(int requestCode, int resultCode, Intent intent) {
            if (requestCode == REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    String contents = intent.getStringExtra("SCAN_RESULT");
                    String formatName = intent.getStringExtra("SCAN_RESULT_FORMAT");
                    return new IntentResult(contents, formatName);
                } else {
                    return new IntentResult(null, null);
                }
            }
            return null;
        }


        public static void shareText(Activity activity, String text) {
            shareText(activity, text, DEFAULT_TITLE, DEFAULT_MESSAGE, DEFAULT_YES, DEFAULT_NO);
        }


        public static void shareText(Activity activity,
                                     String text,
                                     int stringTitle,
                                     int stringMessage,
                                     int stringButtonYes,
                                     int stringButtonNo) {
            shareText(activity,
                    text,
                    activity.getString(stringTitle),
                    activity.getString(stringMessage),
                    activity.getString(stringButtonYes),
                    activity.getString(stringButtonNo));
        }


        public static void shareText(Activity activity,
                                     String text,
                                     String stringTitle,
                                     String stringMessage,
                                     String stringButtonYes,
                                     String stringButtonNo) {

            Intent intent = new Intent();
            intent.setAction("com.google.zxing.client.android.ENCODE");
            intent.putExtra("ENCODE_TYPE", "TEXT_TYPE");
            intent.putExtra("ENCODE_DATA", text);
            try {
                activity.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                showDownloadDialog(activity, stringTitle, stringMessage, stringButtonYes, stringButtonNo);
            }
        }


}
