# upi

This is an Android library that can be used to integrate UPI payments and QR code generation in Android Studio.

**Note: Transactions may not be successful if the receving account is not a business account.**

Setup:

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

 Add the dependency in your module level build.gradle:

	dependencies {
	        implementation 'com.github.SamudraGanguly:upi:1.0'
	}

 How to use:

 In order to create an use the library, first you have to create an object of the UPI class using the static getInstance() method.

 ```
  UPI upi = UPI.getInstance();
 ```

 Now, there are three instance methods of the UPI class.

 1) **getPaymentUri(String UPI_ID, String name, String amount, String currency, String note, String transaction, String merchant_code)**
    
    This method returns the payment Uri.

    Usage:

    ```
    String UPI_ID = "<UPI ID of the payee>";
    String name = "<Name of the payee>";
    String amount = "<Amount to be paid>";
    String currency = "<Currency>";
    String note = "<Any message>";
    String transaction = "<Transaction reference>";
    String merchant_code = "<Merchant code>";
    Uri uri;
    
    UPI upi = upi.getInstance();
    try {
      uri = upi.getPaymentUri(UPI_ID, name, amount, currency, note, transaction, merchant_code);
    }
    catch (Exception e) {
      //Some exception handling code
    }
    ```
 2) **getPaymentIntent(String UPI_ID, String name, String amount, String currency, String note, String transaction, String merchant_code, byte app)**
    
    This method returns the Payment Intent.

    The first 7 parameters are same as the getPaymentUri() method.
    The last parameter takes the app as the parameter. The following values can be passed:

    **UPI.GOOGLE_PAY** - uses the Google Pay app
    
    **UPI.PHONE_PE** - uses the Phone Pe app
    
    **UPI.PAYTM** - uses the PayTM app
    
    **UPI.AMAZON_PAY** - uses the Amazon Pay app
    
    **UPI.WHATSAPP** - uses WhatsApp Payment
    
    **UPI.ALL_APPS** - lets the user choose from the installed apps

    Note: If the specified app is not installed, it will throw an Exception.

    ```
    Intent intent;
    UPI upi = upi.getInstance();
    try {
      intent = upi.getPaymentIntent(UPI_ID, name, amount, currency, note, transaction, merchant_code, UPI.GOOGLE_PAY);
    }
    catch (Exception e) {
      //Some exception handling code
    }
    ```

 4) **getQR(String UPI_ID, String name, String amount, String currency, String note, String transaction, String merchant_code, int pixels)**

    This method returns the QR code as a Bitmap.

    int pixels - The width and height of the required Bitmap in pixels.

    ```
    UPI upi = UPI.getInstance();
    try {
      Bitmap bitmap = upi.getQR(UPI_ID, name, amount, currency, note, transaction, merchant_code, 512);
      imageView.setBitmap(bitmap);
    }
    catch(Exception e) {
      //Some exception handling
    }
    ```
